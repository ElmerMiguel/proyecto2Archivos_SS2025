<template>
  <div class="container mt-4">
    <h3>📊 Dashboard Administrativo</h3>
    
    <!-- LOADING -->
    <div v-if="loading" class="text-center">
      <div class="spinner-border" role="status"></div>
    </div>

    <!-- CONTADORES -->
    <div v-else class="row">
      <!-- USUARIOS -->
      <div class="col-md-3 mb-3">
        <div class="card bg-primary text-white">
          <div class="card-body text-center">
            <h1>{{ stats.totalUsuarios }}</h1>
            <h6>👥 Usuarios Registrados</h6>
          </div>
        </div>
      </div>

      <!-- PRODUCTOS -->
      <div class="col-md-3 mb-3">
        <div class="card bg-success text-white">
          <div class="card-body text-center">
            <h1>{{ stats.totalProductos }}</h1>
            <h6>📦 Productos Activos</h6>
          </div>
        </div>
      </div>

      <!-- PEDIDOS -->
      <div class="col-md-3 mb-3">
        <div class="card bg-warning text-white">
          <div class="card-body text-center">
            <h1>{{ stats.totalPedidos }}</h1>
            <h6>🛒 Pedidos Realizados</h6>
          </div>
        </div>
      </div>

      <!-- VENTAS -->
      <div class="col-md-3 mb-3">
        <div class="card bg-info text-white">
          <div class="card-body text-center">
            <h1>Q{{ stats.totalVentas }}</h1>
            <h6>💰 Ventas Totales</h6>
          </div>
        </div>
      </div>
    </div>

    <!-- SOLICITUDES PENDIENTES -->
    <div class="row mt-4">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">
            <h5>🟡 Solicitudes Pendientes</h5>
          </div>
          <div class="card-body">
            <h2 class="text-center">{{ stats.solicitudesPendientes }}</h2>
            <router-link to="/moderador/solicitudes" class="btn btn-warning w-100">
              Ver Solicitudes
            </router-link>
          </div>
        </div>
      </div>

      <!-- PEDIDOS POR ENTREGAR -->
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">
            <h5>🚚 Pedidos por Entregar</h5>
          </div>
          <div class="card-body">
            <h2 class="text-center">{{ stats.pedidosPorEntregar }}</h2>
            <router-link to="/logistica/pedidos" class="btn btn-primary w-100">
              Ver Pedidos
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- ACCESOS RÁPIDOS -->
    <div class="row mt-4">
      <div class="col-12">
        <h5>🚀 Accesos Rápidos</h5>
        <div class="d-flex gap-2 flex-wrap">
          <router-link to="/admin/reportes" class="btn btn-outline-primary">
            📈 Ver Reportes
          </router-link>
          <router-link to="/admin/usuarios" class="btn btn-outline-success">
            👥 Gestionar Usuarios
          </router-link>
          <router-link to="/moderador/solicitudes" class="btn btn-outline-warning">
            📋 Revisar Solicitudes
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import api from '../../services/api'

export default {
  data() {
    return {
      loading: true,
      stats: {
        totalUsuarios: 0,
        totalProductos: 0,
        totalPedidos: 0,
        totalVentas: 0,
        solicitudesPendientes: 0,
        pedidosPorEntregar: 0
      }
    }
  },
  computed: {
    ...mapGetters(['isAuthenticated', 'isAdmin'])
  },
  async mounted() {
    if (!this.isAdmin) {
      this.$router.push({ name: 'Home' })
      return
    }
    await this.cargarEstadisticas()
  },
  methods: {
    async cargarEstadisticas() {
      this.loading = true
      try {
        const response = await api.get('/admin/dashboard')
        this.stats = response.data
        console.log('📊 Stats cargadas:', this.stats)
      } catch (e) {
        console.error('Error cargando estadísticas:', e)
        alert('Error cargando estadísticas: ' + (e.response?.data?.message || e.message))
      } finally {
        this.loading = false
      }
    }
  }
}
</script>