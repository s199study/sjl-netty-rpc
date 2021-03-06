package com.github.rpc.context.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.rpc.context.spring.annotation.RocketService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: JianLei
 * @date: 2020/6/14 3:44 下午
 * @description:
 */
@Component
@Slf4j
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static ConcurrentHashMap<String, Object> getBeansByAnnotation(Class<? extends Annotation> clazz) {
        ConcurrentHashMap<String, Object> serviceMapping = new ConcurrentHashMap<>();
        try {
            if (clazz != null) {
                Map<String, Object> beans = SpringBeanUtil.applicationContext.getBeansWithAnnotation(clazz);
//        log.info("========beans 是:{}", JSONObject.toJSONString(beans));
                if (CollectionUtil.isNotEmpty(beans)) {
                    for (Object bean : beans.values()) {
                        String interFaceName = bean.getClass().getAnnotation(RocketService.class).value().getName();
                        serviceMapping.putIfAbsent(interFaceName, bean);
                    }
                    return serviceMapping;
                }
            }
        } catch (Exception e) {
            log.error("获取bean出现异常", e);
        }
        return new ConcurrentHashMap<>();
    }

    public static <T> T getBean(Class<T> type) {
        return SpringBeanUtil.applicationContext.getBean(type);
    }

}
