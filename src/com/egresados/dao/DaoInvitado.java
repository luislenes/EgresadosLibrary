/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Invitado;
import com.egresados.model.Persona;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoInvitado implements DataAccessObject<Invitado, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement list;
    
    private DaoInvitado() {
        this.con = Connection.getInstance();
    }
    
    public static DaoInvitado getInstance() {
        return DaoInvitadoHolder.INSTANCE;
    }

    @Override
    public boolean insert(Invitado value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO invitado VALUES (?, ?)");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setString(2, value.getEmpresa());
        
        boolean insertFromPerson = DaoPersona.getInstance().insert(value);
        if (insertFromPerson) {
            int result = this.insert.executeUpdate();
            return result == ROW_AFFECTED_SUCCESSFULLY;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Invitado value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
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
        
        if (delete == null) {
            this.delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM invitado WHERE codigo=?");
        }
        
        this.delete.setString(1, value);
        
        boolean deleteFromPerson = DaoPersona.getInstance().delete(value);
        if (deleteFromPerson) {
            int result = this.delete.executeUpdate();
            return result == ROW_AFFECTED_SUCCESSFULLY;
        } else {
            return false;
        }
    }

    @Override
    public Invitado find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invitado find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM invitado WHERE codigo=?");
        }
        
        this.find.setString(1, value);
        ResultSet executeQuery = this.find.executeQuery();
        
        Invitado invitado = null;
        
        if (executeQuery != null && executeQuery.next()) {
            invitado = convertResultTo(executeQuery);
        }
        
        return invitado;
    }

    @Override
    public List<Invitado> list() throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM invitado");
        }
        
        ResultSet executeQuery = this.list.executeQuery();
        List<Invitado> temp = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            temp.add(convertResultTo(executeQuery));
        }
        
        return temp;
    }
    
    private Invitado convertResultTo(ResultSet result) throws SQLException {
        String codigo = result.getString("codigo");
        String empresa = result.getString("empresa");
        Persona temp = DaoPersona.getInstance().find(codigo);
        return new Invitado(empresa, temp.getIdentificador(), temp.getNombre(), temp.getSegundoNombre(), temp.getApellidos(),
                temp.getCorreo(), temp.getTelefono(), temp.getCodigo(), temp.getPassword());
    }
    
    private static class DaoInvitadoHolder {

        private static final DaoInvitado INSTANCE = new DaoInvitado();
    }
}
