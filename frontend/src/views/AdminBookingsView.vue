<template>
  <div class="container">
    <h4 class="text-center mb-4">Broneeringud</h4>
    <AlertError :error-message="errorMessage" />
    <div class="mb-3">
      <input v-model="searchQuery" type="text" class="form-control w-25" placeholder="Otsi nime järgi" />
    </div>
    <table class="table table-bordered">
      <thead>
      <tr>
        <th style="width: 10%">Broneering</th>
        <th style="width: 30%">Klient</th>
        <th style="width: 20%">Toimumise aeg</th>
        <th style="width: 10%">Staatus</th>
        <th style="width: 30%">Toimingud</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="booking in filteredBookings" :key="booking.bookingId">
        <td>{{ booking.bookingId }}</td>
        <td>{{ booking.customerName }}</td>
        <td>{{ booking.bookingDate }}</td>
        <td>
          <span v-if="booking.bookingStatus === 'OOTEL'" class="badge bg-warning text-dark">Ootel</span>
          <span v-else-if="booking.bookingStatus === 'KINNITATUD'" class="badge bg-success">Kinnitatud</span>
          <span v-else class="badge bg-secondary">Tühistatud</span>
        </td>
        <td>
          <span class="badge bg-secondary me-1" style="cursor: pointer" @click="$router.push('/admin-booking/' + booking.id)">Vaata</span>
          <span v-if="booking.bookingStatus === 'OOTEL'" class="badge bg-success me-1" style="cursor: pointer" @click="confirmBooking(booking.id)">Kinnita</span>
          <span v-if="booking.bookingStatus === 'OOTEL' || booking.bookingStatus === 'KINNITATUD'" class="badge bg-danger" style="cursor: pointer" @click="cancelBooking(booking.id)">Tühista</span>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import BookingService from '@/api-services/BookingService.js'
import AlertError from '@/components/alerts/AlertError.vue'

export default {
  name: 'AdminBookingsView',
  components: { AlertError },
  data() {
    return {
      bookings: [],
      searchQuery: '',
      errorMessage: '',
    }
  },
  computed: {
    filteredBookings() {
      if (!this.searchQuery) return this.bookings
      return this.bookings.filter((b) =>
        b.customerName.toLowerCase().includes(this.searchQuery.toLowerCase()),
      )
    },
  },
  mounted() {
    BookingService.getAllBookings()
      .then((response) => {
        this.bookings = response.data
      })
  },
  methods: {
    confirmBooking(bookingId) {
      this.errorMessage = ''
      BookingService.confirmBooking(bookingId)
        .then(() => {
          BookingService.getAllBookings().then((response) => {
            this.bookings = response.data
          })
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Broneeringu kinnitamine ebaõnnestus'
        })
    },
    cancelBooking(bookingId) {
      this.errorMessage = ''
      BookingService.adminCancelBooking(bookingId)
        .then(() => {
          BookingService.getAllBookings().then((response) => {
            this.bookings = response.data
          })
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Broneeringu tühistamine ebaõnnestus'
        })
    },
  },
}
</script>

