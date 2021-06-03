<template>
  <div id="app">
    <Navigation></Navigation>
    <div class = "m-4 p-3">
        <router-view/>
    </div>
  </div>
</template>

<script>
import Navigation from './components/Navigation.vue'
export default {
  name: 'App',
  components: {
    Navigation,
  }
}
</script>

<style lang="scss">
 @import './assets/styles/bootstrap.min.css';

.card-header {
    font-weight: bold;
    font-size: 17px;
}

</style>
