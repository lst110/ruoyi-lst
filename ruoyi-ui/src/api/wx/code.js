import request from '@/utils/request'

// 查询防伪码管理列表
export function listCode(query) {
  return request({
    url: '/wx/code/list',
    method: 'get',
    params: query
  })
}

// 查询防伪码管理详细
export function getCode(id) {
  return request({
    url: '/wx/code/' + id,
    method: 'get'
  })
}

// 新增防伪码管理
export function addCode(data) {
  return request({
    url: '/wx/code',
    method: 'post',
    data: data
  })
}

// 修改防伪码管理
export function updateCode(data) {
  return request({
    url: '/wx/code',
    method: 'put',
    data: data
  })
}

// 删除防伪码管理
export function delCode(id) {
  return request({
    url: '/wx/code/' + id,
    method: 'delete'
  })
}
