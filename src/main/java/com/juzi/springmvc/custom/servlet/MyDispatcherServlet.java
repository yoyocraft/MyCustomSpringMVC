package com.juzi.springmvc.custom.servlet;

import com.juzi.springmvc.custom.context.MyWebApplicationContext;
import com.juzi.springmvc.custom.handler.MyHandler;
import com.juzi.springmvc.custom.mapping.MyHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 自定义SpringMVC的分发控制器 DispatcherServlet
 * 本质上是一个Servlet
 *
 * @author codejuzi
 */
public class MyDispatcherServlet extends HttpServlet {

    /**
     * Handler Mapping
     */
    private MyHandlerMapping myHandlerMapping;

    /**
     * xml配置文件初始化参数配置
     */
    private static final String INIT_PARAM_CONTEXT_CONFIG_LOCATION = "contextConfigLocation";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String projectPath = getServletContext().getContextPath();
        String contextConfigLocation = config.getInitParameter(INIT_PARAM_CONTEXT_CONFIG_LOCATION);
        MyWebApplicationContext myWebApplicationContext = new MyWebApplicationContext(contextConfigLocation);
        myWebApplicationContext.init();
        myHandlerMapping = new MyHandlerMapping(myWebApplicationContext, projectPath);
        myHandlerMapping.initHandlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeDispatcher(req, resp);
    }

    /**
     * 分发请求
     *
     * @param req  request
     * @param resp response
     */
    private void executeDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        MyHandler handler = myHandlerMapping.getHandlerByRequest(req);
        try {
            if (handler == null) {
                resp.getWriter().print("<h1>404 NOT FOUND</h1>");
            } else {
                // 有匹配的handler
                handler.getMethod().invoke(handler.getController(), req, resp);
            }
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
