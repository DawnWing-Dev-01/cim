package com.dxr.comm.listener;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.utils.SpringUtils;
import com.dxr.system.service.api.ISystemConstant;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-2-23
 */
public class ContextLoaderListener implements ServletContextListener {

    private static Logger logger = null;
    static {
        try {
            // 从配置文件log4j.properties中读取配置信息, 解决 log4j.properties配置文件不在src根目录问题
            Properties props = new Properties();
            InputStream log4jIs = ContextLoaderListener.class.getClassLoader()
                    .getResourceAsStream("zOther/log4j.properties");
            props.load(log4jIs);
            //装入log4j配置信息  
            PropertyConfigurator.configure(props);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger = LoggerFactory.getLogger("dawnwing studio");
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("--------------------------------------DawnWing FrameWork SoftWare--------------------------------------");
        logger.info("注册单位：杨凌示范区经营者信用公示平台");
        logger.info("有效期至：2017.10.01-2018.09.31");
        logger.info("Context Loader Listener initialized...");
        ServletContext servletContext = event.getServletContext();
        logger.info("System Constant Load Done...");
        String ctx = servletContext.getContextPath();
        servletContext.setAttribute("ctx", ctx);
        long timeMillis = System.currentTimeMillis();
        servletContext.setAttribute("version", timeMillis);
        // 设置系统常量缓存
        setCache();
        logger.info("Public resource setting is complete...");
        memoryInfo("Memory usage: ");
        logger.info("--------------------------------------DawnWing FrameWork SoftWare--------------------------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("--------------------------------------DawnWing FrameWork SoftWare--------------------------------------");
        logger.info("This Project Is undeploy...");
        logger.info("--------------------------------------DawnWing FrameWork SoftWare--------------------------------------");
    }

    /**
     * @param info
     */
    private void memoryInfo(String info) {
        logger.info(info + Runtime.getRuntime().totalMemory() / (1024 * 1024)
                + "/" + Runtime.getRuntime().freeMemory() / (1024 * 1024)
                + " (M)");
    }

    /**
     * 设置缓存
     */
    private void setCache() {
        ISystemConstant systemConstantMgr = (ISystemConstant) SpringUtils
                .getBean("systemConstantMgr");
        try {
            Integer number = systemConstantMgr.setCache();
            logger.info("cache constant number: " + number);
        } catch (ServiceException e) {
            logger.error("ContextLoaderListener setCache is error...", e);
        }
    }

}
