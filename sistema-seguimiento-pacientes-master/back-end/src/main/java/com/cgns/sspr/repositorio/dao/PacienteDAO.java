/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.Paciente;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public interface PacienteDAO {
    public List<Paciente> getAll();
    public Paciente getPacienteByDocumento(String nHistoria);
    public Paciente buscarPorLogin(int idLogin);
    public boolean addPaciente(Paciente paciente);
    public boolean editPaciente(Paciente paciente);
    
    //public Paciente getPacienteByDni(String dni);
    //public Paciente searchPacienteByNombre(String nombre);
}
