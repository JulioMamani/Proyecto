<template>
  <div>
     <b-button variant="primary" v-on:click="button" class="mb-3">Volver a busqueda medico</b-button>
    <b-card class="mt-2" header="Añadir Medico">
      <div class="p-3">
        <b-form @submit="onSubmit" @reset="onReset" v-show="show">
          <b-form-group
            id="input-group-1"
            label="ID:"
            label-for="input-1"
            description="ID o código del medico"
          >
            <b-form-input
              id="input-1"
              v-model="form.idMedico"
              type="number"
              placeholder="ID"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-2"
            label="Especialidad:"
            label-for="input-2"
            description="Especialidad del medico"
          >
            <b-form-input
              id="input-2"
              v-model="form.especialidad"
              placeholder="Especialidad"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-3"
            label="Nombres:"
            label-for="input-3"
            description="Apellidos y nombres del medico"
          >
            <b-form-input
              id="input-3"
              v-model="form.nombre"
              placeholder="Nombres"
              required
            ></b-form-input>
          </b-form-group>

         

          <b-button type="submit" variant="primary">Submit</b-button>
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
        nombre : "",
        idMedico:  "",
        especialidad : ""
      },
       medicoid: this.$route.params.medicoid,
       show : true
    };
  },
  methods: {
    button(){
      this.$router.go(-1)
    },
    onSubmit(event) {
      event.preventDefault();
      fetch(api + "medico/", {
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
            alert("Medico registrado");
        }else{
          alert("Error al registrar");
        }
      }).catch(error => {
        alert("Error al registrar");
        console.error(error)}
        );
      
     
     // this.$router.push("/paciente/"+this.pacienteid+"/verHistoria")
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
        this.form.nombre = "",
        this.form.idMedico =  "",
        this.form.especialidad = ""
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