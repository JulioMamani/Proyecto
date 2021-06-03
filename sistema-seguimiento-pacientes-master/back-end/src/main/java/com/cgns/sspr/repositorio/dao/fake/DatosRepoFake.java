/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.dataLogin.Login.TipoUser;
import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.entidad.AnalisisResultado;
import com.cgns.sspr.entidad.Diagnostico;
import com.cgns.sspr.entidad.Login;
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.entidad.Paciente;
import com.cgns.sspr.entidad.SesionLlamada;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public class DatosRepoFake {

        private static final DatosRepoFake instance = new DatosRepoFake();
   
    public List<Paciente> pacientes = new ArrayList<>();
    public List<Diagnostico> diagnostico = new ArrayList<>();
    public ArrayList<Login> login = new ArrayList<>();
    public List<Medico> medicos = new ArrayList<>();
    public List<Analisis> analisis = new ArrayList<>();
    public List<AnalisisResultado> resultados = new ArrayList<>();
    public List<SesionLlamada> sesiones = new ArrayList<>();

    private DatosRepoFake() {
        login.add(new Login(0, "user", "$2y$10$WdDmWPK2EIhSovECOSzY/.iJbTMJUrURehDVINq9Ooj1AiW6xylAq", TipoUser.MEDICO));
        initMedico();
        initPaciente();
        initDiagnostico();
    }

    public static DatosRepoFake getInstance() {
        return instance;
    }

    private void initDiagnostico() {
       // MedicoDAOFake medicoDAOFake = new MedicoDAOFake();
       Medico medico = medicos.stream().filter( i -> i.getIdMedico().equals("123")).findFirst().get();
        diagnostico.add(new Diagnostico(1, 0, "987654321", "covid", "tos, fiebre", "tiene covid", "a mimir", false, LocalDateTime.of(2021, Month.MARCH, 15, 13, 13),medico ));
        Diagnostico diagnostico2 = new Diagnostico(2, 0, "987654321", "covid", "tos, fiebre, perdida del gusto, malestar", "tiene covid", "ivermectnina", true, LocalDateTime.of(2021, Month.MARCH, 17, 17, 13), medico);
        analisis.add(new Analisis(1, 2, "987654321", "prueba covid", LocalDateTime.of(2021, Month.MARCH, 17, 17, 13), LocalDateTime.of(2021, Month.MARCH, 17, 17, 23)));
        resultados.add(new AnalisisResultado(1,"test serlogico", "positivo"));

        analisis.add( new Analisis(2, 2, "987654321", "prueba de sangre", LocalDateTime.of(2021, Month.MARCH, 17, 17, 13), LocalDateTime.of(2021, Month.MARCH, 17, 17, 23)));
        resultados.add(new AnalisisResultado(2,"hemoglobina", "14,2"));
        resultados.add(new AnalisisResultado(2,"glucosa", "98"));
        resultados.add(new AnalisisResultado(2,"plaquetas", "200 000"));
        resultados.add(new AnalisisResultado(2,"leucocitos", "6 000"));
        resultados.add(new AnalisisResultado(2,"colesterol", "130"));
        diagnostico.add(diagnostico2);
        diagnostico.add(new Diagnostico(3, 0, "987654321", "covid - recuperado", "recuperado", "ya no tiene covid", "nada", false, LocalDateTime.of(2021, Month.MARCH, 20, 17, 13), medico));
        diagnostico.add(new Diagnostico(4, 0, "123456789", "pereza", "pereza", "pereza", "a mimir", true, LocalDateTime.of(2021, Month.MARCH, 30, 10, 18), medico));

    }

    private void initMedico() {
        medicos.add(new Medico("123", "Jimmy Kochi", "neurologo", 0));
        medicos.add(new Medico("321", "Jimmy Kochi", "cardiologo"));
    }

    private void initPaciente() {
        pacientes.add(new Paciente("Martin Garcia Gamboa", "123456789"));
        pacientes.add(new Paciente("Eduardo Cruzado Neciosup", "987654321"));
        pacientes.add(new Paciente("Juan Gonzales Girio", "11111111"));
    }
    
}
