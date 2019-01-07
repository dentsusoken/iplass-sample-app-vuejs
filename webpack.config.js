var path = require('path')
var webpack = require('webpack')
var staticContentPath = '/mtp'

module.exports = {
  entry: './src/main/vue/main.js',
  output: {
    path: path.resolve(__dirname, './src/main/webapp/scripts/'),
    publicPath: staticContentPath + '/scripts/',
    filename: 'build.js'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          'css-loader'
        ],
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
          }
          // other vue-loader options go here
        }
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/
      },
      {
        test: /\.(png|jpg|gif|svg|eot|otf|ttf|woff)$/,
        loader: 'file-loader',
        options: {
          name: '[name].[ext]?[hash]',
          publicPath: staticContentPath + '/styles/fonts/',
          outputPath: '../styles/fonts/'
        }
      }
    ]
  },
  resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    },
    extensions: ['*', '.js', '.vue', '.json']
  },
  performance: {
    hints: false
  },
  mode: 'development',
  devtool: '#eval-source-map'
}

if (process.env.NODE_ENV === 'production') {
  module.exports.mode = 'production'
  module.exports.devtool = '#source-map'
  // http://vue-loader.vuejs.org/en/workflow/production.html
  module.exports.plugins = (module.exports.plugins || []).concat([
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new webpack.LoaderOptionsPlugin({
      minimize: true
    })
  ])
}
