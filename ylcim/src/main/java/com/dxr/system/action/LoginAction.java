package com.dxr.system.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.SessionConstants;
import com.dawnwing.framework.core.CaptchaBuilder;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dawnwing.framework.utils.IoUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.system.entity.UserInfo;
import com.google.code.kaptcha.Producer;

/**
 * @description: <登录Action>
 * @author: w.xL
 * @date: 2018-2-21
 */
public class LoginAction extends BasalAction {

    private static final long serialVersionUID = 6454846517595649440L;

    private static final Logger logger = LoggerFactory
            .getLogger(LoginAction.class);

    @Autowired
    private Producer defaultKaptcha = null;
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private SystemConstantCache scCache;

    private String showCaptcha;

    private UserInfo userInfo;

    private String loginError;

    private Integer rememberMe;

    /**
     * 验证码的输入流
     */
    private InputStream captchaIs = null;

    private final static String GOTO_HOME = "gotoHome";

    private final static String HOME_INDEX = "homeIndex";

    private final static String GOTO_LOGIN = "index";

    private final static String GOTO_NOAUTH = "noAuth";

    /**
     * @return
     */
    @Override
    public String index() {
        // 判断是否已经登录过了
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return GOTO_HOME;
        }

        String copyright = String.valueOf(scCache.get("Copyright"));
        super.putViewDeftData("copyright", copyright);
        super.putViewDeftData("showCaptcha", showCaptcha);
        return super.index();
    }

    /**
     * 登录主方法
     * @return
     */
    public String uniLogin() {
        super.putViewDeftData("showCaptcha", showCaptcha);
        // 判断用户是否输入账号密码
        if (StringUtils.isEmpty(userInfo.getUsername())
                || StringUtils.isEmpty(userInfo.getPassword())) {
            loginError = "请输入账号/密码!";
            return GOTO_LOGIN;
        }

        Subject subject = SecurityUtils.getSubject();

        Session session = subject.getSession();
        // 验证码校验
        if (Boolean.valueOf(showCaptcha)) {
            Object verifiedCode = session
                    .getAttribute(SessionConstants.SESSION_KEY_CAPTCHA);
            if (!verifiedCode.equals(object)) {
                loginError = "请输入正确的验证码!";
                return GOTO_LOGIN;
            }
        }

        // 组装登录令牌（用户名密码）
        UsernamePasswordToken token = new UsernamePasswordToken(
                userInfo.getUsername(), userInfo.getPassword().toCharArray());
        // 设置记住密码
        //token.setRememberMe(true);
        try {
            // 踢出上一个登录的用户, 一个账号只允许一处登录
            this.kickedUser(userInfo.getUsername());

            // 执行登录操作
            subject.login(token);
            if (subject.isAuthenticated()) {
                session.setAttribute(SessionConstants.SESSION_KEY_LOGIN_NAME,
                        userInfo.getUsername());
                return GOTO_HOME;
            } else {
                loginError = "登录失败, 请联系管理员!";
                return GOTO_LOGIN;
            }
        } catch (UnknownAccountException e) {
            loginError = "账号不存在!";
            return GOTO_LOGIN;
        } catch (DisabledAccountException e) {
            loginError = e.getMessage();
            return GOTO_LOGIN;
        } catch (IncorrectCredentialsException e) {
            loginError = "用户名/密码错误!";
            logger.error("username or password is Incorrect Credentials...", e);
            return GOTO_LOGIN;
        } catch (ExcessiveAttemptsException e) {
            loginError = e.getMessage();
            return GOTO_LOGIN;
        } catch (Throwable e) {
            loginError = "登录发生未知异常!";
            logger.error("loginAction.uniLogin() error... ", e);
            return GOTO_LOGIN;
        }
    }

    public String showHome() {
        return HOME_INDEX;
    }

    public void logOut() {
        logger.info("---------------------The user exits the system.");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public String noAuth() {
        return GOTO_NOAUTH;
    }

    /**
     * 生成验证码
     */
    public String newCaptcha() {
        HttpServletResponse response = super.getResponse();
        // 设置验证码图像无缓存
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");

        // create the text for the image 
        String captcha = defaultKaptcha.createText();

        // store the captcha text in the session
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute(SessionConstants.SESSION_KEY_CAPTCHA, captcha);

        // create the image with the text  
        BufferedImage bi = defaultKaptcha.createImage(captcha);
        bi.flush();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageOutputStream imOut = null;
        try {
            imOut = ImageIO.createImageOutputStream(bos);
            ImageIO.write(bi, "jpg", imOut);
            captchaIs = new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException e) {
            logger.error(
                    "create ImageOutputStream or ImageIO.write() is error...",
                    e);
        } finally {
            // 关闭所有的IO流
            IoUtils.unifyClose(imOut, bos);
        }
        return "newCaptcha";
    }

    /**
     * 创建验证码
     */
    public String builderCaptcha() {
        HttpServletResponse response = super.getResponse();
        // 设置验证码图像无缓存
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");

        CaptchaBuilder cb = new CaptchaBuilder(100, 30, 5, 10);
        // create the text for the image 
        String captcha = cb.getCode();

        // store the captcha text in the session
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        session.setAttribute(SessionConstants.SESSION_KEY_CAPTCHA, captcha);

        // create the image with the text  
        BufferedImage buffImg = cb.getBuffImg();
        buffImg.flush();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageOutputStream imOut = null;
        try {
            imOut = ImageIO.createImageOutputStream(bos);
            ImageIO.write(buffImg, "jpg", imOut);
            captchaIs = new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException e) {
            logger.error(
                    "create ImageOutputStream or ImageIO.write() is error...",
                    e);
        } finally {
            // 关闭所有的IO流
            IoUtils.unifyClose(imOut, bos);
        }
        return "builderCaptcha";
    }

    /**
     * 踢出上一个登录的用户
     * @param loginName
     */
    private void kickedUser(String loginName) {
        onlineUser();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            String loginSessionKey = String.valueOf(session
                    .getAttribute(SessionConstants.SESSION_KEY_LOGIN_NAME));
            if (StringUtils.isEmpty(loginSessionKey)) {
                continue;
            }
            if (loginName.equals(loginSessionKey)) {
                // 设置session立即失效，即将其踢出系统
                session.setTimeout(0);
                logger.info("User [" + loginSessionKey
                        + "] are forced to exit the system...");
                break;
            }
        }
    }

    /**
     * 获取在线用户
     */
    private void onlineUser() {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        logger.info("--------------------------------------- Online User List ---------------------------------------");
        for (Session session : sessions) {
            logger.info("登录ip: " + session.getHost());
            SimplePrincipalCollection principals = (SimplePrincipalCollection) session
                    .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principals != null) {
                ShiroUser shirouser = (ShiroUser) principals
                        .getPrimaryPrincipal();
                if (shirouser != null) {
                    logger.info("登录用户: " + shirouser.getLoginName() + "【"
                            + shirouser.getRealName() + "】");
                }
            }
            logger.info("最后操作日期: " + session.getLastAccessTime());
            logger.info("------------------------------------------------------------------------------------------------");
        }
        logger.info("--------------------------------------- Online User List ---------------------------------------");
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public InputStream getCaptchaIs() {
        return captchaIs;
    }

    public void setCaptchaIs(InputStream captchaIs) {
        this.captchaIs = captchaIs;
    }

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getShowCaptcha() {
        return showCaptcha;
    }

    public void setShowCaptcha(String showCaptcha) {
        this.showCaptcha = showCaptcha;
    }
}
