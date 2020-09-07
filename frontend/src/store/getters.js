export default {
  getIsLogin (state) {
    return state.isLogin
  },
  getLoginInfo (state) {
    return state.loginInfo
  },
  getLoginId (state) {
    if (state.loginInfo != null) {
      return state.loginInfo.id
    }

    return null
  },
  getBoardList (state) {
    return state.boardList
  },
  getBoardTitle (state) {
    if (state.board === null) {
      return ''
    }
    return state.board.title
  },
  getBoardContent (state) {
    if (state.board === null) {
      return ''
    }
    return state.board.title
  },
  getSameUser (state) {
    if (state.board === null || state.loginInfo === null) {
      return false
    }
    return state.board.writer === state.loginInfo.nickName
  },
  getLoginLocationX (state) {
    console.log('getLoginLocationX')
    if (state.loginInfo === null || state.loginInfo.positionX === undefined) {
    // return 37.566851234596804

      return 37.563439370739424
    }

    return state.loginInfo.positionX
  },
  getLoginLocationY (state) {
    console.log('getLoginLocationY')
    if (state.loginInfo === null || state.loginInfo.positionY === undefined) {
    // return 126.97866357016943
      return 127.03692392199129
    }

    return state.loginInfo.positionY
  }
}
