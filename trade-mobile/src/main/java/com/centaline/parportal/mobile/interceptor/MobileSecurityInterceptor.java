package com.centaline.parportal.mobile.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.centaline.parportal.mobile.login.service.MobileSecurityHandler;
import com.centaline.parportal.mobile.login.service.TokenService;
import com.centaline.parportal.mobile.login.vo.MobileReturnVo;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.common.vo.TokenVo;

@Component
public class MobileSecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MobileSecurityHandler mobileSecurityHandler;
    @Autowired
    private TokenService          tokenService;
    private List<String>          excludedUrls = new ArrayList<String>();
    //借用shiro的路径解析
    protected PatternMatcher      pathMatcher;

    public MobileSecurityInterceptor() {
        pathMatcher = new AntPathMatcher();
        excludedUrls.add("/mobile/login");
        //        excludedUrls.add("/mobile/track/**");

    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // excluded URLs:
        // see
        // http://stackoverflow.com/questions/9908124/spring-mvc-3-interceptor-on-all-excluding-some-defined-paths
        String requestURI = getPathWithinApplication(request);

        for (String url : excludedUrls) {
            if (pathsMatch(url, requestURI)) {
                return true;
            }
        }

        // intercept
        boolean sp = false;
        try {
            sp = mobileSecurityHandler.doCheckAuthorization(request);
        } catch (AuthorizationException e) {
            doCheckAuthorizationFailure(request, response, e.getMessage());
            return false;
        }
        // intercept
        if (!sp) {
            doCheckAuthorizationFailure(request, response);
            return false;
        } else {
            doCheckAuthorizationScuess(request, response);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        MobileHolder.clearMobileUserHolder();
        MobileHolder.clearToken();
    }

    /**
     * 认证成功处理
     * 
     * @param request
     */
    private void doCheckAuthorizationScuess(HttpServletRequest request,
                                            HttpServletResponse response) {
        TokenVo tokenVo = tokenService.getToken(request.getParameter("token"));
        MobileHolder.setToken(tokenVo);
        MobileHolder.setMobileUserHolder(tokenVo.getSessionUser());
    }

    private void doCheckAuthorizationFailure(HttpServletRequest request,
                                             HttpServletResponse response) {
        doCheckAuthorizationFailure(request, response, null);
    }

    private void doCheckAuthorizationFailure(HttpServletRequest request,
                                             HttpServletResponse response, String msg) {
        MobileReturnVo mrv = new MobileReturnVo();
        mrv.setContent("");
        mrv.setEmpId("");
        mrv.setMsg(msg == null ? "认证失败" : msg);
        mrv.setSuccess(false);
        JSONObject josnObj = (JSONObject) JSONObject.toJSON(mrv);
        // String result = josnObj.toJSONString();

        PrintWriter out = null;
        try {
            out = response.getWriter();
            josnObj.writeJSONString(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*
             * if (out != null) { out.flush(); out.close(); }
             */
        }

    }

    protected String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }

    protected boolean pathsMatch(String pattern, String path) {
        return this.pathMatcher.matches(pattern, path);
    }
}
