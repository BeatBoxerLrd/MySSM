package com.derry.JdkProxy.impl;

import com.derry.JdkProxy.Book;

/**
 * @Author:LiuRuidong
 * @Description: 业务的真实实现类
 * @Date: Created in 16:05 2019/2/19
 * @Modified By:
 */
public class BookImpl implements Book {
    @Override
    public void addBook() {
        System.out.println("新增图书的方法！");
    }
}
