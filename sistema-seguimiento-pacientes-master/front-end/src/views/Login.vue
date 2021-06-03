<template>
  <div id="body">
    <b-form id="form">
        <p style="color:red;">{{errormsg}}</p>
      <b-form-group id="input-group-1" label="Usuario:" label-for="user">
        <b-input
          id="user"
          v-model="form.username"
          @keypress.enter.prevent
        ></b-input>
      </b-form-group>
      <b-form-group id="input-group-1" label="Password:" label-for="pass">
        <b-input
          id="pass"
          v-model="form.password"
          v-on:keyup.enter="log"
          type="password"
          @keypress.enter.prevent
        ></b-input>
      </b-form-group>
      <b-button id="button" variant="primary" v-on:click="log" class="mt-4 btn-block"
        >Login</b-button
      >
      <div class="mt-2">
        <a href="#" @click="$router.push('/registro')" >Registrarse</a >
      </div>
      
    </b-form> 
  </div>
</template>

<script>
import { apiNoAuth } from "@/constantes/constantes.js";
export default {
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      errormsg: "",
    };
  },
  methods: {
    log(event) {
      event.preventDefault();
      fetch(apiNoAuth + "login/", {
        // Adding method type
        method: "POST",
        // Adding body or contents to send
        body: JSON.stringify(this.form),
        // Adding headers to the request
        headers: { 
          "Content-type": "Authorization",
        },
        credentials: 'include',
      })
        .then((datos) =>{
          return datos.json()
        } )
        .then((data) => {
          if(data.isLogged){
             this.$router.push({name:'Home',params:{loginData: data}})
          }else{
            this.errormsg = data.mensaje
          }
        })
        .catch((error) => console.error(error));
      this.reset();
      //this.$router.push("/paciente/"+this.pacienteid+"/verHistoria")
    },
    reset() {
      // Reset our form values
      this.form.username = "";
      this.form.password = "";
    },
  },
  mounted(){
    fetch(apiNoAuth + 'checklogged', {
      credentials: "include"
    })
      .then((respuesta) => {
        return respuesta.json()
      }).then((data)=>{
         if(data.isLogged){
              this.$router.replace({name:'Home',params:{loginData: data}})
            this.errormsg = data.mensaje
          }
      })
      .catch((e) => {
        this.errormsg = "No se encuentra el servidor de backend o ha ocurrido un error de conexion"
        console.log(e)
      })
  }
};
</script>
<style scoped>
a{
  color: gray;
}
#body {
  display: block;
  text-align: center;
}
#form {
  width: 20%;
  min-width: 350px;
  max-width: 100%;
  display: inline-block;
  text-align: left;
  background:whitesmoke;
  border-radius: 5px;
  padding: 40px;
  box-shadow: 0 4px 10px 4px rgba(0, 0, 0, 0.3);

}
</style>