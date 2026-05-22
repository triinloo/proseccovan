import axios from 'axios'

export default {
  createEvent(data) {
    return axios.post('/api/admin/events', data)
  },
  getEvent(eventId) {
    return axios.get(`/api/admin/events/${eventId}`)
  },
  updateEvent(eventId, data) {
    return axios.put(`/api/admin/events/${eventId}`, data)
  },
  getAdminEvents() {
    return axios.get('/api/admin/events')
  },
  deleteEvent(eventId) {
    return axios.delete(`/api/admin/events/${eventId}`)
  },
}
