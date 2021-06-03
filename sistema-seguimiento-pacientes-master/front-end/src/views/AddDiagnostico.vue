<template>
  <div>
     <b-button variant="primary" v-on:click="button" class="mb-3">Volver a diagnosticos</b-button>
    <b-card class="mt-2" header="Diagnostico">
      <div class="p-3">
        <b-form @submit="onSubmit" @reset="onReset">
          <b-form-group
            id="input-group-1"
            label="Diagnostico corto:"
            label-for="input-1"
            description="Aqui se ingresa un resumen corto acerca del diagnostico"
          >
            <b-form-input
              id="input-1"
              v-model="form.resumen"
              type="text"
              placeholder="Corto"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-2"
            label="Sintomas:"
            label-for="input-2"
            description="Aqui se ingresa los sintomas del paciente"
          >
            <b-form-textarea
              id="input-2"
              v-model="form.sintomas"
              placeholder="Sintomas"
              required
              style="height: 150px"
            ></b-form-textarea>
          </b-form-group>

          <b-form-group
            id="input-group-3"
            label="Diagnostico:"
            label-for="input-3"
            description="Aqui se ingresa el diagnostico"
          >
            <b-form-textarea
              id="input-3"
              v-model="form.diagnostico"
              placeholder="Diagnostico"
              required
              style="height: 200px"
            ></b-form-textarea>
          </b-form-group>

          <b-form-group
            id="input-group-4"
            label="Receta:"
            label-for="input-4"
            description="Aqui se ingresa la receta"
          >
            <b-form-textarea
              id="input-4"
              v-model="form.receta"
              placeholder="Receta"
              required
              style="height: 150px"
            ></b-form-textarea>
          </b-form-group>

          <b-form-group id="input-group-5" >
              <b-form-checkbox  v-model="form.altoRiesgo">Paciente de alto riesgo
                </b-form-checkbox>
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
       pacienteid: this.$route.params.pacienteId,
      form: {
        docPaciente: this.$route.params.pacienteId,
        resumen: "",
        sintomas: "",
        diagnostico: "",
        receta: "",
        altoRiesgo: false,
        fecha : new Date().getTime()
      }
      
    };
  },
  methods: {
    button(){
       this.$router.push({name:'VerTodosLosDiagnosticos',params:{
        pacienteId: this.pacienteid
      }})
    },
    onSubmit(event) {
      event.preventDefault();
      fetch(api + "paciente/" + this.pacienteid + "/diagnostico", {
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
            alert("Diagnostico registrado");
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
      this.form.resumen = ""
      this.form.sintomas = ""
      this.form.diagnostico = ""
      this.form.receta = ""
      this.form.altoRiesgo = false
      this.fecha = new Date()
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