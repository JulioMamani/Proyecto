/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;

import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.entidad.SesionLlamada;
import com.cgns.sspr.repositorio.AccesoDB;
import com.cgns.sspr.repositorio.dao.SesionLlamadaDAO;
import com.cgns.sspr.repositorio.database.BdManager;
import com.cgns.sspr.repositorio.database.SentenciaSqlBuilder;
import com.cgns.sspr.servicio.MedicoService;
import com.cgns.sspr.servicio.PacienteService;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class SesionLlamadaDAOImpl implements SesionLlamadaDAO {

    //Para poder almacenar la conexion
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //Para obtener la conexion
    private AccesoDB acceso;
    private MedicoService medicoService = new MedicoService();
    private PacienteService pacienteService = new PacienteService();

    public SesionLlamadaDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }

    @Override
    public boolean crearSesion(SesionLlamada sessionLlamada) {
        String sql = "INSERT INTO sesion_llamada VALUES (default, ?, ?, ?, default,?)";
        return BdManager.startQuery(sql)
                .setParametroObjeto(sessionLlamada, (SesionLlamada res, SentenciaSqlBuilder conversor) -> setSession(res,conversor))
                .ejecutarUpdate();
    }

    @Override
    public SesionLlamada getSesionById(int isSesion) {
        String sql = "SELECT * FROM sesion_llamada WHERE id = ?";
        return BdManager.startQuery(sql).setParametro(isSesion).getObjetoFromBD( rs ->getSessionFromBd(rs));
    }

    @Override
    public List<SesionLlamada> getSesionesMedico(String idMedico) {
       String sql = "SELECT * FROM sesion_llamada WHERE idmedico = ?";
        return BdManager.startQuery(sql).setParametro(idMedico).getObjetosListFromBD(rs ->getSessionFromBd(rs));
    }

    @Override
    public List<SesionLlamada> getSesionesPaciente(String docPaciente) {
        String sql = "SELECT * FROM sesion_llamada WHERE idpaciente = ?";
        return BdManager.startQuery(sql).setParametro(docPaciente).getObjetosListFromBD(rs ->getSessionFromBd(rs));
    }

    @Override
    public boolean finalizarSesion(int idSesion) {
        String sql = "UPDATE sesion_llamada SET finalizado = ? WHERE id = ?";
        return BdManager.startQuery(sql).setParametro(Boolean.TRUE).setParametro(idSesion).ejecutarUpdate();
    }
    private void setSession(SesionLlamada sesionLlamada, SentenciaSqlBuilder sql){
        sql.setParametro(sesionLlamada.getSessionId());
        sql.setParametro(sesionLlamada.getMedico().getIdMedico());
        sql.setParametro(sesionLlamada.getPaciente().getNumDocumento());
        sql.setParametro(sesionLlamada.getFecha());
    }
    private SesionLlamada getSessionFromBd(ResultSet rs) {
        try {
            int id = rs.getInt("id");

            String sessionid = rs.getString("sessionid");
            String medicoId = rs.getString("idmedico");
            String pacienteId = rs.getString("idPaciente");
            boolean finalizadp = rs.getBoolean("finalizado");
            Medico medico = medicoService.BuscarPorId(medicoId);
            Paciente paciente = pacienteService.BuscarPorNumDocumento(pacienteId);
            LocalDateTime localDateTime = rs.getObject("fecha", LocalDateTime.class);
            return new SesionLlamada(id,sessionid, medico, paciente, finalizadp, localDateTime);
        } catch (SQLException ex) {
            logger.error("error al convertir a objeto", ex);
        }
        return null;
    }
}
