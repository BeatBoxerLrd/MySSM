package com.derry.Test;

import com.derry.JdkProxy.Book;
import com.derry.JdkProxy.impl.BookImpl;
import com.derry.JdkProxy.impl.JdkBookProxy;

/**
 * @Author:LiuRuidong
 * @Description:
 * 在使用时，首先创建一个业务实现类对象和一个代理类对象，
 * 然后定义接口引用（这里使用向上转型）并用代理对象.bind(业务实现类对象)的返回值进行赋值。
 * 最后通过接口引用调用业务方法即可。
 * （接口引用真正指向的是一个绑定了业务类的代理类对象，所以通过接口方法名调用的是被代理的方法们）
 * @Date: Created in 16:23 2019/2/19
 * @Modified By:
 */
public class TestJdkProxy  {
    public static void main(String[] args) {
        BookImpl bookImpl = new BookImpl();
        JdkBookProxy proxy = new JdkBookProxy();
        Book book = (Book) proxy.bind(bookImpl);
        book.addBook();
    }
}
