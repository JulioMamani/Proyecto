/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.dataLogin.sesion.OpentokData;
import com.cgns.sspr.dataLogin.sesion.SesionRespuesta;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.SesionLlamada;
import com.cgns.sspr.servicio.SesionLlamadaService;
import io.javalin.http.Context;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class SesionLlamadaApi {

    private SesionLlamadaService sesionService = new SesionLlamadaService();
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void postCrearSala(Context ctx) {

        SesionRespuesta sr = ctx.bodyAsClass(SesionRespuesta.class);
        Login usuario = ctx.sessionAttribute("user");
        if (sesionService.crearSesion(sr.getIdPaciente(), usuario, sr.getFecha())) {
            logger.info("sesion creada");
            ctx.status(200);
        } else {
            logger.info("error al crear sesion");
            ctx.status(400);
        }
    }

    public void getSesiones(Context ctx) {
        Login usuario = ctx.sessionAttribute("user");
        List<SesionLlamada> lista = sesionService.getAllSesionesUser(usuario);
        if (lista != null && !lista.isEmpty()) {
            logger.info("Obtenido: "+lista.toString());
            ctx.json(lista);
            ctx.status(200);
        } else {
            logger.info("error al obtener sesion paciente");
            ctx.status(400);
        }
    }


    public void postCrearToken(Context ctx) {
        String idSession = ctx.body();
        OpentokData datos = sesionService.crearToken(Integer.parseInt(idSession));
        if (datos != null) {
            ctx.json(datos);
            logger.info("Obtenido: "+datos.toString());
            ctx.status(200);
        } else {
            logger.info("error al crear token");
            ctx.status(400);
        }
    }
}
