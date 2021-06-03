<template>
  <div>
    <b-button @click="irSesiones" variant="primary" class="mb-3">Ver sesiones</b-button>
    <b-card no-body header="Diagnosticos">
      <b-list-group flush>
        <div
          v-for="item in historia"
          v-bind:key="item.idDiagnostico + '-' + item.numEdiciones"
        >
          <b-list-group-item
            class="flex-column align-items-start"
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
          href="#" v-for="analisis in item.analisis" v-bind:key="analisis.id"  v-bind:class="{ 'colorAnalisisPendiente':analisis.resultados.length === 0}" >
             <h6> Analisis</h6>{{analisis.tipoAnalisis}} <span v-if="analisis.resultados.length === 0">-- pendiente</span>
          </b-list-group-item>
          <div class="mb-3"></div>
        </div>
      </b-list-group>
    </b-card>

  </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";

export default {

  data() {
    return {
      historia: [],
      existe: true,
    };
  },
  methods: {
   
    editar: function (e){
      e.preventDefault();
      console.log("gggaaaaaaaaaaaa")
      this.$router.push({name:'EditPaciente',params:{
        pacienteId: this.pacienteId
      }})
    },
    fetchItems() {
      fetch(api + "diagnosticosuser", {
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
    irSesiones(){
         this.$router.push({
        name: "VerSesiones",
        
      });
    }
  },
  mounted() {
    this.fetchItems();
    console.log(this.historia)
  },
};
</script>
<style>
#riesgo {
  color: white;
  font-weight: bold;
  background-color: red;
}
 .colorAnalisisPendiente{
    background-color:#fff3cd
 }
</style>