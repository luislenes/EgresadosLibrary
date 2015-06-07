/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Administrador;
import com.egresados.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoAdministrador implements DataAccessObject<Administrador, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement list;
    
    private DaoAdministrador() {
        this.con = Connection.getInstance();
    }
    
    public static DaoAdministrador getInstance() {
        return DaoAdministradorHolder.INSTANCE;
    }

    @Override
    public boolean insert(Administrador value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO administrador VALUES (?, ?, ?)");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setString(2, value.getNombre());
        this.insert.setString(3, value.getCorreo());
        
        DaoUsuario.getInstance().insert(value);
        
        int result = this.insert.executeUpdate();
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean update(Administrador value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (this.update == null) {
            this.update = con.getConnection().prepareStatement(""
                    + "UPDATE administrador SET nombre=?, correo=? WHERE codigo=?");
        }
        
        this.update.setString(1, value.getNombre());
        this.update.setString(2, value.getCorreo());
        this.update.setString(3, value.getCodigo());
        
        int result = this.update.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean delete(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String code) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (delete == null) {
            this.delete =  con.getConnection().prepareStatement(""
                    + "DELETE FROM administrador WHERE codigo=?");
        }
        
        this.delete.setString(1, code);
        
        boolean deleteFromUsuario = DaoUsuario.getInstance().delete(code);
        
        if (deleteFromUsuario) {
            int result = this.delete.executeUpdate();
            return result == ROW_AFFECTED_SUCCESSFULLY;
        } else {
            return false;
        }
    }

    @Override
    public Administrador find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Administrador find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM administrador WHERE codigo=?");
        }
        
        this.find.setString(1, value);
        ResultSet executeQuery = this.find.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? convertResultTo(executeQuery) : null;
    }

    @Override
    public List<Administrador> list() throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM administrador ORDER BY codigo");
        }
        
        ResultSet executeQuery = this.list.executeQuery();
        List<Administrador> temp = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            temp.add(convertResultTo(executeQuery));
        }
        
        return temp;
    }
    
    private Administrador convertResultTo(ResultSet result) throws SQLException {
        String name = result.getString("nombre");
        String email = result.getString("correo");
        Usuario temp = DaoUsuario.getInstance().find(result.getString("codigo"));
        return new Administrador(name, email, temp.getCodigo(), temp.getPassword());
    }
    
    private static class DaoAdministradorHolder {

        private static final DaoAdministrador INSTANCE = new DaoAdministrador();
    }
}
