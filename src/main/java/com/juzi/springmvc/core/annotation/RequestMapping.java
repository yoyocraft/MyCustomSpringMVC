package com.juzi.springmvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义RequestMapping注解
 *
 * @author codejuzi
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 访问路径
     *
     * @return path
     */
    String value() default "";
}
