package com.derry.proxy.impl;

import com.derry.proxy.Book;

/**
 * @Author:LiuRuidong
 * @Description:
    静态代理：
    代理类和被代理类实现同一接口，在编译之后，运行之前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了
    缺点：如果接口增加一个方法，业务类实现之外，所有的代理类也需要实现此方法
 * @Date: Created in 15:29 2019/2/19
 * @Modified By:
 */
public class BookProxy implements Book {
    private BookImpl bookImpl;

    public BookProxy(BookImpl bookImpl) {
        this.bookImpl = bookImpl;
    }

    @Override
    public void addBook() {
        System.out.println("代理过程开始-------------");
        bookImpl.addBook();
        System.out.println("代理过程结束-------------");

    }
}
