import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import cookies from 'vue-cookies'
import vuex from 'vuex'

import {
  SET_AUTH_TOKEN,
  SET_LOGIN_INFO
} from '@/store/mutation-types.js'

import axios from 'axios'
import decode from 'jwt-decode'

Vue.use(cookies)
Vue.use(vuex)

Vue.config.productionTip = false

axios.interceptors.request.use(function (config) {
  // console.log('request interceptors')
  if (cookies.isKey('jwt') && cookies.isKey('refresh') && config.url !== 'http://localhost:7777/refreshToken') {
    const accessToken = cookies.get('jwt')
    const { exp } = decode(accessToken)
    if (Date.now() >= exp * 1000) {
      const refreshToken = cookies.get('refresh')
      axios.defaults.headers.common.Authorization = `Bearer ${refreshToken}`
      axios.post('http://localhost:7777/refreshToken').then(res => {
        if (res.status === 200) {
          const token = res.data.jwt.access_token
          store.commit(SET_AUTH_TOKEN, token)
        } else {
          console.log('status : ' + res.status + ', data : ' + res.data)
          alert('Token 갱신 실패')
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }
  return config
}, function (err) {
  console.log(err)
  return Promise.reject(err)
})

function init () {
  if (cookies.isKey('jwt') && cookies.isKey('usuerid')) {
    const accessToken = cookies.get('jwt')
    const usuerid = cookies.get('usuerid')
    store.commit(SET_AUTH_TOKEN, accessToken)
    axios.get(`http://localhost:7777/member/getLoginInfo/${usuerid}`).then(res => {
      if (res.status === 200 && res.data !== null) {
        store.commit(SET_LOGIN_INFO, res.data)
        // alert('로그인 성공')
        router.go(-1)
      } else {
        console.log('status : ' + res.status + ', data : ' + res.data)
        // alert('로그인 실패')
      }
    }).catch(err => {
      console.log(err)
      // alert('로그인 실패')
    })
  }
  return Promise.resolve()
}

init().then(() => {
  new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
  }).$mount('#app')
})
