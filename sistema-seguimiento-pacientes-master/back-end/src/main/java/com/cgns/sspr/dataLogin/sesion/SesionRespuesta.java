/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.dataLogin.sesion;

import java.time.LocalDateTime;

/**
 *
 * @author Nagamine
 */
public class SesionRespuesta {
    private String idPaciente;
    private LocalDateTime fecha;

    public SesionRespuesta(String idPaciente, LocalDateTime fecha) {
        this.idPaciente = idPaciente;
        this.fecha = fecha;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "SesionRespuesta{" + "idPaciente=" + idPaciente + ", fecha=" + fecha + '}';
    }

    
}
