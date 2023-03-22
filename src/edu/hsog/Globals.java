/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author ubuntu
 */
public class Globals {

    static String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    static String username = "chris";
    static String passwd = "xyz";

    static BasicDataSource conPool = null;

    public static void initConnectionPool() {

        System.out.println("Account: " + username + ":" + passwd);

        conPool = new BasicDataSource();
        conPool.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        conPool.setUrl(url);
        conPool.setUsername(username);
        conPool.setPassword(passwd);
        conPool.setMaxTotal(15);
        conPool.setInitialSize(5);
    }

    public static Connection getPoolConnection() {
        Connection v_connection = null;
        try {
            v_connection = conPool.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v_connection;
    }
}
