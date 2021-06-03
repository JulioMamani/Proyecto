/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;

import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.repositorio.*;
import com.cgns.sspr.repositorio.dao.MedicoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo
 */
public class MedicoDAOImpl implements MedicoDAO {

    //Para preparar consultas con parametros
    private PreparedStatement ps;
    //Para obtener el resultado de la ejecucion de la consulta
    private ResultSet rs;
    //Para poder almacenar la conexion

    //Para obtener la conexion
    private AccesoDB acceso;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(MedicoDAOImpl.class);

    public MedicoDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }

    @Override
    public List<Medico> getAll() {
        Connection conexion = null;
        List<Medico> listaPaciente = new ArrayList<>();
        Medico nuevoPaciente = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM medico LIMIT 500");
            rs = ps.executeQuery();

            while (rs.next()) {
                nuevoPaciente = new Medico(
                        String.valueOf(rs.getInt("idmedico")),
                        rs.getString("nombre"),   
                        rs.getString("especialidad"),
                        rs.getObject("id_login", Integer.class));
                //Leeemos de la bd
                //     System.out.println(nuevoPaciente);
                listaPaciente.add(nuevoPaciente);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                //Logger.getLogger(CitaDAODB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPaciente;
    }

    @Override
    public Medico getMedicoByID(String dni) {
        Connection conexion = null;
        Medico medicoBuscado = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM medico WHERE idmedico = ?");
            ps.setString(1, dni);
            rs = ps.executeQuery();

            if (rs.next()) {
                medicoBuscado = new Medico(
                         String.valueOf(rs.getInt("idmedico")),
                        rs.getString("nombre"),
                        rs.getString("especialidad"),
                        rs.getObject("id_login", Integer.class));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                //Logger.getLogger(CitaDAODB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return medicoBuscado;
    }

    @Override
    public boolean addMedico(Medico medico) {
        Connection conexion = null;
        boolean check = false;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("INSERT INTO medico VALUES(?,?,?,?)");
            ps.setString(1, medico.getIdMedico());
            ps.setString(2, medico.getNombre());
            ps.setString(3, medico.getEspecialidad());
            if (medico.getLoginID() != null) {
                ps.setInt(4, medico.getLoginID());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.execute();
            check = true;
        } catch (SQLException ex) {
            check = false;
            logger.error(ex.getMessage());
        } finally {
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
    public Medico getMedicoByLoginId(int loginId) {
        Connection conexion = null;
        Medico medicoBuscado = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM medico WHERE id_login = ?");
            ps.setInt(1, loginId);
            rs = ps.executeQuery();

            if (rs.next()) {
                medicoBuscado = new Medico(
                        String.valueOf(rs.getInt("idmedico")),
                        rs.getString("nombre"),
                        rs.getString("especialidad"),
                        rs.getObject("id_login", Integer.class));

            }
            System.out.println(loginId);
            System.out.println(medicoBuscado);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                //Logger.getLogger(CitaDAODB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return medicoBuscado;
    }
    
    @Override
    public boolean editMedico(Medico medico) {
        Connection conexion = null;
        boolean check = false;
        try {
            //Obtenemos la conexion
            
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("UPDATE medico SET nombre=?,especialidad=? WHERE idmedico = ?");
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getEspecialidad());
            ps.setInt(3,Integer.parseInt(medico.getIdMedico()));
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
}
