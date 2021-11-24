package cn.com.common.utils.http;

import cn.com.common.utils.nulls.ObjectUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @Description
 * @Author guangming
 * @Date 2021/1/15 11:51
 */
@Slf4j
public final class RestTemplateUtils {

    private static RestTemplate restTemplate = new RestTemplate();

    private HttpEntity httpEntity;

    /**
     * 构造函数已经私有化，只能通过create方法构造HttpContent
     *
     * @return HttpContent
     */
    public static RestTemplateUtils create() {
        return new RestTemplateUtils();
    }

    /**
     * get请求返回模板对象
     *
     * @param url   url
     * @param clazz T.class
     * @param <T>   T
     * @return T
     */
    public static <T> T getForObj(String url, Class<T> clazz) {
        return RestTemplateUtils.restTemplate.getForObject(url, clazz);
    }


    /**
     * get请求返回模板对象
     * <p>
     * 发送带headers的GET请求
     *
     * @param url   url
     * @param clazz T.class
     * @param <T>   T
     * @return T
     */
    public <T> T exchange(String url, Class<T> clazz) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), clazz).getBody();
    }

    /**
     * post请求返回模板对象
     *
     * @param url   url
     * @param clazz t.class
     * @param <T>   模板
     * @return T
     */
    public <T> T postForObj(String url, Class<T> clazz) {
        this.build();
        return restTemplate.postForObject(url, this.httpEntity, clazz);
    }

    /**
     * header
     */
    private HttpHeaders httpHeaders;

    private String contentType;

    /**
     * body
     */
    private HashMap<String, Object> params;

    private String paramBody;


    private RestTemplateUtils() {
    }

    /**
     * 添加Header内容
     *
     * @param headerName  key
     * @param headerValue value
     * @return HttpContent
     */
    public RestTemplateUtils addHeader(String headerName, String headerValue) {
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.add(headerName, headerValue);
        return this;
    }

    /**
     * 设置请求体
     *
     * @param key   key
     * @param value value
     * @return HttpContent
     */
    public RestTemplateUtils addBody(String key, Object value) {
        if (Objects.isNull(params)) {
            params = new HashMap<>(1);
        }
        params.put(key, value);
        return this;
    }


    /**
     * 设置请求体
     *
     * @param mapList
     * @return
     */
    public RestTemplateUtils addBody(List<Map<String, Object>> mapList) {
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                JSONObject jsonObject = new JSONObject();
                for (String s : map.keySet()) {
                    try {
                        jsonObject.put(s, map.get(s));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray.add(jsonObject);
            }
        }
        paramBody = jsonArray.toString();//StringEscapeUtils.unescapeJava( );
        return this;
    }


    /**
     * 设置content-Type
     *
     * @param contentType string
     * @return HttpContent
     */
    public RestTemplateUtils setContentType(String contentType) {
        if (Strings.isNullOrEmpty(contentType)) {
            throw new RuntimeException("内容类型不能为空，请检查您的媒体类型并重试 ~");
        }
        this.contentType = contentType;
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        return this;
    }

    /**
     * 构建HttpEntity对象
     * 不设置Content-Type默认application/json
     */
    private void build() {
        if (Objects.isNull(contentType) || ContentType.APPLICATION_JSON.getMimeType().equals(contentType)) {
            this.setContentType(ContentType.APPLICATION_JSON.getMimeType());

            if (ObjectUtils.isEmpty(this.paramBody)) {
                if (Objects.isNull(this.params)) {
                    throw new RuntimeException("正文必须不是空的，请添加正文并重试 ~");
                }

                JSONObject jsonObject = new JSONObject();
                for (String s : params.keySet()) {
                    try {
                        jsonObject.put(s, params.get(s));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                this.paramBody = jsonObject.toString();
            }

            log.info("请求入参:{}", this.paramBody);

            this.httpEntity = new HttpEntity<>(paramBody, httpHeaders);
        } else if (ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equals(contentType)) {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            for (String s : params.keySet()) {
                formData.add(s, params.get(s));
            }
            this.httpEntity = new HttpEntity<>(formData, httpHeaders);
        } else {
            throw new RuntimeException("对不起，这个工具只支持内容类型包括 {application/json,multipart/form-data}");
        }
    }





}