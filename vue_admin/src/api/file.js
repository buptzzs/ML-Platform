import request from '@/utils/request'


export function getFileInfos(params) {
    return request({
        url: '/file/files',
        method: 'get',
        params
    })
}

export function preview(params) {
    return request({
        url: '/file/preview',
        method: 'get',
        params
    })
}

export function download(params) {
    return request({
        url: '/file/download',
        method: 'get',
        responseType: 'blob',
        params
    })
}

export function delete_file(params) {
    return request({
        url: '/file/delete',
        method: 'post',
        params
    })
}

export function upload(formData) {
    return request({
        url: '/file/upload',
        method: 'post',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}