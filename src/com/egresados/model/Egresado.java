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
public class Egresado extends Persona {
    
    private PeriodoAcademico ingreso;
    
    private PeriodoAcademico salida;
    
    private Programa programa;

    public Egresado(PeriodoAcademico ingreso, PeriodoAcademico salida,
            Programa programa, String identificador, String nombre,
            String segundoNombre, String apellidos, String correo,
            String codigo, String password) {
        super(identificador, nombre, segundoNombre, apellidos, correo, codigo, password, TipoDeUsuario.EGRESADO);
        this.ingreso = ingreso;
        this.salida = salida;
        this.programa = programa;
    }

    public Egresado(PeriodoAcademico ingreso, PeriodoAcademico salida,
            Programa programa, String identificador, String nombre,
            String segundoNombre, String apellidos, String correo,
            String telefono, String codigo, String password) {
        super(identificador, nombre, segundoNombre, apellidos, correo, telefono, codigo, password, TipoDeUsuario.EGRESADO);
        this.ingreso = ingreso;
        this.salida = salida;
        this.programa = programa;
    }

    public Egresado() {
    }

    public PeriodoAcademico getIngreso() {
        return ingreso;
    }

    public void setIngreso(PeriodoAcademico ingreso) {
        this.ingreso = ingreso;
    }

    public PeriodoAcademico getSalida() {
        return salida;
    }

    public void setSalida(PeriodoAcademico salida) {
        this.salida = salida;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
    
}
