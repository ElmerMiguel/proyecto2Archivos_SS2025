import api from './api'

export default {
  getCarrito() {
    return api.get('/carrito/mis-items')
  },
  agregarItem(idProducto, cantidad = 1) {
    return api.post(`/carrito/agregar?idProducto=${idProducto}&cantidad=${cantidad}`)
  },
  actualizarItem(idItem, cantidad) {
    return api.put(`/carrito/item/${idItem}?cantidad=${cantidad}`)
  },
  eliminarItem(idItem) {
    return api.delete(`/carrito/item/${idItem}`)
  },
  vaciarCarrito() {
    return api.delete('/carrito/vaciar')
  }
}