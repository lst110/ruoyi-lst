import request from '@/utils/request'

// 查询扫码记录列表
export function listLog(query) {
  return request({
    url: '/wx/log/list',
    method: 'get',
    params: query
  })
}

// 查询扫码记录详细
export function getLog(id) {
  return request({
    url: '/wx/log/' + id,
    method: 'get'
  })
}

// 新增扫码记录
export function addLog(data) {
  return request({
    url: '/wx/log',
    method: 'post',
    data: data
  })
}

// 修改扫码记录
export function updateLog(data) {
  return request({
    url: '/wx/log',
    method: 'put',
    data: data
  })
}

// 删除扫码记录
export function delLog(id) {
  return request({
    url: '/wx/log/' + id,
    method: 'delete'
  })
}
