/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.dataLogin.Login;

/**
 *
 * @author Nagamine
 */
public class LoginRespuesta {
    private boolean isLogged;
    private TipoUser tipoUser;
    private String mensaje;

    public LoginRespuesta(boolean logged, TipoUser tipoUser, String mensaje) {
        this.isLogged = logged;
        this.tipoUser = tipoUser;
        this.mensaje = mensaje;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        this.isLogged = logged;
    }

    public TipoUser getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(TipoUser tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "CheckLogged{" + "logged=" + isLogged + ", tipoUser=" + tipoUser + ", mensaje=" + mensaje + '}';
    }
    
}
