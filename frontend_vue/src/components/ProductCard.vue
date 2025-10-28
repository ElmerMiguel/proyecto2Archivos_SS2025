<template>
  <div class="card h-100">
    <div class="card-body">
      <h6 class="card-title">{{ producto.nombreProducto }}</h6>
      <p class="card-text text-muted small">{{ producto.descripcion }}</p>
      <p class="card-text">
        <strong>Q{{ producto.precio }}</strong><br>
        <small class="text-success">Stock: {{ producto.stock }}</small><br>
        <small class="text-info">{{ producto.categoria?.nombreCategoria }}</small>
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
    }
  }
}
</script>