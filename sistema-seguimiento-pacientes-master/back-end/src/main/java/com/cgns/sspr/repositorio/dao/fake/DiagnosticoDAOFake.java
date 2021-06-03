/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

import com.cgns.sspr.entidad.Analisis;
import com.cgns.sspr.entidad.AnalisisResultado;
import com.cgns.sspr.entidad.Diagnostico;
import com.cgns.sspr.repositorio.dao.DiagnosticoDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nagamine
 */
public class DiagnosticoDAOFake implements DiagnosticoDAO {

    private List<Diagnostico> diagnostico = DatosRepoFake.getInstance().diagnostico;


    public DiagnosticoDAOFake() {
      
    }

    @Override
    public List<Diagnostico> getAll() {
        return diagnostico;
    }

    @Override
    public List<Diagnostico> getDiagnosticosByDni(String historia) {
        List<Diagnostico> lista = new ArrayList<>();
        for (Diagnostico i : diagnostico) {
            if (i.getNumDocumento() != null) {
                if (i.getNumDocumento().equals(historia)) {
                    lista.add(i);
                }
            }

        }
        return lista;
    }

    @Override
    public boolean addDiagnostico(Diagnostico diagnostico) {
        if (diagnostico != null) {
            this.diagnostico.add(diagnostico);
            diagnostico.setId(this.diagnostico.size());
            diagnostico.setFecha(LocalDateTime.now());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Diagnostico getDiagnosticoByid(int id) {
        for (Diagnostico i : diagnostico) {
            if (i != null) {
                if (i.getId() == id) {
                    return i;
                }
            }

        }
        return null;
    }

    @Override
    public boolean deleteDiagnostico(int idDiagnostico) {
        return diagnostico.removeIf(diagn -> diagn.getId() == idDiagnostico);
    }

    @Override
    public boolean updateDiagnostico(Diagnostico diagnostico) {
        deleteDiagnostico(diagnostico.getId());
        addDiagnostico(diagnostico);
        return true;
    }

}
