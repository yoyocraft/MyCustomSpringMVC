package com.juzi.springmvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义Controller注解
 *
 * @author codejuzi
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

    /**
     * 开发者自定义的beanName
     *
     * @return beanName
     */
    String value() default "";
}
