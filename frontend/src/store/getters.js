export default {
  getIsLogin (state) {
    return state.isLogin
  },
  getLoginInfo (state) {
    return state.loginInfo
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
    if (state.loginInfo === null || state.loginInfo.locationX === null) {
    // return 37.566851234596804
      return 37.563439370739424
    }

    return state.loginInfo.locationX
  },
  getLoginLocationY (state) {
    if (state.loginInfo === null || state.loginInfo.locationY === null) {
    // return 126.97866357016943
      return 127.03692392199129
    }

    return state.loginInfo.locationX
  }
}
