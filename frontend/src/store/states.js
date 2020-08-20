export default {

  idRules: [
    v => !!v || 'id를 입력해 주세요'
  ],
  passwordRules: [
    v => !!v || '비밀번호를 입력해 주세요'
  ],
  nickNameRules: [
    v => !!v || 'nickname을 입력해 주세요.',
    v => (v.trim() !== '') || 'nickname을 입력해 주세요'
  ],
  phoneRules: [
    v => !!v || 'phone을 입력해 주세요.',
    v => (v.trim() !== '') || 'phone을 입력해 주세요.'
  ],
  emailRules: [
    v => !!v || '이메일을 작성해 주세요.',
    v => (v.trim() !== '') || '이메일을 작성해 주세요.',
    v => /.+@.+\..+/.test(v) || '이메일 형식으로 작성해주세요.'
  ],
  isLogin: false
}
