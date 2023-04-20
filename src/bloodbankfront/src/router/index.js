import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import AdminBoardView from '../views/AdminBoardView.vue'
import DonorEditAccountView from '../views/DonorEditAccountView.vue'
import DonorHome from '../views/DonorHome'
import DoctorHomeView from '../views/DoctorHomeView'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/admin',
    name: 'adminBoard',
    component: AdminBoardView
  },
  {
    path: '/editAccount',
    name: 'editAccount',
    component: DonorEditAccountView
  },
  {
    path: '/donorHome',
    name: 'donorHome',
    component: DonorHome
  },
  {
    path: '/doctorHome',
    name: 'doctorHome',
    component: DoctorHomeView
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
