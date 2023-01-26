package com.corbandalas.reaper.springreaper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@Component
public class SelfAutowiredBeanPostProcessor implements BeanPostProcessor, Ordered {

    private Map<String, Object> map = new HashMap<>();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        var declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(SelfAutowired.class)) {
                map.put(beanName, bean);
                break;
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Object o = map.get(beanName);

        if (o != null) {
            var declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(SelfAutowired.class)) {

                    field.setAccessible(true);
                    ReflectionUtils.setField(field, o, bean);
                }
            }
        }

        return bean;

    }

    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
