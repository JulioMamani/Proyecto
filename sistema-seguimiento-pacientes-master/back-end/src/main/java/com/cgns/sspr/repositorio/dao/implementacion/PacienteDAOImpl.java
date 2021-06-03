/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;
import com.cgns.sspr.Main;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.repositorio.*;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.repositorio.dao.PacienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nagamine
 */
public class PacienteDAOImpl implements PacienteDAO {
//Para preparar consultas con parametros
    private PreparedStatement ps;
    //Para obtener el resultado de la ejecucion de la consulta
    private ResultSet rs;
    //Para poder almacenar la conexion
    
    //Para obtener la conexion
    private AccesoDB acceso;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(PacienteDAOImpl.class);
    public PacienteDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }
    @Override
    public List<Paciente> getAll() {
        Connection conexion = null;
        List<Paciente> listaPaciente = new ArrayList<>();
        Paciente nuevoPaciente = null;
        Login login;
        int idlogin;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM pacientep LIMIT 500 ");
            rs = ps.executeQuery();

            while (rs.next()) {
                nuevoPaciente = new Paciente(
                        rs.getString("nombres"),
                        rs.getString("dni"));
                //Leeemos de la bd
           //     System.out.println(nuevoPaciente);
                idlogin =  rs.getInt("idlogin");
                if(rs.wasNull()){
                   login = null; 
                }else{
                   LoginDAOImpl log =  new LoginDAOImpl();
                   login = log.getById(idlogin);
                }
                nuevoPaciente.setUsuario(login);
                listaPaciente.add(nuevoPaciente);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }finally{
           try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage());
            }
        }
        return listaPaciente;
    }    

    @Override
    public Paciente getPacienteByDocumento(String dni) {
        Connection conexion = null;
        Paciente pacienteBuscado = null;
        Login login;
        int idlogin;
        try {
            //Obtenemos la conexion
            
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM pacientep WHERE dni = ?");
            ps.setString(1, dni);
            rs = ps.executeQuery();

            if (rs.next()) {               
                pacienteBuscado = new Paciente(
                        rs.getString("nombres"),
                        String.valueOf(rs.getInt("dni"))                                             
                );
                idlogin =  rs.getInt("idlogin");
                if(rs.wasNull()){
                   login = null; 
                }else{
                   LoginDAOImpl log =  new LoginDAOImpl();
                   login = log.getById(idlogin);
                }
                pacienteBuscado.setUsuario(login);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }finally{
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage());
            }
        }
        return pacienteBuscado;
    }

    @Override
    public boolean addPaciente(Paciente paciente) {
        Connection conexion = null;
        boolean check = false;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("INSERT INTO pacientep VALUES(?,?,?)");
            ps.setInt(1,Integer.parseInt(paciente.getNumDocumento()));
            ps.setString(2, paciente.getNombres());
            if(paciente.getLogin()!=null){
                ps.setInt(4, paciente.getLogin().getId());
            }else{
                ps.setNull(4, Types.INTEGER);
            }
            
             ps.execute();
            check = true;
        } catch (SQLException ex) {
            check = false;
            logger.error(ex.getMessage());
        }finally{
            try {
                conexion.close(); 
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage());
                check = false;
                return false;
                //Logger.getLogger(CitaDAODB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
  
    }
    
    @Override
    public boolean editPaciente(Paciente paciente) {
        Connection conexion = null;
        boolean check = false;
        try {
            //Obtenemos la conexion
            
            conexion = acceso.getConexion();

            ps = conexion.prepareStatement("UPDATE pacienteP SET nombres=? WHERE dni = ?");
            ps.setString(1, paciente.getNombres());
            ps.setInt(2,Integer.parseInt(paciente.getNumDocumento()));

            ps.executeUpdate();
            check = true;
        } catch (SQLException ex) {
            check = false;
            logger.error(ex.getMessage());
        }finally{
            try {
                conexion.close(); 
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage());
                check = false;
                return false;
                //Logger.getLogger(CitaDAODB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;

            
    }

    @Override
    public Paciente buscarPorLogin(int idLogin) {
       Connection conexion = null;
        Paciente pacienteBuscado = null;
        Login login;
        int idlogin;
        try {
            //Obtenemos la conexion
            
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM pacientep WHERE id_login = ?");
            ps.setInt(1, idLogin);
            rs = ps.executeQuery();

            if (rs.next()) {               
                pacienteBuscado = new Paciente(
                        rs.getString("nombres"),
                        String.valueOf(rs.getInt("dni"))                                             
                );
                idlogin =  rs.getInt("idlogin");
                if(rs.wasNull()){
                   login = null; 
                }else{
                   LoginDAOImpl log =  new LoginDAOImpl();
                   login = log.getById(idlogin);
                }
                pacienteBuscado.setUsuario(login);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }finally{
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage());
            }
        }
        return pacienteBuscado;
    }
    
}
