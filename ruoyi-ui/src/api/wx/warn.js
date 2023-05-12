import request from '@/utils/request'

// 查询预警处理列表
export function listWarn(query) {
  return request({
    url: '/wx/warn/list',
    method: 'get',
    params: query
  })
}

// 查询预警处理详细
export function getWarn(id) {
  return request({
    url: '/wx/warn/' + id,
    method: 'get'
  })
}

// 新增预警处理
export function addWarn(data) {
  return request({
    url: '/wx/warn',
    method: 'post',
    data: data
  })
}

// 修改预警处理
export function updateWarn(data) {
  return request({
    url: '/wx/warn',
    method: 'put',
    data: data
  })
}

// 删除预警处理
export function delWarn(id) {
  return request({
    url: '/wx/warn/' + id,
    method: 'delete'
  })
}
