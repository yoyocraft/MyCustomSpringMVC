package com.juzi.springmvc.core.mapping;

import com.juzi.springmvc.core.annotation.Controller;
import com.juzi.springmvc.core.annotation.RequestMapping;
import com.juzi.springmvc.core.context.MyWebApplicationContext;
import com.juzi.springmvc.core.handler.MyHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 完成映射处理， 将映射关系保存到集合中
 *
 * @author codejuzi
 */
public class MyHandlerMapping {
    // TODO: 2023/3/1 后续改成Map存储 key->path, val->map<controller, method>
    /**
     * 存放url 和 控制器，方法的映射关系
     */
    private final List<MyHandler> handlerList;

    /**
     * 容器对象
     */
    private final MyWebApplicationContext myWebApplicationContext;

    /**
     * 工程路径
     */
    private final String PROJECT_PATH;

    public MyHandlerMapping(MyWebApplicationContext myWebApplicationContext, String projectPath) {
        handlerList = new ArrayList<>();
        this.myWebApplicationContext = myWebApplicationContext;
        PROJECT_PATH = projectPath;
    }


    /**
     * 初始化映射关系，封装成MyHandler对象存入集合
     */
    public void initHandlerMapping() {
        ConcurrentHashMap<String, Object> singletonObjects = myWebApplicationContext.getSingletonObjects();
        if (singletonObjects.isEmpty()) {
            throw new RuntimeException("Spring Singleton Objects is empty");
        }
        for (Map.Entry<String, Object> entry : singletonObjects.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                // 得到控制器的所有方法
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    // 判断方法是否有@RequestMapping注解修饰
                    if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                        // 得到注解并获取路径
                        RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(RequestMapping.class);
                        String uri = requestMapping.value();
                        // 拼接上工程路径
                        String url = PROJECT_PATH + uri;
                        handlerList.add(new MyHandler(url, entry.getValue(), declaredMethod));
                    }
                }
            }
        }
    }

    /**
     * 根据Request Url 获取对应的Handler
     *
     * @param request request请求
     * @return handler
     */
    public MyHandler getHandlerByRequest(HttpServletRequest request) {
        // 获取URL
        String requestUri = request.getRequestURI();
        for (MyHandler myHandler : handlerList) {
            if (requestUri.equals(myHandler.getUrl())) {
                return myHandler;
            }
        }
        return null;
    }

}
