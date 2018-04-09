package com.d1m.wechat.web;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web应用关闭时，清理第三方类库的线程.
 *
 * @author Yuan Zhen
 * @version 1.0.0  2016-08-16
 */
public class CleanupContextListener implements ServletContextListener {
	
	private static final Logger log = LoggerFactory.getLogger(CleanupContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        deregisterDrivers();
        tryShutdown("com.mysql.jdbc.AbandonedConnectionCleanupThread", "shutdown");
        tryShutdown("cn.d1m.wechat.client.token.DefaultAccessTokenProvider", "shutdown");
        tryShutdown("net.sf.ehcache.CacheManager", "getInstance", "shutdown");
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                log.info(String.format("Driver %s deregistered", d));
            } catch (SQLException ex) {
                log.warn(String.format("Error deregistering driver %s", d), ex);
            }
        }
    }

    private void tryShutdown(String className, String shutdownMethodName) {
        tryShutdown(className, null, shutdownMethodName);
    }

    private void tryShutdown(String className, String getInstanceMethodName, String shutdownMethodName) {
        try {
            Class<?> cls = Class.forName(className);
            Object instance = null;
            if (getInstanceMethodName != null) {
                try {
                    Method getInstanceMethod = cls.getMethod(getInstanceMethodName);
                    instance = getInstanceMethod.invoke(null);
                } catch (Exception e) {
                    log.error("Failed to invoke " + className + "#" + shutdownMethodName, e);
                }
            }
            Method shutdownMethod = cls.getMethod(shutdownMethodName);
            log.info("Shutdown {}", className);
            shutdownMethod.invoke(instance);
            log.info("Shutdown {} successfully!", className);
        } catch (Throwable thr) {
            log.error("Failed to invoke " + className + "#" + shutdownMethodName, thr);
        }
    }
}
