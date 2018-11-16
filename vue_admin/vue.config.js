module.exports = {
    // 修改的配置
    // 将baseUrl: '/api',改为baseUrl: '/',
    baseUrl: '/',
    devServer: {
        port:8299,
        proxy: {
            '/': {
                target: 'http://10.108.117.226:8080',
                changeOrigin: true,
                ws: true,
            },
        }
    },
    outputDir: 'target/dist',
    assetsDir: 'static'
}