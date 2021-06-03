<template>
  <div>
    <b-button variant="primary" v-on:click="button" class="mb-3"
        >Volver</b-button
      >
    <b-card
      class="mt-2"
      v-if="analisis.tipoAnalisis"
      :header="analisis.tipoAnalisis"
    >
      <b-form>
        <div v-for="(input, index) in analisis.resultados" :key="index">
          <b-form-row>
            <b-col
              ><b-form-input
                v-model="input.campo"
                type="text"
                placeholder="Campo"
                required
            /></b-col>
            <b-col
              ><b-form-input
                id="input-2"
                v-model="input.valor"
                type="text"
                placeholder="Valor"
                required
            /></b-col>
          </b-form-row>
           <b-button
          class="mr-3 mt-2 mb-3"
          variant="primary"
          @click="add()"
          v-show="index == analisis.resultados.length - 1"
          >Añadir</b-button
        >
           <b-button
          class="mr-3 mt-2 mb-3"
          variant="danger"
          @click="remove(index)"
          v-show="index || (!index && analisis.resultados.length > 1)"
          >Eliminar</b-button
        >
       
        </div>

       <b-button @click="send()" variant="primary">Submit</b-button>
      </b-form>
    </b-card>
  </div>
</template>
<script>
import { api } from "@/constantes/constantes.js";
export default {
  
  props:['analisis'],
  data() {
    return {
      
    };
  },
  methods: {
    button(){
      this.$router.go(-1)
    },
    add() {
      this.analisis.resultados.push({
        id: this.analisis.idAnalisis,
        campo: "",
        valor: "",
      });
    },
    remove(index) {
      this.analisis.resultados.splice(index, 1);
    },
    send() {
      fetch(api + "analisis/", {
        // Adding method type
        method: "PATCH",
        // Adding body or contents to send
        body: JSON.stringify(this.analisis),
        
        // Adding headers to the request
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        credentials: 'include',
      })
      .then(response => {
        if(response.ok){
            alert("Analisis registrado");
        }else{
          alert("Error al registrar");
        }
      }).catch(error => {
        alert("Error al registrar");
        console.error(error)}
        );
      
     
     // this.$router.push("/paciente/"+this.pacienteid+"/verHistoria")
    },
  },
  mounted(){
    this.analisis.resultados =  [
        {
          idAnalisis: this.analisis.idAnalisis,
          campo: "",
          valor: "",
        },
      ]
  }
};
</script>