import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import cookies from 'vue-cookies'
import vuex from 'vuex'
import {
  SET_AUTH_TOKEN, SET_LOGIN_INFO
} from '@/store/mutation-types.js'
import axios from 'axios'

Vue.use(cookies)
Vue.use(vuex)

Vue.config.productionTip = false

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
