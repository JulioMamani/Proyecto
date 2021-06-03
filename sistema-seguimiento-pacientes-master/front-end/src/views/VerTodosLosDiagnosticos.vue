<template>
  <div>
    <div class="d-inline-flex">
       <b-button variant="primary" v-on:click="button" class="mb-3 mr-2"
      >añadir nuevo diagnostico</b-button
    >
    <programar-videollamada :idPaciente="pacienteId" class="mb-3"></programar-videollamada>
    </div>
   
    <b-card no-body header="Diagnosticos">
      <b-list-group flush>
        <div
          v-for="item in historia"
          v-bind:key="item.idDiagnostico + '-' + item.numEdiciones"
          class="mb-4"
        >
          <b-list-group-item
            class="flex-column align-items-start"
            v-on:click="accessDiagnostico(item.idDiagnostico)"
            v-bind:class="{ 'list-group-item-danger': item.altoRiesgo }"
            href="#"
          ><h6>Resumen: </h6>
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">{{ item.resumen }}</h5>
              <small class="text-muted">Fecha: {{ new Date(item.fecha).toLocaleDateString("es-PE") }} {{new Date(item.fecha).toLocaleTimeString("es-PE") }}</small>
            </div>
            <p class="mb-1">
              {{ item.diagnostico }}
            </p>

            <small class="text-muted">{{ item.receta }}</small>
          </b-list-group-item>
          <b-list-group-item class="list-group-item-primary" 
          v-on:click="accessAnalisis(analisis)"
          href="#" v-for="analisis in item.analisis" v-bind:key="analisis.id"  v-bind:class="{ 'colorAnalisisPendiente':analisis.resultados.length === 0}" >
             <h6> Analisis</h6>{{analisis.tipoAnalisis}} <span v-if="analisis.resultados.length === 0">-- pendiente</span>
          </b-list-group-item>
        </div>
      </b-list-group>
    </b-card>

  </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";
import ProgramarVideollamada from '../components/ProgramarVideollamada.vue';
export default {
 components:{
  ProgramarVideollamada

 },
  data() {
    return {
      pacienteId: this.$route.params.pacienteId,
      historia: [],
      existe: true,
    };
  },
  methods: {
    button() {
      this.$router.push({
        name: "AddDiagnostico",
        params: {
          pacienteId: this.pacienteId,
        },
      });
    },
    editar: function (e){
      e.preventDefault();
      console.log("gggaaaaaaaaaaaa")
      this.$router.push({name:'EditPaciente',params:{
        pacienteId: this.pacienteId
      }})
    },
    accessAnalisis(analisis){
      this.$router.push({
        name: "VerAnalisis",
        params: {
          pacienteId: this.pacienteId,
          diagnosticoId: analisis.idDiagnostico,
          analisisId: analisis.idAnalisis,
          analisis,
        },
      });
    },
    accessDiagnostico(diagnosticoId) {
      this.$router.push({
        name: "VerDiagnostico",
        params: {
          pacienteId: this.pacienteId,
          diagnosticoId: diagnosticoId,
        },
      });
    },
    fetchItems() {
      fetch(api + "paciente/" + this.pacienteId + "/diagnostico", {
        credentials: "include",
      })
        .then((stream) => stream.json())
        .then((data) => {
          this.existe = true;
          this.historia = data;
          this.historia.sort(this.orderByDate);
        })
        .catch((error) => {
          this.existe = false;
          console.error(error);
        });
        
    },
    orderByDate(a, b) {
      return new Date(b.fecha) - new Date(a.fecha);
    },
  },
  mounted() {
    this.fetchItems();
    console.log(this.historia)
  },
};
</script>
<style scoped>
#riesgo {
  color: white;
  font-weight: bold;
  background-color: red;
}
 .colorAnalisisPendiente{
    background-color:#fff3cd
 }
</style>