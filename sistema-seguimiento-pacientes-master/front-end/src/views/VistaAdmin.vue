<template>
  <div>
    <buscar-medico class="mb-3"></buscar-medico>
    <buscar-paciente></buscar-paciente>
    <b-button variant="primary" @click="addPaciente">Añadir paciente</b-button>
  </div>
</template>
<script>
import BuscarMedico from "@/components/BuscarMedico.vue";
import BuscarPaciente from '@/components/BuscarPaciente.vue';
export default {
  components: { BuscarMedico,BuscarPaciente },
methods:{
    addPaciente(){
        this.$router.push({name:'AddPaciente'})
    }
}
};
</script>