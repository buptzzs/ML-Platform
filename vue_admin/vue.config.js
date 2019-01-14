module.exports = {
    // 修改的配置
    // 将baseUrl: '/api',改为baseUrl: '/',
    baseUrl: '/',
    devServer: {
        port:8299,
        proxy: {
            '/': {
                target: 'http://127.0.0.1:8080',
                changeOrigin: true,
                ws: true,
            },
        }
    },
    chainWebpack: config => {
        config.module
            .rule('svg')
            .exclude.add(resolve('src/icons'))
            .end()

        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(resolve('src/icons'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            })
    },
    outputDir: 'target/dist',
    assetsDir: 'static'
}

var path = require('path')

function resolve(dir) {
    return path.join(__dirname, './', dir)
}