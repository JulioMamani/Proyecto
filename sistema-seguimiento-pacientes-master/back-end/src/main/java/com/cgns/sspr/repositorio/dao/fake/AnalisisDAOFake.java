/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.repositorio.dao.AnalisisDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nagamine
 */
public class AnalisisDAOFake implements AnalisisDAO{
    private List<Analisis> analisis = DatosRepoFake.getInstance().analisis;
    public AnalisisDAOFake(){
 
    }
    @Override
    public List<Analisis> getAllAnalisisByPaciente(String documento) {
        List<Analisis> a = new ArrayList<>();
    
       analisis.forEach( i -> {
           if (i.getDocPaciente().equals(documento)){
               a.add(i);
           }
       });
        return a;   
    }

    @Override
    public Analisis getAnalisisById(int id) {
    
        Optional<Analisis> o = analisis.stream().filter( i -> i.getIdAnalisis() == id).findFirst();
        if(o!=null){
            return o.get();
        }
        return null;
    }

    @Override
    public boolean eliminarAnalisis(int id) {
          analisis.removeIf( i -> i.getIdAnalisis() == id);
        return true;
    }

    @Override
    public List<Analisis> getAllAnalisisByDiagnosticoId(int id) {
        List<Analisis> list = new ArrayList<>();
         analisis.stream().filter(i -> i.getIdDiagnostico() == id).forEach(i -> list.add(i));
         return list;
    }

    @Override
    public boolean solicitarAnalisis(int idDiagnostico,String docPaciente,String tipo) {
        analisis.add(new Analisis(analisis.size(),idDiagnostico, docPaciente,tipo,LocalDateTime.now()));
        return true;
    }

    @Override
    public boolean realizarAnalisis(int id) {
        getAnalisisById(id).setFecha_realizacion(LocalDateTime.now());
        return true;
    }

 
    
}
