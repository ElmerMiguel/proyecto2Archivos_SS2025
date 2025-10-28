<template>
  <div class="container mt-4">
    <h3>Bienvenido a E-COMMERCE GT</h3>
    <div v-if="isAuthenticated">
      <p>Hola {{ user?.nombreCompleto }}!</p>
    </div>
    <div v-else>
      <p>Por favor, inicia sesión para continuar.</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  computed: {
    ...mapGetters(['isAuthenticated', 'user'])
  }
}
</script>