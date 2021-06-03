/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public class SecurityConfig {
    /**
     * Habilita o desahilita el sistema de login
     * Si esta en false, el login siempre dara verdadero y nunca pedira credenciales
     */
    public static boolean habilitarSeguridad = true;
    
    /**
     * Habilita o desahilita el encriptado SSL
     * Si esta en false, el las rutas redirigiran a hhtp si esta en true las rutas redirigen a https
     * Se necesita un certificado para que funcione
     */
    public static boolean habilitarHTTPS = false;
    
    /**
     * Habilitar test local
     * si es true se usa una bd local y se desactiva el atributo secure y samesite en la cookie
     */
    public static boolean habilitarPruebaLocal=false;
    /**
     * Habilita o desahilita el sistema de la bd
     * Si esta en false, se usara una bd fake 
     * Si esta en true sera necesario tener la bd mysql
     */
    public static boolean habilitarBD = true;
    
    
   public static List<String> rutasPermitidas = Arrays.asList("127.0.0.1:8080","localhost:8080","localtest.me:8080","sistema-seguimiento-pacientes-self.vercel.app");
    
}
