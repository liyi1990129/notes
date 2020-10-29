import httpRequest from '@/utils/httpRequest'
import constant from './constant'

export function pageList (data) {
  return httpRequest({
    url: constant.DR + '/entity/page',
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
export function entityItemList (data) {
  return httpRequest({
    url: constant.DR + '/entity/listItem',
    method: 'post',
    data: data
  })
}

export function saveOrUpdate (data) {
  return httpRequest({
    url: constant.DR + '/entity/saveOrUpdate',
    method: 'post',
    data: data
  })
}

export function del (data) {
  return httpRequest({
    url: constant.DR + '/entity/del',
    method: 'post',
    data: data
  })
}

export function getInfo (data) {
  return httpRequest({
    url: constant.DR + '/entity/get',
    method: 'post',
    data: data
  })
}
export function getEntitys (data) {
  return httpRequest({
    url: constant.DR + '/entity/getEntitys',
    method: 'post',
    data: data
  })
}
export function getEntityProperties (data) {
  return httpRequest({
    url: constant.DR + '/entity/getEntityProperties',
    method: 'post',
    data: data
  })
}
