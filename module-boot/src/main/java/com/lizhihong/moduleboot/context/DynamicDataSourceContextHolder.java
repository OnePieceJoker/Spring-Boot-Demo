package com.lizhihong.moduleboot.context;

import com.lizhihong.moduleboot.constants.DataSourceConstants;

/**
 *
 * @author Mr.Joker
 * @date 2020/04/06
 * @time 10:51:55
 * @description 动态数据源名称上下文处理
 */
public class DynamicDataSourceContextHolder {

    /**
     * 动态数据源名称上下文
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<String>();

    /**
     * 设置数据源
     * @param contextKey
     */
    public static void setContextKey(String contextKey) {
        System.out.println("切换数据源" + contextKey);
        DATASOURCE_CONTEXT_KEY_HOLDER.set(contextKey);
    }

    /**
     * 获取数据源名称
     * @return
     */
    public static String getContextKey() {
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? DataSourceConstants.DS_KEY_MASTER : key;
    }

    /**
     * 删除当前数据源
     */
    public static void removeContextKey() {
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }

}