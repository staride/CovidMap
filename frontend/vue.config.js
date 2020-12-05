module.exports = {
  configureWebpack: {
    // other webpack options to merge in
  }
  // dev Server Options don't belong into 'configureWebpack'
  devServer: {
    host: '0.0.0.0',
    hto: true,
    disableHostCheck: true
  }
}
