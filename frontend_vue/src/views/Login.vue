<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card p-3">
          <h5 class="card-title">Login</h5>
          <form @submit.prevent="onSubmit">
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input v-model="email" type="email" class="form-control" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input v-model="password" type="password" class="form-control" required>
            </div>
            <div class="d-flex justify-content-between">
              <button class="btn btn-primary" type="submit" :disabled="loading">Login</button>
              <router-link to="/register">Register</router-link>
            </div>
            <div v-if="error" class="mt-3 alert alert-danger">{{ error }}</div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return { email: '', password: '', loading: false, error: null }
  },
  methods: {
    async onSubmit() {
      this.loading = true
      this.error = null
      try {
        await this.$store.dispatch('login', { email: this.email, password: this.password })
        this.$router.push({ name: 'Home' })
      } catch (e) {
        this.error = e.response?.data?.message || 'Login failed'
      } finally { 
        this.loading = false 
      }
    }
  }
}
</script>