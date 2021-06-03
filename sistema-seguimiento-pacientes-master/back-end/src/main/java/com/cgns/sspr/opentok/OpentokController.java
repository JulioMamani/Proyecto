/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.opentok;

import com.opentok.OpenTok;
import com.opentok.exception.OpenTokException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nagamine
 */
public class OpentokController {
    private String session = "";
    private OpenTok opentok ;
    public OpentokController(){
        opentok = new OpenTok(OpentokConfig.api_key,OpentokConfig.api_secret);
        crearSesionGrupal();
    }
    public void crearSesionGrupal(){
        try {
            session = opentok.createSession().getSessionId();
        } catch (OpenTokException ex) {
            Logger.getLogger(OpentokController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public String crearTokenGrupal(){ //El grupal usa una sesion unica
         String token = null;
         try {
            token = opentok.generateToken(session);
        } catch (OpenTokException e) {
            e.printStackTrace();
        }
        return token;
    }
     
    public String crearSesion(){
        try {
           String ss = opentok.createSession().getSessionId();
           return ss;
        } catch (OpenTokException ex) {
            Logger.getLogger(OpentokController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String crearToken(String session){
         String token = null;
         try {
            token = opentok.generateToken(session);
        } catch (OpenTokException e) {
            e.printStackTrace();
        }
        return token;
    }
   
    

    public String getSession() {
        return session;
    }
    
}
