<template>
  <div>
    <DatosPaciente v-bind:id="pacienteid"></DatosPaciente>
    <router-view></router-view>
  </div>
</template>
<script>
import DatosPaciente from "@/components/DatosPaciente.vue";
export default {
  components: {
    DatosPaciente,
  },
  data() {
    return {
      pacienteid:  this.$route.params.pacienteId,
    };
  },
};
</script>