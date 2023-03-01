package com.juzi.springmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义SpringMVC的分发控制器 DispatcherServlet
 * 本质上是一个Servlet
 *
 * @author codejuzi
 */
public class MyDispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MyDispatcherServlet--doGet()--");
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MyDispatcherServlet--doPost()--");
        super.doPost(req, resp);
    }
}
