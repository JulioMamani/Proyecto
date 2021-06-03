/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.entidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public class Analisis {
    private int idAnalisis;
    private int idDiagnostico;
    private String docPaciente;
    private String tipoAnalisis;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRealizacion;
    private List<AnalisisResultado> resultados = new ArrayList<>();

    public Analisis(int idAnalisis, int idDiagnostico, String docPaciente, String tipoAnalisis) {
        this.idAnalisis = idAnalisis;
        this.docPaciente = docPaciente;
        this.tipoAnalisis = tipoAnalisis;
    }

    public Analisis(int idAnalisis, int idDiagnostico, String docPaciente, String tipoAnalisis, LocalDateTime fecha_solicitud) {
        this.idAnalisis = idAnalisis;
        this.idDiagnostico = idDiagnostico;
        this.docPaciente = docPaciente;
        this.tipoAnalisis = tipoAnalisis;
        this.fechaSolicitud = fecha_solicitud;
    }

    public Analisis(int idAnalisis, String docPaciente, String tipoAnalisis, LocalDateTime fecha_realizacion) {
        this.idAnalisis = idAnalisis;
        this.docPaciente = docPaciente;
        this.tipoAnalisis = tipoAnalisis;
        this.fechaRealizacion = fecha_realizacion;
    }

    public Analisis(int idAnalisis, int idDiagnostico, String docPaciente, String tipoAnalisis, LocalDateTime fecha_solicitud, LocalDateTime fecha_realizacion) {
        this.idAnalisis = idAnalisis;
        this.idDiagnostico = idDiagnostico;
        this.docPaciente = docPaciente;
        this.tipoAnalisis = tipoAnalisis;
        this.fechaSolicitud = fecha_solicitud;
        this.fechaRealizacion = fecha_realizacion;
    }

    public int getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getDocPaciente() {
        return docPaciente;
    }

    public void setDocPaciente(String docPaciente) {
        this.docPaciente = docPaciente;
    }

    public String getTipoAnalisis() {
        return tipoAnalisis;
    }

    public void setTipoAnalisis(String tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    public LocalDateTime getFecha_solicitud() {
        return fechaSolicitud;
    }

    public void setFecha_solicitud(LocalDateTime fecha_solicitud) {
        this.fechaSolicitud = fecha_solicitud;
    }

    public LocalDateTime getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFecha_realizacion(LocalDateTime fecha_realizacion) {
        this.fechaRealizacion = fecha_realizacion;
    }

    public List<AnalisisResultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<AnalisisResultado> resultados) {
        this.resultados = resultados;
    }
    
    public void addResultado(AnalisisResultado result){
        resultados.add(result);
    }
    public void addResultados(List<AnalisisResultado> res){
        this.resultados.addAll(res);
    }

    @Override
    public String toString() {
        return "Analisis{" + "idAnalisis=" + idAnalisis + ", idDiagnostico=" + idDiagnostico + ", docPaciente=" + docPaciente + ", tipoAnalisis=" + tipoAnalisis + ", fechaSolicitud=" + fechaSolicitud + ", fechaRealizacion=" + fechaRealizacion + ", resultados=" + resultados + '}';
    }
    
   
    
}
