/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.servicio.AnalisisService;
import io.javalin.http.Context;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class AnalisisApi {
    AnalisisService analsisService = new AnalisisService();
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public void getAnalisisID(Context ctx){
        String id = ctx.pathParam(":id");
        if(id!=null && !id.equals("")){
            Integer i = Integer.parseInt(id);
            Analisis analisis = analsisService.getAnalisisById(i);
            if (analisis != null){
                   logger.info("Se busco el analisis " + analisis);
            ctx.json(analisis);
            ctx.status(200);
            }else{
                 ctx.status(404);
            logger.info("no se encontro el analisis con id " + id);
            }
         
        }else{
            ctx.status(404);
            logger.info("Error con el parametro");
        }
        
    }
    public void postSolicitarAnalisis(Context ctx){
         Analisis analisis = ctx.bodyAsClass(Analisis.class);
        
        boolean aniadido = analsisService.solicitarAnalisis(analisis);
        if (aniadido) {
            logger.info("se creo " + analisis.toString()); //Logger para imprimir en consola que se creo
            //codigo de respuesta 201 que significa creado
            ctx.status(201); // 201 Created
            ctx.json(analisis); //rtorna json del objeto creado
        } else {
            //codigo de respuesta 422 que significa error
            ctx.status(422);
            logger.info("Error con al crear");
        }
    }
     public void patchRealizarAnalisis(Context ctx){
         Analisis analisis = ctx.bodyAsClass(Analisis.class);
        
        boolean aniadido = analsisService.realizarAnalisis(analisis);
        if (aniadido) {
            logger.info("se creo " + analisis.toString()); //Logger para imprimir en consola que se creo
            //codigo de respuesta 201 que significa creado
            ctx.status(201); // 201 Created
            ctx.json(analisis); //rtorna json del objeto creado
        } else {
            //codigo de respuesta 422 que significa error
            ctx.status(422);
            logger.info("Error con al crear");
        }
    }
}
