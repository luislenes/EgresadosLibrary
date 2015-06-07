/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Persona;
import com.egresados.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoPersona implements DataAccessObject<Persona, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    
    private DaoPersona() {
        this.con = Connection.getInstance();
    }
    
    public static DaoPersona getInstance() {
        return DaoPersonaHolder.INSTANCE;
    }

    @Override
    public boolean insert(Persona value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO persona VALUES (?, ?, ?, ?, ?, ?, ?)");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setString(2, value.getIdentificador());
        this.insert.setString(3, value.getNombre());
        this.insert.setString(4, value.getSegundoNombre());
        this.insert.setString(5, value.getApellidos());
        this.insert.setString(6, value.getCorreo());
        this.insert.setString(7, value.getTelefono());
        
        boolean insertFromUsuario = DaoUsuario.getInstance().insert(value);
        if (insertFromUsuario) {
            int result = this.insert.executeUpdate();
            return result == ROW_AFFECTED_SUCCESSFULLY;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Persona value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (update == null) {
            this.update = con.getConnection().prepareStatement(""
                    + "UPDATE persona SET identidad=?, nombre=?, `segundo-nombre`=?, apellidos=?, correo=?, telefono=? WHERE codigo=?");
        }
        
        this.update.setString(1, value.getIdentificador());
        this.update.setString(2, value.getNombre());
        this.update.setString(3, value.getSegundoNombre());
        this.update.setString(4, value.getApellidos());
        this.update.setString(5, value.getCorreo());
        this.update.setString(6, value.getTelefono());
        this.update.setString(7, value.getCodigo());
        
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
        
        if (delete == null) {
            this.delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM persona WHERE codigo=?");
        }
        
        this.delete.setString(1, value);
        
        boolean deleteFromUsuario = DaoUsuario.getInstance().delete(value);
        if (deleteFromUsuario) {
            int result = this.delete.executeUpdate();
            return result == ROW_AFFECTED_SUCCESSFULLY;
        } else {
            return false;
        }
        
    }

    @Override
    public Persona find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM persona WHERE codigo=?");
        }
        
        this.find.setString(1, value);
        ResultSet executeQuery = this.find.executeQuery();
        
        Persona persona = null;
        
        if (executeQuery != null && executeQuery.next()) {
            persona = convertResultTo(executeQuery);
        }
        
        return persona;
    }

    @Override
    public List<Persona> list() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }
    
    private Persona convertResultTo(ResultSet result) throws SQLException {
        String nombre = result.getString("nombre");
        String segundoNombre = result.getString("segundo-nombre");
        String apellidos = result.getString("apellidos");
        String identidad = result.getString("identidad");
        String correo = result.getString("correo");
        String telefono = result.getString("telefono");
        Usuario temp = DaoUsuario.getInstance().find(result.getString("codigo"));
        return new Persona(identidad, nombre, segundoNombre, apellidos, correo,
                telefono, temp.getCodigo(), temp.getPassword(), temp.getTipo());
    }
    
    private static class DaoPersonaHolder {

        private static final DaoPersona INSTANCE = new DaoPersona();
    }
}
