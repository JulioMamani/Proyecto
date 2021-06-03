/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.repositorio.dao.LoginDAO;
import java.util.ArrayList;

/**
 *
 * @author Nagamine
 */
public class LoginDAOFake implements LoginDAO{
    private ArrayList<Login> datos = DatosRepoFake.getInstance().login;
    public LoginDAOFake(){
        //La contraseña es 123456
      
    }
    @Override
    public Login getByUsuario(String usuario) {
        for (Login i : datos){
            if(i.getUsername().equalsIgnoreCase(usuario)){
                return i;
            }
        }
        return null;
    }

    @Override
    public Login getById(int id) {
        return datos.get(id);
    }

    @Override
    public boolean addLogin(Login login) {
        datos.add(login);
        return true;
    }
    
}
