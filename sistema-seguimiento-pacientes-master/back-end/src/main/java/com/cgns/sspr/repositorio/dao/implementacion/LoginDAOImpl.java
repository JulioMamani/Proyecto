/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.implementacion;

import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.repositorio.AccesoDB;
import com.cgns.sspr.repositorio.dao.LoginDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.slf4j.LoggerFactory;

/**
 *
 * @author magic
 */
public class LoginDAOImpl implements LoginDAO {
  //Para preparar consultas con parametros
    private PreparedStatement ps;
    //Para obtener el resultado de la ejecucion de la consulta
    private ResultSet rs;
    //Para poder almacenar la conexion

    //Para obtener la conexion
    private AccesoDB acceso;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);
    public LoginDAOImpl() {
        acceso = AccesoDB.getInstance();
        //conexion = acceso.getConexion();
    }
    @Override
    public Login getByUsuario(String usuario) {
        Connection conexion = null;
        Login loginBuscado = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM login WHERE username = ?");
                ps.setString(1, usuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                loginBuscado = new Login(
                        rs.getInt("idlogin"),
                        rs.getString("username"),
                        rs.getString("password"),                        
                        TipoUser.valueOf(rs.getString("tipouser")));                
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }finally{
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return loginBuscado;
    }

    @Override
    public Login getById(int id) {
        Connection conexion = null;
        Login loginBuscado = null;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM login WHERE idlogin = ?");
                ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                loginBuscado = new Login(
                        rs.getInt("idlogin"),
                        rs.getString("username"),
                        rs.getString("password"),                        
                        TipoUser.valueOf(rs.getString("tipouser")));                
            }

        } catch (SQLException ex) {
           logger.error(ex.getMessage(), ex);
        }finally{
            try {
                conexion.close();
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return loginBuscado;
   }

    @Override
    public boolean addLogin(Login login) {
     Connection conexion = null;
        boolean check = false;
        try {
            //Obtenemos la conexion
            conexion = acceso.getConexion();
            ps = conexion.prepareStatement("INSERT INTO login VALUES(?,?,?,?)");
            ps.setInt(1,(login.getId()));
            ps.setString(2, login.getUsername());
            ps.setString(3, login.getPassword());
            ps.setString(4, login.getTipoUser().name());
             ps.execute();
            check = true;
        } catch (SQLException ex) {
            check = false;
            logger.error(ex.getMessage(), ex);
        }finally{
            try {
                conexion.close(); 
            } catch (SQLException | NullPointerException ex) {
                logger.error(ex.getMessage(), ex);
                check = false;
                return false;
                
            }
        }
        return check;
    }
    
}
