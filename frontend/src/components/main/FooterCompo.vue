<template>
  <v-footer app dark v-bind:height="footerHeight" flat>
    <v-spacer></v-spacer>
    <span>&copy; App Title</span>
  </v-footer>
</template>
<script>
export default {
  name: 'FooterCompo',
  props: {
    footerHeight: {
      type: Number,
      required: true,
      default: 70
    }
  },
  computed: {
  }
}
</script>
