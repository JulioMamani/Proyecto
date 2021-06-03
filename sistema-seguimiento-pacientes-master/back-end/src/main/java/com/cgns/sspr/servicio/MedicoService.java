/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.servicio;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.repositorio.dao.MedicoDAO;
import com.cgns.sspr.repositorio.dao.fake.MedicoDAOFake;
import com.cgns.sspr.repositorio.dao.implementacion.MedicoDAOImpl;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo
 */
public class MedicoService {
    
    private MedicoDAO medicos = new MedicoDAOImpl();
private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public MedicoService() {
        if (!SecurityConfig.habilitarBD){
            medicos = new MedicoDAOFake();
        }
    }
    
    public List<Medico> mostrarTodo() {
        logger.info("mostrarTodo");
        return medicos.getAll();
    }
    
    public Medico BuscarPorId(String idMedico) {
        logger.info("BuscarPorId");
        return medicos.getMedicoByID(idMedico);
    }
    
    public void setDao(MedicoDAO medicoDAO) {
        medicos = medicoDAO;
    }
    public boolean aniadirMedico(Medico medico) {
        logger.info("aniadirMedico");
        return medicos.addMedico(medico);
    }
    public Medico BuscarPorLogin(int loginId){
        logger.info("BuscarPorLogin");
        return medicos.getMedicoByLoginId(loginId);
    }
    public boolean editarMedico(Medico medico){
        logger.info("editarMedico");
        return medicos.editMedico(medico);
    }
    public Medico getMedicoFromLogin(Login login){
         if (login != null) {
                if (login.getTipoUser() == TipoUser.MEDICO) {
                    Medico medico = BuscarPorLogin(login.getId());
                    return medico;
                }else{
                    logger.info("EL usuario no es un medico");
                }
            }else{
                logger.info("Login no valido");
            }
         return  null;
    }
}
