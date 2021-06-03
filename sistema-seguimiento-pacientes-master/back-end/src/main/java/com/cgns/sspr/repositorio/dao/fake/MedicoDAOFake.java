/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.repositorio.dao.fake;

/**
 *
 * @author Eduardo
 */
import com.cgns.sspr.entidad.Medico;
import com.cgns.sspr.repositorio.dao.MedicoDAO;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAOFake implements MedicoDAO {

    private List<Medico> medicos = DatosRepoFake.getInstance().medicos;

    public MedicoDAOFake() {
        
    }

    @Override
    public List<Medico> getAll() {
        return medicos;
    }

    @Override
    public Medico getMedicoByID(String idMedico) {
        for (Medico i : medicos) {
            if (i.getIdMedico().equals(idMedico)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public boolean addMedico(Medico medico) {
        medicos.add(medico);
        return true;
    }

    @Override
    public Medico getMedicoByLoginId(int loginId) {
        for (Medico i : medicos) {
            if (i.getLoginID() != null) {
                if (i.getLoginID() == loginId) {
                    return i;
                }
            }

        }
        return null;
    }
    @Override
    public boolean editMedico(Medico medico){
        return true;
    }

}
