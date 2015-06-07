/*
 * Copyright (c) 2013, Dets Group. All rights reserved.
 * This work is licensed under the Creative Commons Atribución-CompartirIgual 2.5 Colombia License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/2.5/co/.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

package com.egresados.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo Pacheco Gomez
 * @version 1.0
 */
public class Connection {
    
    private static final String URL = "jdbc:mysql://%s:%s/%s";
    
    public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
    
    private String user;
    
    private String password;
    
    private String baseDate;
    
    private String driver;
    
    private String server;
    
    private String port;
    
    private static Connection singleton;
    
    private java.sql.Connection connection;
    
    private Connection() {
        this.server = "localhost";
        this.port = "3306";
        this.baseDate = "egresados_db";
        this.user = "root";
        this.password = "root";
        this.driver = Connection.DRIVER_MYSQL;
    }
    
    public static Connection getInstance() {
        if (singleton == null)
            singleton = new Connection();
        return singleton;
    }
    
    /**
     * Este metodo sirve para conectarse a la base de datos.
     * @return true, si se realizo la conexión de manera exitosa.
     * @throws SQLException 
     */
    public boolean connect() throws SQLException {
        String url = String.format(URL, server, port, baseDate);
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Este metodo sirve para desconectarse de la base de datos.
     * @return true, si se desconecto perfectamente de la base de datos.
     */
    public boolean disconnect() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    /**
     * Este metodo sirve para obtener la conexion.
     * @return Connection
     */
    public java.sql.Connection getConnection() {
        return connection;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    /**
     * Este metodo sirve para saber si estas conectado a la base de datos.
     * @return true, si se encuentra conectado a la base de datos.
     * @throws SQLException 
     */
    public boolean isConnect() throws SQLException {
        return this.connection != null && !this.connection.isClosed();
    }
    
}
