<template>
  <div>
    <b-card no-body header="Analisis" header-bg-variant="primary" header-text-variant="white">
      <b-list-group flush>
        <b-list-group-item v-for="item in analisis" :key="item.id">
          <b-card no-body :header="item.tipoAnalisis">
            <b-list-group flush>
              <b-list-group-item v-for="resultado in item.resultados" :key="resultado.id"  >
                  <div class="d-flex">
                    <div>
                    {{ resultado.campo }}
                    </div>
                    <div class="ml-auto">
                    {{ resultado.valor }}
                    </div>
                  </div>
              </b-list-group-item>
              <b-card-footer v-if="item.resultados.length <= 0" class="a">Analisis pendiente</b-card-footer>
            </b-list-group>
          </b-card>
        </b-list-group-item>
      </b-list-group>
    </b-card>
  </div>
</template>
<script>
export default {
  props: ["analisis"],
};
</script>
<style scoped>
 .a{
    background-color:#fff3cd
 }
</style>