<template>
  <div class="container mt-4">
    <div v-if="loading" class="text-center">
      <div class="spinner-border" role="status"></div>
    </div>
    
    <div v-else-if="error" class="alert alert-danger">
      {{ error }}
    </div>
    
    <div v-else-if="producto" class="row">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h4>{{ producto.nombreProducto }}</h4>
            <p class="text-muted">{{ producto.descripcion }}</p>
            <p><strong>Precio: Q{{ producto.precio }}</strong></p>
            <p>Stock disponible: {{ producto.stock }}</p>
            <p>Categoría: {{ producto.categoria?.nombreCategoria }}</p>
            <p>Vendedor: {{ producto.vendedor?.nombreCompleto }}</p>
            
            <div v-if="isAuthenticated && producto.stock > 0">
              <button class="btn btn-success btn-lg">
                <i class="fas fa-cart-plus"></i> Agregar al Carrito
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <div class="col-md-6">
        <h5>Información adicional</h5>
        <p>Estado: {{ producto.estadoProducto?.nombreEstado }}</p>
        <p>Fecha publicación: {{ formatearFecha(producto.fechaPublicacion) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import productService from '../services/productService'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      producto: null,
      loading: true,
      error: null
    }
  },
  computed: {
    ...mapGetters(['isAuthenticated'])
  },
  async mounted() {
    await this.cargarProducto()
  },
  methods: {
    async cargarProducto() {
      try {
        this.loading = true
        const response = await productService.getProductoById(this.$route.params.id)
        this.producto = response.data
      } catch (e) {
        this.error = 'Producto no encontrado'
        console.error(e)
      } finally {
        this.loading = false
      }
    },
    formatearFecha(fecha) {
      return new Date(fecha).toLocaleDateString()
    }
  }
}
</script>