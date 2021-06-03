/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.servicio;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.repositorio.dao.PacienteDAO;
import com.cgns.sspr.repositorio.dao.fake.PacienteDAOFake;
import com.cgns.sspr.repositorio.dao.implementacion.PacienteDAOImpl;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de logica de negocio que se comunicara con las clases de la api. Aqui
 * es donde se obtienen, se buscan los pacientes y se transforman al formato
 * requerido
 *
 * @author Nagamine
 */
public class PacienteService {

    //inyeccion de dependencias, se puede cambiar en cualquier momento incluso durante ejecucion
    private PacienteDAO pacientes = new PacienteDAOImpl();
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public PacienteService(){
        if (!SecurityConfig.habilitarBD){
            pacientes = new PacienteDAOFake();
        }
    }
    /**
     * Busca al paciente por DNI
     *
     * @return una lista con todos los pacientes
     * @see Paciente
    *
     */
    public List<Paciente> mostrarTodo() {
        logger.info("mostrarTodo");
        return pacientes.getAll();
    }

    /**
     * Busca al paciente por DNI
     *
     * @param dni el dni del paciente
     * @return el paciente buscado
     * @see Paciente
    *
     */
    public Paciente BuscarPorNumDocumento(String dni) {
        logger.info("BuscarPorNumDocumento");
        return pacientes.getPacienteByDocumento(dni);
    }
    
    /**
     * Busca al paciente por DNI
     *
     * @param paciente el paciente añadir
     * @return true si la operacion fue exitosa, false en otro caso
     * @see Paciente
    *
     */
    public boolean aniadirPaciente(Paciente paciente) {
        logger.info("aniadirPaciente");
        return pacientes.addPaciente(paciente);
    }

    /**
     * Seleccion el dao
     *
     * @param pacienteDAO la clase dao a inyectar
     */
    public void setDao(PacienteDAO pacienteDAO) {
        pacientes = pacienteDAO;
    }

    public boolean editarPaciente(Paciente paciente){
        logger.info("editarPaciente");
        return pacientes.editPaciente(paciente);
    }
     public Paciente getPacienteFromLogin(Login login){
         if (login != null) {
                if (login.getTipoUser() == TipoUser.PACIENTE) {
                    Paciente paciente = pacientes.buscarPorLogin(login.getId());
                    return paciente;
                }else{
                    logger.info("EL usuario no es un paciente");
                }
            }else{
                logger.info("Login no valido");
            }
         return  null;
    }
}
