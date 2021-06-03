<template>
  <div>
    <b-button @click="atras" class="m-3">Atras</b-button>
    <b-card no-body header="Sesiones">
      <b-list-group flush>
        <div
          v-for="item in sesiones"
          v-bind:key="item.idDiagnostico + '-' + item.numEdiciones"
        >
          <b-list-group-item
            class="flex-column align-items-start"
            v-on:click="accederLlamada(item.id)"
            href="#"
          ><h6 class="m-0">Medico: </h6>
            <div class="d-flex w-100 justify-content-between m-0">
              <h5 class="mb-1">{{ item.medico.nombre }}</h5>
              <small class="text-muted">Fecha: {{ new Date(item.fecha).toLocaleDateString("es-PE") }} {{new Date(item.fecha).toLocaleTimeString("es-PE") }}</small>
            </div>
            <div class="m-2"></div>
            <h6 class="m-0">Paciente: </h6>
            <h5 class="mb-1">
              {{ item.paciente.nombres }}
            </h5>
          </b-list-group-item>
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
      sesiones: [],
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
    accederLlamada(idSesion){
      this.$router.push({
        name: "CrearVideollamada",
        params: {
          salaId:idSesion,
        },
      });
    },
    fetchItems() {
      fetch(api + "videollamada/sesiones", {
        credentials: "include",
      })
        .then((stream) => stream.json())
        .then((data) => {
          this.existe = true;
          this.sesiones = data;
          this.sesiones.sort(this.orderByDate);
        })
        .catch((error) => {
          this.existe = false;
          console.error(error);
        });
        
    },
    atras(){
      this.$router.go(-1)
    },
    orderByDate(a, b) {
      return new Date(b.fecha) - new Date(a.fecha);
    },
  },
  mounted() {
    this.fetchItems();
    console.log(this.sesiones)
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