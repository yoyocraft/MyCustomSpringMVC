package com.juzi.springmvc.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 使用dom4j解析xml
 * XML文件解析器
 *
 * @author codejuzi
 */
public class XmlParserUtil {
    /**
     * 指定扫描包的xml标签
     */
    public static final String COMPONENT_SCAN = "component-scan";

    /**
     * 指定扫描包的xml标签属性
     */
    public static final String BASE_PACKAGE = "base-package";

    /**
     * 得到配置文件中指定要扫描的包
     *
     * @param xmlFile 配置文件名（默认在classpath:）
     * @return 配置要扫描的包路径
     */
    public static String getBasePackage(String xmlFile) {

        String basePackage = null;
        try {
            SAXReader saxReader = new SAXReader();
            // 通过类加载器得到输入流
            InputStream inputStream = XmlParserUtil.class.getClassLoader().getResourceAsStream(xmlFile);
            // dom4j解析文件
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            Element element = rootElement.element(COMPONENT_SCAN);
            Attribute attribute = element.attribute(BASE_PACKAGE);
            // 得到扫描包路径
            basePackage = attribute.getText();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return basePackage;
    }
}
