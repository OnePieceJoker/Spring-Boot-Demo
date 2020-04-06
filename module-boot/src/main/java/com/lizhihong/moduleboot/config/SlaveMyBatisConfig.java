package com.lizhihong.moduleboot.config;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 *
 * @author Mr.Joker
 * @date 2020/04/06
 * @time 10:32:55
 * @description slave数据源的mybatis配置
 */
@Configuration
@MapperScan(basePackages = "com.lizhihong.moduleboot.mapper.slave", sqlSessionFactoryRef = "slvaeSqlSessionFactory")
public class SlaveMyBatisConfig {

    @Bean("masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("master") DataSource dataSource) throws Exception {
        // 设置数据源
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // mapper的xml文件位置
        PathMatchingResourcePatternResolver resolve = new PathMatchingResourcePatternResolver();
        String locationPattern = "classpath*:/mapper/master/*.xml";
        sqlSessionFactoryBean.setMapperLocations(resolve.getResources(locationPattern));
        // 对应数据库的entity位置
        String typeAliasesPackage = "com.lizhihong.moduleboot.entity.master";
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return sqlSessionFactoryBean.getObject();
    }
}