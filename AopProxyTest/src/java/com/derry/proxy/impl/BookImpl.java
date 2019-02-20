package com.derry.proxy.impl;

import com.derry.proxy.Book;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 15:26 2019/2/19
 * @Modified By:
 */
public class BookImpl implements Book {
    @Override
    public void addBook() {
        System.out.println("新增图书方法！");
    }
}
