<template>
  <div>
    <b-button @click="getToken" v-if="!opentokdata"
      >Ingresar a la llamada</b-button
    >
    <prueba-open
      v-else
      :apiKey="opentokdata.apiKey"
      :sessionId="opentokdata.sessionId"
      :token="opentokdata.token"
    />
  </div>
</template>

<script>
import { EventBus } from "@/eventbus/eventbus.js";
import { api } from "@/constantes/constantes.js";
import PruebaOpen from "@/components/pruebaOpen.vue";
export default {
  name: "prueba",
  components: {
    PruebaOpen,
  },
  data() {
    return {
      opentokdata: null,
      kkkk:"",
    };
  },

  methods: {
    async getToken() {
      await fetch(
        api + "opentok/",
        //api + "paciente/" + this.$route.params.pacienteId + "/diagnostico/" + this.$route.params.diagnosticoId+"/analisis/"+this.$route.params.analisisId,
        {
          credentials: "include",
        }
      )
        .then((datos) => datos.json())
        .then((data) => {
          this.opentokdata = data;
        })
        .catch((error) => console.error(error));
       
    },

  },
  mounted(){
     EventBus.$on("desconectado", () =>{
         this.opentokdata = null
      })
  }

};
</script>