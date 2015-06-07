/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.dao;

import com.egresados.connection.Connection;
import com.egresados.connection.DataAccessObject;
import com.egresados.model.Administrador;
import com.egresados.model.Encuesta;
import com.egresados.model.Opcion;
import com.egresados.model.Pregunta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class DaoEncuesta implements DataAccessObject<Encuesta, String> {
    
    private final Connection con;
    
    private PreparedStatement insert;
    private PreparedStatement count;
    private PreparedStatement find;
    private PreparedStatement list;
    private PreparedStatement delete;
    private PreparedStatement update;
    
    private DaoEncuesta() {
        con = Connection.getInstance();
    }
    
    public static DaoEncuesta getInstance() {
        return DaoEncuestaHolder.INSTANCE;
    }

    @Override
    public boolean insert(Encuesta value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (insert == null) {
            insert = con.getConnection().prepareStatement(""
                    + "INSERT INTO encuesta VALUES(?, ?, NOW(), ?, ?)");
        }
        
        insert.setString(1, value.getCodigo());
        insert.setString(2, value.getNombre());
        insert.setString(3, value.getCreador().getCodigo());
        insert.setBoolean(4, false);
        
        return insert.executeUpdate() == ROW_AFFECTED_SUCCESSFULLY;
    }
    
    public int count() throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (count == null) {
            count = con.getConnection().prepareStatement(""
                    + "SELECT COUNT(*) AS cantidad FROM encuesta");
        }
        
        ResultSet executeQuery = count.executeQuery();
        return executeQuery != null && executeQuery.next() ? executeQuery.getInt("cantidad") : -1;
    }

    @Override
    public boolean update(Encuesta value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (update == null) {
            update = con.getConnection().prepareStatement(""
                    + "UPDATE encuesta SET habilitado=?, nombre=? WHERE codigo=?");
        }
        
        update.setBoolean(1, value.isHabilitado());
        update.setString(2, value.getNombre());
        update.setString(3, value.getCodigo());
        
        //update question in poll
        DaoPregunta.getInstance().delete(null, value.getCodigo()); //remove all poll question
        for (Pregunta question : value.getPreguntas()) {
            DaoPregunta.getInstance().insert(question); //inserting new questions in the poll
            for (Opcion option : question.getOpciones()) {
                DaoOpcion.getInstance().insert(option); //inserting the options by question only
            }
        }
        
        return update.executeUpdate() == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public boolean delete(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String codigo) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (delete == null) {
            delete = con.getConnection().prepareStatement(""
                    + "DELETE FROM encuesta WHERE codigo=?");
        }
        
        delete.setString(1, codigo);
        
        boolean delete1 = DaoPregunta.getInstance().delete(null, codigo);
        
        return delete1 && delete.executeUpdate() == ROW_AFFECTED_SUCCESSFULLY;
    }

    @Override
    public Encuesta find(String column, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Encuesta find(String value) throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (find == null) {
            find = con.getConnection().prepareStatement(""
                    + "SELECT * FROM encuesta WHERE codigo = ?");
        }
        
        find.setString(1, value);
        ResultSet executeQuery = find.executeQuery();
        return executeQuery != null & executeQuery.next() ? convertoResultTo(executeQuery) : null;
    }

    @Override
    public List<Encuesta> list() throws SQLException {
        if (!con.isConnect()) {
            this.con.connect();
        }
        
        if (list == null) {
            list = con.getConnection().prepareStatement(""
                    + "SELECT * FROM encuesta ORDER BY creado DESC");
        }
        
        List<Encuesta> encuestas = new ArrayList<>();
        
        ResultSet executeQuery = list.executeQuery();
        while (executeQuery != null && executeQuery.next()) {
            encuestas.add(convertoResultTo(executeQuery));
        }
        
        return encuestas;
    }
    
    private Encuesta convertoResultTo(ResultSet result) throws SQLException {
        String codigo = result.getString("codigo");
        String nombre = result.getString("nombre");
        Timestamp fecha = result.getTimestamp("creado");
        Administrador admin = DaoAdministrador.getInstance().find(result.getString("creador"));
        boolean habilitado = result.getBoolean("habilitado");
        List<Pregunta> preguntas = DaoPregunta.getInstance().listByCode(codigo);
        Encuesta encuesta = new Encuesta(codigo, nombre, fecha, preguntas, admin);
        encuesta.setHabilitado(habilitado);
        return encuesta;
    }
    
    private static class DaoEncuestaHolder {
        private static final DaoEncuesta INSTANCE = new DaoEncuesta();
    }
}
