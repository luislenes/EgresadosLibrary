/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.model;

/**
 *
 * @author Gustavo Pacheco
 * @author Roberto Ahumada
 * @author Miguel Estremor
 * @author Luis Lenes
 * @version 1.0
 */
public class Opcion {
    
    private static int ID = 1;
    
    private String codigo;
    
    private String cuerpo;
    
    private String codigoPregunta;

    public Opcion(String codigoEncuesta, String codigoPregunta, String opcion) {
        generarCodigo(codigoEncuesta, codigoPregunta);
        this.cuerpo = opcion;
        this.codigoPregunta = codigoPregunta;
    }

    public Opcion() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getCodigoPregunta() {
        return codigoPregunta;
    }

    public void setCodigoPregunta(String codigoPregunta) {
        this.codigoPregunta = codigoPregunta;
    }

    public static void reset() {
        Opcion.ID = 1;
    }

    private void generarCodigo(String codigoEncuesta, String codigoPregunta) {
        this.codigo = codigoEncuesta.concat(String.format("%s%02d", codigoPregunta, ID++));
    }
    
}
