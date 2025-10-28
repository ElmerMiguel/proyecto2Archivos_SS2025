<template>
  <div class="container mt-4">
    <h3>Finalizar Compra</h3>
    
    <div v-if="loading" class="text-center">
      <div class="spinner-border" role="status"></div>
    </div>
    
    <div v-else-if="!carrito || !carrito.items || carrito.items.length === 0" class="alert alert-warning">
      No hay productos en el carrito. <router-link to="/productos">Ver productos</router-link>
    </div>
    
    <div v-else class="row">
      <div class="col-md-8">
        <div class="card mb-4">
          <div class="card-header">
            <h5>📦 Resumen del Pedido</h5>
          </div>
          <div class="card-body">
            <div v-for="item in carrito.items" :key="item.idItemCarrito" class="row align-items-center border-bottom py-2">
              <div class="col-md-6">
                <h6>{{ item.producto?.nombreProducto }}</h6>
              </div>
              <div class="col-md-2 text-center">
                x{{ item.cantidad }}
              </div>
              <div class="col-md-2 text-center">
                Q{{ item.producto?.precio }}
              </div>
              <div class="col-md-2 text-end">
                <strong>Q{{ item.subtotal }}</strong>
              </div>
            </div>
            <div class="row mt-3">
              <div class="col-md-8"></div>
              <div class="col-md-4 text-end">
                <h5><strong>Total: Q{{ carrito.total || calcularTotal() }}</strong></h5>
              </div>
            </div>
          </div>
        </div>

        <div class="card">
          <div class="card-header">
            <h5>🚚 Información de Entrega</h5>
          </div>
          <div class="card-body">
            <form @submit.prevent="confirmarPedido">
              <div class="mb-3">
                <label class="form-label">Dirección de Entrega</label>
                <textarea v-model="form.direccionEntrega" class="form-control" rows="3" required 
                          placeholder="Dirección completa para la entrega..."></textarea>
              </div>
              
              <div class="row">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label">Teléfono de Contacto</label>
                    <input v-model="form.telefono" type="tel" class="form-control" required>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label">Método de Pago</label>
                    <select v-model="form.metodoPago" class="form-select" required>
                      <option value="">Seleccionar...</option>
                      <option value="EFECTIVO">Efectivo contra entrega</option>
                      <option value="TARJETA">Tarjeta de crédito</option>
                    </select>
                  </div>
                </div>
              </div>

              <div v-if="form.metodoPago === 'TARJETA'" class="card bg-light">
                <div class="card-body">
                  <h6>💳 Información de Tarjeta</h6>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="mb-3">
                        <label class="form-label">Número de Tarjeta</label>
                        <input v-model="form.numeroTarjeta" type="text" class="form-control" placeholder="1234 5678 9012 3456">
                      </div>
                    </div>
                    <div class="col-md-3">
                      <div class="mb-3">
                        <label class="form-label">Vencimiento</label>
                        <input v-model="form.vencimiento" type="text" class="form-control" placeholder="MM/AA">
                      </div>
                    </div>
                    <div class="col-md-3">
                      <div class="mb-3">
                        <label class="form-label">CVV</label>
                        <input v-model="form.cvv" type="text" class="form-control" placeholder="123">
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-success btn-lg flex-grow-1" :disabled="procesando">
                  {{ procesando ? 'Procesando...' : '✅ Confirmar Pedido' }}
                </button>
                <router-link to="/carrito" class="btn btn-secondary">Volver al Carrito</router-link>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="col-md-4">
        <div class="card sticky-top">
          <div class="card-header">
            <h5>💰 Total a Pagar</h5>
          </div>
          <div class="card-body">
            <div class="d-flex justify-content-between">
              <span>Subtotal:</span>
              <span>Q{{ carrito.total || calcularTotal() }}</span>
            </div>
            <div class="d-flex justify-content-between">
              <span>Envío:</span>
              <span>Gratis</span>
            </div>
            <hr>
            <div class="d-flex justify-content-between">
              <strong>Total:</strong>
              <strong>Q{{ carrito.total || calcularTotal() }}</strong>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="pedidoCreado" class="modal show d-block" tabindex="-1" style="background: rgba(0,0,0,0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">🎉 ¡Pedido Confirmado!</h5>
          </div>
          <div class="modal-body">
            <p>Tu pedido #{{ pedidoCreado.idPedido }} ha sido confirmado.</p>
            <p><strong>Total: Q{{ pedidoCreado.total }}</strong></p>
            <p>Recibirás una notificación cuando tu pedido esté en camino.</p>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" @click="irHome">Continuar Comprando</button>
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
      procesando: false,
      pedidoCreado: null,
      form: {
        direccionEntrega: '',
        telefono: '',
        metodoPago: '',
        numeroTarjeta: '',
        vencimiento: '',
        cvv: ''
      }
    }
  },
  computed: {
    ...mapGetters(['carrito', 'user'])
  },
  async mounted() {
    await this.cargarCarrito()
    // Pre-llenar teléfono del usuario
    if (this.user?.telefono) {
      this.form.telefono = this.user.telefono
    }
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
    calcularTotal() {
      if (!this.carrito?.items) return 0
      return this.carrito.items.reduce((total, item) => total + parseFloat(item.subtotal), 0)
    },
    async confirmarPedido() {
      this.procesando = true
      try {
        // Simular creación de pedido
        const nuevoPedido = {
          idPedido: Math.floor(Math.random() * 10000),
          total: this.carrito.total || this.calcularTotal(),
          direccionEntrega: this.form.direccionEntrega,
          metodoPago: this.form.metodoPago
        }
        
        // Aquí iría la llamada al backend para crear el pedido
        // await pedidoService.crearPedido(this.form)
        
        this.pedidoCreado = nuevoPedido
        
        // VACIAR CARRITO DESPUÉS DEL PEDIDO
        await this.$store.dispatch('vaciarCarrito')
        
      } catch (e) {
        alert('Error procesando pedido: ' + (e.response?.data?.message || e.message))
      } finally {
        this.procesando = false
      }
    },
    irHome() {
      this.pedidoCreado = null
      this.$router.push({ name: 'Products' })
    }
  }
}
</script>