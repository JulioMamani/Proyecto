/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgns.sspr.security;

import io.javalin.core.security.Role;

/**
 *
 * @author Nagamine
 */
public enum RolesUsuarios implements Role{NINGUNO, TODOS, PACIENTE_READ, PACIENTE_WRITE, MEDICO_READ, MEDICO_WRITE, ADMIN, SUPERADMIN }