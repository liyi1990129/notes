import httpRequest from '@/utils/httpRequest'
import constant from './constant'

export function pageList (data) {
  return httpRequest({
    url: constant.DR + '/action/page',
    method: 'post',
    data: data
  })
}
export function propertyList (data) {
  return httpRequest({
    url: constant.DR + '/property/list',
    method: 'post',
    data: data
  })
}

export function sceneList (data) {
  return httpRequest({
    url: constant.DR + '/scene/list',
    method: 'post',
    data: data
  })
}

export function saveOrUpdate (data) {
  return httpRequest({
    url: constant.DR + '/rule/saveOrUpdate',
    method: 'post',
    data: data
  })
}

export function del (data) {
  return httpRequest({
    url: constant.DR + '/rule/del',
    method: 'post',
    data: data
  })
}

export function getInfo (data) {
  return httpRequest({
    url: constant.DR + '/rule/get',
    method: 'post',
    data: data
  })
}
