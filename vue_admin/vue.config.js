module.exports = {
    // 修改的配置
    // 将baseUrl: '/api',改为baseUrl: '/',
    baseUrl: '/',
    devServer: {
        proxy: {
            '^/auth': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                ws: true,
            }
        }
    },
    outputDir: 'target/dist',
    assetsDir: 'static'
}