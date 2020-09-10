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
    console.log('state.board.title : ' + state.board.title)
    return state.board.title
  },
  getBoardContents (state) {
    console.log('state.board.contents : ' + state.board.contents)
    return state.board.contents
  },
  getSameUser (state) {
    if (state.board === null || state.loginInfo === null) {
      return false
    }
    return state.board.writer === state.loginInfo.nickName
  },
  getLoginLocationX (state) {
    // console.log('getLoginLocationX')
    if (state.loginInfo === null || state.loginInfo.positionX === undefined || state.loginInfo.positionX === null) {
    // return 37.566851234596804
      return 37.563439370739424
    }
    // console.log('state LoginLocationY : ' + state.loginInfo.positionX)
    return state.loginInfo.positionX
  },
  getLoginLocationY (state) {
    // console.log('getLoginLocationY')
    if (state.loginInfo === null || state.loginInfo.positionY === undefined || state.loginInfo.positionY === null) {
    // return 126.97866357016943
      return 127.03692392199129
    }
    return state.loginInfo.positionY
  }
}
