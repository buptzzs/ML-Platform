import request from '@/utils/request'


export function getSystemInfo(params) {
    return request({
        url: '/monitor/system',
        method: 'get',
        params
    })
}
