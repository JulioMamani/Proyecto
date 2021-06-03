/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.servicio;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.dataLogin.sesion.OpentokData;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.entidad.SesionLlamada;
import com.cgns.sspr.opentok.OpentokConfig;
import com.cgns.sspr.opentok.OpentokController;
import com.cgns.sspr.repositorio.dao.SesionLlamadaDAO;
import com.cgns.sspr.repositorio.dao.fake.SesionLlamadaDAOFake;
import com.cgns.sspr.repositorio.dao.implementacion.SesionLlamadaDAOImpl;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class SesionLlamadaService {
    //inyeccion de dependencias, se puede cambiar en cualquier momento incluso durante ejecucion

    private SesionLlamadaDAO sesiones;
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private MedicoService medicoService = new MedicoService();
    private PacienteService pacienteService = new PacienteService();
    private OpentokController opentokController = new OpentokController();

    public SesionLlamadaService() {
        if (!SecurityConfig.habilitarBD) {
            sesiones = new SesionLlamadaDAOFake();
        } else {
            sesiones = new SesionLlamadaDAOImpl();
        }
    }

    public boolean crearSesion(String paciente, Login usuario, LocalDateTime localDateTime) {
        Medico medico = medicoService.getMedicoFromLogin(usuario);

        if (medico != null) {
            String sessionid = opentokController.crearSesion();
            SesionLlamada sesion = new SesionLlamada(sessionid, medico, new Paciente("a", paciente), localDateTime);
            return sesiones.crearSesion(sesion);
        } else {
            logger.info("Solo los medicos pueden crear sesiones");
        }
        return false;
    }

    public OpentokData crearToken(int idSesion) {
        String sesion = sesiones.getSesionById(idSesion).getSessionId();
        String token = opentokController.crearToken(sesiones.getSesionById(idSesion).getSessionId());
        return new OpentokData(OpentokConfig.api_key, sesion, token);
    }

    public List<SesionLlamada> getAllSesionesMedico(String idMedico) {
        return sesiones.getSesionesMedico(idMedico);
    }

    public List<SesionLlamada> getAllSesionesPacientes(String idPaciente) {
        return sesiones.getSesionesPaciente(idPaciente);
    }

    public List<SesionLlamada> getAllSesionesUser(Login login) {
        if (login.getTipoUser() == TipoUser.MEDICO) {
            Medico medico = medicoService.getMedicoFromLogin(login);

            if (medico != null) {
                return getAllSesionesMedico(medico.getIdMedico());
            }
        }else if(login.getTipoUser() == TipoUser.PACIENTE){
            Paciente paciente = pacienteService.getPacienteFromLogin(login);
            if (paciente != null){
                return getAllSesionesPacientes(paciente.getNumDocumento());
            }
        }
        return new ArrayList<>();
    }
}
