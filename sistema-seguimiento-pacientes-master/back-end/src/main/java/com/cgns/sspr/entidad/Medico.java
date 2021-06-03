/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.entidad;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Eduardo
 */
public class Medico {
    private String nombre;
    @SerializedName("idMedico")
    private String idMedico;
    private String especialidad;
     private transient Integer loginID;

    public Medico( String idMedico,String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.idMedico = idMedico;        
    }
    public Medico( String idMedico,String nombre, String especialidad, Integer login) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.idMedico = idMedico;        
        this.loginID = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getLoginID() {
        return loginID;
    }

    public void setLoginID(Integer loginID) {
        this.loginID = loginID;
    }

    @Override
    public String toString() {
        return "Medico{" + "nombre=" + nombre + ", idMedico=" + idMedico + ", especialidad=" + especialidad + ", loginID=" + loginID + '}';
    }

 
}
