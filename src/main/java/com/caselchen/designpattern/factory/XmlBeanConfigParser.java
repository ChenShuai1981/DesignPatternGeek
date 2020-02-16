package com.caselchen.designpattern.factory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanConfigParser implements BeanConfigParser {

    public List<BeanDefinition> parse(InputStream inputStream) {
        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BeanDefinitionHandler handler = new BeanDefinitionHandler();
            saxParser.parse(inputStream, handler);
            beanDefinitions = handler.getBeanDefinitions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanDefinitions;
    }

}