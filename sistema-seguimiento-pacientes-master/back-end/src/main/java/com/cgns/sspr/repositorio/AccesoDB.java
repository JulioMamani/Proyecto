    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cgns.sspr.SecurityConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 * @author Nagamine
 */
public class AccesoDB {
    
    
    //se inicializa en el constructor
    private static  String USUARIO;
    private static  String PASWORD;
    private static  String HOST;
    private static  String BD;

    private static AccesoDB datasource;
    // Esta es la fuente de datos que conecta con la base de datos
    private ComboPooledDataSource cpds = null;
private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * Crea el constructor del pool, notara que este constructor es privado esto
     * con el fin de que podamos controlar cuando se crea el pool
     *
     * @throws IOException
     * @throws SQLException
     * @throws PropertyVetoException
     */
    private AccesoDB() {
        if(SecurityConfig.habilitarPruebaLocal){
            USUARIO = "root";
            PASWORD = "root";
            HOST="localhost:3306";
            BD = "pruebita";
            System.err.println("IMPORTANTE: USANDO BD DE PRUEBA");
            logger.error("Usando bd de prueba localhost");
        }else{
            USUARIO = "freedbtech_clinica";//"iQmBH1IuoL";//"root";
            PASWORD = "root";//"hwnFI3W7fg";//"root";
            HOST = "freedb.tech:3306";//ip de remotemysql,com  //localhost:3306
            BD = "freedbtech_clinicapruebita";//iQmBH1IuoL";//"pruebita";
            logger.info("Usando bd: " + HOST + " " + BD);
        }
        //try {
            // Configuramos la conexion a base de datos
            // Creamos la fuente de datos
            cpds = new ComboPooledDataSource();
            // Que driver de base de datos usaremos
            //cpds.setDriverClass("com.mysql.jdbc.Driver");
            // La url de la base de datos a la que nos conectaremos
            cpds.setJdbcUrl("jdbc:mysql://" + HOST + "/" + BD + "?useSSL=false&serverTimezone=UTC");
            // Usuario de esa base de datos
            cpds.setUser(USUARIO);
            // Contraseña de la base de datos
            cpds.setPassword(PASWORD);
            cpds.setAcquireRetryDelay(200);
            // Configuramos el pool
            // Numero de conexiones con las que iniciara el pool
            cpds.setInitialPoolSize(10);
            // Minimo de conexiones que tendra el pool
            cpds.setMinPoolSize(10);
            // Numero de conexiones a crear cada incremento
            cpds.setAcquireIncrement(1);
            // Maximo numero de conexiones
            cpds.setMaxPoolSize(30); //50
            // Maximo de consultas
            cpds.setMaxStatements(180);
            // Maximo numero de reintentos para conectar a base de datos
            cpds.setAcquireRetryAttempts(2);
            // Que se genere una excepcion si no se puede conectar
            cpds.setBreakAfterAcquireFailure(true);
        //} catch (PropertyVetoException ex) {
         //   Logger.getLogger(AccesoDB.class.getName()).log(Level.SEVERE, null, ex);
       // }

    }

    /**
     * Nos regresa la instancia actual del pool, en caso que no halla una
     * instancia crea una nueva y la regresa
     *
     * @return datasource
     */
    public static AccesoDB getInstance() {

        if (datasource == null) {
            datasource = new AccesoDB();
            return datasource;
        } else {
            return datasource;
        }
    }

    /**
     * Este metodo nos regresa una conexion a base de datos, esta la podemos
     * usar como una conexion usual
     *
     * @return Conexion a base de datos
     * @throws java.sql.SQLException
     *
     */
    public Connection getConexion() throws SQLException {

        return this.cpds.getConnection();

    }

}
