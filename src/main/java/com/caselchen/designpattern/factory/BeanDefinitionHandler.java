package com.caselchen.designpattern.factory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanDefinitionHandler extends DefaultHandler {

    private List<BeanDefinition> beanDefinitions;
    private BeanDefinition beanDefinition;
    private BeanDefinition.ConstructorArg constructorArg;
    private String tag; // 存储操作标签

    private static Map<String, Class> typeMap = new HashMap<String, Class>();

    static {
        typeMap.put("string", String.class);
        typeMap.put("int", Integer.class);
    }

    /**
     * @author lastwhisper
     * @desc 文档解析开始时调用，该方法只会调用一次
     * @param
     * @return void
     */
    @Override
    public void startDocument() throws SAXException {
        beanDefinitions = new ArrayList<BeanDefinition>();
    }

    /**
     * @author lastwhisper
     * @desc 标签（节点）解析开始时调用
     * @param uri xml文档的命名空间
     * @param localName 标签的名字
     * @param qName 带命名空间的标签的名字
     * @param attributes 标签的属性集
     * @return void
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tag = qName;
        if ("bean".equals(tag)) {
            beanDefinition = new BeanDefinition();
            String id = attributes.getValue("id");
            if (id != null && !id.isEmpty()) {
                beanDefinition.setId(id);
            }
            String className = attributes.getValue("class");
            if (className != null && !className.isEmpty()) {
                beanDefinition.setClassName(className);
            }
        }

        if ("constructor-arg".equals(tag)) {
            constructorArg = new BeanDefinition.ConstructorArg();
            String typeName = attributes.getValue("type");
            if (typeName != null && !typeName.isEmpty()) {
                constructorArg.setType(typeMap.get(typeName));
            }
            Object arg = attributes.getValue("value");
            if (arg != null) {
                constructorArg.setArg(typeName.equals("int") ? Integer.valueOf(String.valueOf(arg)) : arg);
            }
            String ref = attributes.getValue("ref");
            if (ref != null && !ref.isEmpty()) {
                constructorArg.setIsRef(true);
                constructorArg.setArg(ref);
            }
        }
    }

    /**
     * @author lastwhisper
     * @desc 解析标签的内容的时候调用
     * @param ch  字符
     * @param start 字符数组中的起始位置
     * @param length 要从字符数组使用的字符数
     * @return void
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    /**
     * @author lastwhisper
     * @desc 标签（节点）解析结束后调用
     * @param uri xml文档的命名空间
     * @param localName 标签的名字
     * @param qName 带命名空间的标签的名字
     * @return void
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("bean".equals(qName)) {
            beanDefinitions.add(beanDefinition);
        }
        if ("constructor-arg".equals(qName)) {
            beanDefinition.getConstructorArgs().add(constructorArg);
        }
        tag = null; //tag丢弃了
    }

    /**
     * @author lastwhisper
     * @desc 文档解析结束后调用，该方法只会调用一次
     * @param
     * @return void
     */
    @Override
    public void endDocument() throws SAXException {
    }

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }
}
