import request from '@/utils/request'


export function getTasks(params) {
    return request({
        url: '/alComponent/getTasks',
        method: 'get',
        params
    })
}

export function getTask(params){
    return request({
        url: '/alComponent/getTask',
        method: 'get',
        params
    })
}

export function addTask(params) {
    return request({
        url: '/alComponent/createTask',
        method: 'post',
        params
    })
}

export function deleteTask(params) {
    return request({
        url: '/alComponent/deleteTask',
        method: 'post',
        params
    })
}

export function saveTask(params){
    return request({
        url: '/alComponent/save',
        method: 'post',
        data : params,
        headers: { 'Content-Type': 'application/json' }

    })
}

export function runTask(params){
    return request({
        url: '/alComponent/startTask',
        method: 'post',
        params
    })
}

export function stopTask(params) {
    return request({
        url: '/alComponent/stopTask',
        method: 'post',
        params
    })
}