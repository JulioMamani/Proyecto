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
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://stackoverflow.com/questions/8993188/mapping-a-row-from-a-sql-data-to-a-java-object
 *
 * @author Nagamine
 */
public class SentenciaSqlBuilder {

    private String query;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection conn = null;
    private int numero = 1; 
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    

    public SentenciaSqlBuilder(String query) {
        this.query = query;
        try {
            conn = AccesoDB.getInstance().getConexion();
            ps = conn.prepareStatement(query);
        } catch (SQLException ex) {
            logger.error("error en sql", ex);
        }
    }

    public SentenciaSqlBuilder setParametro(Integer valor) {
        try {
            if (valor != null) {
                ps.setInt(numero, valor);
            } else {
                ps.setNull(numero, Types.INTEGER);
            }
            numero++;
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return this;
    }

    public SentenciaSqlBuilder setParametro(String valor) {
        try {
            if (valor != null) {
                ps.setString(numero, valor);
            } else {
                ps.setNull(numero, Types.VARCHAR);
            }
            numero++;
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return this;
    }

    public SentenciaSqlBuilder setParametro(Boolean valor) {
        try {
            if (valor != null) {
                ps.setBoolean(numero, valor);
            } else {
                ps.setNull(numero, Types.BOOLEAN);
            }
            numero++;
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return this;
    }

    public SentenciaSqlBuilder setParametro(LocalDateTime valor) {
        try {
            if (valor != null) {
                ps.setObject (numero, valor);
            } else {
                ps.setNull(numero, Types.TIME_WITH_TIMEZONE);
            }
            numero++;
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return this;
    }

    public <T> SentenciaSqlBuilder setParametroObjeto(T objeto, ConversorJavaABD<T> conversor) {

        conversor.convertir(objeto, this);
        return this;
    }

    public <T> SentenciaSqlBuilderBatch setParametroObjetoBatch(List<T> objetos, ConversorJavaABD<T> conversor) {
       
        try {
             int numeroActual = numero;
            for (T i : objetos) {
                numero = numeroActual;
                conversor.convertir(i, this);
                ps.addBatch();
            }
            
            
            
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return createSqlBatch();
    }

    public <T> List<T> getObjetosListFromBD(ConversorBDaJava<T> conversor) {

        List<T> lista = new ArrayList<>();
        try {
            for (rs = ps.executeQuery(); rs.next();) {
                lista.add(conversor.convertir(rs));
            }
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
                rs.close();
                ps.close();

                conn.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        return lista;
    }

    public <T> T getObjetoFromBD(ConversorBDaJava<T> conversor) {
        T objeto = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                objeto = conversor.convertir(rs);
            }
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(), ex);
            }

            return objeto;
        }
    }

    public boolean ejecutarUpdate() {
        boolean resultado = true;
        try {
            ps.execute();
            resultado = true;
        } catch (SQLException ex) {
            resultado = false;
            java.util.logging.Logger.getLogger(SentenciaSqlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(), ex);
            }

        }
        return resultado;
    }

    PreparedStatement getPs() {
        return ps;
    }

    ResultSet getRs() {
        return rs;
    }

    

    public void cerrarConexion(){
        try {
            if(ps != null){
                 ps.close();
            }
           
       if(rs != null){
           rs.close();
       }
        if(conn != null){
            conn.close();
        }
        
         } catch (SQLException ex) {
            logger.error(ex.getMessage(),ex);
        }
    }
    
    private SentenciaSqlBuilderBatch createSqlBatch() {
        return new SentenciaSqlBuilderBatch(this);
    }

}
