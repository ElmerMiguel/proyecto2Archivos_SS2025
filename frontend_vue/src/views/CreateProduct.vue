<template>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">
            <h5>Solicitar Venta de Producto</h5>
          </div>
          <div class="card-body">
            <form @submit.prevent="onSubmit">
              <div class="row">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label">Nombre del Producto</label>
                    <input v-model="form.nombreProducto" class="form-control" required>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label">Categoría</label>
                    <select v-model="form.idCategoria" class="form-select" required>
                      <option value="">Seleccionar...</option>
                      <option v-for="cat in categorias" :key="cat.idCategoria" :value="cat.idCategoria">
                        {{ cat.nombreCategoria }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              
              <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea v-model="form.descripcion" class="form-control" rows="3" required></textarea>
              </div>
              
              <div class="row">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label">Precio (Q)</label>
                    <input v-model="form.precio" type="number" step="0.01" class="form-control" required>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label">Stock</label>
                    <input v-model="form.stock" type="number" class="form-control" required>
                  </div>
                </div>
              </div>
              
              <div class="mb-3">
                <label class="form-label">Estado del Producto</label>
                <select v-model="form.idEstadoProducto" class="form-select" required>
                  <option value="1">NUEVO</option>
                  <option value="2">USADO</option>
                </select>
              </div>
              
              <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary" :disabled="loading">
                  {{ loading ? 'Enviando...' : 'Enviar Solicitud' }}
                </button>
                <router-link to="/productos" class="btn btn-secondary">Cancelar</router-link>
              </div>
              
              <div v-if="error" class="mt-3 alert alert-danger">{{ error }}</div>
              <div v-if="success" class="mt-3 alert alert-success">{{ success }}</div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import productService from '../services/productService'
import categoryService from '../services/categoryService'

export default {
  data() {
    return {
      form: {
        nombreProducto: '',
        descripcion: '',
        precio: '',
        stock: '',
        idEstadoProducto: '1',
        idCategoria: ''
      },
      categorias: [],
      loading: false,
      error: null,
      success: null
    }
  },
  async mounted() {
    await this.cargarCategorias()
  },
  methods: {
    async cargarCategorias() {
      try {
        const response = await categoryService.getAllCategorias()
        this.categorias = response.data
      } catch (e) {
        console.error('Error cargando categorías:', e)
      }
    },
    async onSubmit() {
      this.loading = true
      this.error = null
      this.success = null
      
      try {
        await productService.crearProducto(this.form)
        this.success = 'Solicitud enviada correctamente. Espera la aprobación del moderador.'
        this.resetForm()
      } catch (e) {
        this.error = e.response?.data?.message || 'Error enviando solicitud'
      } finally {
        this.loading = false
      }
    },
    resetForm() {
      this.form = {
        nombreProducto: '',
        descripcion: '',
        precio: '',
        stock: '',
        idEstadoProducto: '1',
        idCategoria: ''
      }
    }
  }
}
</script>