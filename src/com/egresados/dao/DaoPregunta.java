/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Opcion;
import com.egresados.model.Pregunta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoPregunta implements DataAccessObject<Pregunta, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement list;
    private PreparedStatement deleteForColumn;
    private PreparedStatement delete;
    
    private DaoPregunta() {
        con = Connection.getInstance();
    }
    
    public static DaoPregunta getInstance() {
        return DaoPreguntaHolder.INSTANCE;
    }

    @Override
    public boolean insert(Pregunta value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (insert == null) {
            insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO pregunta VALUES (?, ?, ?, ?, ?)");
        }
        
        insert.setString(1, value.getCodigo());
        insert.setString(2, value.getCodigoEncuesta());
        insert.setString(3, value.getEnunciado());
        insert.setString(4, value.getOpciones().isEmpty() ? "ABIERTO" : "CERRADO");
        insert.setBoolean(5, value.isMultiple());
        
        return insert.executeUpdate() == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean update(Pregunta value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Este metodo ignora el parametro column para solo eliminar por codigo de
     * encuesta.
     * 
     * @param column parametro ignorado
     * @param value codigo de encuesta
     * @return true, si elimino las preguntas correspondientes
     * @throws SQLException 
     */
    @Override
    public boolean delete(String column, Object value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (deleteForColumn == null) {
            deleteForColumn = con.getConnection().prepareStatement(""
                    + "DELETE FROM pregunta WHERE encuesta=?");
        }
        
        deleteForColumn.setString(1, value.toString());
        
        //eliminando opciones
        List<Pregunta> preguntas = listByCode(value.toString());
        for (Pregunta pregunta : preguntas) {
            DaoOpcion.getInstance().delete(null, pregunta.getCodigo());
        }
        
        return deleteForColumn.executeUpdate() > MINIMUM_OF_ROWS_AFFECTED;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (delete == null) {
            delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM pregunta WHERE id=?");
        }
        
        delete.setString(1, id);
        
        //eliminar opciones
        boolean delete1 = DaoOpcion.getInstance().delete(null, id);
        
        return delete1 && delete.executeUpdate() == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public Pregunta find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pregunta find(String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Pregunta> list() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<Pregunta> listByCode(String encuesta) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (list == null) {
            list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM pregunta WHERE encuesta = ?");
        }
        
        list.setString(1, encuesta);
        
        List<Pregunta> preguntas = new ArrayList<>();
        ResultSet executeQuery = list.executeQuery();
        
        while (executeQuery != null && executeQuery.next()) {
            preguntas.add(convertResultTo(executeQuery));
        }
        
        return preguntas;
    }

    private Pregunta convertResultTo(ResultSet executeQuery) throws SQLException {
        String codigo = executeQuery.getString("id");
        String encuesta = executeQuery.getString("encuesta");
        String enunciado = executeQuery.getString("enunciado");
        boolean multiple = executeQuery.getBoolean("multiple");
        List<Opcion> opciones = DaoOpcion.getInstance().listByPregunta(codigo);
        return new Pregunta(codigo, enunciado, opciones, encuesta, multiple);
    }
    
    private static class DaoPreguntaHolder {

        private static final DaoPregunta INSTANCE = new DaoPregunta();
    }
}
