/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.model;

import com.egresados.util.KeyGenerator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 * @author Roberto Ahumada
 * @author Miguel Estremor
 * @author Luis Lenes
 * @version 1.0
 */
public class Encuesta {
    
    private String codigo;
    
    private String nombre;
    
    private Timestamp fechaDeCreacion;
    
    private List<Pregunta> preguntas;
    
    private Administrador creador;
    
    private boolean habilitado;

    /**
     * Contructor general para crear nuevos objetos no almacenados en la base de
     * datos, este constructor te genera un pin de 4 digitos como codigo el cual
     * no esta en la base de datos.
     * 
     * @param creador Administrador quien creo la encuesta
     * @param nombre Nombre de la encuesta
     */
    public Encuesta(Administrador creador, String nombre) {
        this.codigo = KeyGenerator.getPin();
        this.nombre = nombre;
        this.fechaDeCreacion = Timestamp.valueOf(LocalDateTime.now());
        this.preguntas = new ArrayList<>();
        this.creador = creador;
    }

    /**
     * Constructor para construir objetos ya almacenados en la base de datos.
     * 
     * @param codigo 
     * @param nombre
     * @param fechaDeCreacion
     * @param preguntas
     * @param creador 
     */
    public Encuesta(String codigo, String nombre, Timestamp fechaDeCreacion,
            List<Pregunta> preguntas, Administrador creador) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaDeCreacion = fechaDeCreacion;
        this.preguntas = preguntas;
        this.creador = creador;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Timestamp fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Administrador getCreador() {
        return creador;
    }

    public void setCreador(Administrador creador) {
        this.creador = creador;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    public boolean addPregunta(Pregunta pregunta) {
        return this.preguntas.add(pregunta);
    }

}
