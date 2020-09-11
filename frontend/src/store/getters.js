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
    if (state.board !== null) {
      return state.board.title
    }

    return ''
  },
  getBoardContents (state) {
    if (state.board !== null) {
      return state.board.contents
    }

    return ''
  },
  getSameUser (state) {
    if (state.board === null || state.loginInfo === null) {
      return false
    }
    return state.board.writer === state.loginInfo.nickName
  },
  getLoginLocationX (state) {
    if (state.loginInfo === null || state.loginInfo.positionX === undefined || state.loginInfo.positionX === '') {
      return 37.566851234596804
    }
    return state.loginInfo.positionX
  },
  getLoginLocationY (state) {
    if (state.loginInfo === null || state.loginInfo.positionY === undefined || state.loginInfo.positionY === '') {
      return 126.97866357016943
    }
    return state.loginInfo.positionY
  }
}
