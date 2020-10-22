import httpRequest from '@/utils/httpRequest'
import constant from './constant'

export function pageList (data) {
  return httpRequest({
    url: constant.DR + '/scene/page',
    method: 'post',
    data: data
  })
}
export function entityList (data) {
  return httpRequest({
    url: constant.DR + '/entity/list',
    method: 'post',
    data: data
  })
}

export function saveOrUpdate (data) {
  return httpRequest({
    url: constant.DR + '/scene/saveOrUpdate',
    method: 'post',
    data: data
  })
}

export function del (data) {
  return httpRequest({
    url: constant.DR + '/scene/del',
    method: 'post',
    data: data
  })
}

export function getInfo (data) {
  return httpRequest({
    url: constant.DR + '/scene/get',
    method: 'post',
    data: data
  })
}
