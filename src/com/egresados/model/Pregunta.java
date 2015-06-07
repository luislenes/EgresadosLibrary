/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.model;

import com.egresados.util.KeyGenerator;
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
public class Pregunta {
    
    private String codigo;
    
    private String enunciado;
    
    private final List<Opcion> opciones;
    
    private String codigoEncuesta;
    
    private boolean multiple;

    public Pregunta(String enunciado, String codigoEncuesta, boolean multiple) {
        this.codigo = KeyGenerator.getPin();
        this.enunciado = enunciado;
        this.codigoEncuesta = codigoEncuesta;
        this.opciones = new ArrayList<>();
        this.multiple = multiple;
        
        Opcion.reset(); //reseteando las opciones
    }

    public Pregunta(String codigo, String enunciado, List<Opcion> opciones,
            String codigoEncuesta, boolean multiple) {
        this.codigo = codigo;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.codigoEncuesta = codigoEncuesta;
        this.multiple = multiple;
        //opciones no reset porque ya estan ahi xD
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCodigoEncuesta() {
        return codigoEncuesta;
    }

    public void setCodigoEncuesta(String codigoEncuesta) {
        this.codigoEncuesta = codigoEncuesta;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }
    
    public boolean addOpcion(Opcion opcion) {
        return this.opciones.add(opcion);
    }
    
}
