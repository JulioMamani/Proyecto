/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nagamine
 */
public interface ConversorBDaJava <T> {
     public T convertir(ResultSet i_rs) throws SQLException;
}
