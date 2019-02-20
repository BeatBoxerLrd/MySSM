package com.derry.test.controller;

import com.derry.annotation.Controller;
import com.derry.annotation.RequestMapping;
import com.derry.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 18:12 2019/2/18
 * @Modified By:
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String Name){
        System.out.println(Name+"登录成功！！！");
        try {
            response.setHeader("Content-Type","text/html;charset=UTF-8");
            // 设置response的缓冲区的编码.
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("method login success!!!"+"        s"+"用户为"+Name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/quit")
    public void quit(HttpServletRequest request,HttpServletResponse response,@RequestParam("name") String Name){
        System.out.println(Name+"用户已退出!!!");
        try {
            response.setHeader("Content-Type","text/html;charset=UTF-8");
            // 设置response的缓冲区的编码.
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("method quit success!!!"+"         "+"用户名"+Name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
