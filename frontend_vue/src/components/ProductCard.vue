<template>
  <div class="card h-100">
    <img 
      v-if="producto.urlImagen" 
      :src="producto.urlImagen" 
      class="card-img-top" 
      style="height: 200px; object-fit: cover;"
      @error="onImageError"
      :alt="producto.nombreProducto">
    <div v-else class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 200px;">
      <i class="fas fa-image fa-3x text-muted"></i>
    </div>
    
    <div class="card-body d-flex flex-column">
      <h6 class="card-title">{{ producto.nombreProducto }}</h6>
      <p class="card-text text-muted small flex-grow-1">{{ producto.descripcion }}</p>
      <div class="mt-auto">
        <p class="card-text mb-2">
          <strong>Q{{ producto.precio }}</strong><br>
          <small class="text-success">Stock: {{ producto.stock }}</small><br>
          <small class="text-info">{{ producto.nombreCategoria }}</small><br>
          <small class="text-secondary">Por: {{ producto.nombreVendedor }}</small>
        </p>
        <div class="d-flex justify-content-between">
          <button class="btn btn-primary btn-sm" @click="verDetalle">Ver</button>
          <button 
            v-if="isAuthenticated && producto.stock > 0" 
            class="btn btn-success btn-sm"
            @click="agregarCarrito">
            <i class="fas fa-cart-plus"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  props: {
    producto: { type: Object, required: true }
  },
  computed: {
    ...mapGetters(['isAuthenticated'])
  },
  methods: {
    verDetalle() {
      this.$router.push({ name: 'ProductDetail', params: { id: this.producto.idProducto } })
    },
    agregarCarrito() {
      console.log('Agregar al carrito:', this.producto.idProducto)
    },
    onImageError(event) {
      event.target.style.display = 'none'
      event.target.nextElementSibling.style.display = 'flex'
    }
  }
}
</script>