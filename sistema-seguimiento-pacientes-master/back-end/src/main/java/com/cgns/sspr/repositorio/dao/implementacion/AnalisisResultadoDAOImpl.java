/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;

import com.cgns.sspr.repositorio.database.BdManager;
import com.cgns.sspr.repositorio.database.ConversorJavaABD;
import com.cgns.sspr.repositorio.database.SentenciaSqlBuilder;
import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.entidad.AnalisisResultado;
import com.cgns.sspr.repositorio.AccesoDB;
import com.cgns.sspr.repositorio.dao.AnalisisResultadoDAO;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Nagamine
 */
public class AnalisisResultadoDAOImpl implements AnalisisResultadoDAO{
 //Para poder almacenar la conexion
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //Para obtener la conexion
    private AccesoDB acceso;
    
    public AnalisisResultadoDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }
    @Override
    public List<AnalisisResultado> getResultadosByAnalisisId(int id) {
       String sql = "SELECT id_analisis, campo , valor FROM analisis_resultados where id_analisis = ?";
         return BdManager.startQuery(sql).setParametro(id).getObjetosListFromBD(rs -> convertirAnalisisAJava(rs));
    }
    
     @Override
    public boolean addResultados(int id, List<AnalisisResultado> list) {
        String sql = "INSERT into analisis_resultados VALUES (default,?,?,?)";
        return BdManager.startQuery(sql)
                .<AnalisisResultado>setParametroObjetoBatch(list, (AnalisisResultado res, SentenciaSqlBuilder conversor) -> convertirAnalisisABD(res,conversor))
                .ejecutarBatch();
    }
    
    private AnalisisResultado convertirAnalisisAJava(ResultSet rs){
         AnalisisResultado anal = null;
        try {
            anal =new AnalisisResultado(
                    rs.getInt("id_analisis"),
                    rs.getString("campo"),
                    rs.getString("valor"));
        } catch (SQLException ex) {
            logger.error("Error in fetching user from DB: \n" + ex.getMessage());
        }
        return anal;
    }
    private void convertirAnalisisABD(AnalisisResultado result, SentenciaSqlBuilder sql){
        sql.setParametro(result.getIdAnalisis());
        sql.setParametro(result.getCampo());
        sql.setParametro(result.getValor());
    }


   
}
