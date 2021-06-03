/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.servicio;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import com.cgns.sspr.SecurityConfig;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.repositorio.dao.LoginDAO;
import com.cgns.sspr.repositorio.dao.fake.LoginDAOFake;
import com.cgns.sspr.repositorio.dao.implementacion.LoginDAOImpl;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Nagamine
 */
public class LoginService {
private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private LoginDAO loginDao = new LoginDAOImpl();
    public LoginService(){
        if (!SecurityConfig.habilitarBD){
            loginDao = new LoginDAOFake();
        }
    }
    /**
     * Se pasa el usuario y contraseña y se verifica si tiene credencial
     * @param user
     * @param password
     * @return true o false
     */
    public boolean autenticar(String user, String password) {
        logger.info("autenticar");
        //obtiene el usuario del DAO
        Login login = loginDao.getByUsuario(user);
        //si el usuario no existe retorna null
        if(login != null){
            //checa la contraseña con con el hash encriptado guardado en el server
           Result result= BCrypt.verifyer().verify(password.toCharArray(), login.getPassword()); 
           return result.verified;
        }
        return false;
    }
    public boolean addLogin(Login login){
        logger.info("addLogin");
        String pass = login.getPassword();
        //encripta la contraseña antes de guardarla en la BD
        login.setPassword(BCrypt.withDefaults().hashToString(10,pass.toCharArray()));
        return loginDao.addLogin(login);
    }
    public Login getLoginByUser(String user){
        logger.info("getLoginByUser");
        Login login = loginDao.getByUsuario(user);
        return login;
    }
}
