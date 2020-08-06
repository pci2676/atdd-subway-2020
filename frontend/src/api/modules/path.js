import ApiService from '@/api'

const PathService = {
  get({source, target, type}) {
    return ApiService.get(`/paths`, {source, target, type})
  }
}

export default PathService
