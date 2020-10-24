import httpRequest from '@/utils/httpRequest'
import constant from './constant'

export function pageList (data) {
  return httpRequest({
    url: constant.DR + '/property/page',
    method: 'post',
    data: data
  })
}
