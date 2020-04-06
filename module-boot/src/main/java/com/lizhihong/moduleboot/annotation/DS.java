package com.lizhihong.moduleboot.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lizhihong.moduleboot.constants.DataSourceConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Mr.Joker
 * @date 2020/04/06
 * @time 12:54:22
 * @description 自定义数据源注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    String value() default DataSourceConstants.DS_KEY_MASTER;

}