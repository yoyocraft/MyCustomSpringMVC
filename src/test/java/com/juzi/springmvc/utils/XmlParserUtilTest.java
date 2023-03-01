package com.juzi.springmvc.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Xml parser test
 * @author codejuzi
 */
public class XmlParserUtilTest {

    @Test
    public void getBasePackage() {
        String basePackage = XmlParserUtil.getBasePackage("myspringmvc.xml");
        System.out.println("basePackage = " + basePackage);
    }
}