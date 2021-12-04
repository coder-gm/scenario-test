package cn.com.java.basics.api.lang;

/**
 * 同步代码块和同步方法的综合使用
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 8:45
 */
public class SynchronizedBlockedMethodTest {


    public static void main(String[] args) {

        ShoppingThreadB shoppingThreadB = new ShoppingThreadB();
        shoppingThreadB.setName("B线程");
        shoppingThreadB.start();


        ShoppingThreadA shoppingThreadA = new ShoppingThreadA(ShoppingThreadB.class);
        shoppingThreadA.setName("A线程");
        shoppingThreadA.start();
    }

}


/**
 * A线程
 */
class ShoppingThreadA extends Thread {
    private final Object lock;

    public ShoppingThreadA(Object obj) {
        this.lock = obj;
    }

    @Override
    public void run() {
        synchronized (lock) {
            String name = Thread.currentThread().getName();
            System.out.println(name + "1.注册京东成功");
            System.out.println(name + "2.登录京东成功");
            System.out.println(name + "3.浏览商品");
            System.out.println(name + "4.添加商品到购物车");
            System.out.println(name + "5.支付订单成功");
            System.out.println(name + "6.提交订单成功");
        }
    }

}


/**
 * B线程
 */
class ShoppingThreadB extends Thread {

    @Override
    public void run() {
        doShopping();
    }

    public static synchronized void doShopping() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "1.注册京东成功");
        System.out.println(name + "2.登录京东成功");
        System.out.println(name + "3.浏览商品");
        System.out.println(name + "4.添加商品到购物车");
        System.out.println(name + "5.支付订单成功");
        System.out.println(name + "6.提交订单成功");
    }

}