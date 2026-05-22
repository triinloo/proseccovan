<template>
  <div class="container">
    <h4 class="text-center mb-4">Minu broneeringud</h4>
    <AlertError :error-message="errorMessage" />
    <table class="table table-bordered text-center">
      <thead>
        <tr>
          <th style="width: 10%">Broneering</th>
          <th style="width: 30%">Klient</th>
          <th style="width: 30%">Toimumise aeg</th>
          <th style="width: 10%">Staatus</th>
          <th style="width: 10%">Toimingud</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="booking in bookings" :key="booking.bookingId">
          <td>{{ booking.bookingId }}</td>
          <td>{{ booking.customerName }}</td>
          <td>{{ booking.bookingDate }}</td>
          <td>
            <span v-if="booking.bookingStatus === 'OOTEL'" class="badge bg-warning text-dark">Ootel</span>
            <span v-else-if="booking.bookingStatus === 'KINNITATUD'" class="badge bg-success">Kinnitatud</span>
            <span v-else class="badge bg-secondary">Tühistatud</span>
          </td>
          <td>
            <button v-if="booking.bookingStatus === 'OOTEL'" class="btn btn-sm btn-outline-secondary" @click="$router.push('/customer-booking/' + booking.id)">Muuda</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="d-flex justify-content-end">
      <button class="btn btn-primary" @click="$router.push({ name: 'booking-form' })">Lisa broneering</button>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import BookingService from '@/api-services/BookingService.js'
import { AuthService } from '@/auth/AuthService.js'

export default {
  name: 'CustomerBookingsView',
  components: { AlertError },
  setup() {
    const authStore = AuthService()
    return { authStore }
  },
  data() {
    return {
      bookings: [],
      errorMessage: '',
    }
  },
  mounted() {
    BookingService.getCustomerBookings(this.authStore.userId)
      .then((response) => {
        this.bookings = response.data
      })
      .catch(() => {
        this.errorMessage = 'Broneeringute laadimine ebaõnnestus'
      })
  },
}
</script>
