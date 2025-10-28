<template>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card shadow-lg border-0 rounded-lg">
          <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Solicitar Venta de Producto</h5>
          </div>
          <div class="card-body">
            <form @submit.prevent="onSubmit">
              <div class="row">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label fw-bold">Nombre del Producto</label>
                    <input v-model="form.nombreProducto" class="form-control" required>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label fw-bold">Categoría</label>
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
                <label class="form-label fw-bold">Descripción</label>
                <textarea v-model="form.descripcion" class="form-control" rows="3" required></textarea>
              </div>
              
              <div class="row">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label fw-bold">Precio (Q)</label>
                    <input v-model="form.precio" type="number" step="0.01" min="0.01" class="form-control" required>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="mb-3">
                    <label class="form-label fw-bold">Stock</label>
                    <input v-model="form.stock" type="number" min="1" class="form-control" required>
                  </div>
                </div>
              </div>
              
              <!-- Se mantiene el campo de estado del producto, aunque no se usa en productData, para completar el formulario visualmente -->
              <div class="mb-3">
                <label class="form-label fw-bold">Estado del Producto</label>
                <select v-model="form.idEstadoProducto" class="form-select" required>
                  <option value="1">NUEVO</option>
                  <option value="2">USADO</option>
                </select>
              </div>
              
              <div class="d-flex gap-2 justify-content-end">
                <router-link to="/productos" class="btn btn-secondary">Cancelar</router-link>
                <button type="submit" class="btn btn-primary" :disabled="loading">
                  <span v-if="loading" class="spinner-border spinner-border-sm me-2"></span>
                  {{ loading ? 'Enviando...' : 'Enviar Solicitud' }}
                </button>
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
import { mapGetters } from 'vuex'
import categoryService from '../services/categoryService'

// Nota: Hemos asumido que '$axios' está disponible globalmente a través de Vue prototype
// y que el servicio productService ya no se utiliza para esta función específica.

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
  computed: {
    ...mapGetters(['user']) // Mantenido por si es necesario para el futuro, aunque no se usa en onSubmit
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
        // CREAR OBJETO CON PARÁMETROS CORRECTOS
        const productData = {
          nombreProducto: this.form.nombreProducto,
          descripcion: this.form.descripcion,
          // Aseguramos que los tipos de datos sean correctos para el backend
          precio: parseFloat(this.form.precio),
          stock: parseInt(this.form.stock),
          // Se elimina idVendedor: this.user.idUsuario, ya que se asume que el backend lo obtendrá del token.
          idCategoria: parseInt(this.form.idCategoria)
        }
        
        console.log('📦 Enviando producto:', productData)

        // Dispatch a la acción de Vuex para manejar la lógica de la API
        // Se asume que existe una acción 'createProduct' en el store.
        await this.$store.dispatch('createProduct', productData)
        
        this.success = 'Solicitud enviada exitosamente. Esperando aprobación del moderador.'
        
        // Redireccionar al usuario a la lista de productos después de un breve retraso
        setTimeout(() => {
          this.$router.push({ path: '/productos' })
        }, 1500)
        
      } catch (e) {
        console.error('❌ Error al crear producto:', e)
        // Manejo de errores más robusto
        this.error = e.response?.data?.message || e.message || 'Error enviando solicitud. Verifica tus datos.'
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
