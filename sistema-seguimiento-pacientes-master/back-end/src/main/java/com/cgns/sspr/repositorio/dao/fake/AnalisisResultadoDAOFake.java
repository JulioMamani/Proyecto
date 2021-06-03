/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.entidad.AnalisisResultado;
import com.cgns.sspr.repositorio.dao.AnalisisResultadoDAO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Nagamine
 */
public class AnalisisResultadoDAOFake implements AnalisisResultadoDAO{

    @Override
    public List<AnalisisResultado> getResultadosByAnalisisId(int id) {
        return DatosRepoFake.getInstance().resultados.stream().filter( i -> i.getIdAnalisis() == id).collect(Collectors.toList());
    }

    @Override
    public boolean addResultados(int id, List<AnalisisResultado> resultados) {
        DatosRepoFake.getInstance().resultados.addAll(resultados);
        return true;
    }
    
}
