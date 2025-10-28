<template>
  <div class="container mt-4">
    <h3>📈 Reportes Administrativos</h3>
    
    <!-- TABS -->
    <ul class="nav nav-tabs">
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'productos' }" @click="activeTab = 'productos'">
          🏆 Top Productos
        </button>
      </li>
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'clientes' }" @click="activeTab = 'clientes'">
          👥 Top Clientes
        </button>
      </li>
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'ventas' }" @click="activeTab = 'ventas'">
          💰 Ventas por Fecha
        </button>
      </li>
    </ul>

    <!-- LOADING -->
    <div v-if="loading" class="text-center mt-4">
      <div class="spinner-border" role="status"></div>
    </div>

    <!-- TOP PRODUCTOS TAB -->
    <div v-if="activeTab === 'productos' && !loading" class="mt-4">
      <h5>🏆 Productos Más Vendidos</h5>
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Producto</th>
              <th>Vendedor</th>
              <th>Cantidad Vendida</th>
              <th>Total Ventas</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(producto, index) in topProductos" :key="producto.idProducto">
              <td>
                <span class="badge" :class="getBadgeRank(index + 1)">
                  {{ index + 1 }}
                </span>
              </td>
              <td>
                <strong>{{ producto.nombreProducto }}</strong>
                <br><small class="text-muted">{{ producto.descripcion }}</small>
              </td>
              <td>{{ producto.nombreVendedor }}</td>
              <td>
                <span class="badge bg-info">{{ producto.cantidadVendida }} unidades</span>
              </td>
              <td>
                <strong>Q{{ producto.totalVentas }}</strong>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- TOP CLIENTES TAB -->
    <div v-if="activeTab === 'clientes' && !loading" class="mt-4">
      <h5>👥 Clientes Más Activos</h5>
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Cliente</th>
              <th>Email</th>
              <th>Total Pedidos</th>
              <th>Total Gastado</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(cliente, index) in topClientes" :key="cliente.idUsuario">
              <td>
                <span class="badge" :class="getBadgeRank(index + 1)">
                  {{ index + 1 }}
                </span>
              </td>
              <td>
                <strong>{{ cliente.nombreCompleto }}</strong>
              </td>
              <td>{{ cliente.email }}</td>
              <td>
                <span class="badge bg-primary">{{ cliente.totalPedidos }} pedidos</span>
              </td>
              <td>
                <strong>Q{{ cliente.totalGastado }}</strong>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- VENTAS POR FECHA TAB -->
    <div v-if="activeTab === 'ventas' && !loading" class="mt-4">
      <h5>💰 Reporte de Ventas</h5>
      
      <!-- FILTROS -->
      <div class="row mb-3">
        <div class="col-md-4">
          <label class="form-label">Fecha Inicio:</label>
          <input type="date" class="form-control" v-model="filtros.fechaInicio">
        </div>
        <div class="col-md-4">
          <label class="form-label">Fecha Fin:</label>
          <input type="date" class="form-control" v-model="filtros.fechaFin">
        </div>
        <div class="col-md-4">
          <label class="form-label">&nbsp;</label>
          <button class="btn btn-primary d-block" @click="cargarVentasPorFecha">
            🔍 Filtrar
          </button>
        </div>
      </div>

      <!-- RESULTADOS -->
      <div class="row">
        <div class="col-md-4">
          <div class="card bg-success text-white">
            <div class="card-body text-center">
              <h3>Q{{ resumenVentas.totalVentas }}</h3>
              <p>Total Ventas</p>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="card bg-info text-white">
            <div class="card-body text-center">
              <h3>{{ resumenVentas.totalPedidos }}</h3>
              <p>Total Pedidos</p>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="card bg-warning text-white">
            <div class="card-body text-center">
              <h3>Q{{ resumenVentas.promedioVenta }}</h3>
              <p>Promedio por Pedido</p>
            </div>
          </div>
        </div>
      </div>

      <!-- TABLA DETALLE -->
      <div class="table-responsive mt-3">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Fecha</th>
              <th>Pedidos</th>
              <th>Total Ventas</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="venta in ventasPorFecha" :key="venta.fecha">
              <td>{{ formatDate(venta.fecha) }}</td>
              <td>{{ venta.totalPedidos }}</td>
              <td>Q{{ venta.totalVentas }}</td>
            </tr>
          </tbody>
        </table>
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
      activeTab: 'productos',
      loading: false,
      topProductos: [],
      topClientes: [],
      ventasPorFecha: [],
      resumenVentas: {
        totalVentas: 0,
        totalPedidos: 0,
        promedioVenta: 0
      },
      filtros: {
        fechaInicio: '',
        fechaFin: ''
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
    
    // ESTABLECER FECHAS POR DEFECTO (ÚLTIMO MES)
    const hoy = new Date()
    const hace30Dias = new Date(hoy.getTime() - (30 * 24 * 60 * 60 * 1000))
    
    this.filtros.fechaInicio = hace30Dias.toISOString().split('T')[0]
    this.filtros.fechaFin = hoy.toISOString().split('T')[0]
    
    await this.cargarTodosLosReportes()
  },
  watch: {
    activeTab() {
      this.cargarTodosLosReportes()
    }
  },
  methods: {
    async cargarTodosLosReportes() {
      this.loading = true
      try {
        const [productosRes, clientesRes] = await Promise.all([
          api.get('/admin/reportes/productos-mas-vendidos'),
          api.get('/admin/reportes/clientes-mas-activos')
        ])
        
        this.topProductos = productosRes.data
        this.topClientes = clientesRes.data
        
        console.log('📊 Reportes cargados')
        
      } catch (e) {
        console.error('Error cargando reportes:', e)
        alert('Error cargando reportes: ' + (e.response?.data?.message || e.message))
      } finally {
        this.loading = false
      }
    },
    
    async cargarVentasPorFecha() {
      if (!this.filtros.fechaInicio || !this.filtros.fechaFin) {
        alert('Selecciona ambas fechas')
        return
      }
      
      this.loading = true
      try {
        const params = new URLSearchParams()
        params.append('fechaInicio', this.filtros.fechaInicio)
        params.append('fechaFin', this.filtros.fechaFin)
        
        const response = await api.get('/admin/reportes/ventas-por-fecha', { params })
        this.ventasPorFecha = response.data
        
        // CALCULAR RESUMEN
        this.resumenVentas.totalPedidos = this.ventasPorFecha.reduce((sum, v) => sum + v.totalPedidos, 0)
        this.resumenVentas.totalVentas = this.ventasPorFecha.reduce((sum, v) => sum + v.totalVentas, 0)
        this.resumenVentas.promedioVenta = this.resumenVentas.totalPedidos > 0 
          ? (this.resumenVentas.totalVentas / this.resumenVentas.totalPedidos).toFixed(2)
          : 0
        
      } catch (e) {
        console.error('Error cargando ventas por fecha:', e)
        alert('Error cargando ventas: ' + (e.response?.data?.message || e.message))
      } finally {
        this.loading = false
      }
    },
    
    getBadgeRank(posicion) {
      const badges = {
        1: 'bg-warning',  // Oro
        2: 'bg-secondary', // Plata
        3: 'bg-danger'     // Bronce
      }
      return badges[posicion] || 'bg-primary'
    },
    
    formatDate(fecha) {
      return new Date(fecha).toLocaleDateString('es-GT')
    }
  }
}
</script>