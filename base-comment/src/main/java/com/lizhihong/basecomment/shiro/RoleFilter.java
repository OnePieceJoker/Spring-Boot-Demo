package com.lizhihong.basecomment.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/16
 * @time 22:CURRENT_MINUTE:CURRENT_SECOND
 * @description 角色过滤器，为了实现or的效果，shiro默认是and的效果
 */
public class RoleFilter extends RolesAuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for(int i=0;i < rolesArray.length;i++) {
            if(subject.hasRole(rolesArray[i])) {
                return true;
            }
        }
        return false;
    }

}