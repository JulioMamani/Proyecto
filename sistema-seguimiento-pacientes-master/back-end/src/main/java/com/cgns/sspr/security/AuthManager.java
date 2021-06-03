/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.security;

import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.entidad.Login;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que maneja los roles y quien puede acceder
 *
 * @author Nagamine
 */
public class AuthManager implements AccessManager {

    /**
     * Este metodo determina si se permite o no una petición
     *
     * @param handler parametro que permite pasar o bloquear
     * @param ctx el contexto recibe los datos del front
     * @param roles los roles permitidos
     * @throws Exception en caso haya error
     */
    private Logger logger = LoggerFactory.getLogger(AuthManager.class);

    @Override
    public void manage(Handler handler, Context ctx, Set<Role> roles) throws Exception {
        // se revisan los roles y si esta permitido se realiza handler.handle(ctx); 
        //si no esta permitido se devuelve ctx.status(401)

        if (SecurityConfig.habilitarSeguridad) {
            //Si el rol permitido es todos entonces lo acepta
            if (roles.contains(RolesUsuarios.TODOS)) {
                handler.handle(ctx);
                logger.info("permitido rol todos");
            } else {

                //sino obtiene el login del usuario
                Login login = ctx.sessionAttribute("user");
                //Luego compara si se intersectan los roles del login con los roles permitidos
                if (!Collections.disjoint(getRoles(login), roles)) {
                    //en caso se intersecta lo acepta
                    handler.handle(ctx);
                    logger.info("permitido");
                } else {
                    //en caso no es permitido
                    logger.info("no permitido");
                    ctx.status(401).json("No puedes pasar, safa nomas prro");
                }
            }
        } else {
            logger.info("Seguridad no habilitada, permitidas todas las rutas");
            handler.handle(ctx);
        }

    }

    /**
     * Metodo para obetener el rol basado en el tipoUser
     *
     * @param login el login pues que mas
     * @return un SET de los roles permitidos
     */
    private Set<RolesUsuarios> getRoles(Login login) {
        Set<RolesUsuarios> roles = new HashSet<>();
        if (login != null) {
            switch (login.getTipoUser()) {
                case MEDICO:
                    roles.add(RolesUsuarios.MEDICO_READ);
                    roles.add(RolesUsuarios.MEDICO_WRITE);
                    roles.add(RolesUsuarios.PACIENTE_READ); //todo lo que el paciente puede ver, el medico tmb 
                    break;
                case ADMIN:
                    roles.add(RolesUsuarios.ADMIN);
                    break;
                case PACIENTE:
                    roles.add(RolesUsuarios.PACIENTE_READ);
                    roles.add(RolesUsuarios.PACIENTE_WRITE);
                    break;
                case SUPERUSER: //tiene todos los permisos
                    roles.add(RolesUsuarios.MEDICO_READ);
                    roles.add(RolesUsuarios.MEDICO_WRITE);
                    roles.add(RolesUsuarios.PACIENTE_READ);
                    roles.add(RolesUsuarios.PACIENTE_WRITE);
                    roles.add(RolesUsuarios.ADMIN);
                    roles.add(RolesUsuarios.SUPERADMIN);
            }
        }
        return roles;
    }
}
