package com.lizhihong.moduleboot.config;

import java.util.Map;

import javax.sql.DataSource;

import com.lizhihong.moduleboot.context.DynamicDataSourceContextHolder;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> backupTargetDataSources;

    /**
     * 自定义构造函数
     * @param defaultDataSource
     * @param targetDataSource
     */
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSource) {
        backupTargetDataSources = targetDataSource;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(backupTargetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 添加新数据源
     * @param key
     * @param dataSource
     */
    public void addDataSource(String key, DataSource dataSource) {
        this.backupTargetDataSources.put(key, dataSource);
        super.setTargetDataSources(this.backupTargetDataSources);
        super.afterPropertiesSet();
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getContextKey();
    }


}