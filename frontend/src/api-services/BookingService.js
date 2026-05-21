import axios from "axios";


export default {
  getCustomerBookings(userId) {
    return axios.get(`/api/bookings/user/${userId}`)
  },
    createBooking(data) {
    return axios.post('/api/booking-form', data)
  }
}
