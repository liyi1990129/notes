import httpRequest from '@/utils/httpRequest'
import constant from './constant'

export function pageList (data) {
  return httpRequest({
    url: constant.DR + '/action/page',
    method: 'post',
    data: data
  })
}

export function saveOrUpdate (data) {
  return httpRequest({
    url: constant.DR + '/action/saveOrUpdate',
    method: 'post',
    data: data
  })
}

export function del (data) {
  return httpRequest({
    url: constant.DR + '/action/del',
    method: 'post',
    data: data
  })
}

export function getInfo (data) {
  return httpRequest({
    url: constant.DR + '/action/get',
    method: 'post',
    data: data
  })
}
