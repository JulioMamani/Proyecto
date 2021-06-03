<template>
  <div>
    <b-button v-b-modal.modal-prevent-closing>Solicitar analisis</b-button>
    <b-modal
      id="modal-prevent-closing"
      ref="modal"
      title="Solicitar analisis"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group label="Tipo analisis" label-for="tipo-input"
          ><b-form-select
            v-model="solicitud.tipoAnalisis"
            :options="options"
            class="mb-3"
          ></b-form-select>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
import {api} from "@/constantes/constantes.js"
export default {
  props: ["diagnostico"],
  data() {
    return {

      options: [
        { value: null, text: "elija un tipo de prueba" },
        "prueba rapida covid",
        "prueba molecular covid",
        "analisis de sangre",
      ],
      solicitud:{
          idDiagnostico: this.diagnostico.idDiagnostico,
          docPaciente:this.diagnostico.docPaciente,
          tipoAnalisis:null,
      }
    };
  },
  methods: {
    solicitar() {
      fetch(api + "analisis/solicitar", {
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
  },
};
</script>