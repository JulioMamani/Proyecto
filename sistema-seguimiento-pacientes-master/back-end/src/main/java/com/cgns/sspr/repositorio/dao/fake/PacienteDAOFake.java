/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.repositorio.dao.PacienteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO de pruebas
 * @author Nagamine
 */
public class PacienteDAOFake implements PacienteDAO{
    private List<Paciente> pacientes = DatosRepoFake.getInstance().pacientes;

    public PacienteDAOFake() {
       
    }
    
    @Override
    public List<Paciente> getAll() {
        return pacientes;
    }

    @Override
    public Paciente getPacienteByDocumento(String documento) {
        for ( Paciente i : pacientes){
            if (i.getNumDocumento().equals(documento)){
                return i;
            }
        }
        return null;
    }

    @Override
    public boolean addPaciente(Paciente paciente) {
        pacientes.add(paciente);
        return true;
    }
    
    @Override
    public boolean editPaciente(Paciente paciente){
        return true;
    }

    @Override
    public Paciente buscarPorLogin(int idLogin) {
         for ( Paciente i : pacientes){
            if (i.getLogin().getId() == idLogin){
                return i;
            }
        }
        return null;
    }
    
}
