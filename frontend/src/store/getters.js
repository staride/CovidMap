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
  }
}
