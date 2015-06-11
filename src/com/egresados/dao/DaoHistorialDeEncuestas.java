/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.HistorialDeEncuestas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis
 */
public class DaoHistorialDeEncuestas implements DataAccessObject<HistorialDeEncuestas, String>{

    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement count;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement find2;
    private PreparedStatement list;
    private PreparedStatement list2;
    
    private DaoHistorialDeEncuestas() {
        con = Connection.getInstance();
    }
    
    public static DaoHistorialDeEncuestas getInstance() {
        return DaoHistorialDeEncuestasHolder.INSTANCE;
    }
    
    @Override
    public boolean insert(HistorialDeEncuestas value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO historial VALUES (?,?,NOW())");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setString(2, value.getEgresado().getCodigo());
        
        int result = this.insert.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean update(HistorialDeEncuestas value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HistorialDeEncuestas find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HistorialDeEncuestas find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM historial WHERE egresado=?");
        }
        
        this.find.setString(1, value);
        
        ResultSet executeQuery = this.find.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? convertResultTo(executeQuery) : null;
    }
    
    public HistorialDeEncuestas find(String value, String value2) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find2 == null) {
            this.find2 = con.getConnection().prepareStatement(""
                    + "SELECT * FROM historial WHERE egresado=? AND encuesta=?");
        }
        
        this.find2.setString(1, value);
        this.find2.setString(2, value2);
        
        ResultSet executeQuery = this.find2.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? convertResultTo(executeQuery) : null;
    }
    
    @Override
    public List<HistorialDeEncuestas> list() throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM historial ORDER BY egresado");
        }
        
        ResultSet executeQuery = this.list.executeQuery();
        List<HistorialDeEncuestas> historial = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            historial.add(convertResultTo(executeQuery));
        }
        
        return historial;
    }
    
    public List<HistorialDeEncuestas> list(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list2 == null) {
            this.list2 = con.getConnection().prepareStatement(""
                    + "SELECT * FROM historial WHERE egresado=? ORDER BY egresado");
        }
        
        this.list2.setString(1, value);
        
        ResultSet executeQuery = this.list2.executeQuery();
        List<HistorialDeEncuestas> historial = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            historial.add(convertResultTo(executeQuery));
        }
        
        return historial;
    }
    
    public int countHistoryByPoll(String code) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (count == null) {
            this.count = con.getConnection().prepareStatement(""
                    + "SELECT COUNT(*) AS cantidad FROM historial WHERE encuesta=?");
        }
        
        count.setString(1, code);
        
        ResultSet executeQuery = this.count.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? executeQuery.getInt("cantidad") : -1;
    }

    private HistorialDeEncuestas convertResultTo(ResultSet executeQuery) throws SQLException {
        String encuesta = executeQuery.getString("encuesta");
        String egresado = executeQuery.getString("egresado");
        return new HistorialDeEncuestas(encuesta, DaoEgresado.getInstance().find(egresado));
    }
    
    private static class DaoHistorialDeEncuestasHolder {

        private static final DaoHistorialDeEncuestas INSTANCE = new DaoHistorialDeEncuestas();
    }
}
