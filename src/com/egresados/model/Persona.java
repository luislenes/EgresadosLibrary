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
public class Persona extends Usuario {

    protected String identificador;
    
    protected String nombre;
    
    protected String segundoNombre;
    
    protected String apellidos;
    
    protected String correo;
    
    protected String telefono;

    public Persona(String identificador, String nombre, String segundoNombre,
            String apellidos, String correo, String codigo, String password,
            TipoDeUsuario tipo) {
        super(codigo, password, tipo);
        this.identificador = identificador;
        this.nombre = nombre;
        this.segundoNombre = segundoNombre;
        this.apellidos = apellidos;
        this.correo = correo;
    }

    public Persona(String identificador, String nombre, String segundoNombre,
            String apellidos, String correo, String telefono, String codigo,
            String password, TipoDeUsuario tipo) {
        super(codigo, password, tipo);
        this.identificador = identificador;
        this.nombre = nombre;
        this.segundoNombre = segundoNombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Persona() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreCompletoAbreviado() {
        return String.format("%s %s", this.nombre, this.apellidos.split(" ")[0]);
    }

    public String getNombreCompleto() {
        return String.format("%s %s %s", this.nombre, this.segundoNombre, this.apellidos);
    }

}
