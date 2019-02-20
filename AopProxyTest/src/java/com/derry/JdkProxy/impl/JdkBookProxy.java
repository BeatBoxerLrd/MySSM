package com.derry.JdkProxy.impl;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author:LiuRuidong
 * @Description: jdl动态代理
 *  JDK动态代理所用到的代理类在程序调用到代理类对象时才由JVM真正创建，
 *  JVM根据传进来的业务实现类对象以及方法名 ，
 *  动态地创建了一个代理类的class文件并被字节码引擎执行，然后通过该代理类对象进行方法调用。
 *  优点：解决了静态代理中，代理必须要实现接口中的方法
 *  缺点：需要提供接口
 * @Date: Created in 16:07 2019/2/19
 * @Modified By:
 */
public class JdkBookProxy implements InvocationHandler {
    //用于表示业务实现类对象，用来调用真正的实现方法
    private Object target;
    //绑定业务对象，并返回一个代理类
    public Object bind(Object target){
        this.target = target;
        //通过反射机制，创建一个代理类对象实例并返回。用户进行方法调用时使用
        /**三个参数，详细说明
          * 1.一个ClassLoader loader,定义了他由那个classLoader对象生成代理类进行加载
          * 2.一个Class<?>[] interfaces,定义了代理对象所提供的接口
          * 3.一个InvocationHandler h，表示当动态代理对象调用方法的时候会关联到哪一个InvocationHandler对象
          */
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(),this);
    }



    //返回的代理类调用方法时，会执行到这里，最终通过反射完成调用
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("调用开始--------");
        //通过反射调用真正的业务实现方法
        result = method.invoke(target,args);
        System.out.println("调用完成----------");
        return result;
    }
}
