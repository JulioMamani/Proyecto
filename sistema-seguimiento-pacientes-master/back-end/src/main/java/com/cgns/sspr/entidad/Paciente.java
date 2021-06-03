/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.entidad;



/**
 *
 * @author Nagamine
 */
public class Paciente {
    private String nombres;
    private String numDocumento;
    private transient Login usuario; //transient significa que no se convertira a JSON al mandarlo por internet
                                     //no queremos que se envie el user y pass al buscar pacientes xd

    public Paciente(String nombre, String dni, Login usuario) {
        this.nombres = nombre;
        this.numDocumento = dni;
        this.usuario = usuario;
    }
    public Paciente(String nombre, String dni) {
        this.nombres = nombre;
        this.numDocumento = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

   
    public String getNumDocumento() {
        return numDocumento;
    }

    public void setDni(String dni) {
        this.numDocumento = dni;
    }

    public Login getLogin() {
        return usuario;
    }

    public void setUsuario(Login usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Paciente{" + "nombres=" + nombres + ", dni=" + numDocumento + ", usuario=" + usuario + '}';
    }
    
    
}
