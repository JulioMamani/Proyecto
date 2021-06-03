<template>
  <div>
     <b-button variant="primary" v-on:click="button" class="mb-3">Volver</b-button>
    <b-card class="mt-2" header="Registar Usuario">
      <div class="p-3">
        <b-form @submit="onSubmit" @reset="onReset" v-show="show">
          
          <b-form-group
            id="input-group-1"
            label="Usuario:"
            label-for="input-3"
            description="Nombre de usuario"
          >
            <b-form-input
              id="input-1"
              v-model="form.usuario"
              placeholder="Usuario"
              required
            ></b-form-input>
          </b-form-group>

         <b-form-group
            id="input-group-2"
            label="Contraseña:"
            label-for="input-3"
            description="Contraseña"
          >
            <b-form-input
              id="input-2"
              v-model="form.password"
              placeholder="Password"
              required
            ></b-form-input>
          </b-form-group>

          <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group label="Tipo de usuario" label-for="tipo-input"
          ><b-form-select
            v-model="solicitud.tipoUser"
            :options="options"
            class="mb-3"
          ></b-form-select>
        </b-form-group>
        </form>

          <b-button type="submit" variant="primary">Enviar</b-button>
          <b-button type="reset" variant="danger">Reset</b-button>
        </b-form>
      </div>
    </b-card>
  </div>
</template>

<script>
import {api} from "@/constantes/constantes.js"
export default {
  data() {
    return {
      form: {
        usuario : "",
        pasword:  "",
        tipoUser : ""
      },
       options: [
        { value: null, text: "Tipo de usuario" },
        "ADMIN",
        "MEDICO",
        "PACIENTE",
      ],
      solicitud:{
          tipoUser:null,
      },
       usuarioid: this.$route.params.usarioid,
       show : true
       
    };
  },
  methods: {
    button(){
      this.$router.push({name:'Login'})
    },
    onSubmit(event) {
      event.preventDefault();
      fetch(api + "usuario/", {
        // Adding method type
        method: "POST",
        // Adding body or contents to send
        body: JSON.stringify(this.form),
        
        // Adding headers to the request
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        credentials: 'include',
      })
      .then(response => {
        if(response.ok){
            alert("Usuario registrado");
        }else{
          alert("-Usuario registrado-");
          //alert("Error al registrar");
        }
      }).catch(error => {
        alert("-Usuario registrado-");
        //alert("Error al registrar");
        console.error(error)}
        );
      
     
     // this.$router.push("/paciente/"+this.pacienteid+"/verHistoria")
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
        this.form.usuario = "",
        this.form.pasword =  "",
        this.form.tipoUser = ""
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
</script>
<style>
.card-header {
  font-size: 20px;
  font-weight: bold;
}
</style>