/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.servicio.MedicoService;
import io.javalin.http.Context;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo
 */
public class MedicoApi {
    
    private MedicoService medicoService = new MedicoService();
    
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
      
      public void getMedico(Context ctx){
          System.out.println("llego");// get:/paciente/:id
         String id = ctx.pathParam(":id");
         Medico medico = medicoService.BuscarPorId(id);
          System.out.println(id);
         if(medico != null){
             logger.info("Se busco el paciente: " + medico);
             ctx.status(200); //200 = OK
             ctx.json(medico);
         } else{
             logger.info("el medico con el codigo " + id + " no existe");
             ctx.status(404); //404 = no encontrado
             ctx.html("No encontrado");
         }
         
    }
      
      public void postAddMedico(Context ctx) { // post:/paciente/:id
    
        Medico medico = ctx.bodyAsClass(Medico.class);//Se transforma el JSON a clase Medico
        boolean aniadido = medicoService.aniadirMedico(medico);//se añade medico
        if (aniadido) {
            logger.info("se creo " + medico.toString()); //Logger para imprimir en consola que se creo
            //codigo de respuesta 201 que significa creado
            ctx.status(201); // 201 Created
            ctx.json(medico); //rtorna json del objeto creado
        } else {
            //codigo de respuesta 422 que significa error
            ctx.status(422);
            logger.info("error al crear");//Logger para imprimir en consola que se hubo error
            ctx.html("entidad no creada");
        }

    }
      
      public void postEditMedico(Context ctx) { // post:/paciente/:id
        Medico medico = ctx.bodyAsClass(Medico.class);
        boolean editado = medicoService.editarMedico(medico);//se añade paciente
        if (editado) {
            logger.info("se edito " + medico.toString()); //Logger para imprimir en consola que se creo
            //codigo de respuesta 201 que significa creado
            ctx.status(201); // 201 Created
            ctx.json(medico); //rtorna json del objeto creado
        } else {
            //codigo de respuesta 422 que significa error
            ctx.status(422);
            logger.info("error al editar");//Logger para imprimir en consola que se hubo error
            ctx.html("Error al editar");
        }

    }
}
