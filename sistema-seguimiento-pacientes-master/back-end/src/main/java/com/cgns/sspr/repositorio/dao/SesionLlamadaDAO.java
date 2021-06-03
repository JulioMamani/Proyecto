/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.SesionLlamada;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public interface SesionLlamadaDAO {

    public boolean crearSesion(SesionLlamada sessionLlamada);
    public SesionLlamada getSesionById(int isSesion);
    public List<SesionLlamada> getSesionesMedico(String idMedico);
    public List<SesionLlamada> getSesionesPaciente(String docPaciente);
    public boolean finalizarSesion(int idSesion);
}
