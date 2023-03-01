package com.juzi.springmvc.core.context;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author codejuzi
 */
public class MyWebApplicationContextTest {

    @Test
    public void init() {
        MyWebApplicationContext myWebApplicationContext = new MyWebApplicationContext("classpath:myspringmvc.xml");
        myWebApplicationContext.init();
        System.out.println("ok");
    }
}