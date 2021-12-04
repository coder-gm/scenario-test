package cn.com.java.basics.api.util.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试ArrayList线程不安全问题
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 14:41
 */
public class ArrayListThreadNotSafeTest {
    public static void main(String[] args) {
        ArrayListHolder arrayListHolder = new ArrayListHolder();
        arrayListHolder.start();

        //在主线程中往list中添加20万个元素
        for (int i = 0; i < 20_0000; i++) {
            ArrayListHolder.list.add(i);
        }

        try {
            //在主线程睡眠200毫秒，确保40万数据已经被添加
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 期望40万
        // 实际小于40万
        // 有可能发生异常（没测试过）
        System.out.println(Thread.currentThread().getName() + "List 集合数量是：" + ArrayListHolder.list.size());
    }
}


class ArrayListHolder extends Thread {
    // 共享变量
    // 会在这个list中添加40万条数据
    static List<Integer> list = new ArrayList<>();


    @Override
    public void run() {
        for (int i = 0; i < 20_0000; i++) {
            list.add(i);
        }

        System.out.println(Thread.currentThread().getName() + "List 集合数量是：" + list.size());
    }


}