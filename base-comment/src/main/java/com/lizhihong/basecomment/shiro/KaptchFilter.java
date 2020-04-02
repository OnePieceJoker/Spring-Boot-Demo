package com.lizhihong.basecomment.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/15
 * @time 20:CURRENT_MINUTE:CURRENT_SECOND
 * @description 验证码过滤器， 此过滤器已经在shiro中配置，这里无需再次配置拦截路径
 */
public class KaptchFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    // 登录验证
    protected boolean executeLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUsernamePasswordToken token = createToken(request, response);
        String username = token.getUsername();
        try {
            doCaptchaValidate((HttpServletRequest) request, token);
            Subject subject = getSubject(request, response);
            // 正常验证
            subject.login(token);

            // 验证成功
            ((HttpServletRequest) request).getSession().setAttribute("name", username);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    // 验证码校验
    protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token) {
        // session中的图形码字符串
        String captcha = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        if (captcha == null || !captcha.equalsIgnoreCase(token.getCaptcha())) {
            throw new IncorrectCaptchaException();
        }
    }

    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        return new CaptchaUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    // 保存异常对象到request
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

}
