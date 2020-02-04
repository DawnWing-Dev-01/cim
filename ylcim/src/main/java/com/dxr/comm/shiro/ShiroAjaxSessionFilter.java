package com.dxr.comm.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import com.dawnwing.framework.utils.StringUtils;

/**
 * @description: <Ajax Shiro session超时统一处理>
 * 参考：http://looooj.github.io/blog/2014/06/17/shiro-user-filter.html
 * @author: w.xL
 * @date: 2018-3-20
 */
public class ShiroAjaxSessionFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request,
            ServletResponse response) throws Exception {
        HttpServletRequest req = WebUtils.toHttp(request);
        String xmlHttpRequest = req.getHeader("X-Requested-With");
        if (StringUtils.isNotEmpty(xmlHttpRequest)) {
            if (xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
                HttpServletResponse res = WebUtils.toHttp(response);
                res.setHeader("shiro-status", "401");
                return false;
            }
        }
        return super.onAccessDenied(request, response);
    }

}