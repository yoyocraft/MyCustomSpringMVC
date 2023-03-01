package com.juzi.springmvc.core.servlet;

import com.juzi.springmvc.core.annotation.RequestParam;
import com.juzi.springmvc.core.context.MyWebApplicationContext;
import com.juzi.springmvc.core.handler.MyHandler;
import com.juzi.springmvc.core.mapping.MyHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                // 生成参数数组，传入invoke
                Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
                // 定义请求的参数集合
                Object[] reqParam = new Object[parameterTypes.length];

                // 遍历parameterTypes, 先处理req和resp两个参数
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    // 根据类型来匹配请求参数，此处需要注意请求参数的位置
                    if (parameterType.isAssignableFrom(HttpServletRequest.class)) {
                        reqParam[i] = req;
                    } else if (parameterType.isAssignableFrom(HttpServletResponse.class)) {
                        reqParam[i] = resp;
                    }
                }
                // 再处理请求参数中携带的参数
                Map<String, String[]> parameterMap = req.getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String paramKey = entry.getKey();
                    // 此处简化操作，只考虑一个参数值，不考虑checkbox等的情况
                    String paramValue = entry.getValue()[0];
                    int reqParamIndex = getReqParamIdxByParamKey(handler.getMethod(), paramKey);
                    reqParam[reqParamIndex] = paramValue;
                }


                // 传入参数数组，动态获取
                handler.getMethod().invoke(handler.getController(), reqParam);
            }
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ParamKey获取请求参数再请求参数数组中的位置
     *
     * @param method   请求方法
     * @param paramKey 请求参数key
     * @return parameter key
     */
    private int getReqParamIdxByParamKey(Method method, String paramKey) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(RequestParam.class)) {
                // 参数带有@RequestParam修饰
                return getIdxWithRequestParam(parameter, paramKey, i);
            } else {
                // 参数不带@RequesParam修饰
                // 获取所有参数名
                List<String> paramNameList = getParamNameList(method);
                for (int j = 0; j < paramNameList.size(); j++) {
                    if(paramKey.equals(paramNameList.get(j))) {
                        return j;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 获取方法所有的参数列表
     * <b>
     *     注意：
     *         此处需要使用Jdk8的新特性， 并需要在pom.xml中配置maven的编译插件
     * </b>
     * @param method 方法
     * @return 参数集合
     */
    private List<String> getParamNameList(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> paramNameList = new ArrayList<>(parameters.length);
        for (Parameter parameter : parameters) {
            paramNameList.add(parameter.getName());
        }
        return paramNameList;
    }

    /**
     * 处理带有@RequestParam注解修饰的请求参数
     *
     * @param parameter 形参
     * @param paramKey  请求参数Key
     * @param idx       形参所在位置
     * @return 请求参数在请求参数数组中的位置 || -1
     */
    private int getIdxWithRequestParam(Parameter parameter, String paramKey, int idx) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String reqParamKey = requestParam.value();
        if (paramKey.equals(reqParamKey)) {
            return idx;
        }
        return -1;
    }
}
