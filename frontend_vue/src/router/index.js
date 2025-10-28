import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Products from '../views/Products.vue'
import ProductDetail from '../views/ProductDetail.vue'
import CreateProduct from '../views/CreateProduct.vue'
import Cart from '../views/Cart.vue'
import Checkout from '../views/Checkout.vue'
import store from '../store'

// nuevas vistas
import AdminDashboard from '../views/admin/AdminDashboard.vue'
import AdminReportes from '../views/admin/AdminReportes.vue'
import AdminUsuarios from '../views/admin/AdminUsuarios.vue'
import ModeradorSolicitudes from '../views/moderador/Solicitudes.vue'
import ModeradorSanciones from '../views/moderador/Sanciones.vue'
import LogisticaPedidos from '../views/logistica/Pedidos.vue'
import LogisticaEntregas from '../views/logistica/Entregas.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login, meta: { guest: true } },
  { path: '/register', name: 'Register', component: Register, meta: { guest: true } },
  { path: '/productos', name: 'Products', component: Products },
  { path: '/producto/:id', name: 'ProductDetail', component: ProductDetail },
  { path: '/crear-producto', name: 'CreateProduct', component: CreateProduct, meta: { requiresAuth: true } },
  { path: '/carrito', name: 'Cart', component: Cart, meta: { requiresAuth: true } },
  { path: '/checkout', name: 'Checkout', component: Checkout, meta: { requiresAuth: true } },

  // Admin
  { path: '/admin/dashboard', name: 'AdminDashboard', component: AdminDashboard, meta: { requiresAuth: true, roles: ['ADMINISTRADOR'] } },
  { path: '/admin/reportes', name: 'AdminReportes', component: AdminReportes, meta: { requiresAuth: true, roles: ['ADMINISTRADOR'] } },
  { path: '/admin/usuarios', name: 'AdminUsuarios', component: AdminUsuarios, meta: { requiresAuth: true, roles: ['ADMINISTRADOR'] } },

  // Moderador
  { path: '/moderador/solicitudes', name: 'ModeradorSolicitudes', component: ModeradorSolicitudes, meta: { requiresAuth: true, roles: ['MODERADOR'] } },
  { path: '/moderador/sanciones', name: 'ModeradorSanciones', component: ModeradorSanciones, meta: { requiresAuth: true, roles: ['MODERADOR'] } },

  // Logística
  { path: '/logistica/pedidos', name: 'LogisticaPedidos', component: LogisticaPedidos, meta: { requiresAuth: true, roles: ['LOGISTICA'] } },
  { path: '/logistica/entregas', name: 'LogisticaEntregas', component: LogisticaEntregas, meta: { requiresAuth: true, roles: ['LOGISTICA'] } },

  // fallback
  { path: '/:pathMatch(.*)*', redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuth = store.getters.isAuthenticated
  const userRole = store.getters.userRole

  if (to.meta.requiresAuth && !isAuth) return next({ name: 'Login' })
  if (to.meta.guest && isAuth) return next({ name: 'Home' })

  // role guard
  if (to.meta.roles && to.meta.roles.length > 0) {
    if (!isAuth) return next({ name: 'Login' })
    if (!userRole) return next({ name: 'Home' })
    if (!to.meta.roles.includes(userRole)) return next({ name: 'Home' })
  }

  next()
})

export default router