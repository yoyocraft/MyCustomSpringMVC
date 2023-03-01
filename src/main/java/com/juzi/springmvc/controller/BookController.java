package com.juzi.springmvc.controller;

import com.juzi.springmvc.custom.annotation.Controller;
import com.juzi.springmvc.custom.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 测试控制器
 *
 * @author codejuzi
 */
@Controller
public class BookController {

    /**
     * 测试方法
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/book/list")
    public void listBooks(HttpServletRequest request, HttpServletResponse response)  {
        response.setContentType("text/html; charset=utf-8");

        try {
            PrintWriter writer = response.getWriter();
            writer.write("<h1>Book List</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
