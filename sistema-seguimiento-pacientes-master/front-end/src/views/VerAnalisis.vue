<template>
  <div>
    <b-button variant="primary" v-on:click="button" class="mb-3">Volver a diagnosticos</b-button>
    <b-button variant="primary" v-on:click="registrar" class="ml-3 mb-3">Registrar analisis</b-button>
    <resultados-analisis :analisis="analisisData"></resultados-analisis>
  </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";
import ResultadosAnalisis from "@/components/ResultadosAnalisis.vue";
export default {
  props: ["pacienteId","diagnosticoId", "analisisId", "analisis"],
  components: {
    ResultadosAnalisis,
  },
  data() {
    return {
      analisisData: [],
    };
  },
  methods: {
    button() {
      this.$router.push({
        name: "VerTodosLosDiagnosticos",
        params: {
          pacienteId: this.pacienteId,
        },
      });
    },
      registrar() {
      this.$router.push({
        name: "AddResultadosAnalisis",
        params: {
          analisis: this.analisisData[0]
        }
      });
    },
    fetchData() {
      fetch(
        api + "analisis/" + this.$route.params.analisisId,
        //api + "paciente/" + this.$route.params.pacienteId + "/diagnostico/" + this.$route.params.diagnosticoId+"/analisis/"+this.$route.params.analisisId,
        {
          credentials: "include",
        }
      )
        .then((datos) => datos.json())
        .then((data) => {
          this.analisisData.push(data);
        })
        .catch((error) => console.error(error));
    },
  },
  mounted() {
    if (this.analisis === undefined) {
      this.fetchData();
    } else {
      this.analisisData.push(this.analisis);
    }
  },
};
</script>