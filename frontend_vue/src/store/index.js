import { createStore } from 'vuex'
import authService from '../services/authService'
import cartService from '../services/cartService'

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
      
      // DEBUG: Ver qué datos llegan y se guardan
      console.log('🔍 Usuario guardado en store:', user)
      // Nota: El rol se detecta mejor en el getter, pero aquí vemos la estructura
      console.log('🔍 Posible estructura de Rol:', user?.rol)
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
      // Se utiliza r.data.usuario, pero se añade r.data como fallback
      const user = r.data.usuario || r.data
      
      console.log('🔍 Respuesta completa del backend:', r.data)
      console.log('🔍 Usuario extraído (puede ser r.data.usuario o r.data):', user)
      
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
        await dispatch('loadCarrito') // Recargar carrito
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
        // Establecer carrito vacío en el state
        commit('setCarrito', { items: [], total: 0 })
        return true
      } catch (e) {
        console.error('Error vaciando carrito:', e)
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
    
    // GETTERS DE ROLES CON MÁS OPCIONES
    userRole: state => {
      const user = state.user
      if (!user) return null
      
      // Probar diferentes rutas para el nombre del rol: user.rol.nombreRol, user.rol, user.nombreRol, etc.
      return user.rol?.nombreRol || user.rol || user.nombreRol || user.role || user.roleName || null
    },
    
    // Helper para obtener el rol, buscando en múltiples propiedades
    _getRole: state => {
        const user = state.user
        if (!user) return null
        return user.rol?.nombreRol || user.rol || user.nombreRol || user.role
    },

    isComun: (state, getters) => {
      const role = getters._getRole
      console.log('🔍 Verificando isComun, rol:', role)
      return role === 'COMUN' || role === 'CLIENTE' || role === 'USER'
    },
    isAdmin: (state, getters) => {
      const role = getters._getRole
      console.log('🔍 Verificando isAdmin, rol:', role)
      return role === 'ADMINISTRADOR' || role === 'ADMIN'
    },
    isModerador: (state, getters) => {
      const role = getters._getRole
      console.log('🔍 Verificando isModerador, rol:', role)
      return role === 'MODERADOR' || role === 'MODERATOR'
    },
    isLogistica: (state, getters) => {
      const role = getters._getRole
      console.log('🔍 Verificando isLogistica, rol:', role)
      return role === 'LOGISTICA' || role === 'LOGISTICS'
    }
  }
})

export default store
