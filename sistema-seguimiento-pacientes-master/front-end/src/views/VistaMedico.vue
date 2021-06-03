<template>
    <div>
        <buscar-paciente></buscar-paciente>
         <b-button @click="sesiones">Ver sesiones videollamada</b-button>
    </div>
</template>
<script>
import BuscarPaciente from '@/components/BuscarPaciente.vue'
export default {
    components:{
        BuscarPaciente
    },methods:{
        sesiones(){
      this.$router.push({name:'VerSesiones'})
    }
    }
}
</script>BuscarPaciente