import api from './api'

export default {
  getAllProductos() {
    return api.get('/productos')
  },
  getProductoById(id) {
    return api.get(`/productos/${id}`)
  },
  getProductosByCategoria(idCategoria) {
    return api.get(`/productos/categoria/${idCategoria}`)
  },
  getMisProductos() {
    return api.get('/productos/mis-productos')
  },
  crearProducto(producto) {
    return api.post('/productos', producto)
  }
}