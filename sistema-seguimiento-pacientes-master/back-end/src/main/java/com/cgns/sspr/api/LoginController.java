/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.api;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.dataLogin.Login.LoginRespuesta;
import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.servicio.LoginService;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.GsonUtils;

/**
 *
 * @author Nagamine
 */
public class LoginController {

    private LoginService loginService = new LoginService();
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void postLogin (Context ctx) { //post:

        Login user =ctx.bodyAsClass(Login.class);
        if (SecurityConfig.habilitarSeguridad) {
            if (user != null) {
                if (loginService.autenticar(user.getUsername(), user.getPassword())) {
                    Login login = loginService.getLoginByUser(user.getUsername());
                    ctx.sessionAttribute("user",login );
                    ctx.status(200);
                    logger.info(user.getUsername() + " autenticado");
                    ctx.json(new LoginRespuesta(true,login.getTipoUser(),"Autenticado exitosamente"));
                    //request.session().maxInactiveInterval(500);
                } else {
                    //response.status(401);
                    logger.info("fallo en autentcar" + user.getUsername());
                    //halt(401, "Fuera de aqui prro");
                    ctx.status(401);
                    ctx.json(new LoginRespuesta(false,TipoUser.NINGUNO,"Usuario o contraseña equivocada"));
                }
            } else {
                logger.info("Body vacio");
                ctx.status(401);
                ctx.json(new LoginRespuesta(false,TipoUser.NINGUNO,"error"));
            }
        } else {
            ctx.status(200);
            logger.info(" autenticado sin seguridad");
            ctx.json(new LoginRespuesta(true,TipoUser.SUPERUSER,"pase usted"));
        }

    }
    public void postLogout (Context ctx) {
        if (SecurityConfig.habilitarSeguridad) {
            Login user = ctx.sessionAttribute("user");
            if(user!=null){
                //colocamos la variable en null
                 ctx.sessionAttribute("user",null);
                 //invalidamos la sesion
                 ctx.req.getSession().invalidate();
                 
                ctx.status(201);
            logger.info(user.getUsername() + "deslogueado");
             ctx.html("deslogueado");
            }
            else{
                logger.info("el usuario no esta logueado");
                ctx.status(201);
                ctx.html("no hay sesion");
            }
           
        }else{
            logger.info("el usuario no tiene login");
            ctx.status(201);
            ctx.html( "sin login");
        }
    }
    
    private boolean checkLogged(Context ctx){
        if (SecurityConfig.habilitarSeguridad) {
            if (ctx.sessionAttribute("user") != null) {
                logger.info("autenticado");
                return true;
            } else {
                logger.info("no autenticado");
                return false;
            }
        } else {
             Login login = loginService.getLoginByUser("user");
                    ctx.sessionAttribute("user",login );
            return true;
        }
    }
    public void getCheckLogin(Context ctx) {
        if(checkLogged(ctx)){
            ctx.status(200);
            Login login = ctx.sessionAttribute("user");
            ctx.json(new LoginRespuesta(true,login.getTipoUser(),"autenticado exitosamente"));
        }else{
            ctx.status(401);
            ctx.json(new LoginRespuesta(false,TipoUser.NINGUNO,"fallo en autenticar"));
        }
    }
    public void postAddLogin(Context ctx) {
        Login login = ctx.bodyAsClass(Login.class);
        if(loginService.addLogin(login)){
             ctx.status(201);
             logger.info("Creado Login con usuario:" + login.getUsername());
            ctx.html("creado exitosamente");
            
        }else{
            ctx.status(422);
            logger.info("error al crear");//Logger para imprimir en consola que se hubo error
            ctx.html("entidad no creada"); 
        }
    }
}
