<template>
  <div>
    <div v-if="existe">
      <b-card header="Datos del paciente" class="mb-4">
        <div class="float-left">
          <h5 class>
            <span class="font-weight-bold">Nombre:</span>{{ nombres }}
          </h5>
          <h6 class><span class="font-weight-bold">Num Documento:</span>{{ numDocumento }}</h6>
        </div>
      </b-card>
    </div>
    <div v-else>
      <b-alert variant="danger" show>El paciente con documento {{id}} no existe</b-alert>
      <b-button variant="primary" v-on:click="button" class="mb-3">Volver a busqueda</b-button>
    </div>
  </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";
export default {
  name: "DatosPaciente",
  props: ["id"],
  data() {
    return {
      nombres: "",
      numDocumento: "",
      existe: false
    };
  },
  methods: {
    button(){
      this.$router.push("/paciente")
    },
    fetchItems() {
      fetch(api + "paciente/" + this.id,{
        credentials:"include"
      })
        .then((respuesta) => {
          if(respuesta.ok){
              this.existe = true
          }else{
              this.existe = false
          }
          return respuesta.json()})
        .then((data) => {
          this.nombres = data.nombres;
          this.numDocumento = data.numDocumento;
        })
        .catch((error) =>{
          this.existe = false
            console.error(error)
        } );
    },
  },
  mounted() {
    this.fetchItems();
  },
};
</script>