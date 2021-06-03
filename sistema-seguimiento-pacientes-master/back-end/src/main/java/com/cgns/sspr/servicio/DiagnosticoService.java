/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.servicio;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.entidad.Diagnostico;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.repositorio.dao.AnalisisDAO;
import com.cgns.sspr.repositorio.dao.DiagnosticoDAO;
import com.cgns.sspr.repositorio.dao.fake.AnalisisDAOFake;
import com.cgns.sspr.repositorio.dao.fake.DiagnosticoDAOFake;
import com.cgns.sspr.repositorio.dao.implementacion.DiagnosticoDAOImpl;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class DiagnosticoService {

    private DiagnosticoDAO diagnosticoDAO;
    private AnalisisService analisisService = new AnalisisService();
    private MedicoService medicoService = new MedicoService();
    private PacienteService pacienteService = new PacienteService();
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public DiagnosticoService() {
        if (!SecurityConfig.habilitarBD) {
            diagnosticoDAO = new DiagnosticoDAOFake();
        } else {
            diagnosticoDAO = new DiagnosticoDAOImpl();
        }
    }

    public List<Diagnostico> buscarDiagnosticosPaciente(String nHistoria) {
        logger.info("buscarDiagnosticosPaciente");

        return getDiagnosticosAnalisisList(diagnosticoDAO.getDiagnosticosByDni(nHistoria));

    }

    public Diagnostico buscarDiagnosticoId(int id) {
        logger.info("buscarDiagnosticoId");
        return getDiagnosticoAnalisis(diagnosticoDAO.getDiagnosticoByid(id));
    }

    /*public boolean addDiagnostico(Diagnostico diagnostico){
        return diagnosticoDAO.addDiagnostico(diagnostico);
    }*/
    public boolean addDiagnostico(Diagnostico diagnostico, Login usuario) {

        Medico medico = medicoService.getMedicoFromLogin(usuario);
        if (medico != null) {

            diagnostico.setMedico(medico);
        } else {
            logger.info("Diagnostico Añadido por un NO medico");
        }

        logger.info("Usuario Null");
        return diagnosticoDAO.addDiagnostico(diagnostico);
    }

    public boolean updateDiagnostico(Diagnostico diagnostico) {
        logger.info("updateDiagnostico");
        return diagnosticoDAO.updateDiagnostico(diagnostico);
    }

    public boolean deleteDiagnostico(int diagnosticoId) {
        logger.info("deleteDiagnostico");
        return diagnosticoDAO.deleteDiagnostico(diagnosticoId);
    }

    private Diagnostico getDiagnosticoAnalisis(Diagnostico diagnostico) {
        diagnostico.addAllAnalisis(analisisService.getAllAnalisisByDiagnostico(diagnostico.getId()));
        return diagnostico;
    }

    private List<Diagnostico> getDiagnosticosAnalisisList(List<Diagnostico> diagnostico) {
        for (Diagnostico i : diagnostico) {
            i.addAllAnalisis(analisisService.getAllAnalisisByDiagnostico(i.getId()));
        }
        return diagnostico;
    }
    public List<Diagnostico>getDiagnosticosPaciente(Login login){
        Paciente paciente = pacienteService.getPacienteFromLogin(login);
        if(paciente != null){
            return buscarDiagnosticosPaciente(paciente.getNumDocumento());
        }
        return new ArrayList<>();
    }
    /**
     * Seleccion el dao
     *
     * @param diagnosticoDAO la clase dao a inyectar
     */
    public void setDao(DiagnosticoDAO diagnosticoDAO) {
        this.diagnosticoDAO = diagnosticoDAO;
    }
}
