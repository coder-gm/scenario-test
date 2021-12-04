package cn.com.java.basics.api.util.collection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 并发集合的使用
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 14:52
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayListHolder arrayListHolder = new CopyOnWriteArrayListHolder();
        arrayListHolder.start();

        //在主线程中往list中添加20万个元素
        for (int i = 0; i < 10_0000; i++) {
            CopyOnWriteArrayListHolder.list.add(i);
        }

        try {
            //在主线程睡眠200毫秒，确保40万数据已经被添加
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 期望40万
        // 实际小于40万
        // 有可能发生异常（没测试过）
        System.out.println(Thread.currentThread().getName() + "List 集合数量是：" + CopyOnWriteArrayListHolder.list.size());
    }
}




class CopyOnWriteArrayListHolder extends Thread {
    // 共享变量
    // 会在这个list中添加40万条数据
    static List<Integer> list = new CopyOnWriteArrayList<>();


    @Override
    public void run() {
        for (int i = 0; i < 10_0000; i++) {
            list.add(i);
        }

        System.out.println(Thread.currentThread().getName() + "List 集合数量是：" + list.size());
    }


}