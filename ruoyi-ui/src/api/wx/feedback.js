import request from '@/utils/request'

// 查询反馈处理列表
export function listFeedback(query) {
  return request({
    url: '/wx/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询反馈处理详细
export function getFeedback(id) {
  return request({
    url: '/wx/feedback/' + id,
    method: 'get'
  })
}

// 新增反馈处理
export function addFeedback(data) {
  return request({
    url: '/wx/feedback',
    method: 'post',
    data: data
  })
}

// 修改反馈处理
export function updateFeedback(data) {
  return request({
    url: '/wx/feedback',
    method: 'put',
    data: data
  })
}

// 删除反馈处理
export function delFeedback(id) {
  return request({
    url: '/wx/feedback/' + id,
    method: 'delete'
  })
}
