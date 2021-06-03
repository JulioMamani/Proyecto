<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="#">SSRP - Medico</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav class="p-0 m-0">
        <b-navbar-nav id="menu">
          <router-link to="/">Menu principal</router-link>
          <!-- router-link to="/Paciente/3/RegistrarHistoria">Registrar Historia</router-link>
          <router-link to="/Paciente/3/VerHistoria">Ver Historia</router-link -->
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>Usuario</em>
            </template>
            <b-dropdown-item  @click="$router.push({name:'VerSesiones'})">Sesion Camara</b-dropdown-item>
            <b-dropdown-item href="#" @click="logout">Cerrar Sesion</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script>
import { apiNoAuth } from "@/constantes/constantes.js";
export default {
  methods: {
    logout() {
      fetch(apiNoAuth + "logout", {
        method: "POST",
        credentials: "include"
      })
        .then((respuesta) => {
          if(respuesta.ok){
            this.$router.push({ name: "Login" }); 
          }else{
           console.log("error? en logout")
           this.$router.push({ name: "Login" });  
          }
          
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>
<style scoped>
#menu a:hover {
  background-color: #f3969a;
  cursor: pointer;
}
.navbar-nav > a {
  margin: 0px;
  padding-left: 10px;
  padding-right: 10px;
  text-decoration: none;
  color: whitesmoke;
}
</style>