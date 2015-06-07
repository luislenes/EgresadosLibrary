/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egresados.model;

/**
 *
 * @author Gustavo Pacheco
 */
public class PeriodoAcademico {
    
    private int year;
    
    private int periodo;
    
    /**
     * Representa el primer periodo academico de la universidad
     */
    public static final int PRIMER_PERIODO_ACADEMICO = 1;
    
    /**
     * Representa el segundo periodo academico de la universidad
     */
    public static final int SEGUNDO_PERIODO_ACADEMICO = 2;

    public PeriodoAcademico(int year, int periodo) {
        this.year = year;
        this.periodo = periodo;
    }

    public PeriodoAcademico() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

}
