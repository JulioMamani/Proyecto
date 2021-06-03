<template>
  <div class="home">
    <b-form class="mb-3" >
      <b-input v-model="dni" v-on:keyup.enter="buscar"  @keypress.enter.prevent placeholder="Buscar paciente por numHistoria"></b-input>
      <b-button variant="primary" v-on:click="buscar" class="mt-4">Buscar</b-button>
     
    </b-form>
    
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
  data(){
    return{
      dni : ""
    }
  },
  methods: {
    addPaciente(){
      this.$router.push({name:'AddPaciente'})
    },
    buscar: function (e){
      e.preventDefault();
      this.$router.push({name:'VerTodosLosDiagnosticos',params:{
        pacienteId: this.dni
      }})
    },
    
  }
}
</script>
