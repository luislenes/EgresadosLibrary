/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.TipoDeUsuario;
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
public class DaoUsuario implements DataAccessObject<Usuario, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement list;
    private PreparedStatement access;
    
    private static final String KEY = "H7qYLcwfs85urDK";
    
    private DaoUsuario() {
        con = Connection.getInstance();
    }
    
    public static DaoUsuario getInstance() {
        return DaoUsuarioHolder.INSTANCE;
    }

    @Override
    public boolean insert(Usuario value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO usuario VALUES (?, AES_ENCRYPT(? ,?), ?)");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setString(2, value.getPassword());
        this.insert.setString(3, KEY);
        this.insert.setString(4, value.getTipo().name());
        
        int result = this.insert.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean update(Usuario value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (this.update == null) {
            this.update = con.getConnection().prepareStatement(""
                    + "UPDATE usuario SET password=AES_ENCRYPT(?, ?) WHERE codigo=?");
        }
        
        this.update.setString(1, value.getPassword());
        this.update.setString(2, KEY);
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
    public boolean delete(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (this.delete == null) {
            this.delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM usuario WHERE codigo=?");
        }
        
        this.delete.setString(1, value);
        
        int result = this.delete.executeUpdate();
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public Usuario find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT codigo, AES_DECRYPT(password, ?) AS pass, tipo FROM usuario WHERE codigo=?");
        }
        
        this.find.setString(1, KEY);
        this.find.setString(2, value);
        
        ResultSet executeQuery = this.find.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? convertResultTo(executeQuery) : null;
    }

    @Override
    public List<Usuario> list() throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM usuario ORDER BY codigo");
        }
        
        ResultSet executeQuery = this.list.executeQuery();
        List<Usuario> usuarios = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            usuarios.add(convertResultTo(executeQuery));
        }
        
        return usuarios;
    }
    
    public boolean access(String user, String password) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        this.access = con.getConnection().prepareStatement(""
                + "SELECT * FROM usuario WHERE codigo=? AND password=AES_ENCRYPT(?, ?)");
        
        this.access.setString(1, user);
        this.access.setString(2, password);
        this.access.setString(3, KEY);
        
        ResultSet executeQuery = this.access.executeQuery();
        
        return executeQuery.next();
    }
    
    private Usuario convertResultTo(ResultSet executeQuery) throws SQLException {
        String pass = executeQuery.getString("pass");
        String code = executeQuery.getString("codigo");
        TipoDeUsuario type = TipoDeUsuario.valueOf(executeQuery.getString("tipo"));
        return new Usuario(code, pass, type);
    }
    
    private static class DaoUsuarioHolder {

        private static final DaoUsuario INSTANCE = new DaoUsuario();
    }
}
