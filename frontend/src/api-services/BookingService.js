import axios from "axios";


export default {
  getCustomerBookings(userId) {
    return axios.get(`/api/bookings/user/${userId}`)
  },
    createBooking(data) {
    return axios.post('/api/booking-form', data)
  },
  getBookingById(bookingId) {
    return axios.get(`/api/customer-booking/${bookingId}`)
  },
  cancelBooking(bookingId) {
    return axios.delete(`/api/customer-booking/${bookingId}`)
  },
  getAllBookings() {
    return axios.get('/api/admin-bookings?status=A')
  },
  confirmBooking(bookingId) {
    return axios.put(`/api/admin-bookings/${bookingId}/confirm`)
  },
  adminCancelBooking(bookingId) {
    return axios.put(`/api/admin-bookings/${bookingId}/cancel`)
  },
}
