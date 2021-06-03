/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.servicio;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.entidad.AnalisisResultado;
import com.cgns.sspr.repositorio.dao.fake.AnalisisDAOFake;
import com.cgns.sspr.repositorio.dao.AnalisisDAO;
import com.cgns.sspr.repositorio.dao.AnalisisResultadoDAO;
import com.cgns.sspr.repositorio.dao.implementacion.AnalisisDAOImpl;
import com.cgns.sspr.repositorio.dao.implementacion.AnalisisResultadoDAOImpl;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class AnalisisService {

    private AnalisisDAO analisisDao;
    private AnalisisResultadoDAO analisisResultadoDAO;
private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public AnalisisService() {
        if (!SecurityConfig.habilitarBD) {
            analisisDao = new AnalisisDAOFake();
        } else {
            analisisDao = new AnalisisDAOImpl();
            analisisResultadoDAO = new AnalisisResultadoDAOImpl();
        }
    }

    public Analisis getAnalisisById(int id) {
        logger.info("getAnalisisById");
        return getAnalisisResultado(analisisDao.getAnalisisById(id));
    }

    public List<Analisis> getAllAnalisisByDocPaciente(String documento) {
        logger.info("getAllAnalisisByDocPaciente");
        return getAnalisisResultadosList(analisisDao.getAllAnalisisByPaciente(documento));

    }

    public List<Analisis> getAllAnalisisByDiagnostico(int idDiagnostico) {
        logger.info("getAllAnalisisByDiagnostico");
        return getAnalisisResultadosList(analisisDao.getAllAnalisisByDiagnosticoId(idDiagnostico));

    }

    private Analisis getAnalisisResultado(Analisis analisis) {

        if (analisis != null) {
            List<AnalisisResultado> anal = analisisResultadoDAO.getResultadosByAnalisisId(analisis.getIdAnalisis());
            if (anal != null) {
                analisis.addResultados(anal);
            }
        }
        return analisis;

    }

    private List<Analisis> getAnalisisResultadosList(List<Analisis> analisis) {

        for (Analisis i : analisis) {
 
            i.addResultados(analisisResultadoDAO.getResultadosByAnalisisId(i.getIdAnalisis()));
        }
        return analisis;
    }
    public boolean solicitarAnalisis(Analisis analisis){
       logger.info("getAllAnalisisByDiagnostico");
        if(analisis!= null){
            //logger.info(analisis.toString());
             return analisisDao.solicitarAnalisis(analisis.getIdDiagnostico(),analisis.getDocPaciente(),analisis.getTipoAnalisis());
        }
       return false;
    }
    public boolean realizarAnalisis(Analisis analisis){
        logger.info("addAnalisis");
        if(analisis!= null){
            boolean a = analisisDao.realizarAnalisis(analisis.getIdAnalisis());
            boolean b = analisisResultadoDAO.addResultados(analisis.getIdAnalisis(), analisis.getResultados());
            return a && b; //si uno de los 2 es falso tonces falso
        }
        return false;
    }
}
