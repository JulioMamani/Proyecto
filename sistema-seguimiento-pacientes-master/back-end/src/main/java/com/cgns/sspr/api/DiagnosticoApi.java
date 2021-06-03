/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Diagnostico;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.servicio.DiagnosticoService;
import com.cgns.sspr.servicio.MedicoService;
import io.javalin.http.Context;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class DiagnosticoApi {

    private DiagnosticoService diagnosticoService = new DiagnosticoService();
    private MedicoService medidcoService = new MedicoService();
    /**
     * Genera los logs
     */
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Envia un array en JSON de todos los diagnosticos del paciente
     *
     * @param ctx Contiene los datos que se envian y se reciben del front
     * Handler es lo que recibe la libreria para poder intercambiar los datos
     * @see Diagnostico
     */
    public void getDiagnosticosDocumento(Context ctx) { // get:/paciente/:id/diagnostico
        String documento = ctx.pathParam(":id");
        List<Diagnostico> diagnostico = diagnosticoService.buscarDiagnosticosPaciente(documento);
        if (!diagnostico.isEmpty()) {
            logger.info("Se busco el diagnostico: " + diagnostico);
            ctx.status(200); //200 = OK
            ctx.json(diagnostico);
        } else {
            logger.info("el paciente con el documento " + documento + " no existe o no tiene diagnosticos");
            ctx.status(404); //404 = no encontrado
        }

    }

    ;
    
    /**
     * Envia un Json con el diagnostico segun el ID
     * @param ctx Contiene los datos que se envian y se reciben del front
     * Handler es lo que recibe la libreria para poder intercambiar los datos
     * @see Diagnostico
     */
    public void getDiagnosticoId(Context ctx) { // get:/paciente/:id/diagnostico/:historia
        //obtiene el id por la url ejem: /diagnostico/123456
        String nhist = ctx.pathParam(":diagnosticoId");
        Diagnostico diagnostico = diagnosticoService.buscarDiagnosticoId(Integer.parseInt(nhist)); //obtiene el diagnostico
        if (diagnostico != null) {
            logger.info("Se busco el diagnostico: " + diagnostico);
            ctx.status(200); //200 = OK
            ctx.json(diagnostico); //devuelve un json de todos los diagnosticos
        } else {
            logger.info("el diagnostico " + nhist + " no existe");
            ctx.status(404); //404 = no encontrado
        }
    }

    /**
     * Procesa el POST para añadir un diagnostico
     *
     * @param ctx Contiene los datos que se envian y se reciben del front
     * Handler es lo que recibe la libreria para poder intercambiar los datos
     * @see Diagnostico
     */
    public void postAddDiagnostico(Context ctx) { // post:/paciente/:id/diagnostico
        Diagnostico diagnostico = ctx.bodyAsClass(Diagnostico.class); //El body recibido en JSON se convierte a clase
        if (diagnostico != null) {
            Login usuario = ctx.sessionAttribute("user");
           
            if (diagnosticoService.addDiagnostico(diagnostico, usuario)) {

                logger.info("se creo " + diagnostico.toString());
                ctx.status(201); // 201 Created
                ctx.json(diagnostico);
            } else {
                ctx.status(422);
            }
        }else{
            logger.error("Error al convertir objeto en diagnostico");
        }

    }

    public void patchUpdateDiagnostico(Context ctx){
         Diagnostico diagnostico = ctx.bodyAsClass(Diagnostico.class); //El body recibido en JSON se convierte a clase
        if (diagnostico != null) {
            Login usuario = ctx.sessionAttribute("user");
           
            if (diagnosticoService.updateDiagnostico(diagnostico)) {

                logger.info("se actualizo " + diagnostico.toString());
                ctx.status(200); // 200 Ok
                ctx.json(diagnostico);
            } else {
                logger.error("Error al convertir objeto en diagnostico");
                ctx.status(422);
            }
        }else{
            logger.error("Error al convertir objeto en diagnostico");
        }
    }
    
    public void deleteDiagnostico(Context ctx){
        String diagnosticoId = ctx.pathParam(":diagnosticoId");
        boolean resultado = diagnosticoService.deleteDiagnostico(Integer.parseInt(diagnosticoId)); //obtiene el diagnostico
        if (resultado) {
            logger.info("Se elimino el diagnostico: ");
            ctx.status(200); //200 = OK
        } else {
            logger.info("el diagnostico " + diagnosticoId + " no existe");
            ctx.status(404); //404 = no encontrado
        }
    }
    public void getDiagnosticoPaciente(Context ctx){
          Login usuario = ctx.sessionAttribute("user");
          List<Diagnostico> lista = diagnosticoService.getDiagnosticosPaciente(usuario);
          if(lista != null){
              ctx.json(lista);
              ctx.status(200);
          }else{
              ctx.status(400);
          }
    }
}
