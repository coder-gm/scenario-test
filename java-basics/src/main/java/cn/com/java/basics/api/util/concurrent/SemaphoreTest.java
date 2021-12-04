package cn.com.java.basics.api.util.concurrent;

import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 18:17
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        //创建一个教室
        final ClassRoom classRoom = new ClassRoom();
        //创建听课任务
        ListenRunnable listenRunnable = new ListenRunnable(classRoom);

        for (int i = 0; i < 100; i++) {
            new Thread(listenRunnable, "同学" + i).start();
        }
    }


}


class ListenRunnable implements Runnable {
    final ClassRoom classRoom;

    public ListenRunnable(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public void run() {
        classRoom.listen();
    }
}


class ClassRoom {

    /**
     * 同时只能让2个人听课
     */
    final Semaphore semaphore = new Semaphore(5);

    /**
     * 听课方法
     */
    public void listen() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String name = Thread.currentThread().getName();
        System.out.println(name + "进入教室");

        System.out.println(name + "正在听课");


        //模拟听课
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + "离开教室");

        //听完课之后释放许可
        semaphore.release();

    }

}