/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Opcion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoOpcion implements DataAccessObject<Opcion, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement list;
    
    private DaoOpcion() {
        con = Connection.getInstance();
    }
    
    public static DaoOpcion getInstance() {
        return DaoOpcionHolder.INSTANCE;
    }

    @Override
    public boolean insert(Opcion value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO opcion VALUES(?, ?, ?)");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setString(2, value.getCodigoPregunta());
        this.insert.setString(3, value.getCuerpo());
        
        return this.insert.executeUpdate() == ROW_AFFECTED_SUCCESSFULLY;
    }
    
    @Override
    public Opcion find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM opcion WHERE codigo=?");
        }
        
        find.setString(1, value);
        
        ResultSet executeQuery = find.executeQuery();        
        
        if (executeQuery != null && executeQuery.next()) {
            return convertResultTo(executeQuery);
        }
        
        return null;
    }

    public List<Opcion> listByPregunta(String idPregunta) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            list = con.getConnection().prepareStatement("SELECT * FROM opcion WHERE pregunta=?");
        }
        
        list.setString(1, idPregunta);
        
        ResultSet executeQuery = list.executeQuery();
        List<Opcion> temp = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            temp.add(convertResultTo(executeQuery));
        }
        
        return temp;
    }
    
    private Opcion convertResultTo(ResultSet resultSet) throws SQLException {
        String codigo = resultSet.getString("codigo");
        String cuerpo = resultSet.getString("cuerpo");
        String pregunta = resultSet.getString("pregunta");
        
        Opcion opcion = new Opcion();
        opcion.setCodigo(codigo);
        opcion.setCodigoPregunta(pregunta);
        opcion.setCuerpo(cuerpo);
        
        return opcion;
    }

    @Override
    public boolean delete(String column, Object value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (delete == null) {
            delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM opcion WHERE pregunta=?");
        }
        
        delete.setString(1, value.toString());
        
        return delete.executeUpdate() > MINIMUM_OF_ROWS_AFFECTED;
    }
    
    //<editor-fold defaultstate="collapsed" desc="no implements">
    @Override
    public boolean update(Opcion value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean delete(String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Opcion find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Opcion> list() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>
    
    private static class DaoOpcionHolder {
        private static final DaoOpcion INSTANCE = new DaoOpcion();
    }
}
