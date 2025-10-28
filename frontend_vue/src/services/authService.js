import api from './api'

export default {
  login(email, password) {
    return api.post('/auth/login', { email, password })
  },
  register(payload) {
    return api.post('/auth/register', payload)
  },
  verify() {
    return api.get('/auth/verify')
  },
  perfil() {
    return api.get('/usuarios/perfil')
  }
}