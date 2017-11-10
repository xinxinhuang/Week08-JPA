/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import database.DBUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author 721292
 */
public class EMFLisener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // application is starting up
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //application shutting down, shut down the old one before the new one
        DBUtil.close();
    }
}
