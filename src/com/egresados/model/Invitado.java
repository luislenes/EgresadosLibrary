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
public class Invitado extends Persona {
    
    private String empresa;

    public Invitado(String empresa, String identificador, String nombre,
            String segundoNombre, String apellidos, String correo,
            String codigo, String password) {
        super(identificador, nombre, segundoNombre, apellidos, correo, codigo, password, TipoDeUsuario.INVITADO);
        this.empresa = empresa;
    }

    public Invitado(String empresa, String identificador, String nombre,
            String segundoNombre, String apellidos, String correo,
            String telefono, String codigo, String password) {
        super(identificador, nombre, segundoNombre, apellidos, correo, telefono, codigo, password, TipoDeUsuario.INVITADO);
        this.empresa = empresa;
    }

    public Invitado() {
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
}
