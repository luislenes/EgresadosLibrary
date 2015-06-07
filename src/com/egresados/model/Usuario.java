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
public class Usuario {
    
    protected String codigo;
    
    private String password;

    protected TipoDeUsuario tipo;
    
    public Usuario(String codigo, String password) {
        this.codigo = codigo;
        this.password = password;
    }

    public Usuario(String codigo, String password, TipoDeUsuario tipo) {
        this.codigo = codigo;
        this.password = password;
        this.tipo = tipo;
    }

    public Usuario() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoDeUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeUsuario tipo) {
        this.tipo = tipo;
    }
    
}
