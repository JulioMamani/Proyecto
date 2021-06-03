/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.Diagnostico;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public interface DiagnosticoDAO {
    public List<Diagnostico> getAll();
    public Diagnostico getDiagnosticoByid(int id);
    public List<Diagnostico> getDiagnosticosByDni(String nHistoria);
    public boolean addDiagnostico(Diagnostico diagnostico);
    public boolean deleteDiagnostico(int idDiagnostico);
    public boolean updateDiagnostico(Diagnostico diagnostico);
}
