import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import cookies from 'vue-cookies'
import vuex from 'vuex'

Vue.use(cookies)
Vue.use(vuex)

Vue.config.productionTip = false

function init () {
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
