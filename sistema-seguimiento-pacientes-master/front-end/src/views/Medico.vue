<template>
    <div>
        <DatosMedico v-bind:id="this.medicoid"></DatosMedico>
        <router-view></router-view>
    </div>
</template>
<script>
import DatosMedico from "@/components/DatosMedico.vue";
export default {
  components: {
    DatosMedico
  },
     data() {
    return {
      medicoid: this.$route.params.medicoid,
}
     }
}
</script>