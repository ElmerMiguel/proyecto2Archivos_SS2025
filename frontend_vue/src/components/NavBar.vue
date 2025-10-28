<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <router-link class="navbar-brand" to="/">E-COMMERCE GT</router-link>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto">
          <li class="nav-item"><router-link class="nav-link" to="/">Home</router-link></li>
          <li class="nav-item"><router-link class="nav-link" to="/productos">Productos</router-link></li>
        </ul>
        <ul class="navbar-nav ms-auto">
          <li v-if="isAuthenticated" class="nav-item">
            <router-link class="nav-link position-relative" to="/carrito">
              <i class="fas fa-shopping-cart"></i>
              <span v-if="carritoCount > 0" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                {{ carritoCount }}
              </span>
            </router-link>
          </li>
          <li v-if="!isAuthenticated" class="nav-item"><router-link class="nav-link" to="/login">Login</router-link></li>
          <li v-if="!isAuthenticated" class="nav-item"><router-link class="nav-link" to="/register">Register</router-link></li>
          <li v-if="isAuthenticated" class="nav-item">
            <span class="nav-link">{{ user?.nombreCompleto || user?.email }}</span>
          </li>
          <li v-if="isAuthenticated" class="nav-item">
            <a class="nav-link" @click.prevent="logout" href="#">Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  computed: {
    ...mapGetters(['isAuthenticated', 'user', 'carritoCount'])
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>