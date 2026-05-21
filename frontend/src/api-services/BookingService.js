import axios from 'axios'

export default {
  createBooking(data) {
    return axios.post('/api/booking-form', data)
  },
}