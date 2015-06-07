/*
 * Copyright (c) 2013, Dets Group. All rights reserved.
 * This work is licensed under the Creative Commons Atribución-CompartirIgual 2.5 Colombia License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/2.5/co/.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

package com.egresados.connection;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco Gomez
 * @version 1.0
 * @param <T> Objeto Mapeado el cual tiene interacción con la base de datos.
 * @param <E> Tipo de dato de la llave primaria de la tabla
 */
public interface DataAccessObject<T, E> {
    
    /**
     * Expresa el minimo de filas afectadas por una ejecución de una sentencia
     * SQL.
     */
    static final int MINIMUM_OF_ROWS_AFFECTED = 0;
    
    /**
     * Expresa el numero de una fila afectada exitosamente.
     */
    static final int ROW_AFFECTED_SUCCESSFULLY = 1;
    
    
    /**
     * 
     * @param value
     * @return
     * @throws SQLException 
     */
    boolean insert(T value) throws SQLException;
    
    /**
     * 
     * @param value
     * @return
     * @throws SQLException 
     */
    boolean update(T value) throws SQLException;
    
    /**
     * 
     * @param column
     * @param value
     * @return
     * @throws SQLException 
     */
    boolean delete(String column, Object value) throws SQLException;
    
    /**
     * 
     * @param value
     * @return
     * @throws SQLException 
     */
    boolean delete(E value) throws SQLException;
    
    /**
     * 
     * @param column
     * @param value
     * @return
     * @throws SQLException 
     */
    T find(String column, Object value) throws SQLException;
    
    /**
     * 
     * @param value
     * @return
     * @throws SQLException 
     */
    T find(E value) throws SQLException;
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    List<T> list() throws SQLException;
    
}
