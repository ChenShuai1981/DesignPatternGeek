package com.caselchen.designpattern.factory;


public interface ApplicationContext {
    Object getBean(String beanId);
}