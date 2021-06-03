<template>
  <div>
    <div v-if="existe">
      <b-card header="Datos del Médico" class="mb-4">
        <div class="float-left">
          <h5 class>
            <span class="font-weight-bold">Nombre:</span>{{ nombre }}
          </h5>
          <h6 class><span class="font-weight-bold">DNI:</span>{{ idMedico }}</h6>
         <h6 class>
            <span class="font-weight-bold">Especialidad:</span>{{ especialidad }}
          </h6> 
        </div>
      </b-card>
   </div>
    <div v-else> 
      <b-alert variant="danger" show>El medico con historia {{idMedico}} no existe</b-alert>
      <b-button variant="primary" v-on:click="button" class="mb-3">Volver a busqueda</b-button>
    </div>
   </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";
export default {
  name: "DatosMedico",
  props: ["id"],
  data() {
    return {
      nombre: "",
      idMedico: "",
      especialidad: "",
      existe: false,
    };
  },
  methods: {
    button() {
      this.$router.push("/medico");
    },
    fetchItems() {
      fetch(api + "medico/" + this.id, {
        credentials: "include",
      })
        .then((respuesta) => {
          if (respuesta.ok) {
            this.existe = true;
          } else {
            this.existe = false;
          }
          return respuesta.json();
        })
        .then((data) => {
          this.nombre = data.nombre;
          this.idMedico = data.idMedico;
          this.especialidad = data.especialidad;
        })
        .catch((error) => {
          this.existe = false;
          console.error(error);
        });
    },
  },
  mounted() {
    this.fetchItems();
  },
};
</script>