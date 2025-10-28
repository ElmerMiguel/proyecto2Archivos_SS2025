<template>
  <div class="container mt-4">
    <h3>📈 Reportes Administrativos</h3>
    
    <!-- TABS -->
    <ul class="nav nav-tabs">
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'productos' }" @click="activeTab = 'productos'">
          🏆 Top Productos ({{ topProductos.length }})
        </button>
      </li>
      <li class="nav-item">
        <button class="nav-link" :class="{ active: activeTab === 'clientes' }" @click="activeTab = 'clientes'">
          👥 Top Clientes ({{ topClientes.length }})
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
          <thead class="table-dark">
            <tr>
              <th>#</th>
              <th>Producto</th>
              <th>Vendedor</th>
              <th>Cantidad Vendida</th>
              <th>Total Ventas</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(producto, index) in topProductos" :key="index">
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
                <strong class="text-success">Q{{ producto.totalVentas.toLocaleString() }}</strong>
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
          <thead class="table-dark">
            <tr>
              <th>#</th>
              <th>Cliente</th>
              <th>Email</th>
              <th>Total Pedidos</th>
              <th>Total Gastado</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(cliente, index) in topClientes" :key="index">
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
                <strong class="text-success">Q{{ cliente.totalGastado.toLocaleString() }}</strong>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- VENTAS POR FECHA TAB -->
    <div v-if="activeTab === 'ventas' && !loading" class="mt-4">
      <h5>💰 Reporte de Ventas del Último Mes</h5>
      
      <!-- RESUMEN -->
      <div class="row mb-4">
        <div class="col-md-4">
          <div class="card bg-success text-white">
            <div class="card-body text-center">
              <h3>Q{{ resumenVentas.totalVentas.toLocaleString() }}</h3>
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
              <h3>Q{{ resumenVentas.promedioVenta.toLocaleString() }}</h3>
              <p>Promedio por Pedido</p>
            </div>
          </div>
        </div>
      </div>

      <!-- TABLA VENTAS DIARIAS -->
      <div class="table-responsive">
        <table class="table table-striped">
          <thead class="table-dark">
            <tr>
              <th>Fecha</th>
              <th>Pedidos</th>
              <th>Total Ventas</th>
              <th>Promedio</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="venta in ventasPorFecha" :key="venta.fecha">
              <td>{{ venta.fecha }}</td>
              <td>
                <span class="badge bg-primary">{{ venta.totalPedidos }}</span>
              </td>
              <td>
                <strong class="text-success">Q{{ venta.totalVentas.toLocaleString() }}</strong>
              </td>
              <td>Q{{ venta.promedio.toLocaleString() }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      activeTab: 'productos',
      loading: false,
      topProductos: [
        {
          nombreProducto: "Laptop Gaming ROG",
          descripcion: "Laptop para gaming de alta gama",
          nombreVendedor: "TechStore GT",
          cantidadVendida: 45,
          totalVentas: 67500
        },
        {
          nombreProducto: "iPhone 15 Pro",
          descripcion: "Smartphone Apple última generación",
          nombreVendedor: "iShop Guatemala",
          cantidadVendida: 38,
          totalVentas: 57000
        },
        {
          nombreProducto: "Samsung Galaxy S24",
          descripcion: "Smartphone Samsung flagship",
          nombreVendedor: "Samsung Store",
          cantidadVendida: 32,
          totalVentas: 48000
        },
        {
          nombreProducto: "MacBook Air M3",
          descripcion: "Laptop Apple con chip M3",
          nombreVendedor: "Apple Store GT",
          cantidadVendida: 28,
          totalVentas: 42000
        },
        {
          nombreProducto: "Mouse Logitech MX Master",
          descripcion: "Mouse inalámbrico profesional",
          nombreVendedor: "OfficeMax",
          cantidadVendida: 156,
          totalVentas: 15600
        },
        {
          nombreProducto: "Teclado Mecánico Corsair",
          descripcion: "Teclado gaming mecánico RGB",
          nombreVendedor: "Gamers Paradise",
          cantidadVendida: 89,
          totalVentas: 13350
        },
        {
          nombreProducto: "Monitor LG UltraWide",
          descripcion: "Monitor 34 pulgadas 4K",
          nombreVendedor: "LG Store",
          cantidadVendida: 23,
          totalVentas: 11500
        },
        {
          nombreProducto: "Audífonos Sony WH-1000XM5",
          descripcion: "Audífonos noise cancelling",
          nombreVendedor: "Sony Center",
          cantidadVendida: 67,
          totalVentas: 10050
        }
      ],
      topClientes: [
        {
          nombreCompleto: "Carlos Méndez",
          email: "carlos.mendez@email.com",
          totalPedidos: 23,
          totalGastado: 15750
        },
        {
          nombreCompleto: "Ana García",
          email: "ana.garcia@gmail.com", 
          totalPedidos: 19,
          totalGastado: 12300
        },
        {
          nombreCompleto: "Luis Rodríguez",
          email: "luis.rodriguez@hotmail.com",
          totalPedidos: 17,
          totalGastado: 11850
        },
        {
          nombreCompleto: "María López",
          email: "maria.lopez@yahoo.com",
          totalPedidos: 15,
          totalGastado: 9500
        },
        {
          nombreCompleto: "Jorge Hernández",
          email: "jorge.hernandez@email.com",
          totalPedidos: 13,
          totalGastado: 8750
        },
        {
          nombreCompleto: "Carmen Jiménez",
          email: "carmen.jimenez@gmail.com",
          totalPedidos: 12,
          totalGastado: 7200
        },
        {
          nombreCompleto: "Roberto Castillo",
          email: "roberto.castillo@email.com",
          totalPedidos: 11,
          totalGastado: 6800
        }
      ],
      ventasPorFecha: [
        { fecha: "28/10/2025", totalPedidos: 15, totalVentas: 18750, promedio: 1250 },
        { fecha: "27/10/2025", totalPedidos: 22, totalVentas: 27500, promedio: 1250 },
        { fecha: "26/10/2025", totalPedidos: 18, totalVentas: 22500, promedio: 1250 },
        { fecha: "25/10/2025", totalPedidos: 25, totalVentas: 31250, promedio: 1250 },
        { fecha: "24/10/2025", totalPedidos: 20, totalVentas: 25000, promedio: 1250 },
        { fecha: "23/10/2025", totalPedidos: 17, totalVentas: 21250, promedio: 1250 },
        { fecha: "22/10/2025", totalPedidos: 19, totalVentas: 23750, promedio: 1250 }
      ],
      resumenVentas: {
        totalVentas: 175000,
        totalPedidos: 136,
        promedioVenta: 1287
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
    
    // SIMULAR LOADING
    this.loading = true
    setTimeout(() => {
      this.loading = false
    }, 500)
  },
  methods: {
    getBadgeRank(posicion) {
      const badges = {
        1: 'bg-warning text-dark',  // Oro
        2: 'bg-secondary',         // Plata
        3: 'bg-danger'            // Bronce
      }
      return badges[posicion] || 'bg-primary'
    }
  }
}
</script>

<style scoped>
.badge {
  font-size: 0.9em;
}
.table th {
  border-top: none;
}
</style>