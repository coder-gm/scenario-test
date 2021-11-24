package cn.com.common.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zgming
 * @Param: null
 * @Return
 * @Throws:
 **/
@Slf4j
public class JwtUtils {



    /**
     * 设置token过期时间7天
     * <p>
     * 原因: 登录成功后设置token过期时间,然后把token交给redis来管理,因为redis可以增删改查
     **/
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * 设置token私钥
     **/
    private static final String SECRET_KEY = "smart_office_secret_key";

    /**
     * 生成签名
     *
     * @Author: zgming
     * @Param1: username
     * @Param2: password
     * @Return java.lang.String
     * @Throws:
     **/
    public static String sign(String username, String password) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.info("检验token是否失败:{}", e);
            return false;
        }
    }

    /**
     * 从token中获取username信息,无需解密
     *
     * @param **token**
     * @return
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            if (System.currentTimeMillis() - jwt.getExpiresAt().getTime() > 0) {
                return null;
            }
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }
}