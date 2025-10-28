import api from './api'

export default {
  getAllProductos() {
    return api.get('/productos')
  },
  getProducts(categoria = '', page = 0, size = 20) {
    return api.get(`/productos?categoria=${categoria}&page=${page}&size=${size}`)
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
  createProduct(productData) {
    const params = new URLSearchParams()
    params.append('nombreProducto', productData.nombreProducto)
    params.append('descripcion', productData.descripcion)
    params.append('precio', productData.precio)
    params.append('stock', productData.stock)
    params.append('idCategoria', productData.idCategoria)
    
    return api.post('/usuarios/solicitar-producto', null, { params })
  },
  crearProducto(producto) {
    return api.post('/productos', producto)
  }
}
