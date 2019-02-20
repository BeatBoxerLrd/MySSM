package com.derry.Test;

import com.derry.CglibProxy.impl.BookImpl;
import com.derry.CglibProxy.impl.CglibBookProxy;

/**
 * @Author:LiuRuidong
 * @Description:
 * 创建业务类和代理类对象，然后通过代理类对象.getInstance(业务类对象)
 * 返回一个动态代理类对象（它是业务类的子类，可以用业务类引用指向它）。
 * 最后通过动态代理类对象进行方法调用。
 * @Date: Created in 18:06 2019/2/19
 * @Modified By:
 */
public class TestCglibProxy {
    public static void main(String[] args) {
        BookImpl bookImpl = new BookImpl();
        CglibBookProxy cglibBookProxy = new CglibBookProxy();
        BookImpl book = (BookImpl) cglibBookProxy.getInstance(bookImpl);
        book.addBook();
    }
}