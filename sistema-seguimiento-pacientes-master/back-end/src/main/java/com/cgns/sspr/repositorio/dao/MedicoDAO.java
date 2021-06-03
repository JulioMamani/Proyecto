/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.Medico;
import java.util.List;
/**
 *
 * @author Eduardo
 */
public interface MedicoDAO {
    public List<Medico> getAll();
    public Medico getMedicoByID(String idMedico);
    public Medico getMedicoByLoginId(int loginId);
    public boolean addMedico(Medico medico);
    public boolean editMedico(Medico medico);
}
