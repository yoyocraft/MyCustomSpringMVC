package com.juzi.springmvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义Service注解，表示Service层
 *
 * @author codejuzi
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    /**
     * 开发者自定义的beanName
     *
     * @return beanName
     */
    String value() default "";
}
