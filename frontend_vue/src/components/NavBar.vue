<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <router-link class="navbar-brand" to="/">E-COMMERCE GT</router-link>
      
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item"><router-link class="nav-link" to="/">Home</router-link></li>
          
          <!-- TODOS LOS USUARIOS AUTENTICADOS -->
          <li v-if="isAuthenticated" class="nav-item"><router-link class="nav-link" to="/productos">Productos</router-link></li>
          
          <!-- SOLO USUARIOS COMUNES -->
          <li v-if="isComun" class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              Mi Cuenta
            </a>
            <ul class="dropdown-menu">
              <li><router-link class="dropdown-item" to="/mis-productos">Mis Productos</router-link></li>
              <li><router-link class="dropdown-item" to="/mis-pedidos">Mis Pedidos</router-link></li>
              <li><router-link class="dropdown-item" to="/crear-producto">Vender Producto</router-link></li>
            </ul>
          </li>
          
          <!-- SOLO MODERADORES -->
          <li v-if="isModerador" class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              Moderación
            </a>
            <ul class="dropdown-menu">
              <li><router-link class="dropdown-item" to="/moderador/solicitudes">Solicitudes Pendientes</router-link></li>
              <li><router-link class="dropdown-item" to="/moderador/sanciones">Gestionar Sanciones</router-link></li>
            </ul>
          </li>
          
          <!-- SOLO LOGÍSTICA -->
          <li v-if="isLogistica" class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              Logística
            </a>
            <ul class="dropdown-menu">
              <li><router-link class="dropdown-item" to="/logistica/pedidos">Pedidos en Curso</router-link></li>
              <li><router-link class="dropdown-item" to="/logistica/entregas">Gestionar Entregas</router-link></li>
            </ul>
          </li>
          
          <!-- SOLO ADMINISTRADORES -->
          <li v-if="isAdmin" class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              Administración
            </a>
            <ul class="dropdown-menu">
              <li><router-link class="dropdown-item" to="/admin/dashboard">Dashboard</router-link></li>
              <li><router-link class="dropdown-item" to="/admin/reportes">Reportes</router-link></li>
              <li><router-link class="dropdown-item" to="/admin/usuarios">Gestión Usuarios</router-link></li>
            </ul>
          </li>
        </ul>
        
        <ul class="navbar-nav ms-auto">
          <!-- CARRITO SOLO PARA USUARIOS COMUNES -->
          <li v-if="isComun" class="nav-item">
            <router-link class="nav-link position-relative" to="/carrito">
              🛒 <span class="d-none d-md-inline">Carrito</span>
              <span v-if="carritoCount > 0" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                {{ carritoCount }}
              </span>
            </router-link>
          </li>
          
          <li v-if="!isAuthenticated" class="nav-item"><router-link class="nav-link" to="/login">Login</router-link></li>
          <li v-if="!isAuthenticated" class="nav-item"><router-link class="nav-link" to="/register">Register</router-link></li>
          
          <li v-if="isAuthenticated" class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              {{ user?.nombreCompleto || user?.email }}
              <span class="badge bg-secondary">{{ userRole }}</span>
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" @click.prevent="logout" href="#">Cerrar Sesión</a></li>
            </ul>
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
    ...mapGetters(['isAuthenticated', 'user', 'carritoCount', 'userRole', 'isComun', 'isAdmin', 'isModerador', 'isLogistica'])
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>
