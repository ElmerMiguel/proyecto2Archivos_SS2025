import api from './api'

export default {
  getCarrito() {
    // Cambiar de /carrito a /carrito/mis-items
    return api.get('/carrito/mis-items')
  },
  agregarItem(idProducto, cantidad = 1) {
    // Cambiar estructura de parámetros
    return api.post(`/carrito/agregar?idProducto=${idProducto}&cantidad=${cantidad}`)
  },
  actualizarItem(idItem, cantidad) {
    return api.put(`/carrito/item/${idItem}?cantidad=${cantidad}`)
  },
  eliminarItem(idItem) {
    return api.delete(`/carrito/item/${idItem}`)
  }
}