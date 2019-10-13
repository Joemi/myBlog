const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const path = require('path');
const { VueLoaderPlugin } = require('vue-loader')
const nodeExternals = require('webpack-node-externals');

const config = {
    entry: ["babel-polyfill", "./main.js"],

    output: {
        filename: 'build.js',
        path: path.join(__dirname, 'src/dist')
    },
    
    module: {

        rules: [
                {
                    test: /\.vue$/, 
                    loader: 'vue-loader'
                },
                { 
                    test: /\.js$/,
                    use: 'babel-loader',
                    exclude: /node_modules/
                },
                {
                    test: /\.(scss|css)$/,
                    use: [
                        'vue-style-loader',
                        'css-loader',
                        // {
                        //     loader: 'css-loader',
                        //     options: {
                        //         // root './' not effect
                        //         alias: {
                        //             "fonts/element-icons.ttf": "./fonts/element-icons.ttf",
                        //             "fonts/element-icons.woff": "./fonts/element-icons.woff"
                        //           },
                        //         // enable CSS Modules
                        //         modules: true,
                        //         // customize generated class names
                        //         localIdentName: '[local]_[hash:base64:8]',
                        //     }
                        // },
                        'sass-loader',
                        // {
                        //     loader: "sass-resources-loader",
                        //     options: {
                        //       resources: path.join(__dirname, "src/css/resource.scss"),
                        //     }
                        // }
                    ]
                },
                {
                    test: /\.(png|jpg|gif|svg|eot|ttf|woff|woff2)$/,
                    loader: 'url-loader',
                    options: {
                      limit: 10000
                    }
                },
                {
                    test: /\.txt$/,
                    use: 'raw-loader'
                },
                {
                    test: /\.json$/,
                    loader: 'json-loader'
                }
            ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: './index.html'
        }),
        new VueLoaderPlugin(),
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery"
        })
    ],
    
    resolve: {
        extensions: [".js", ".vue", '.json'],
        alias: {
            '@': path.resolve(__dirname, 'src/'),
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    // watch: true
  
    //axios跨域

};


process.env.NODE_ENV === 'production' ? null :config.devServer = {
        contentBase: path.join(__dirname, "src/dist"),
        compress: true,
        port: 3000,
    }

module.exports = config;