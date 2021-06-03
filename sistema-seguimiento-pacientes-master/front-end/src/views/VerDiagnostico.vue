<template>
  <div>
    <div v-if="diagnostico.medico !== null">
      <b-card header="Datos del Médico" class="mb-4">
        <div class="float-left">
          <h5 class>
            <span class="font-weight-bold">Nombre:</span>{{ diagnostico.medico.nombre }}
          </h5>
          <h6 class>
            <span class="font-weight-bold">Especialidad:</span>{{ diagnostico.medico.especialidad }}
          </h6>
        </div>
      </b-card>
    </div>
    <div v-else>
      <b-alert variant="warning" show>No hay medico registrado con este diagnostico</b-alert>
    </div>

    <div>
      <solicitar-analisis :diagnostico="diagnostico" class="mb-3"></solicitar-analisis>
      <b-button variant="primary" v-on:click="button" class="mb-3"
        >Volver a diagnosticos</b-button
      >
    </div>

    <b-alert id="riesgo" v-if="diagnostico.altoRiesgo" show variant="Danger">
      Alto riesgo</b-alert
    >
    <h6>{{ diagnostico.fecha }}</h6>

  <resultados-analisis :analisis="diagnostico.analisis" class="mt-3 mb-3" v-if="diagnostico.analisis!==null && diagnostico.analisis.length >0"></resultados-analisis>

    <b-card header="Diagnostico corto:" class="mb-4">
      <b-card-body>{{ diagnostico.resumen }}</b-card-body>
    </b-card>
    <b-card header="Sintomas:" class="mb-4">
      <b-card-body>{{ diagnostico.sintomas }}</b-card-body>
    </b-card>
    <b-card header="Diagnostico:" class="mb-4">
      <b-card-body>{{ diagnostico.diagnostico }}</b-card-body>
    </b-card>
    <b-card header="Receta:" class="mb-4">
      <b-card-body>{{ diagnostico.receta }}</b-card-body>
    </b-card>


  </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";
import ResultadosAnalisis from "@/components/ResultadosAnalisis.vue"
import SolicitarAnalisis from "@/components/SolicitarAnalisis.vue"
export default {
   components:{
    ResultadosAnalisis,
    SolicitarAnalisis
   
  },
  data() {
    return {
      pacienteid: this.$route.params.pacienteId,
      historiaid: this.$route.params.diagnosticoId,
      diagnostico: null,
    };
  },
  methods: {
    button() {
      this.$router.push({
        name: "VerTodosLosDiagnosticos",
        params: {
          pacienteId: this.pacienteid,
        },
      });
    },
    fetchItems() {
      fetch(
        api + "paciente/" + this.pacienteid + "/diagnostico/" + this.historiaid,
        {
          credentials: "include",
        }
      )
        .then((datos) => datos.json())
        .then((data) => {
          console.log(data)
          this.diagnostico = data
          this.diagnostico.fecha = new Date(data.fecha).toLocaleString("es-PE")
        })
        .catch((error) => console.error(error));
    },
  },
  mounted() {
    this.fetchItems();
  },
};
</script>
<style>
#riesgo {
  color: white;
  font-weight: bold;
  background-color: red;
}
</style>