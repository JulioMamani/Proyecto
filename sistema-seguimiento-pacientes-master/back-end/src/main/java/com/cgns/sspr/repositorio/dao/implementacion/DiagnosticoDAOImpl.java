    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;

import com.cgns.sspr.entidad.Diagnostico;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.repositorio.AccesoDB;
import com.cgns.sspr.repositorio.dao.DiagnosticoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class DiagnosticoDAOImpl implements DiagnosticoDAO{
private PreparedStatement ps;
    //Para obtener el resultado de la ejecucion de la consulta
    private ResultSet rs;
    //Para poder almacenar la conexion
    private Logger logger = LoggerFactory.getLogger(DiagnosticoDAOImpl.class);
    //Para obtener la conexion
    private AccesoDB acceso;
    
    public DiagnosticoDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }
    
    @Override
    public List<Diagnostico> getAll() {
        Connection conexion = null;
        List<Diagnostico> listaDiagnostico = new ArrayList<>();
        Diagnostico nuevoDiagnostico = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM diagnostico LEFT JOIN medico ON diagnostico.idmedico = medico.idmedico LIMIT 500");
            rs = ps.executeQuery();

            while (rs.next()) {
                nuevoDiagnostico = new Diagnostico(
                        rs.getInt("id"),
                        rs.getInt("numEdiciones"),
                        rs.getString("docPaciente"),
                        rs.getString("resumen"),
                        rs.getString("sintomas"),
                        rs.getString("diagnostico"),
                        rs.getString("receta"),
                        rs.getBoolean("altoriesgo"),                        
                        rs.getTimestamp("fecha").toLocalDateTime() );// sql date to localdatetime
                       if(rs.getString("idMedico")!=null){
                    nuevoDiagnostico.setMedico(new Medico(rs.getString("idmedico"), rs.getString("nombre"), rs.getString("especialidad")));
                }
                //Leeemos de la bd
           //     System.out.println(nuevoPaciente);
                listaDiagnostico.add(nuevoDiagnostico);
            }

        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(),ex);
        }finally{
           try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(),ex);
            }
        }
        return listaDiagnostico;
    }

    @Override
    public List<Diagnostico> getDiagnosticosByDni(String numDocumento) {
        Connection conexion = null;
        List<Diagnostico> listaDiagnostico = new ArrayList<>();
        Diagnostico nuevoDiagnostico = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            //select * -> join medico trae solo el ultimo
            ps = conexion.prepareStatement("select * from diagnostico a LEFT JOIN medico ON a.idmedico = medico.idmedico where numEdiciones = ( select max(numEdiciones) from diagnostico b where b.id=a.id) and a.docPaciente = ?");
            ps.setString(1, numDocumento);
            rs = ps.executeQuery();
            

            while (rs.next()) {
                nuevoDiagnostico = new Diagnostico(
                        rs.getInt("id"),
                        rs.getInt("numEdiciones"),
                        rs.getString("docPaciente"),
                        rs.getString("resumen"),
                        rs.getString("sintomas"),
                        rs.getString("diagnostico"),
                        rs.getString("receta"),
                        rs.getBoolean("altoriesgo"),                        
                        rs.getTimestamp("fecha").toLocalDateTime());
                if(rs.getString("idMedico")!=null){
                    nuevoDiagnostico.setMedico(new Medico(rs.getString("idmedico"), rs.getString("nombre"), rs.getString("especialidad")));
                }
                //Leeemos de la bd
           //     System.out.println(nuevoPaciente);
                listaDiagnostico.add(nuevoDiagnostico);
            }

        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(),ex);
        }finally{
           try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(),ex);
            }
        }
        return listaDiagnostico;
    }

    @Override
    public boolean addDiagnostico(Diagnostico diagnostico) {
        boolean resultado = true;
        Connection conexion = null;
        try {
            //Obtener la conexion a la BD

            //Preparamos la consulta a ejecutar
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement(
                    "INSERT INTO diagnostico VALUES(default,default,?,?,?,?,?,?,?,?)");
            //Indicamos los parametros para la consulta
            ps.setString(1, diagnostico.getNumDocumento());            
            ps.setString(2, diagnostico.getResumen());
            ps.setString(3, diagnostico.getSintomas());
            ps.setString(4, diagnostico.getDiagnostico());
            ps.setString(5, diagnostico.getReceta());
            ps.setBoolean(6, diagnostico.isAltoRiesgo());
            ps.setDate(7, java.sql.Date.valueOf(diagnostico.getFecha().toLocalDate()));
            if(diagnostico.getMedico() !=null){
                ps.setString(8, diagnostico.getMedico().getIdMedico());
            }else{
                ps.setNull(8, Types.VARCHAR);
            }

            // ps.set(4, a); //sqlDate.toLocalDate(date);java.sql.Date.valueOf( localDate );
            ps.execute();
            resultado = true;
        } catch (SQLException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            resultado = false;
        }finally {
             try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                resultado = false;
               logger.error(ex.getMessage(),ex);
            }
        }
        return resultado;
    }

    @Override
    public Diagnostico getDiagnosticoByid(int id) {
        Connection conexion = null;
        Diagnostico nuevoDiagnostico = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM diagnostico LEFT JOIN medico ON diagnostico.idmedico = medico.idmedico where id = ? ORDER BY numEdiciones DESC Limit 1");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            

            while (rs.next()) {
                nuevoDiagnostico = new Diagnostico(
                        rs.getInt("id"),
                        rs.getInt("numEdiciones"),
                        rs.getString("docPaciente"),
                        rs.getString("resumen"),
                        rs.getString("sintomas"),
                        rs.getString("diagnostico"),
                        rs.getString("receta"),
                        rs.getBoolean("altoriesgo"),                        
                        rs.getTimestamp("fecha").toLocalDateTime() // sql date to localdatetime
                      );
                if(rs.getString("idMedico")!=null){
                    nuevoDiagnostico.setMedico(new Medico(rs.getString("idmedico"), rs.getString("nombre"), rs.getString("especialidad")));
                }
                //Leeemos de la bd
           //     System.out.println(nuevoPaciente);
            }

        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage(),ex);
        }finally{
           try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
               logger.error(ex.getMessage(),ex);
            }
        }
        return nuevoDiagnostico;
    }

    @Override
    public boolean deleteDiagnostico(int idDiagnostico) {
         Connection conexion = null;
        List<Diagnostico> listaDiagnostico = new ArrayList<>();
         boolean resultado = true;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("DELETE FROM diagnostico where id = ? ");
            ps.setInt(1, idDiagnostico);
            ps.execute();
            resultado = true;
          } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage());
            resultado = false;
        }finally {
             try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                resultado = false;
               logger.error(ex.getMessage(),ex);
            }
        }   
        return resultado;
    }

    @Override
    public boolean updateDiagnostico(Diagnostico diagnostico) {
        boolean resultado = true;
        Connection conexion = null;
        try {
            //Obtener la conexion a la BD

            //Preparamos la consulta a ejecutar
            conexion = acceso.getConexion();
            //un poderoso insert into select datos
            //https://stackoverflow.com/questions/25969/insert-into-values-select-from
            ps = conexion.prepareStatement(
                    "INSERT INTO diagnostico SELECT ?,max(numEdiciones) +1 ,?,?,?,?,?,?,?,? from diagnostico b where b.id=?");
            //Indicamos los parametros para la consulta
            ps.setInt(1, diagnostico.getId());   
            
            ps.setString(2, diagnostico.getNumDocumento());            
            ps.setString(3, diagnostico.getResumen());
            ps.setString(4, diagnostico.getSintomas());
            ps.setString(5, diagnostico.getDiagnostico());
            ps.setString(6, diagnostico.getReceta());
            ps.setBoolean(7, diagnostico.isAltoRiesgo());
            ps.setDate(8, java.sql.Date.valueOf(diagnostico.getFecha().toLocalDate()));
  
            if(diagnostico.getMedico() !=null){
                ps.setString(9, diagnostico.getMedico().getIdMedico());
            }else{
                ps.setNull(9, Types.VARCHAR);
            }
                      ps.setInt(10,  diagnostico.getId());

            // ps.set(4, a); //sqlDate.toLocalDate(date);java.sql.Date.valueOf( localDate );
            ps.execute();
            resultado = true;
        } catch (SQLException | NullPointerException ex) {
            logger.error(ex.getMessage());
            resultado = false;
        }finally {
             try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                resultado = false;
               logger.error(ex.getMessage(),ex);
            }
        }
        return resultado;
    }
    
}
