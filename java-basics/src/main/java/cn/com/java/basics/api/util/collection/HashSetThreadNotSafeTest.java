package cn.com.java.basics.api.util.collection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * HashSet线程不安全示例
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 15:01
 */
public class HashSetThreadNotSafeTest {


    public static void main(String[] args) {
        HashSetHolder hashSetHolder = new HashSetHolder();
        hashSetHolder.setName("子线程");
        hashSetHolder.start();


        //在主线程中往list中添加10万个元素
        for (int i = 0; i < 10_0000; i++) {
            HashSetHolder.hashSet.add(i);
        }


        // 期望10万 (因为HashSet是会去掉重复值的)
        // 实际大于10万或者小于10万
        System.out.println(Thread.currentThread().getName() + "HashSet 集合数量是：" + HashSetHolder.hashSet.size());



    }

}


class HashSetHolder extends Thread {


    static Set<Integer> hashSet = new CopyOnWriteArraySet<>();


    @Override
    public void run() {
        for (int i = 0; i < 10_0000; i++) {
            hashSet.add(i);
        }
        System.out.println(Thread.currentThread().getName() + " HashSet元素的数量：" + hashSet.size());
    }


}