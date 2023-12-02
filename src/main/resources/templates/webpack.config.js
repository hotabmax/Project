const path = require('path');
module.exports = {
    mode: 'development',
    entry: {
        renderAdminAddOrDeleteNomenclature:'./js/interfaceAdminAddOrDeleteNomenclature/render.js',
        renderAdminAddOrDeleteUser: './js/interfaceAdminAddOrDeleteUser/render.js',
        renderDeleteHistoryOfPurchase: './js/interfaceAdminDeleteHistoryOfPurchase/render.js',
        renderDeleteHistoryOfSelling: './js/interfaceAdminDeleteHistoryOfSelling/render.js',
        renderGetStatistic: './js/interfaceAdminGetStatistic/render.js'
    },
    output: {
        path: path.resolve(__dirname, 'js/bundles'),
        filename: '[name].js',
        library: '[name]'
    },
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: "babel-loader"
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    devServer:{
        port:8100
    }
}