package com.lizhihong.moduleboot.util;

import javax.sql.DataSource;

import com.lizhihong.moduleboot.config.DynamicDataSource;
import com.lizhihong.moduleboot.context.SpringContextHolder;
import com.lizhihong.moduleboot.vo.DbInfo;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.StringUtils;

/**
 *
 * @author Mr.Joker
 * @date 2020/04/06
 * @time 13:22:36
 * @description 数据源工具
 */
public class DataSourceUtil {

    /**
     * 创建新的数据源
     * @param dbInfo
     * @return
     */
    public static DataSource makeNewDataSource(DbInfo dbInfo) {
        String url = "jdbc:mysql://" + dbInfo.getIp() + ":" + dbInfo.getPort() + "/" + dbInfo.getDbName()
                + "?useSSL=false&serverTimeZone=GMT%2B8&characterEncoding=UTF-8";
        String driverClassName = StringUtils.isEmpty(dbInfo.getDriveClassName()) ? "com.mysql.cj.jdbc.Driver"
                : dbInfo.getDriveClassName();
        return DataSourceBuilder.create().url(url).driverClassName(driverClassName).username(dbInfo.getUsername()).password(dbInfo.getPassword()).build();
    }

    /**
     * 添加数据源到动态源中
     * @param key
     * @param dataSource
     */
    public static void addDataSourceToDynamic(String key, DataSource dataSource) {
        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);
        dynamicDataSource.addDataSource(key, dataSource);
    }

    /**
     * 根据数据库连接信息添加数据源到动态源中
     * @param key
     * @param dbInfo
     */
    public static void addDataSourceToDynamic(String key, DbInfo dbInfo) {
        DataSource dataSource = makeNewDataSource(dbInfo);
        addDataSourceToDynamic(key, dataSource);
    }
}