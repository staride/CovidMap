import axios from 'axios'
import {
  SET_AUTH_TOKEN,
  DESTROY_AUTH_TOKEN
} from '@/store/mutation-types.js'

import router from '@/router'

export default {
  signInAction: function ({ commit }, data) {
    // console.log('id : ' + data.id)
    // console.log('password : ' + data.password)

    axios.post('http://localhost:7777/api/authenticate', data).then(res => {
      if (res.status === 200) {
        const { authorization } = res.headers
        const accessToken = authorization.substring(7)
        // console.log('accessToken : ' + accessToken)

        commit(SET_AUTH_TOKEN, accessToken)
        router.go(-1)
      } else {
        console.log('status : ' + res.status + ', data : ' + res.data)
      }
    }).catch(err => {
      console.log(err)
    })
  },
  logoutAction: function ({ commit }) {
    commit(DESTROY_AUTH_TOKEN)
  }
}
