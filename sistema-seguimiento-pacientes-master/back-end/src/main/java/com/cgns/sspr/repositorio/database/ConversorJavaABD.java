/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.database;

import java.sql.PreparedStatement;

/**
 *
 * @author Nagamine
 */
public interface ConversorJavaABD<T> {
    public  void convertir(T objeto , SentenciaSqlBuilder sql);
}
