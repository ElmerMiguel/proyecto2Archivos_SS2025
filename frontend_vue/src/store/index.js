import { createStore } from 'vuex'
import authService from '../services/authService'

const store = createStore({
  state: {
    token: localStorage.getItem('token') || null,
    user: JSON.parse(localStorage.getItem('user')) || null
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
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  },
  actions: {
    async login({ commit }, { email, password }) {
      const r = await authService.login(email, password)
      const token = r.data.token
      const user = r.data.usuario || null
      commit('setAuth', { token, user })
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
    }
  },
  getters: {
    isAuthenticated: state => !!state.token,
    user: state => state.user
  }
})

export default store