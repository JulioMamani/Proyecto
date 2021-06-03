/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao;

import com.cgns.sspr.entidad.AnalisisResultado;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public interface AnalisisResultadoDAO {
    public List<AnalisisResultado> getResultadosByAnalisisId(int id);
    public boolean addResultados(int id, List<AnalisisResultado> resultados);
}
