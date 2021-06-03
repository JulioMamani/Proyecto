<template>
  <div class="home">
    <b-form class="mb-3" >
      <b-input v-model="id" v-on:keyup.enter="buscar"  @keypress.enter.prevent placeholder="Buscar medico por idMedico"></b-input>
    </b-form>
    <b-button variant="primary" v-on:click="buscar" class="mt-3 mr-2">Buscar Medico</b-button>
      <b-button variant="primary" v-on:click="editar" class="mt-3 mr-2">Editar Medico</b-button>
    <b-button @click="addMedico" variant="primary" class="mt-3 mr-2">Añadir medico</b-button>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
  data(){
    return{
      id : ""
    }
  },
  methods: {
    addMedico(){
      this.$router.push({name:'AddMedico'})
    },
    buscar: function (e){
      e.preventDefault();
      console.log("aaeeeeea")
      this.$router.push('medico/'+this.id+'/VerMedico')
    },
    editar: function (e){
      e.preventDefault();
      console.log("gaaaagaaa")
      this.$router.push({name:'EditMedico',params:{
        medicoid: this.id
      }})
    }
  }
}
</script>