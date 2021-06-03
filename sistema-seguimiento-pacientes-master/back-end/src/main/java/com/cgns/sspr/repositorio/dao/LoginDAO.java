/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.Login;

/**
 *
 * @author Nagamine
 */
public interface LoginDAO {
    public Login getByUsuario(String usuario);
    public Login getById(int id);
    public boolean addLogin(Login login);
}
