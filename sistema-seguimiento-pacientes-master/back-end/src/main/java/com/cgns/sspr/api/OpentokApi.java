/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.opentok.OpentokConfig;
import com.cgns.sspr.opentok.OpentokController;
import com.cgns.sspr.dataLogin.sesion.OpentokData;
import io.javalin.http.Context;

/**
 *
 * @author Nagamine
 */
public class OpentokApi {
    private OpentokController opentokController = new OpentokController();
    public void crearToken(Context ctx){
        String token = opentokController.crearTokenGrupal();
        OpentokData data = new OpentokData(OpentokConfig.api_key, opentokController.getSession(), token);
        ctx.json(data);
    }
}
