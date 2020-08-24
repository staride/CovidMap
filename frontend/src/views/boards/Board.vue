<template>
  <v-main>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="10">
          <v-card>
            <v-toolbar dark flat height="35px">
              <v-spacer />
                <v-toolbar-title>B o a r d</v-toolbar-title>
              <v-spacer />
            </v-toolbar>
            <v-card-text>
              <v-data-table @click:row="rowClick" height="400px" :headers="headers" :items="getBoardList" :fixed-header="true" :footer-props="footerProps">
              </v-data-table>
            </v-card-text>
          </v-card>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn dark @click="moveWriteBoard">글쓰기</v-btn>
          </v-card-actions>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
<script>

import router from '@/router/'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'MainBoardView',
  data: function () {
    return {
      headers: [
        { text: 'No.', align: 'center', sortable: true, value: 'boardNo' },
        { text: 'Title', align: 'center', sortable: false, value: 'title' },
        { text: 'Writer', align: 'center', value: 'writer' },
        { text: 'date', align: 'center', value: 'regDate' }
      ],
      footerProps: {
        itemsPerPageText: '',
        lastIcon: '$last',
        nextIcon: '$next',
        itemsPerPageOptions: []
      }
    }
  },
  computed: {
    ...mapGetters([
      'getBoardList'
    ])
  },
  methods: {
    ...mapActions([
      'getBoardListAction'
    ]),
    rowClick: function (item, row) {
      console.log('boardNo : ' + item.boardNo)
      router.push({ name: 'BoardRead', params: { boardNo: item.boardNo.toString() } })
    },
    moveWriteBoard: function () {
      router.push({ name: 'BoardWrite' })
    }
  },
  mounted () {
    this.getBoardListAction()
  }
}
</script>
<style scope>
tr.v-data-table__selected {
  background: #7d92f5 !important;
}
</style>
