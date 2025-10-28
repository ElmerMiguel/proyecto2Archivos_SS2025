import api from './api'

export default {
  getAllCategorias() {
    return api.get('/categorias')
  }
}