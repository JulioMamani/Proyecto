/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;

import com.cgns.sspr.repositorio.database.BdManager;
import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.repositorio.AccesoDB;
import com.cgns.sspr.repositorio.dao.AnalisisDAO;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class AnalisisDAOImpl implements AnalisisDAO {

    //Para poder almacenar la conexion
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //Para obtener la conexion
    private AccesoDB acceso;

    public AnalisisDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }

    @Override
    public List<Analisis> getAllAnalisisByPaciente(String documento) {
        String sql = "SELECT * FROM analisis where doc_paciente = ?";
        return BdManager.startQuery(sql)
                .setParametro(documento)
                .getObjetosListFromBD(rs -> getAnalisisFromDB(rs));
    }

    @Override
    public List<Analisis> getAllAnalisisByDiagnosticoId(int id) {
        String sql = "SELECT * FROM analisis where id_diagnostico = ?";
        return BdManager.startQuery(sql)
                .setParametro(id)
                .getObjetosListFromBD(rs -> getAnalisisFromDB(rs));
    }

    @Override
    public Analisis getAnalisisById(int id) {
        String sql = "SELECT * FROM analisis where id_analisis = ?";
        return BdManager.startQuery(sql)
                .setParametro(id)
                .getObjetoFromBD(rs -> getAnalisisFromDB(rs));

    }

    @Override
    public boolean eliminarAnalisis(int id) {
        String sql = "DELETE FROM analisis where id_analisis = ?";
        return BdManager.startQuery(sql).setParametro(id).ejecutarUpdate();
    }

    @Override
    public boolean solicitarAnalisis(int idDiagnostico, String docPaciente, String tipo) {
        String sql = "INSERT INTO analisis (id_diagnostico,doc_paciente,tipo_analisis,fecha_solicitud) VALUES (?, ?, ?,?)";
        return BdManager.startQuery(sql)
                .setParametro(idDiagnostico)
                .setParametro(docPaciente)
                .setParametro(tipo)
                .setParametro(LocalDateTime.now())
                .ejecutarUpdate();
    }

    @Override
    public boolean realizarAnalisis(int id) {
        String sql = "UPDATE analisis SET fecha_realizacion = ? WHERE id_analisis = ?";
        return BdManager.startQuery(sql)
                .setParametro(LocalDateTime.now())
                .setParametro(id)
                .ejecutarUpdate();
    }
    private Analisis getAnalisisFromDB(ResultSet rs) {
        Analisis anal = null;
        try {
            anal = new Analisis(
                    rs.getInt("id_analisis"),
                    rs.getInt("id_diagnostico"),
                    rs.getString("doc_paciente"),
                    rs.getString("tipo_analisis")
            );
            LocalDateTime realizacion = rs.getObject("fecha_realizacion",LocalDateTime.class);
            if (realizacion != null) {
                anal.setFecha_realizacion(realizacion);
            }
            LocalDateTime solicitud = rs.getObject("fecha_solicitud",LocalDateTime.class);
            if (realizacion != null) {
                anal.setFecha_solicitud(solicitud);
            }
          
        } catch (SQLException ex) {
            logger.error("Error in fetching user from DB: \n" + ex.getMessage());
        }
        return anal;
    }


}
