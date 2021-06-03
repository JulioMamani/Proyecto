/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.servicio.PacienteService;
import io.javalin.http.Context;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GsonUtils;

/**
 * Clase que se comunica con el frontend convierte los objetos tipo Paciente en
 * JSON
 *
 * @author Nagamine
 */
public class PacienteApi {

    /**
     * La clase service a inyectar, la cual nos devuelve los datos ya procesados
     */
    private PacienteService pacienteService = new PacienteService();

    /**
     * Genera los logs
     */
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Devuelve la lista de pacientes
     *
     * @param ctx Contiene los datos que se envian y se reciben del front
     * @see Paciente
     */
    public void getPaciente(Context ctx) { // get:/paciente/:id
        String dni = ctx.pathParam(":id");
        Paciente paciente = pacienteService.BuscarPorNumDocumento(dni);
        if (paciente != null) {
            logger.info("Se busco el paciente: " + paciente);
            ctx.status(200); //200 = OK
            ctx.json(paciente);
        } else {
            logger.info("el paciente con el numero de documento " + dni + " no existe");
            ctx.status(404); //404 = no encontrado
        }

    }

    /**
     * metodo post para añadir el paciente
     *
     *@param ctx Contiene los datos que se envian y se reciben del front
     * @see Paciente
     */
    public void postAddPaciente(Context ctx) { // post:/paciente/:id
        Paciente paciente = ctx.bodyAsClass(Paciente.class);
        boolean aniadido = pacienteService.aniadirPaciente(paciente);//se añade paciente
        if (aniadido) {
            logger.info("se creo " + paciente.toString()); //Logger para imprimir en consola que se creo
            //codigo de respuesta 201 que significa creado
            ctx.status(201); // 201 Created
            ctx.json(paciente); //rtorna json del objeto creado
        } else {
            //codigo de respuesta 422 que significa error
            ctx.status(422);
            logger.info("error al crear");//Logger para imprimir en consola que se hubo error
        }

    }
    
    public void postEditPaciente(Context ctx) { // post:/paciente/:id
        Paciente paciente = ctx.bodyAsClass(Paciente.class);
        boolean editado = pacienteService.editarPaciente(paciente);//se añade paciente
        if (editado) {
            logger.info("se edito " + paciente.toString()); //Logger para imprimir en consola que se creo
            //codigo de respuesta 201 que significa creado
            ctx.status(201); // 201 Created
            ctx.json(paciente); //rtorna json del objeto creado
        } else {
            //codigo de respuesta 422 que significa error
            ctx.status(422);
            logger.info("error al editar");//Logger para imprimir en consola que se hubo error
            ctx.html("Error al editar");
        }

    }

}
