<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-3">
        <h5>Categorías</h5>
        <div class="list-group">
          <button 
            class="list-group-item list-group-item-action"
            :class="{ active: categoriaSeleccionada === null }"
            @click="filtrarCategoria(null)">
            Todas ({{ productos.length }})
          </button>
          <button 
            v-for="categoria in categorias" 
            :key="categoria.idCategoria"
            class="list-group-item list-group-item-action"
            :class="{ active: categoriaSeleccionada === categoria.idCategoria }"
            @click="filtrarCategoria(categoria.idCategoria)">
            {{ categoria.nombreCategoria }} ({{ contarPorCategoria(categoria.idCategoria) }})
          </button>
        </div>
        
        <!-- DEBUG INFO -->
        <div class="mt-3 small text-muted">
          <strong>Debug:</strong><br>
          Categoría seleccionada: {{ categoriaSeleccionada }}<br>
          Total productos: {{ productos.length }}<br>
          Productos filtrados: {{ productosFiltrados.length }}
        </div>
      </div>
      
      <div class="col-md-9">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h3>Productos</h3>
          <router-link 
            v-if="isAuthenticated" 
            to="/crear-producto" 
            class="btn btn-success">
            <i class="fas fa-plus"></i> Vender Producto
          </router-link>
        </div>
        
        <div v-if="loading" class="text-center">
          <div class="spinner-border" role="status"></div>
        </div>
        
        <div v-else-if="error" class="alert alert-danger">
          {{ error }}
        </div>
        
        <div v-else class="row">
          <div 
            v-for="producto in productosFiltrados" 
            :key="producto.idProducto"
            class="col-md-4 mb-3">
            <ProductCard :producto="producto" />
          </div>
          
          <div v-if="productosFiltrados.length === 0" class="col-12">
            <div class="alert alert-info text-center">
              No hay productos en esta categoría
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProductCard from '../components/ProductCard.vue'
import productService from '../services/productService'
import categoryService from '../services/categoryService'
import { mapGetters } from 'vuex'

export default {
  components: { ProductCard },
  data() {
    return {
      productos: [],
      categorias: [],
      categoriaSeleccionada: null,
      loading: true,
      error: null
    }
  },
  computed: {
    ...mapGetters(['isAuthenticated']),
    productosFiltrados() {
      if (!this.categoriaSeleccionada) return this.productos
      return this.productos.filter(p => {
        const categoriaProducto = p.categoria?.idCategoria
        return Number(categoriaProducto) === Number(this.categoriaSeleccionada)
      })
    }
  },
  async mounted() {
    await this.cargarDatos()
  },
  methods: {
    async cargarDatos() {
      try {
        this.loading = true
        const [productosRes, categoriasRes] = await Promise.all([
          productService.getAllProductos(),
          categoryService.getAllCategorias()
        ])
        this.productos = productosRes.data
        this.categorias = categoriasRes.data
        console.log('Productos cargados:', this.productos)
        console.log('Categorías cargadas:', this.categorias)
      } catch (e) {
        this.error = 'Error cargando productos'
        console.error(e)
      } finally {
        this.loading = false
      }
    },
    filtrarCategoria(idCategoria) {
      this.categoriaSeleccionada = idCategoria
      console.log('Filtrando por categoría:', idCategoria)
    },
    contarPorCategoria(idCategoria) {
      return this.productos.filter(p => 
        Number(p.categoria?.idCategoria) === Number(idCategoria)
      ).length
    }
  }
}
</script>