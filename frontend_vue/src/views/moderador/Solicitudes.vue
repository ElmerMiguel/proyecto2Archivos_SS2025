<template>
  <div class="container mt-4">
    <h3>🟡 Moderador - Solicitudes de Productos</h3>
    
    <!-- TABS -->
    <ul class="nav nav-tabs">
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'pendientes' }" @click="activeTab = 'pendientes'">
          Pendientes ({{ solicitudesPendientes.length }})
        </button>
      </li>
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'todas' }" @click="activeTab = 'todas'">
          Todas ({{ todasSolicitudes.length }})
        </button>
      </li>
    </ul>

    <!-- LOADING -->
    <div v-if="loading" class="text-center mt-4">
      <div class="spinner-border" role="status"></div>
    </div>

    <!-- PENDIENTES TAB -->
    <div v-if="activeTab === 'pendientes' && !loading" class="mt-3">
      <div v-if="solicitudesPendientes.length === 0" class="alert alert-info">
        No hay solicitudes pendientes
      </div>
      <div class="row">
        <div v-for="solicitud in solicitudesPendientes" :key="solicitud.idSolicitud" class="col-md-6 mb-3">
          <div class="card">
            <img :src="solicitud.urlImagen" class="card-img-top" style="height: 200px; object-fit: cover;" alt="Producto">
            <div class="card-body">
              <h5 class="card-title">{{ solicitud.nombreProducto }}</h5>
              <p class="card-text">{{ solicitud.descripcion }}</p>
              <p><strong>Precio:</strong> Q{{ solicitud.precio }}</p>
              <p><strong>Stock:</strong> {{ solicitud.stock }}</p>
              <p><strong>Vendedor:</strong> {{ solicitud.nombreVendedor }}</p>
              <p><strong>Categoría:</strong> {{ solicitud.nombreCategoria }}</p>
              
              <div class="d-flex gap-2">
                <button class="btn btn-success" @click="aprobar(solicitud.idSolicitud)" :disabled="procesando">
                  ✅ Aprobar
                </button>
                <button class="btn btn-danger" @click="rechazar(solicitud.idSolicitud)" :disabled="procesando">
                  ❌ Rechazar
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- TODAS TAB -->
    <div v-if="activeTab === 'todas' && !loading" class="mt-3">
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Producto</th>
              <th>Vendedor</th>
              <th>Estado</th>
              <th>Precio</th>
              <th>Fecha</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="solicitud in todasSolicitudes" :key="solicitud.idSolicitud">
              <td>{{ solicitud.idSolicitud }}</td>
              <td>{{ solicitud.nombreProducto }}</td>
              <td>{{ solicitud.nombreVendedor }}</td>
              <td>
                <span :class="getBadgeClass(solicitud.nombreEstado)">
                  {{ solicitud.nombreEstado }}
                </span>
              </td>
              <td>Q{{ solicitud.precio }}</td>
              <td>{{ formatDate(solicitud.fechaCreacion) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import api from '../../services/api'  // ← IMPORTAR API

export default {
  data() {
    return {
      activeTab: 'pendientes',
      solicitudesPendientes: [],
      todasSolicitudes: [],
      loading: false,
      procesando: false
    }
  },
  computed: {
    ...mapGetters(['isAuthenticated', 'isModerador'])
  },
  async mounted() {
    if (!this.isModerador) {
      this.$router.push({ name: 'Home' })
      return
    }
    await this.cargarSolicitudes()
  },
  methods: {
    async cargarSolicitudes() {
      this.loading = true
      try {
        // USAR API SERVICE
        const [todasResponse, pendientesResponse] = await Promise.all([
          api.get('/moderador/solicitudes'),
          api.get('/moderador/solicitudes/pendientes')
        ])
        
        this.todasSolicitudes = todasResponse.data
        this.solicitudesPendientes = pendientesResponse.data
        
        console.log('📋 Solicitudes cargadas:', this.todasSolicitudes.length)
        
      } catch (e) {
        console.error('Error cargando solicitudes:', e)
        alert('Error cargando solicitudes: ' + (e.response?.data?.message || e.message))
      } finally {
        this.loading = false
      }
    },
    
    async aprobar(idSolicitud) {
      if (!confirm('¿Aprobar esta solicitud?')) return
      
      this.procesando = true
      try {
        await api.put(`/moderador/solicitudes/${idSolicitud}/aprobar`)
        alert('✅ Producto aprobado exitosamente')
        await this.cargarSolicitudes()
      } catch (e) {
        console.error('Error aprobando:', e)
        alert('❌ Error aprobando producto: ' + (e.response?.data?.error || e.message))
      } finally {
        this.procesando = false
      }
    },
    
    async rechazar(idSolicitud) {
      const motivo = prompt('Motivo del rechazo (obligatorio):')
      if (!motivo || motivo.trim() === '') {
        alert('El motivo es obligatorio')
        return
      }
      
      this.procesando = true
      try {
        const params = new URLSearchParams()
        params.append('comentario', motivo)
        
        await api.put(`/moderador/solicitudes/${idSolicitud}/rechazar`, null, { params })
        alert('❌ Producto rechazado')
        await this.cargarSolicitudes()
      } catch (e) {
        console.error('Error rechazando:', e)
        alert('❌ Error rechazando producto: ' + (e.response?.data?.error || e.message))
      } finally {
        this.procesando = false
      }
    },
    
    getBadgeClass(estado) {
      const classes = {
        'PENDIENTE': 'badge bg-warning text-dark',
        'APROBADO': 'badge bg-success',
        'RECHAZADO': 'badge bg-danger'
      }
      return classes[estado] || 'badge bg-secondary'
    },
    
    formatDate(fecha) {
      if (!fecha) return 'N/A'
      return new Date(fecha).toLocaleDateString('es-GT')
    }
  }
}
</script>