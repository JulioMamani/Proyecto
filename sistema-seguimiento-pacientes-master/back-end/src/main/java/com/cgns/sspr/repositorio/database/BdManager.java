/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.database;

import com.cgns.sspr.repositorio.AccesoDB;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class BdManager<T> {
    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

   
    public static SentenciaSqlBuilder startQuery (String query){
        return new SentenciaSqlBuilder(query);
    }
    
  
}
