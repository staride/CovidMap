<template>
  <v-main>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="6">
          <v-card>
            <v-toolbar dark flat height="35px">
              <v-spacer />
                <v-toolbar-title>B o a r d</v-toolbar-title>
              <v-spacer />
            </v-toolbar>
          </v-card>
          <v-card-text>
            <v-text-field label="title" v-model="getBoardTitle" prepend-icon="mdi-lock" type="text" :disabled="isDisabled"></v-text-field>
            <v-textarea height="300" v-model="getBoardContent" label="contents" prepend-icon="mdi-lock" type="text" :disabled="isDisabled"></v-textarea>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn dark v-if="(isDisabled && getSameUser)" @click="setModify">수정</v-btn>
            <v-btn dark v-if="(!isDisabled && getSameUser)" @click="modifyBoard()">수정 완료</v-btn>
            <v-btn dark v-if="(isDisabled && getSameUser)" @click="removeBoard()">삭제</v-btn>
            <v-btn dark @click="moveBack">취소</v-btn>
          </v-card-actions>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
<script>
import router from '@/router/'
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'

export default {
  name: 'BoardReadView',
  data: function () {
    return {
      isDisabled: true
    }
  },
  props: {
    boardNo: {
      type: String,
      required: true
    }
  },
  computed: {
    ...mapGetters([
      'getSameUser',
      'getBoardTitle',
      'getBoardContent'
    ])
  },
  methods: {
    ...mapActions([
      'getBoardAction'
    ]),
    setModify: function () {
      this.isDisabled = !this.isDisabled
    },
    moveBack: function () {
      if (!this.isDisabled) {
        this.isDisabled = !this.isDisabled
      } else {
        router.go(-1)
      }
    },
    modifyBoard: function () {
      var data = this.getBoard
      console.log('modifyBoard() data - boardNo : ' + data.boardNo)
      axios.put(`http://localhost:7777/board/${this.getBoard.boardNo}`, data).then(res => {
        if (res.status === 200 && res.data === 'Success') {
          alert('글 수정 성공')
        } else {
          alert('글 수정 실패')
        }
        router.push({ name: 'BoardMain' })
      }).catch(err => {
        console.log(err)
      })
    },
    removeBoard: function () {
      var data = this.getBoard
      console.log('removeBoard() data - boardNo : ' + data.boardNo)
      axios.delete(`http://localhost:7777/board/${data.boardNo}`).then(res => {
        if (res.status === 200 && res.data === 'Success') {
          alert('글 삭제 성공')
        } else {
          alert('글 삭제 실패')
        }
        router.push({ name: 'BoardMain' })
      }).catch(err => {
        console.log(err)
      })
    }
  },
  mounted () {
    this.getBoardAction(this.boardNo)
  }
}
</script>
