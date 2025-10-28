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
          <img 
            v-if="producto.urlImagen" 
            :src="producto.urlImagen" 
            class="card-img-top" 
            style="height: 300px; object-fit: cover;"
            @error="onImageError"
            :alt="producto.nombreProducto">
          <div v-else class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 300px;">
            <i class="fas fa-image fa-4x text-muted"></i>
          </div>
          
          <div class="card-body">
            <h4>{{ producto.nombreProducto }}</h4>
            <p class="text-muted">{{ producto.descripcion }}</p>
            <p><strong>Precio: Q{{ producto.precio }}</strong></p>
            <p>Stock disponible: {{ producto.stock }}</p>
            <p>Categoría: {{ producto.nombreCategoria }}</p>
            <p>Vendedor: {{ producto.nombreVendedor }}</p>
            
            <div v-if="isAuthenticated && producto.stock > 0" class="d-flex gap-2 align-items-center">
              <div class="input-group" style="width: 120px;">
                <button class="btn btn-outline-secondary" @click="cantidad > 1 ? cantidad-- : null">-</button>
                <input type="number" class="form-control text-center" v-model="cantidad" min="1" :max="producto.stock">
                <button class="btn btn-outline-secondary" @click="cantidad < producto.stock ? cantidad++ : null">+</button>
              </div>
              <button 
                class="btn btn-success btn-lg flex-grow-1" 
                @click="agregarCarrito"
                :disabled="agregando">
                <span v-if="agregando">Agregando...</span>
                <span v-else>🛒 Agregar al Carrito</span>
              </button>
            </div>
            
            <div v-else-if="!isAuthenticated" class="alert alert-info">
              <router-link to="/login">Inicia sesión</router-link> para comprar este producto
            </div>
            
            <div v-else-if="producto.stock === 0" class="alert alert-warning">
              Producto agotado
            </div>
          </div>
        </div>
      </div>
      
      <div class="col-md-6">
        <h5>Información adicional</h5>
        <p>Estado: {{ producto.nombreEstadoProducto || 'NUEVO' }}</p>
        <p>Fecha publicación: {{ formatearFecha(producto.fechaPublicacion) }}</p>
        
        <!-- Mensaje de éxito -->
        <div v-if="mensajeExito" class="alert alert-success">
          {{ mensajeExito }}
        </div>
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
      error: null,
      cantidad: 1,
      agregando: false,
      mensajeExito: null
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
    async agregarCarrito() {
      this.agregando = true
      this.mensajeExito = null
      try {
        await this.$store.dispatch('agregarAlCarrito', { 
          idProducto: this.producto.idProducto, 
          cantidad: this.cantidad 
        })
        this.mensajeExito = `${this.cantidad} unidad(es) de "${this.producto.nombreProducto}" agregada(s) al carrito`
        this.cantidad = 1 // Reset cantidad
      } catch (e) {
        alert('Error agregando al carrito')
        console.error(e)
      } finally {
        this.agregando = false
      }
    },
    formatearFecha(fecha) {
      return new Date(fecha).toLocaleDateString()
    },
    onImageError(event) {
      event.target.style.display = 'none'
      event.target.nextElementSibling.style.display = 'flex'
    }
  }
}
</script>