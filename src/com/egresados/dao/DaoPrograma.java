/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Programa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoPrograma implements DataAccessObject<Programa, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement find2;
    private PreparedStatement list;
    
    private DaoPrograma() {
        this.con = Connection.getInstance();
    }
    
    public static DaoPrograma getInstance() {
        return DaoProgramaHolder.INSTANCE;
    }

    @Override
    public boolean insert(Programa value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (this.insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO programa VALUES (?, ?)");
        }
        
        insert.setString(1, value.getId());
        insert.setString(2, value.getNombre());
        
        int result = insert.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean update(Programa value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (this.update == null) {
            this.update = con.getConnection().prepareStatement(""
                    + "UPDATE programa SET nombre=? WHERE id=?");
        }
        
        this.update.setString(1, value.getNombre());
        this.update.setString(2, value.getId());
        
        int result = this.update.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean delete(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String id) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (this.delete == null) {
            this.delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM programa WHERE id=?");
        }
        
        this.delete.setString(1, id);
        
        int result = this.delete.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public Programa find(String column, Object value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find2 == null) {
            this.find2 = con.getConnection().prepareStatement(""
                    + "SELECT * FROM programa WHERE " + column + "=?");
        }
        
        this.find2.setString(1, value.toString());
        ResultSet executeQuery = this.find2.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? convertResultTo(executeQuery) : null;
    }

    @Override
    public Programa find(String id) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM programa WHERE id=?");
        }
        
        this.find.setString(1, id);
        ResultSet executeQuery = this.find.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? convertResultTo(executeQuery) : null;
    }

    @Override
    public List<Programa> list() throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (this.list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM programa ORDER BY nombre");
        }
        
        ResultSet result = this.list.executeQuery();
        
        List<Programa> temp = new ArrayList<>();

        while (result != null && result.next()) {
            temp.add(convertResultTo(result));
        }

        return temp;
    }
    /**
     * Este metodo convierte el resultado obtenido de la tabla en un objeto 
     * especifico.
     * @param result resultado de la tabla
     * @return Programa, objeto convertido.
     * @throws SQLException 
     */
    private Programa convertResultTo(ResultSet result) throws SQLException {
        String id = result.getString("id");
        String nombre = result.getString("nombre");
        return new Programa(id, nombre);
    }
    
    private static class DaoProgramaHolder {

        private static final DaoPrograma INSTANCE = new DaoPrograma();
    }
}
