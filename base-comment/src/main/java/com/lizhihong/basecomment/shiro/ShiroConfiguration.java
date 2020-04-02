package com.lizhihong.basecomment.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/15
 * @time 20:CURRENT_MINUTE:CURRENT_SECOND
 * @description Apache Shiro 核心通过Filter来实现，就好像SpringMvc 通过DispachServlet来主控制一样
 * 通过URL规则来进行过滤和权限校验，需要定义一系列关于URL的规则和访问权限
 */
@Configuration
@Order(1)
public class ShiroConfiguration {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 初始化ShiroFilterFactoryBean的时候需要注入: SecurityManager
     * 一个URL可以配置多个Filter，使用逗号分隔
     * 当设置多个过滤器时，全部验证通过，才视为通过
     * 部分过滤器可指定参数，如perms, roles
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 验证码过滤器
        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        KaptchFilter kaptchFilter = new KaptchFilter();
        filtersMap.put("kaptchFilter", kaptchFilter);
        // 实现自己规则roles，这是为了实现or的效果
        // RoleFilter roleFilter = new RoleFilter();
        // filtersMap.put("roles", roleFilter);
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 拦截器.
        //rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等。
        //port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
        //perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
        //roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
        //anon：比如/admins/**=anon 没有参数，表示可以匿名使用。
        //authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
        //authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
        //ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
        //user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        // 配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/index", "user");
        filterChainDefinitionMap.put("/", "user");
        filterChainDefinitionMap.put("/login", "kaptchFilter");

        // 过滤链定义，从上向下顺序执行，一般将/**放在最下边 

        // 这段是配合 actuator框架使用的，配置相应的角色才能访问
        // filterChainDefinitionMap.put("/health", "roles[aix]");//服务器健康状况页面
        // filterChainDefinitionMap.put("/info", "roles[aix]");//服务器信息页面
        // filterChainDefinitionMap.put("/env", "roles[aix]");//应用程序的环境变量
        // filterChainDefinitionMap.put("/metrics", "roles[aix]");
        // filterChainDefinitionMap.put("/configprops", "roles[aix]");
        
        // 开放的静态资源
        // 网站图标
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        // TODO 配置static文件下资源能被访问的，这是个例子
        filterChainDefinitionMap.put("/AdminLTE-2.3.7/**", "anon");
        // 图片验证码（kaptcha)
        filterChainDefinitionMap.put("kaptcha.jpg", "anon");
        filterChainDefinitionMap.put("/**", "anon");

        // TODO 前后端分离，java端不做页面路由
        // 如果不设置默认会自动寻找web工程根目录下的login.jsp页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/errorView/403_error.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(myShiroRealm());
        // 注入缓存管理器
        // 注意：开发时请先关闭，如不关闭热启动会报错
        // securityManager..setCacheManager(ehCacheManager());
        // 注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() { {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        //表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }}

    /**
     * 开启 shiro aop 注解支持，使用代理方式
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * shiro 缓存管理器：
     * 需要注入对应的其他的实体类中：
     * 1.安全管理器：securityManager
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * cookie对象
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox的name=rememberMe
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 记住我的cookie生效时间30天，单位秒
        cookie.setMaxAge(259200);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        return rememberMeManager;
    }

}