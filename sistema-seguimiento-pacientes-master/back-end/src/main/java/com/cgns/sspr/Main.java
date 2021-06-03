/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr;

import com.cgns.sspr.api.AnalisisApi;
import com.cgns.sspr.api.DiagnosticoApi;
import com.cgns.sspr.api.LoginController;
import com.cgns.sspr.api.MedicoApi;
import com.cgns.sspr.api.OpentokApi;
import com.cgns.sspr.api.PacienteApi;
import com.cgns.sspr.api.SesionLlamadaApi;
import com.cgns.sspr.opentok.OpentokController;
import com.cgns.sspr.repositorio.dao.fake.DatosRepoFake;
import com.cgns.sspr.security.AuthManager;
import com.cgns.sspr.security.RolesUsuarios;
import io.javalin.Javalin;
import io.javalin.http.util.RedirectToLowercasePathPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import static io.javalin.apibuilder.ApiBuilder.*;
import io.javalin.plugin.json.JavalinJson;
import io.javalin.core.compression.Gzip;
import io.javalin.http.Context;
import static io.javalin.core.security.SecurityUtil.roles;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.session.SessionHandler;

/**
 *
 * @author Nagamine
 */
import utils.GsonUtils;

public class Main {
    private static       Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        //logger sirve para generar logs de forma eficiente y practica
        //viene de la libreria slf4j
      
        
        //Aqui se añaden las dependencias de los controller
        DiagnosticoApi diagnosticoApi = new DiagnosticoApi();
        LoginController loginController = new LoginController();
        MedicoApi medicoApi = new MedicoApi();
        PacienteApi pacienteApi = new PacienteApi();
        AnalisisApi analisisApi = new AnalisisApi();
        OpentokApi opentokApi = new OpentokApi();
        SesionLlamadaApi sesionLlamadaApi = new SesionLlamadaApi();
        //Configuracion de javalin para poder usar ctx.json(la clase aqui)
        JavalinJson.setFromJsonMapper(GsonUtils.gson::fromJson);
        JavalinJson.setToJsonMapper(GsonUtils.gson::toJson);
        //Inicializa el server en el puerto 4567
        Javalin app = Javalin.create(config -> {

            //Habilita HTTPS para que la conexion este encriptada y el navegador acepte las credenciales
            if (SecurityConfig.habilitarHTTPS) {
                /*config.server(() -> {
                    Server server = new Server();
                    ServerConnector sslConnector = new ServerConnector(server, getSslContextFactory());
                    sslConnector.setPort(4000);
                    ServerConnector connector = new ServerConnector(server);
                    connector.setPort(4567);
                    server.setConnectors(new Connector[]{sslConnector, connector});
                    return server;
                });
                config.enforceSsl = true;*/
               
            } else {
                System.err.println("IMPORTANTE: SSL DESHABILITADO, HABILITAR EN SECURITYCONFIG");
            }
           
            if(!SecurityConfig.habilitarPruebaLocal){
               
                  config.sessionHandler(() -> {
                   final SessionHandler sessionHandler = new SessionHandler();
    // [..] add persistence DB etc. management here [..]
                    sessionHandler.getSessionCookieConfig().setHttpOnly(true);
                    sessionHandler.getSessionCookieConfig().setSecure(true);
                    sessionHandler.getSessionCookieConfig().setComment("__SAME_SITE_NONE__"); 
                    //sessionHandler.setSameSite(HttpCookie.SameSite.NONE);
                    //sessionHandler.setSecureRequestOnly(true);
                    //sessionHandler.setHttpOnly(true);
                    //sessionHandler.
                    return sessionHandler;
                });
                 config.enforceSsl = true;
            }else{
                 System.err.println("--------------------------------------------------------");
                System.err.println("IMPORTANTE: MODO DE PRUEBA LOCAL, HABILITAR EN SECURITYCONFIG");
                System.err.println("--------------------------------------------------------");
            }
             
            //para que se redireccione a minuscula la ruta
            config.registerPlugin(new RedirectToLowercasePathPlugin());
            // config.enableCorsForOrigin("localhost:8080","localtest.me:8080");
            config.compressionStrategy(null, new Gzip(6));
            config.accessManager(new AuthManager());
        });
        app.start(getHerokuAssignedPort());
        
        logger.info("Start server port 4567");

        if (!SecurityConfig.habilitarBD) {
            System.err.println("IMPORTANTE: BD DESHABILITADA, USANDO MODO DE PRUEBA");
        }
        if(!SecurityConfig.habilitarSeguridad){
            System.err.println("IMPORTANTE: LOGIN DESHABILITADO , USANDO MODO DE PRUEBA");
        }
        //aqui se configuran los header (ver funcion abajo)
        configurarHeaders(app);

        //aqui van las rutas ejemplo api.com/api/v1/paciente/1234/diagnostico
        //GET: obtener datos
        //POST: añadir datos
        //path: marca una ruta
        //before: se ejecuta antes de entrar a la ruta
        app.routes(() -> {
            path("api/v1", () -> {  //marca la ruta inicial  

                //se ejecuta cuando el front llama a /api/v1/cualquiercosa y muestra en consola a que link accedio
                before("*", ctx -> logger.info("Recivido llamada api: " + ctx.method() + " " + ctx.path()));

                //verifica si esta logueado
                get("checklogged", ctx -> loginController.getCheckLogin(ctx),roles(RolesUsuarios.TODOS )); //get:/api/v1/checklogged/
                //aqui se envian los datos del login  -- ver clase loginController
    
                post("login", ctx -> loginController.postLogin(ctx),roles(RolesUsuarios.TODOS ));  //post:/api/v1/login
                //Registra un nuevo usuario
                post("register", ctx -> loginController.postAddLogin(ctx),roles(RolesUsuarios.TODOS ));
                //desloguea al usuario
                post("logout", ctx -> loginController.postLogout(ctx),roles(RolesUsuarios.TODOS )); //post:/api/v1/logout

                //metodo para asegurar las rutas
                path("paciente", () -> { // api/v1/paciente
                    //añadir paciente -- ver clase pacienteApi
                    post(ctx -> pacienteApi.postAddPaciente(ctx),roles(RolesUsuarios.MEDICO_WRITE )); //post:api/v1/paciente/
                    //aqui se hacen las operaciones especificas a un unico paciente
                    //el :id representa al codigo o numero del paciente 
                    
                    path(":id", () -> {  // api/v1/paciente/12345
                        //obtiene el paciente con el :id
                        
                        post(ctx -> pacienteApi.postEditPaciente(ctx),roles(RolesUsuarios.MEDICO_WRITE ));
                        get(ctx -> pacienteApi.getPaciente(ctx),roles(RolesUsuarios.MEDICO_READ )); // get:api/v1/paciente/12345/
                        
                        
                        
                        //aqui se muestra cada diagnostico  -- ver clae DiagnosticoAPI
                        path("diagnostico", () -> { // get:api/v1/paciente/12345/diagnostico
                            //obtiene todos los diagnosticos del paciente :id
                            get(ctx -> diagnosticoApi.getDiagnosticosDocumento(ctx),roles(RolesUsuarios.MEDICO_READ ));  //get:api/v1/paciente/12345/diagnostico/
                            //añade el diagnostico
                            post(ctx -> diagnosticoApi.postAddDiagnostico(ctx),roles(RolesUsuarios.MEDICO_WRITE )); //post:api/v1/paciente/12345/diagnostico/
                            //actualiza el diagnostico
                            patch(ctx -> diagnosticoApi.patchUpdateDiagnostico(ctx),roles(RolesUsuarios.MEDICO_WRITE ));
                            
                            //opbiente el diagnostico numero :historia del paciente :id

                            get(":diagnosticoId", ctx -> diagnosticoApi.getDiagnosticoId(ctx),roles(RolesUsuarios.MEDICO_READ )); //get:api/v1/paciente/12345/diagnostico/123456
                            
                            //elimina el diagnostico
                            delete(":diagnosticoId", ctx -> diagnosticoApi.deleteDiagnostico(ctx),roles(RolesUsuarios.MEDICO_WRITE));

                            
                        });
                    });
                });
                
                path("medico", ()->{
                    get(":id", ctx -> medicoApi.getMedico(ctx),roles(RolesUsuarios.ADMIN )); //GET:api/v1/medico/123

                    post(ctx -> medicoApi.postAddMedico(ctx),roles(RolesUsuarios.ADMIN));  //POST:api/v1/medico
                    path(":id", () -> {
                        post(ctx -> medicoApi.postEditMedico(ctx),roles(RolesUsuarios.ADMIN));   
                    });

                });
                path("analisis", ()->{
                    get(":id", ctx -> analisisApi.getAnalisisID(ctx),roles(RolesUsuarios.MEDICO_READ));
                    post("solicitar", ctx -> analisisApi.postSolicitarAnalisis(ctx),roles(RolesUsuarios.MEDICO_WRITE) );
                    patch(ctx -> analisisApi.patchRealizarAnalisis(ctx),roles(RolesUsuarios.MEDICO_WRITE));
                });
                path("videollamada",()->{
                    get("sesiones", ctx -> sesionLlamadaApi.getSesiones(ctx),roles(RolesUsuarios.MEDICO_READ,RolesUsuarios.PACIENTE_READ));
                    post("token", ctx -> sesionLlamadaApi.postCrearToken(ctx),roles(RolesUsuarios.MEDICO_WRITE,RolesUsuarios.PACIENTE_WRITE));
                    post("crearsesion", ctx -> sesionLlamadaApi.postCrearSala(ctx),roles(RolesUsuarios.MEDICO_WRITE));
                });
                get("opentok", ctx -> opentokApi.crearToken(ctx),roles(RolesUsuarios.TODOS));
                path("diagnosticosuser", ()->{
                    get(ctx -> diagnosticoApi.getDiagnosticoPaciente(ctx),roles(RolesUsuarios.PACIENTE_READ));
                    get(":id", ctx->diagnosticoApi.getDiagnosticoId(ctx),roles(RolesUsuarios.PACIENTE_READ) );
                });
                        
            });

        });

    }

    //wbds para que el navegador acepte las solicitudes   -- gracias stack overflow pase 10 horas pa que esta parte funque
    public static void configurarHeaders(Javalin app) {
        app.options("/*", ctx -> {
            System.out.println("Options");
            String accessControlRequestHeaders = ctx.header("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                ctx.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                System.out.println(accessControlRequestHeaders);
            }

            String accessControlRequestMethod = ctx.header("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                ctx.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            ctx.status(200);
        },roles(RolesUsuarios.TODOS));
        //añade cabeceras para que se ejecute correctamente en navegador
        app.before(ctx -> {
            permitirRutas(ctx);
            ctx.header("Access-Control-Allow-Method", "GET,PUT,POST,DELETE,OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Content-Length, Accept, Origin,");
            ctx.header("Access-Control-Allow-Credentials", "true");

        });
    }

    //wbds para que el navegador acepte las solicitudes  -- gracias stack overflow pase 10 horas pa que esta parte funque
    public static void permitirRutas(Context ctx) {
      boolean permitido = false;
        for (String s : SecurityConfig.rutasPermitidas) {
            if (ctx.header("Origin") != null) {
                if (ctx.header("Origin").equals("https://" + s)) {
                    ctx.header("Access-Control-Allow-Origin", "https://" + s);
                    permitido = true;
                } else if (ctx.header("Origin").equals("http://" + s)) {
                    ctx.header("Access-Control-Allow-Origin", "http://" + s);
                    permitido = true;
                }
            }

        }
        if(!permitido){

                    logger.error("Conexion no permitida desde: " +ctx.header("Origin")  );
        }

    }
    
  private static int getHerokuAssignedPort() {
    String herokuPort = System.getenv("PORT");
    if (herokuPort != null) {
      return Integer.parseInt(herokuPort);
    }
    return 4567;
  }
    private static SslContextFactory getSslContextFactory() {
        SslContextFactory sslContextFactory = new SslContextFactory.Server();
        //sslContextFactory.setKeyStorePath(Main.class.getResource("/localhost.p12").toExternalForm());
        sslContextFactory.setKeyStorePath("certificado/localhost.p12");
        sslContextFactory.setKeyStorePassword("changeit");
        return sslContextFactory;
    }
}
