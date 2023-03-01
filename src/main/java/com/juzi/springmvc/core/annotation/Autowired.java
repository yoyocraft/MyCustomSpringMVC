package com.juzi.springmvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义Autowired注解
 *
 * @author codejuzi
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    /**
     * 开发者自定义的BeanName
     *
     * @return beanName
     */
    String value() default "";
}
