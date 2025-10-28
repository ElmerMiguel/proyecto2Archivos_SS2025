<template>
  <div class="container mt-4">
    <h3>Mi Carrito</h3>
    
    <div v-if="loading" class="text-center">
      <div class="spinner-border" role="status"></div>
    </div>
    
    <div v-else-if="!carrito || !carrito.items || carrito.items.length === 0" class="text-center">
      <div class="alert alert-info">
        <h5>Tu carrito está vacío</h5>
        <p>Agrega productos para comenzar a comprar</p>
        <router-link to="/productos" class="btn btn-primary">Ver Productos</router-link>
      </div>
    </div>
    
    <div v-else>
      <div class="row">
        <div class="col-md-8">
          <div class="card">
            <div class="card-body">
              <!-- AGREGAR BOTÓN VACIAR -->
              <div class="d-flex justify-content-between align-items-center mb-3">
                <h5>Productos en tu carrito</h5>
                <!-- El botón abre la modal de confirmación -->
                <button 
                  class="btn btn-outline-danger btn-sm" 
                  @click="showConfirmModal = true"
                  :disabled="!carrito?.items?.length"
                >
                  🗑️ Vaciar Carrito
                </button>
              </div>
              
              <div v-for="item in carrito.items" :key="item.idItemCarrito" class="row align-items-center border-bottom py-3">
                <div class="col-md-6">
                  <h6>{{ item.producto?.nombreProducto }}</h6>
                  <small class="text-muted">Q{{ item.producto?.precio }} c/u</small>
                </div>
                <div class="col-md-3">
                  <div class="input-group">
                    <button class="btn btn-outline-secondary btn-sm" @click="cambiarCantidad(item, -1)">-</button>
                    <input type="number" class="form-control form-control-sm text-center" v-model="item.cantidad" @change="actualizarCantidad(item)" min="1">
                    <button class="btn btn-outline-secondary btn-sm" @click="cambiarCantidad(item, 1)">+</button>
                  </div>
                </div>
                <div class="col-md-2">
                  <strong>Q{{ item.subtotal }}</strong>
                </div>
                <div class="col-md-1">
                  <button class="btn btn-danger btn-sm" @click="eliminarItem(item.idItemCarrito)">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="col-md-4">
          <div class="card">
            <div class="card-header">
              <h5>Resumen</h5>
            </div>
            <div class="card-body">
              <div class="d-flex justify-content-between">
                <span>Subtotal:</span>
                <strong>Q{{ carrito.total }}</strong>
              </div>
              <hr>
              <div class="d-flex justify-content-between">
                <span><strong>Total:</strong></span>
                <strong>Q{{ carrito.total }}</strong>
              </div>
              <button class="btn btn-success w-100 mt-3" @click="procederPago">
                Proceder al Pago
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- MODAL DE CONFIRMACIÓN CUSTOM (Reemplazo de confirm()) -->
    <div v-if="showConfirmModal" class="modal show d-block" tabindex="-1" style="background: rgba(0,0,0,0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">⚠️ Confirmación</h5>
          </div>
          <div class="modal-body">
            <p>¿Estás seguro de que quieres **vaciar completamente** el carrito?</p>
            <p class="text-danger">Esta acción no se puede deshacer.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="showConfirmModal = false">Cancelar</button>
            <button type="button" class="btn btn-danger" @click="vaciarTodo">Sí, Vaciar Carrito</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      loading: true,
      showConfirmModal: false 
    }
  },
  computed: {
    ...mapGetters(['carrito'])
  },
  async mounted() {
    await this.cargarCarrito()
  },
  methods: {
    async cargarCarrito() {
      this.loading = true
      try {
        await this.$store.dispatch('loadCarrito')
      } catch (e) {
        console.error('Error cargando carrito:', e)
      } finally {
        this.loading = false
      }
    },
    async cambiarCantidad(item, cambio) {
      const nuevaCantidad = item.cantidad + cambio
      if (nuevaCantidad > 0) {
        await this.actualizarCantidad({ ...item, cantidad: nuevaCantidad })
      }
    },
    async actualizarCantidad(item) {
      try {
        await this.$store.dispatch('actualizarCarrito', {
          idItem: item.idItemCarrito,
          cantidad: item.cantidad
        })
      } catch (e) {
        console.error('Error actualizando cantidad:', e)
      }
    },
    async eliminarItem(idItem) {
      if (confirm('¿Eliminar este producto del carrito?')) {
        try {
          await this.$store.dispatch('eliminarDelCarrito', idItem)
        } catch (e) {
          console.error('Error eliminando producto:', e)
        }
      }
    },
    async vaciarTodo() {
      this.showConfirmModal = false // Cerrar la modal
      try {
        await this.$store.dispatch('vaciarCarrito')
      } catch (e) {
        console.error('Error vaciando carrito:', e)
      }
    },
    procederPago() {
      this.$router.push({ name: 'Checkout' })
    }
  }
}
</script>
