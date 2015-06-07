/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Egresado;
import com.egresados.model.PeriodoAcademico;
import com.egresados.model.Persona;
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
public class DaoEgresado implements DataAccessObject<Egresado, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement list;
    
    private DaoEgresado() {
        this.con = Connection.getInstance();
    }
    
    public static DaoEgresado getInstance() {
        return DaoEgresadoHolder.INSTANCE;
    }

    @Override
    public boolean insert(Egresado value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO egresado VALUES (?, ?, ?, ?, ?, ?)");
        }
        
        this.insert.setString(1, value.getCodigo());
        this.insert.setInt(2, value.getIngreso().getYear());
        this.insert.setInt(3, value.getIngreso().getPeriodo());
        this.insert.setInt(4, value.getSalida().getYear());
        this.insert.setInt(5, value.getSalida().getPeriodo());
        this.insert.setString(6, value.getPrograma().getId());
        
        boolean insertFromPersona = DaoPersona.getInstance().insert(value);
        if (insertFromPersona) {
            int result = this.insert.executeUpdate();
            return result == ROW_AFFECTED_SUCCESSFULLY;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Egresado value) throws SQLException {
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
                    + "DELETE FROM egresado WHERE codigo=?");
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
    public Egresado find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Egresado find(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (find == null) {
            this.find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM egresado WHERE codigo=?");
        }
        
        this.find.setString(1, value);
        ResultSet executeQuery = this.find.executeQuery();
        
        Egresado egresado = null;
        
        if (executeQuery != null && executeQuery.next()) {
            egresado = convertResultTo(executeQuery);
        }
        
        return egresado;
    }

    @Override
    public List<Egresado> list() throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM egresado");
        }
        
        ResultSet executeQuery = this.list.executeQuery();
        List<Egresado> temp = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            temp.add(convertResultTo(executeQuery));
        }
        
        return temp;
    }

    private Egresado convertResultTo(ResultSet executeQuery) throws SQLException {
        int yearIngreso = executeQuery.getInt("y-ingreso");
        int periodoIngreso = executeQuery.getInt("p-ingreso");
        int yearSalida = executeQuery.getInt("y-salida");
        int periodoSalida = executeQuery.getInt("p-salida");
        
        Programa programa = DaoPrograma.getInstance().find(executeQuery.getString("programa"));
        
        Persona p = DaoPersona.getInstance().find(executeQuery.getString("codigo"));
        
        return new Egresado(new PeriodoAcademico(yearIngreso, periodoIngreso),
                new PeriodoAcademico(yearSalida, periodoSalida),
                programa,
                p.getIdentificador(), p.getNombre(), p.getSegundoNombre(),
                p.getApellidos(), p.getCorreo(), p.getTelefono(), p.getCodigo(),
                p.getPassword());
    }
    
    private static class DaoEgresadoHolder {

        private static final DaoEgresado INSTANCE = new DaoEgresado();
    }
}
