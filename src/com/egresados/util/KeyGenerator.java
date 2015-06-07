/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.egresados.util;


/**
 *
 * @author Gustavo Pacheco
 * @author Roberto Ahumada
 * @author Miguel Estremor
 * @author Luis Lenes
 * @version 1.0
 */
public class KeyGenerator {
    
    /**
     * sample of numbers
     */
    private static final String NUMBERS = "0123456789";
    
    /**
     * sample of uppercases characters
     */
    private static final String MAYUS = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
    
    /**
     * sample of lowercases characters
     */
    private static final String MINUS = "abcdefghijklmnopqrstuwxyz";
    
    /**
     * sample of special characters
     */
    private static final String SPECIAL = "ñÑ@$%&";
    
    /**
     * Este metodo te genera un pin de 4 digitos
     * @return pin generado
     */
    public static String getPin() {
        return getKey(NUMBERS, 4);
    }
    
    /**
     * Este metodo te genera un pin de tamaño especificado.
     * @param length numero de caracteres para el pin.
     * @return pin generado
     */
    public static String getPin(int length) {
        return getKey(NUMBERS, length);
    }
    
    /**
     * Este metodo te genera una contraseña aleatoria entre 3 y 8 caracteres
     * @return contraseña generada
     */
    public static String getPassword() {
        return getKey(MAYUS + NUMBERS + MINUS ,(int) (3 * Math.random() + 8));
    }
    
    /**
     * Este metodo te genera una contraseña por el numero de caracteres dados.
     * @param length el numero de caracteres de la contraseña
     * @return contraseña generada
     */
    public static String getPassword(int length) {
        return getKey(MAYUS + NUMBERS + MINUS , length);
    }
    
    /**
     * Metodo no implementado.
     * @param length
     * @return 
     */
    public static String getSerial(int length) {
        return "";
    }
    
    /**
     * Este metodo me genera una linea de caracteres aleatorios a partir de un 
     * patron.
     * @param sample patron.
     * @param length tamaño de caracteres
     * @return caracteres generados.
     */
    public static String getKey(String sample, int length) {
        String key = "";
        for (int i = 0; i < length; i++)
            key += sample.charAt((int) (Math.random() * sample.length()));
        return key;
    }
    
}
