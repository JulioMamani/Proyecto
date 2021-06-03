import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import { apiNoAuth } from '../constantes/constantes.js'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    props: true
  },
  {
    path: '/403',
    name: '403',
    component: () => import(/* webpackChunkName: "403" */ '../views/errors/403.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "Login" */ '../views/Login.vue'),
  },
  {
    path: '/registro',
    name: 'Registro',
    component: () => import(/* webpackChunkName: "Login" */ '../views/Registro.vue'),
  },
  {
    path: '/medico',
    name: 'VistaMedico',
    meta: { requiresAuth: true, tipoUsuario: ['MEDICO'] },
    component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/VistaMedico.vue'),

  },

  {
    path: '/registrarPaciente',
    name: 'AddPaciente',
    meta: { requiresAuth: true, tipoUsuario: ['MEDICO'] },
    component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/AddPaciente.vue'),

  },
  {
    path: '/paciente/:pacienteId',
    name: 'Paciente',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    meta: { requiresAuth: true, tipoUsuario: ['MEDICO'] },
    component: () => import(/* webpackChunkName: "Paciente" */ '../views/Paciente.vue'),
    children: [

      {
        path: 'editarPaciente',
        name: 'EditPaciente',
        component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/EditPaciente.vue'),
      },
      {
        path: 'registrarDiagnostico',
        name: 'AddDiagnostico',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "AddDiagnostico" */ '../views/AddDiagnostico.vue')
      },
      {
        path: 'diagnostico/:diagnosticoId',
        name: 'VerDiagnostico',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "VistaHistoria" */ '../views/VerDiagnostico.vue'),

      },
      {
        path: 'diagnostico',
        name: 'VerTodosLosDiagnosticos',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "VistaTodasHistorias" */ '../views/VerTodosLosDiagnosticos.vue')
      },
      {
        path: 'diagnostico/:diagnosticoId/analisis/:analisisId',
        name: 'VerAnalisis',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "AddDiagnostico" */ '../views/VerAnalisis.vue'),
        props: true
    
      },
    ]
    
  },
  {
    path: '/admin',
    name: 'VistaAdmin',
    meta: { requiresAuth: true, tipoUsuario: ['ADMIN'] },
    component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/VistaAdmin.vue'),
  },
  {
    path: '/registrarMedico',
    name: 'AddMedico',
    meta: { requiresAuth: true, tipoUsuario: ['ADMIN'] },
    component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/AddMedico.vue'),

  },
  {
    path: '/medico/:medicoid/VerMedico',
    name: 'vermedico',
    meta: { requiresAuth: true, tipoUsuario: ['ADMIN'] },
    component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/Medico.vue'),
    children: [
      {
        path: 'editarMedico',
        name: 'EditMedico',
        component: () => import(/* webpackChunkName: "BuscarPaciente" */ '../views/EditMedico.vue'),
      },
    ]
  },
  {

   
    path: '/analisis',
    name: 'AddResultadosAnalisis',
    meta: { requiresAuth: true, tipoUsuario: ['MEDICO'] },
    component: () => import(/* webpackChunkName: "VistaTodasHistorias" */ '../views/AddResultadosAnalisis.vue'),
    props:true

  },
  {
    path: '/sesiones',
    name: 'VerSesiones',
    meta: { requiresAuth: true, tipoUsuario: ['MEDICO','PACIENTE'] },
    component: () => import(/* webpackChunkName: "VerSesiones" */ '../views/VerSesiones.vue'),
  },
  {
    path: '/videollamada',
    name: 'CrearVideollamada',
    meta: { requiresAuth: true, tipoUsuario: ['MEDICO','PACIENTE'] },
    props: true,
    component: () => import(/* webpackChunkName: "VerSesiones" */ '../views/CrearVideollamada.vue'),
  },
  {
    path: '/vistaPaciente',
    name: 'VistaPaciente',
    meta: { requiresAuth: true, tipoUsuario: ['PACIENTE'] },
    component: () => import(/* webpackChunkName: "VerSesiones" */ '../views/VistaPaciente.vue'),
  },
  {
    path: '/prueba',
    name: 'Prueba',
    component: () => import(/* webpackChunkName: "Login" */ '../views/prueba.vue'),

  },
  {
    path: '*',
    name: 'Not Found',
    component: () => import(/* webpackChunkName: "VistaTodasHistorias" */ '../views/errors/404.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    fetch(apiNoAuth + 'checklogged', {
      credentials: "include"
    })
      .then(respuesta => {
        if (!respuesta.ok) {
          next('/403')
        }
        return respuesta.json()
      }).then(data => {
        if (data.tipoUser === 'ADMIN' || data.tipoUser === 'SUPERADMIN') {
          next()
        } else if (to.matched.some(record => record.meta.tipoUsuario.includes(data.tipoUser))) {
          next()
        } else {
          next('/403')
        }
      })
  } else {
    next()
  }

})
export default router
