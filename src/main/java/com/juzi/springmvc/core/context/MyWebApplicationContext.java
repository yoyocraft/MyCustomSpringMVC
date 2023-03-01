package com.juzi.springmvc.core.context;

import com.juzi.springmvc.core.annotation.Controller;
import com.juzi.springmvc.core.annotation.Service;
import com.juzi.springmvc.utils.XmlParserUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义Web Application Context
 *
 * @author codejuzi
 */
public class MyWebApplicationContext {

    /**
     * 单例池，存放Bean实例
     */
    private final ConcurrentHashMap<String, Object> singletonObjects;

    /**
     * 类全路径集合
     */
    private final List<String> classFullPathList;

    /**
     * 容器配置文件
     */
    private String contextConfigLocation;


    {
        classFullPathList = new ArrayList<>();
        singletonObjects = new ConcurrentHashMap<>();
    }

    public MyWebApplicationContext() {
    }

    public MyWebApplicationContext(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    public ConcurrentHashMap<String, Object> getSingletonObjects() {
        return singletonObjects;
    }

    /**
     * 初始化Spring容器，把@Controller修饰的类初始化到容器中
     */
    public void init() {
        String xmlFile = contextConfigLocation.split(":")[1];
        String basePackage = XmlParserUtil.getBasePackage(xmlFile);
        // 按照 , 来分割多个扫描包
        String[] splitBasePackages = basePackage.split(",");
        for (String splitBasePackage : splitBasePackages) {
            // 扫描各个包
            scanPackage(splitBasePackage.trim());
        }
        System.out.println("after scanning, classFullPathList = " + classFullPathList);
        // load bean
        loadBeanToContainer();
        System.out.println("after loading, singletonObjects = " + singletonObjects);
    }

    /**
     * 扫描包，得到类的全路径
     *
     * @param basePackage 带扫描的包名
     */
    public void scanPackage(String basePackage) {
        // 得到URL
        String packName = basePackage.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource(packName);

        // 取出路径
        assert url != null;
        String path = url.getFile();
        File dir = new File(path);
        // 扫描dir以及其子目录
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                // 是目录，递归扫描其子目录
                scanPackage(basePackage + "." + file.getName());
            } else {
                // 是文件，过滤出要处理的.class文件
                // 得到类的全路径，加入list集合
                String classFullPath = basePackage + "." + file.getName().replaceAll("\\.class", "");
                classFullPathList.add(classFullPath);
            }
        }
    }

    /**
     * 实例化扫描到的类，将需要的加载的SpringBean初始化并放入容器中
     */
    public void loadBeanToContainer() {
        if(classFullPathList.isEmpty()) {
            // 没有要初始化的类
            return;
        }
        try {
            for (String classFullPath : classFullPathList) {
                Class<?> clazz = Class.forName(classFullPath);
                // 判断是否有@Controller注解修饰
                if(clazz.isAnnotationPresent(Controller.class)) {
                    // 得到注解
                    Controller controller = clazz.getDeclaredAnnotation(Controller.class);
                    String beanName = controller.value();
                    if("".equals(beanName)) {
                        // 未指定beanName
                        // 得到该类的类名
                        String className = clazz.getSimpleName();
                        // 首字母小写作为beanName
                        beanName = StringUtils.uncapitalize(className);
                    }
                    singletonObjects.put(beanName, clazz.newInstance());
                }
                // 判断是否有@Service注解修饰
                else if(clazz.isAnnotationPresent(Service.class)) {
                    // 得到注解
                    Service service = clazz.getDeclaredAnnotation(Service.class);
                    String beanName = service.value();
                    Object bean = clazz.newInstance();
                    if("".equals(beanName)) {
                        // 没有指定beanName，得到该Service的所有接口名 => 通过接口来注入Service实例
                        Class<?>[] clazzInterfaces = clazz.getInterfaces();
                        for (Class<?> clazzInterface : clazzInterfaces) {
                            // 接口名首字母小写作为BeanName存入容器
                            String clazzInterfaceName = clazzInterface.getSimpleName();
                            String clazzInterfaceBeanName = StringUtils.uncapitalize(clazzInterfaceName);
                            singletonObjects.put(clazzInterfaceBeanName, bean);
                        }
                        // 类名首字母小写作为BeanName存入容器
                        String clazzName = clazz.getSimpleName();
                        String clazzBeanName = StringUtils.uncapitalize(clazzName);
                        singletonObjects.put(clazzBeanName, bean);
                    } else {
                        // 指定了beanName
                        singletonObjects.put(beanName, bean);
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
