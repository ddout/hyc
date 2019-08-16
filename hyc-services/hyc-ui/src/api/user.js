import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/hyc-web-admin/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/hyc-web-admin/user/login',
    method: 'post'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
