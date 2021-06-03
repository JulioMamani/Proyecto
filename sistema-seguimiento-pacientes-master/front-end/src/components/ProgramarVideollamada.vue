<template>
  <div>
    <b-button v-b-modal.modal-prevent-closing variant="primary">Programar Videollamada</b-button>
    <b-modal
      id="modal-prevent-closing"
      ref="modal"
      title="Programar videollamada"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group label="Fecha" label-for="tipo-input">
          <b-form-datepicker id="tipo-input" v-model="fecha" class="mb-2" required ></b-form-datepicker>
        </b-form-group>
        <b-form-group label="Hora" label-for="tipo-input2">
         <b-form-timepicker id="tipo-input2" v-model="hora" class="mb-2" required ></b-form-timepicker>>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
import {api} from "@/constantes/constantes.js"
export default {
  props: ["idPaciente"],
  data() {
    return {

       fecha:null,
       hora:null,
    };
  },
  methods: {
    solicitar() {
      fetch(api + "videollamada/crearsesion", {
        // Adding method type
        method: "POST",
        // Adding body or contents to send
        body: JSON.stringify(this.solicitud),

        // Adding headers to the request
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        credentials: "include",
      })
        .then((response) => {
            this.resetModal()
          if (response.ok) {
            alert("Solicitud realizada");
          } else {
            alert("Error al registrar");
          }
        })
        .catch((error) => {
          alert("Error al registrar");
          console.error(error);
        });
    },

    resetModal() {
      this.selected = null;
    },
    handleOk() {
      this.solicitar()
    },
  },computed:{
      solicitud(){
          return{
              idPaciente:this.idPaciente,
              fecha: new Date (this.fecha + ' ' + this.hora).getTime()
          }
      }
  }
};
</script>