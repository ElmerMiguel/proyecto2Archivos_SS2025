<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-7">
        <div class="card p-3">
          <h5 class="card-title">Register</h5>
          <form @submit.prevent="onSubmit">
            <div class="row">
              <div class="mb-3 col-md-6">
                <label class="form-label">Nombre completo</label>
                <input v-model="nombreCompleto" class="form-control" required>
              </div>
              <div class="mb-3 col-md-6">
                <label class="form-label">Email</label>
                <input v-model="email" type="email" class="form-control" required>
              </div>
            </div>
            <div class="row">
              <div class="mb-3 col-md-6">
                <label class="form-label">Password</label>
                <input v-model="password" type="password" class="form-control" required>
              </div>
              <div class="mb-3 col-md-6">
                <label class="form-label">Teléfono</label>
                <input v-model="telefono" class="form-control">
              </div>
            </div>
            <button class="btn btn-success" type="submit" :disabled="loading">Register</button>
            <div v-if="error" class="mt-3 alert alert-danger">{{ error }}</div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import authService from '../services/authService'
export default {
  data() {
    return {
      nombreCompleto: '',
      email: '',
      password: '',
      telefono: '',
      loading: false,
      error: null
    }
  },
  methods: {
    async onSubmit() {
      this.loading = true
      this.error = null
      try {
        const payload = {
          nombreCompleto: this.nombreCompleto,
          email: this.email,
          password: this.password,
          telefono: this.telefono
        }
        await authService.register(payload)
        this.$router.push({ name: 'Login' })
      } catch (e) {
        this.error = e.response?.data?.message || 'Register failed'
      } finally { 
        this.loading = false 
      }
    }
  }
}
</script>