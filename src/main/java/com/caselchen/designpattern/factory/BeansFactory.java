package com.caselchen.designpattern.factory;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeansFactory {

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            this.beanDefinitions.putIfAbsent(beanDefinition.getId(), beanDefinition);
        }

        for (BeanDefinition beanDefinition : beanDefinitionList) {
            if (beanDefinition.isLazyInit() == false && beanDefinition.isSingleton()) {
                createBean(beanDefinition);
            }
        }
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanId);
        if (beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("Bean is not defined: " + beanId);
        }
        return createBean(beanDefinition);
    }

//    @VisibleForTesting
    protected Object createBean(BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition.getId())) {
            return singletonObjects.get(beanDefinition.getId());
        }

        Object bean = null;
        try {
            Class beanClass = Class.forName(beanDefinition.getClassName());
            Constructor[] constructors = beanClass.getConstructors();
            List<BeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgs();
            args.sort(Comparator.comparingInt(BeanDefinition.ConstructorArg::getIndex));

            List<Constructor> candidateConstructors = new ArrayList<>();
            for (Constructor constructor : constructors) {
                if (constructor.getParameterCount() == args.size()) {
                    candidateConstructors.add(constructor);
                }
            }

            if (args.isEmpty()) {
                bean = beanClass.newInstance();
            } else {
                int[] argIndexes = new int[args.size()];
                Object[] argObjects = new Object[args.size()];
                Map<Integer, Class> refClassMap = new HashMap<Integer, Class>();
                for (int i = 0; i < args.size(); ++i) {
                    BeanDefinition.ConstructorArg arg = args.get(i);
                    if (!arg.getIsRef()) {
                        argIndexes[i] = arg.getIndex();
                        argObjects[i] = arg.getArg();
                    } else {
                        BeanDefinition refBeanDefinition = beanDefinitions.get(arg.getArg());
                        if (refBeanDefinition == null) {
                            throw new NoSuchBeanDefinitionException("Bean is not defined: " + arg.getArg());
                        }
                        Class refClass = Class.forName(refBeanDefinition.getClassName());
                        refClassMap.put(i, refClass);
                        argObjects[i] = createBean(refBeanDefinition);
                    }
                }
                Constructor finalConstructor = getMatchConstructor(candidateConstructors, refClassMap);
                Class[] parameterTypes = finalConstructor.getParameterTypes();
                Object[] parameterValues = getParameterValues(argObjects, parameterTypes);
                bean = beanClass.getConstructor(parameterTypes).newInstance(parameterValues);
            }
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new BeanCreationFailureException("", e);
        }

        if (bean != null && beanDefinition.isSingleton()) {
            singletonObjects.putIfAbsent(beanDefinition.getId(), bean);
            return singletonObjects.get(beanDefinition.getId());
        }
        return bean;
    }

    private Object[] getParameterValues(Object[] argObjects, Class[] parameterTypes) {
        Object[] parameterValues = new Object[parameterTypes.length];
        for (int i=0; i<parameterTypes.length; i++) {
            Class parameterType = parameterTypes[i];
            Object argObject = argObjects[i];
            parameterValues[i] = cast(argObject, parameterType);
        }
        return parameterValues;
    }

    private Constructor getMatchConstructor(List<Constructor> candidateConstructors, Map<Integer, Class> refClassMap) {
        Constructor finalContructor = null;
        if (!refClassMap.isEmpty()) {
            for (Constructor candidateConstructor : candidateConstructors) {
                Class[] parameterTypes = candidateConstructor.getParameterTypes();
                boolean match = false;
                for (Map.Entry<Integer, Class> entry : refClassMap.entrySet()) {
                    int index = entry.getKey();
                    Class refClass = entry.getValue();
                    if (parameterTypes[index] != null && parameterTypes[index].getCanonicalName().equals(refClass.getCanonicalName())) {
                        match = true;
                    } else {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    finalContructor = candidateConstructor;
                    break;
                }
            }
            if (finalContructor == null) {
                throw new BeanCreationFailureException("Not found constructor for parameter types declared");
            }
        } else {
            finalContructor = candidateConstructors.get(0);
        }
        return finalContructor;
    }

    private Object cast(Object argObject, Class parameterType) {
        Object result = null;
        if (parameterType.equals(Integer.class)) {
            result = Integer.valueOf(String.valueOf(argObject));
        } else if (parameterType.equals(Boolean.class)) {
            result = Boolean.valueOf(String.valueOf(argObject));
        } else if (parameterType.equals(Double.class)) {
            result = Double.valueOf(String.valueOf(argObject));
        } else if (parameterType.equals(Float.class)) {
            result = Float.valueOf(String.valueOf(argObject));
        } else if (parameterType.equals(Long.class)) {
            result = Long.valueOf(String.valueOf(argObject));
        } else if (parameterType.equals(String.class)) {
            result = String.valueOf(argObject);
        }
        return result;
    }
}