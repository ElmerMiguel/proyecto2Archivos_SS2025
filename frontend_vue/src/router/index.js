import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Products from '../views/Products.vue'
import ProductDetail from '../views/ProductDetail.vue'
import CreateProduct from '../views/CreateProduct.vue'
import store from '../store'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login, meta: { guest: true } },
  { path: '/register', name: 'Register', component: Register, meta: { guest: true } },
  { path: '/productos', name: 'Products', component: Products },
  { path: '/producto/:id', name: 'ProductDetail', component: ProductDetail },
  { path: '/crear-producto', name: 'CreateProduct', component: CreateProduct, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuth = store.getters.isAuthenticated
  if (to.meta.requiresAuth && !isAuth) return next({ name: 'Login' })
  if (to.meta.guest && isAuth) return next({ name: 'Home' })
  next()
})

export default router