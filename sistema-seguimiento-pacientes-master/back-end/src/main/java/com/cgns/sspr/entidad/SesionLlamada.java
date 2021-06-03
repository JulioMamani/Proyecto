/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.entidad;

import java.time.LocalDateTime;

/**
 *
 * @author Nagamine
 */
public class SesionLlamada {
    private int id = 0;
    private String sessionId;
    private Medico medico;
    private Paciente paciente;
    private boolean finalizado;
    private LocalDateTime fecha;

    public SesionLlamada(String sessionId, Medico idMedico, Paciente idPaciente, LocalDateTime fecha) {
        this.sessionId = sessionId;
        this.medico = idMedico;
        this.paciente = idPaciente;
        this.fecha = fecha;
    }

    public SesionLlamada(int id,String sessionId, Medico idMedico, Paciente idPaciente, boolean finalizado, LocalDateTime fecha) {
        this.id = id;
        this.sessionId = sessionId;
        this.medico = idMedico;
        this.paciente = idPaciente;
        this.finalizado = finalizado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setIdMedico(Medico idMedico) {
        this.medico = idMedico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.paciente = idPaciente;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "SesionLlamada{" + "id=" + id + ", sessionId=" + sessionId + ", idMedico=" + medico + ", idPaciente=" + paciente + ", finalizado=" + finalizado + ", fecha=" + fecha + '}';
    }

   
    
}
