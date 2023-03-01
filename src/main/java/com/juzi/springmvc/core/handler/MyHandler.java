package com.juzi.springmvc.core.handler;

import java.lang.reflect.Method;

/**
 * 记录url和控制器之间的关系
 *
 * @author codejuzi
 */
public class MyHandler {
    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求的控制器
     */
    private Object controller;

    /**
     * 请求的控制器方法
     */
    private Method method;


    public MyHandler() {
    }

    public MyHandler(String url, Object controller, Method method) {
        this.url = url;
        this.controller = controller;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "MyHandler{" +
                "url='" + url + '\'' +
                ", controller=" + controller +
                ", method=" + method +
                '}';
    }
}
