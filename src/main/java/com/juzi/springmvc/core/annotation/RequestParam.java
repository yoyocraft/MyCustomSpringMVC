package com.juzi.springmvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义@RequestParam注解
 *
 * @author codejuzi
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    /**
     * 与请求参数对应的参数
     *
     * @return value
     */
    String value() default "";
}
