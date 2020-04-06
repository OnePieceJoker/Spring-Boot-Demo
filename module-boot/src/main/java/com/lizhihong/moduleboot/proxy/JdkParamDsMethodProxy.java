package com.lizhihong.moduleboot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lizhihong.moduleboot.context.DynamicDataSourceContextHolder;
import com.lizhihong.moduleboot.util.DataSourceUtil;
import com.lizhihong.moduleboot.vo.DbInfo;


public class JdkParamDsMethodProxy implements InvocationHandler {

    /**
     * 数据源key
     */
    private String dataSourceKey;
    /**
     * 数据库连接信息
     */
    private DbInfo dbInfo;
    /**
     * 代理目标对象
     */
    private Object targetObject;

    public JdkParamDsMethodProxy(Object targetObject, String dataSourceKey, DbInfo dbInfo) {
        this.dataSourceKey = dataSourceKey;
        this.targetObject = targetObject;
        this.dbInfo = dbInfo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        // 切换数据源
        DataSourceUtil.addDataSourceToDynamic(dataSourceKey, dbInfo);
        DynamicDataSourceContextHolder.setContextKey(dataSourceKey);
        // 调用方法
        Object result = method.invoke(targetObject, args);
        DynamicDataSourceContextHolder.removeContextKey();
        return result;
    }

    /**
     * 创建代理
     * @param targetObject
     * @param dataSourceKey
     * @param dbInfo
     * @return
     * @throws Exception
     */
    public static Object createProxyInstance(Object targetObject, String dataSourceKey, DbInfo dbInfo) throws Exception {
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader()
                 , targetObject.getClass().getInterfaces()
                 , new JdkParamDsMethodProxy(targetObject, dataSourceKey, dbInfo));
    }

}