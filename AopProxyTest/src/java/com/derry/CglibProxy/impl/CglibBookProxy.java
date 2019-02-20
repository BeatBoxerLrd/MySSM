package com.derry.CglibProxy.impl;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author:LiuRuidong
 * @Description:
 * 解决了jdk动态代理必须提供接口的这个缺陷，但是对final修饰的类无法代理
 * cglib是针对类来实现代理的，原理是对指定的业务类生成一个子类，并覆盖其中业务方法实现代理。
 * 因为采用的是继承，所以不能对final修饰的类进行代理。
 * @Date: Created in 17:33 2019/2/19
 * @Modified By:
 */
public class CglibBookProxy implements MethodInterceptor {
    //业务类对象，供代理方法中进行真正的业务方法调用
    private Object target;

    //创建代理类对象
    public Object getInstance(Object target){
        this.target = target;
        //创建增强器，用来创建动态代理类
        Enhancer enhancer  = new Enhancer();
        //为加强器指定要代理的业务类，即为代理类指定父类
        enhancer.setSuperclass(this.target.getClass());
        //设置回调：用于代理类上所有方法的调用，都会调用callback
        enhancer.setCallback(this);
        //创建并返回动态代理类对象并返回
        return enhancer.create();
    }

    //回调方法的调用
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用开始-----");
        methodProxy.invokeSuper(o,objects);
        System.out.println("调用结束-----");
        return null;
    }
}
