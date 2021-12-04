package cn.com.java.basics.api.util.collection;

import javax.swing.text.Segment;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

/**
 * HashSet线程不安全示例
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 15:01
 */
public class HashMapThreadNotSafeTest {


    public static void main(String[] args) {
        HashMapHolder hashMapHolder = new HashMapHolder();
        hashMapHolder.setName("子线程");
        hashMapHolder.start();


        //在主线程中往list中添加10万个元素
        for (int i = 0; i < 10_0000; i++) {
            HashMapHolder.map.put(i, i);
        }


        // 期望10万
        // 实际大于10万或者小于10万
        System.out.println(Thread.currentThread().getName() + "HashMap 集合数量是：" + HashMapHolder.map.size());


    }

}


class HashMapHolder extends Thread {


    static Map<Integer, Integer> map = new ConcurrentHashMap<>(131072);


    @Override
    public void run() {
        for (int i = 0; i < 10_0000; i++) {
            map.put(i, i);
        }
        System.out.println(Thread.currentThread().getName() + " HashMap元素的数量：" + map.size());
    }


}