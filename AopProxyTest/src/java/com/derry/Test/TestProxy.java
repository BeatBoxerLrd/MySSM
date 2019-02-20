package com.derry.Test;

import com.derry.proxy.Book;
import com.derry.proxy.impl.BookImpl;
import com.derry.proxy.impl.BookProxy;

/**
 * @Author:LiuRuidong
 * @Description:
 * 定义业务代理类：通过组合，在代理类中创建一个业务实现类对象来调用具体的业务方法；
 * 通过实现业务逻辑接口，来统一业务方法；
 * 在代理类中实现业务逻辑接口中的方法时，进行预处理操作、通过业务实现类对象调用真正的业务方法、进行调用后操作的定义。
 * @Date: Created in 15:32 2019/2/19
 * @Modified By:
 */
public class TestProxy {
    public static void main(String[] args) {
        Book bookProxy = new BookProxy(new BookImpl());
        bookProxy.addBook();
    }
}
