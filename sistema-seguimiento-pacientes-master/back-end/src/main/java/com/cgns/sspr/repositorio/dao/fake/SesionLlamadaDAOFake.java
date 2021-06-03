/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.entidad.SesionLlamada;
import com.cgns.sspr.repositorio.dao.SesionLlamadaDAO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Nagamine
 */
public class SesionLlamadaDAOFake implements SesionLlamadaDAO{
    
    
    @Override
    public boolean crearSesion(SesionLlamada sessionLlamada) {
        DatosRepoFake.getInstance().sesiones.add(sessionLlamada);
        return true;
    }

    @Override
    public List<SesionLlamada> getSesionesMedico(String idMedico) {
       return DatosRepoFake.getInstance().sesiones.stream().filter( i -> i.getMedico().getIdMedico().equals(idMedico)).collect(Collectors.toList());
    }

    @Override
    public List<SesionLlamada> getSesionesPaciente(String docPaciente) {
        return DatosRepoFake.getInstance().sesiones.stream().filter( i -> i.getPaciente().getNumDocumento().equals(docPaciente)).collect(Collectors.toList());
    }

    @Override
    public boolean finalizarSesion(int idSesion) {
       Optional<SesionLlamada> sesion = DatosRepoFake.getInstance().sesiones.stream().filter( i -> i.getId() == idSesion).findFirst();
       if(sesion.isPresent()){
           sesion.get().setFinalizado(true);
           return true;
       }else{
           return false;
       }
    }

    @Override
    public SesionLlamada getSesionById(int idSesion) {
       Optional<SesionLlamada> sesion = DatosRepoFake.getInstance().sesiones.stream().filter( i -> i.getId() == idSesion).findFirst();
       if(sesion.isPresent()){
   
           return sesion.get();
       }else{
           return null;
       }
    }
    
}
