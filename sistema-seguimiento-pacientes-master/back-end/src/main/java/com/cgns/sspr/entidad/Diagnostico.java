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
public class Diagnostico {
    private int idDiagnostico;
    private int numEdiciones;
    private String docPaciente;
    private String resumen;
    private String sintomas;
    private String diagnostico;
    private String receta;
    private boolean altoRiesgo;
    private LocalDateTime fecha;
    private Medico medico;
    private List<Analisis> analisis = new ArrayList<>();

    public Diagnostico(int id, int numEdiciones, String docPaciente, String resumen, String sintomas, String diagnostico, String receta, boolean altoRiesgo, LocalDateTime fecha, Medico medico) {
        this.idDiagnostico = id;
        this.numEdiciones = numEdiciones;
        this.docPaciente = docPaciente;
        this.resumen = resumen;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.receta = receta;
        this.altoRiesgo = altoRiesgo;
        this.fecha = fecha;
        this.medico = medico;
    }
     public Diagnostico(int id, int numEdiciones, String docPaciente, String resumen, String sintomas, String diagnostico, String receta, boolean altoRiesgo, LocalDateTime fecha) {
        this.idDiagnostico = id;
        this.numEdiciones = numEdiciones;
        this.docPaciente = docPaciente;
        this.resumen = resumen;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.receta = receta;
        this.altoRiesgo = altoRiesgo;
        this.fecha = fecha;
    }

    //sin id, numero de ediciones ni medico
    public Diagnostico(String docPaciente, String resumen, String sintomas, String diagnostico, String receta, boolean altoRiesgo, LocalDateTime fecha) {
        this.idDiagnostico = 0;
        this.docPaciente = docPaciente;
        this.resumen = resumen;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.receta = receta;
        this.altoRiesgo = altoRiesgo;
        this.fecha = fecha;
    }
    
    public int getNumEdiciones() {
        return numEdiciones;
    }

    public void setNumEdiciones(int numEdiciones) {
        this.numEdiciones = numEdiciones;
    }

    public String getDocPaciente() {
        return docPaciente;
    }

    public void setDocPaciente(String docPaciente) {
        this.docPaciente = docPaciente;
    }
    
    public int getId() {
        return idDiagnostico;
    }

    public void setId(int id) {
        this.idDiagnostico = id;
    }

    public String getNumDocumento() {
        return docPaciente;
    }

    public void setHistoriaPaciente(String historiaPaciente) {
        this.docPaciente = historiaPaciente;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public boolean isAltoRiesgo() {
        return altoRiesgo;
    }

    public void setAltoRiesgo(boolean altoRiesgo) {
        this.altoRiesgo = altoRiesgo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Analisis> getAnalisis() {
        return analisis;
    }

    public void setAnalisis(List<Analisis> analisis) {
        this.analisis = analisis;
    }
    public void addAllAnalisis(List<Analisis> analisis){
        this.analisis.addAll(analisis);
    }
    public void addAnalisis(Analisis analisis){
                this.analisis.add(analisis);
    }

    @Override
    public String toString() {
        return "Diagnostico{" + "idDiagnostico=" + idDiagnostico + ", numEdiciones=" + numEdiciones + ", docPaciente=" + docPaciente + ", resumen=" + resumen + ", sintomas=" + sintomas + ", diagnostico=" + diagnostico + ", receta=" + receta + ", altoRiesgo=" + altoRiesgo + ", fecha=" + fecha + ", medico=" + medico + ", analisis=" + analisis + '}';
    }
    

    
    
}
