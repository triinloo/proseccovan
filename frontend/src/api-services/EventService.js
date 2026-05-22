import axios from 'axios'

export default {
  getEvents(season) {
    const params = season !== 'Kõik' ? { season } : {}
    return axios.get('/api/events', { params })
  },
}
