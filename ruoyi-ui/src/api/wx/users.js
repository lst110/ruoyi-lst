import request from '@/utils/request'

// 查询小程序用户列表
export function listUsers(query) {
  return request({
    url: '/wx/users/list',
    method: 'get',
    params: query
  })
}

// 查询小程序用户详细
export function getUsers(id) {
  return request({
    url: '/wx/users/' + id,
    method: 'get'
  })
}

// 新增小程序用户
export function addUsers(data) {
  return request({
    url: '/wx/users',
    method: 'post',
    data: data
  })
}

// 修改小程序用户
export function updateUsers(data) {
  return request({
    url: '/wx/users',
    method: 'put',
    data: data
  })
}

// 删除小程序用户
export function delUsers(id) {
  return request({
    url: '/wx/users/' + id,
    method: 'delete'
  })
}
