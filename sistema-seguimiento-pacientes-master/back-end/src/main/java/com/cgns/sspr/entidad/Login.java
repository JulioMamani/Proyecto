/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.entidad;

import com.cgns.sspr.dataLogin.Login.TipoUser;

/**
 *Nombre de tabla Login
 * @author Nagamine
 */
public class Login {
    private int id;
    private String username;
    private String password;
    private TipoUser tipoUser;

    public Login(int id, String username, String password, TipoUser tipoUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipoUser = tipoUser;
    }
    public Login(String username, String password, TipoUser tipoUser) {
        this.id = -1;
        this.username = username;
        this.password = password;
        this.tipoUser = tipoUser;
    }
    public Login(String username, String password) {
        this.id = -1;
        this.username = username;
        this.password = password;
        this.tipoUser = TipoUser.NINGUNO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUser getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(TipoUser tipoUser) {
        this.tipoUser = tipoUser;
    }

    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", tipoUser=" + tipoUser + '}';
    }
    
}
