package com.lizhihong.basecomment.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@MapperScan(basePackages = "")
public class MasterMybatisConfig {

}