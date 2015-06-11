/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Opcion;
import com.egresados.model.Respuesta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoRespuesta implements DataAccessObject<Respuesta, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement find;
    private PreparedStatement list;
    private PreparedStatement list2;
    private PreparedStatement list3;
    private PreparedStatement count;
    
    private DaoRespuesta() {
        this.con = Connection.getInstance();
    }
    
    public static DaoRespuesta getInstance() {
        return DaoRespuestaHolder.INSTANCE;
    }

    @Override
    public boolean insert(Respuesta value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (insert == null) {
            this.insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO respuesta VALUES (?, ?, ?, ?, NOW(), ?)");
        }
        
        this.insert.setString(1, value.getEncuesta());
        this.insert.setString(2, value.getEgresado());
        this.insert.setString(3, value.getPregunta());
        this.insert.setString(4, value.getContenido());
        this.insert.setString(5, value.getOpcion());
        
        int result = this.insert.executeUpdate();
        
        return result == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean update(Respuesta value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean update(String egresado, String encuesta, String pregunta, Opcion opcion) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (update == null) {
            update = con.getConnection().prepareStatement(""
                    + "UPDATE respuesta SET opcion=? WHERE encuesta=? AND pregunta=? AND egresado=?");
        }
        
        update.setString(1, opcion.getCodigo());
        update.setString(2, encuesta);
        update.setString(3, pregunta);
        update.setString(4, egresado);
        
        return update.executeUpdate() ==  ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean delete(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta find(String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Respuesta> list() throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list == null) {
            this.list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM respuesta ORDER BY egresado");
        }
        
        ResultSet executeQuery = this.list.executeQuery();
        List<Respuesta> respuestas = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            respuestas.add(convertResultTo(executeQuery));
        }
        
        return respuestas;
    }
    
    public int countAnswerByCodeQuestion(String code) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (count == null) {
            this.count = con.getConnection().prepareStatement(""
                    + "SELECT COUNT(*) AS cantidad FROM respuesta WHERE pregunta=?");
        }
        
        count.setString(1, code);
        
        ResultSet executeQuery = count.executeQuery();
        
        return executeQuery != null && executeQuery.next() ? executeQuery.getInt("cantidad") : -1;
    }
    
    public List<Respuesta> list(String value) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list2 == null) {
            this.list2 = con.getConnection().prepareStatement(""
                    + "SELECT * FROM respuesta WHERE encuesta = ?");
        }
        
        this.list2.setString(1, value);
        
        ResultSet executeQuery = this.list2.executeQuery();
        List<Respuesta> respuestas = new ArrayList<>();
        
        while (executeQuery != null && executeQuery.next()) {
            respuestas.add(convertResultTo(executeQuery));
        }
        
        return respuestas;
    }
    
    public List<Respuesta> list(String codePoll, String codeQuestion) throws SQLException {
        if (!con.isConnect()) {
            con.connect();
        }
        
        if (list3 == null) {
            list3 = con.getConnection().prepareStatement(""
                    + "SELECT * FROM respuesta WHERE encuesta=? AND pregunta=? AND opcion IS NULL");
        }
        
        this.list3.setString(1, codePoll);
        this.list3.setString(2, codeQuestion);
        
        ResultSet exe = this.list3.executeQuery();
        List<Respuesta> respuestas = new ArrayList<>();
        
        while (exe != null && exe.next()) {
            respuestas.add(convertResultTo(exe));
        }
        
        return respuestas;
    }

    private Respuesta convertResultTo(ResultSet executeQuery) throws SQLException {
        String encuesta = executeQuery.getString("encuesta");
        String egresado = executeQuery.getString("egresado");
        String pregunta = executeQuery.getString("pregunta");
        String contenido = executeQuery.getString("respuesta");
        Date fecha = executeQuery.getDate("fecha");
        String opcion = executeQuery.getString("opcion");
        return new Respuesta(pregunta, encuesta, contenido, egresado, opcion);
    }
    
    private static class DaoRespuestaHolder {
        private static final DaoRespuesta INSTANCE = new DaoRespuesta();
    }
}
