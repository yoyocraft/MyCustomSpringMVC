package com.juzi.springmvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义@ResponseBody注解
 *
 * @author codejuzi
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {
}
