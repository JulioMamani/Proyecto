/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.Analisis;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public interface AnalisisDAO {
    public List<Analisis> getAllAnalisisByPaciente(String documento);
    public List<Analisis> getAllAnalisisByDiagnosticoId(int id);
    public Analisis getAnalisisById(int id);
    public boolean eliminarAnalisis(int id);
    public boolean solicitarAnalisis(int idDiagnostico,String docPaciente,String tipo);
    public boolean realizarAnalisis(int id);
}
