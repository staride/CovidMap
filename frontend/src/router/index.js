import Vue from 'vue'
import VueRouter from 'vue-router'

import Index from '@/views/Index.vue'

import SignIn from '@/views/member/SignIn.vue'
import SignUp from '@/views/member/SignUp.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index
  },
  {
    path: '/member/signIn',
    name: 'SignIn',
    component: SignIn
  },
  {
    path: '/member/signUp',
    name: 'SignUp',
    component: SignUp
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
