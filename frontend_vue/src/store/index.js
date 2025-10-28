import { createStore } from 'vuex'
import authService from '../services/authService'
import cartService from '../services/cartService'
import productService from '../services/productService'

const store = createStore({
  state: {
    token: localStorage.getItem('token') || null,
    user: JSON.parse(localStorage.getItem('user')) || null,
    carrito: null,
    carritoCount: 0
  },
  mutations: {
    setAuth(state, { token, user }) {
      state.token = token
      state.user = user
      localStorage.setItem('token', token)
      localStorage.setItem('user', JSON.stringify(user))
    },
    clearAuth(state) {
      state.token = null
      state.user = null
      state.carrito = null
      state.carritoCount = 0
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    },
    setCarrito(state, carrito) {
      state.carrito = carrito
      state.carritoCount = carrito?.items?.length || 0
    }
  },
  actions: {
    async login({ commit, dispatch }, { email, password }) {
      const r = await authService.login(email, password)
      const token = r.data.token
      const user = r.data.usuario || r.data
      
      commit('setAuth', { token, user })
      await dispatch('loadCarrito')
      return r
    },
    logout({ commit }) {
      commit('clearAuth')
    },
    async fetchProfile({ commit }) {
      try {
        const r = await authService.perfil()
        const user = r.data
        commit('setAuth', { token: localStorage.getItem('token'), user })
      } catch (e) {
        commit('clearAuth')
      }
    },
    async loadCarrito({ commit }) {
      try {
        const response = await cartService.getCarrito()
        commit('setCarrito', response.data)
      } catch (e) {
        console.error('Error cargando carrito:', e)
      }
    },
    async agregarAlCarrito({ dispatch }, { idProducto, cantidad = 1 }) {
      try {
        await cartService.agregarItem(idProducto, cantidad)
        await dispatch('loadCarrito')
        return true
      } catch (e) {
        console.error('Error agregando al carrito:', e)
        throw e
      }
    },
    async actualizarCarrito({ dispatch }, { idItem, cantidad }) {
      try {
        await cartService.actualizarItem(idItem, cantidad)
        await dispatch('loadCarrito')
        return true
      } catch (e) {
        console.error('Error actualizando carrito:', e)
        throw e
      }
    },
    async eliminarDelCarrito({ dispatch }, idItem) {
      try {
        await cartService.eliminarItem(idItem)
        await dispatch('loadCarrito')
        return true
      } catch (e) {
        console.error('Error eliminando del carrito:', e)
        throw e
      }
    },
    async vaciarCarrito({ commit }) {
      try {
        await cartService.vaciarCarrito()
        commit('setCarrito', { items: [], total: 0 })
        return true
      } catch (e) {
        console.error('Error vaciando carrito:', e)
        throw e
      }
    },
    
    async createProduct(context, productData) {
      try {
        const response = await productService.createProduct(productData)
        return response
      } catch (e) {
        console.error('Error creando producto:', e)
        throw e
      }
    }
  },
  getters: {
    isAuthenticated: state => !!state.token,
    user: state => state.user,
    carrito: state => state.carrito,
    carritoCount: state => state.carritoCount,
    carritoTotal: state => state.carrito?.total || 0,
    
    userRole: state => {
      const user = state.user
      if (!user) return null
      
      return user.rol?.nombreRol || user.rol || user.nombreRol || user.role || user.roleName || null
    },
    
    _getRole: state => {
        const user = state.user
        if (!user) return null
        return user.rol?.nombreRol || user.rol || user.nombreRol || user.role
    },

    isComun: (state, getters) => {
      const role = getters._getRole
      return role === 'COMUN' || role === 'CLIENTE' || role === 'USER'
    },
    isAdmin: (state, getters) => {
      const role = getters._getRole
      return role === 'ADMINISTRADOR' || role === 'ADMIN'
    },
    isModerador: (state, getters) => {
      const role = getters._getRole
      return role === 'MODERADOR' || role === 'MODERATOR'
    },
    isLogistica: (state, getters) => {
      const role = getters._getRole
      return role === 'LOGISTICA' || role === 'LOGISTICS'
    }
  }
})

export default store
