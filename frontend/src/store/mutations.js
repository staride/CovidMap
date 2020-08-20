import {
  SET_AUTH_TOKEN,
  DESTROY_AUTH_TOKEN
} from '@/store/mutation-types.js'

import axios from 'axios'
import cookies from 'vue-cookies'

export default {
  [SET_AUTH_TOKEN] (state, accessToken) {
    state.isLogin = true
    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`
    cookies.set('jwt', accessToken, '24h')
  },
  [DESTROY_AUTH_TOKEN] (state) {
    state.isLogin = false
    delete axios.defaults.headers.common.Authorization
    cookies.remove('jwt')
  }
}
