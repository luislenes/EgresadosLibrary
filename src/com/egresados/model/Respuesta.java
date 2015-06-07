/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.model;

import java.util.Date;

/**
 *
 * @author Gustavo Pacheco
 */
public class Respuesta {
    
    private String pregunta;
    private String encuesta;
    private String contenido;
    private String egresado;
    private Date fecha;
    private String opcion;

    public Respuesta(String pregunta, String encuesta, String contenido, String egresado, String opcion) {
        this.pregunta = pregunta;
        this.encuesta = encuesta;
        this.contenido = contenido;
        this.egresado = egresado;
        this.opcion = opcion;
    }

    public Respuesta(String pregunta, String encuesta, String contenido, String egresado) {
        this.pregunta = pregunta;
        this.encuesta = encuesta;
        this.contenido = contenido;
        this.egresado = egresado;
    }
    
    

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(String encuesta) {
        this.encuesta = encuesta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEgresado() {
        return egresado;
    }

    public void setEgresado(String egresado) {
        this.egresado = egresado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }   
}
