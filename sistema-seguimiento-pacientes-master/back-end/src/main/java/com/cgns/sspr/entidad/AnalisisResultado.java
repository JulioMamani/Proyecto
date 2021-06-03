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
public class AnalisisResultado {
    private int idAnalisis;
    private String campo;
    private String valor;

    public AnalisisResultado(int id_analisis, String campo, String valor) {
        this.idAnalisis = id_analisis;
        this.campo = campo;
        this.valor = valor;
    }

    public AnalisisResultado(String campo, String valor) {
        this.campo = campo;
        this.valor = valor;
    }

    public int getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    
   
    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "AnalisisResultado{" + "id_analisis=" + idAnalisis + ", campo=" + campo + ", valor=" + valor + '}';
    }
    
}
