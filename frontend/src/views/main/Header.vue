<template>
  <main-header :headerHeight="headerHeight">
    <template #headerItems>
     <v-btn @click="goMainView()" text color="white" class="header-item">COVID Map</v-btn>
     <v-btn @click="goBoardView()" text color="white" class="header-item">Board</v-btn>
     <v-btn @click="SignIn()" text color="white" class="header-item">Sign in</v-btn>
    </template>
  </main-header>
</template>
<script>

import MainHeader from '@/components/main/HeaderCompo.vue'
import router from '@/router'

export default {
  name: 'HeaderView',
  components: {
    MainHeader
  },
  props: {
    headerHeight: {
      type: Number,
      required: true,
      default: 120
    }
  },
  methods: {
    goMainView () {
      console.log('goMainView')
      if (window.location.pathname !== '/') {
        router.push('/')
      }
    },
    goBoardView () {
      console.log('goBoardView')
      if (window.location.pathname !== '/board') {
        router.push('/board')
      }
    },
    SignIn () {
      console.log('signIn')
      if (window.location.pathname !== '/signIn') {
        router.push('/signIn')
      }
    }
  }
}
</script>
<style scope>
  .v-btn__content {
    height: 120px;
    width: 90px;
  }
</style>
