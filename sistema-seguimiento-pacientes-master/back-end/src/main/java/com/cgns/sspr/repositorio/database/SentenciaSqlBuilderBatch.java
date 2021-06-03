/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.database;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class SentenciaSqlBuilderBatch {

    private SentenciaSqlBuilder sqlBuilder;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public SentenciaSqlBuilderBatch(SentenciaSqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    public boolean ejecutarBatch() {
        
        boolean t= false;
        try {
            int[] numUpdates;

            numUpdates = sqlBuilder.getPs().executeBatch();

            /*for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2) {
                    System.out.println("Execution " + i
                            + ": unknown number of rows updated");
                } else {
                    System.out.println("Execution " + i
                            + "successful: " + numUpdates[i] + " rows updated");
                }
            }*/
            t = true;

        } catch (SQLException ex) {
            
            t= false;
            logger.error(ex.getMessage(),ex);
            
        }finally{
            sqlBuilder.cerrarConexion();
        }
        return t;
    }
}
