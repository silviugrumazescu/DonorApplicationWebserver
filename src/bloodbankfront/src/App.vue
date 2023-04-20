<template>
  <header>
    <Header />
  </header>
  <body>
    <router-view/>
  </body>
  
</template>

<script>

  import Header from './components/Header.vue'

  export default {
    name: 'App',
    components: {
      Header,
    }
  }

</script>

<style>
  header {
    margin-bottom: 4em;
  }
</style>