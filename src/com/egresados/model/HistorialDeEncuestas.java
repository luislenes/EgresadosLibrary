/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco
 */
public class HistorialDeEncuestas {
    
    private String codigo;
    
    private Egresado egresado;
    
    private List<Respuesta> respuestas;
    
    private Timestamp fecha;

    public HistorialDeEncuestas(String codigo, Egresado egresado) {
        this.codigo = codigo;
        this.egresado = egresado;
        this.respuestas = new ArrayList<>();
        this.fecha = Timestamp.valueOf(LocalDateTime.now());
    }

    public HistorialDeEncuestas(String codigo, Egresado egresado,
            List<Respuesta> respuestas, Timestamp fecha) {
        this.codigo = codigo;
        this.egresado = egresado;
        this.respuestas = respuestas;
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Egresado getEgresado() {
        return egresado;
    }

    public void setEgresado(Egresado egresado) {
        this.egresado = egresado;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
}
