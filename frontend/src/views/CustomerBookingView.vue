<template>
  <div class="container">
    <h4 class="text-center mb-4">Broneering</h4>
    <div class="card p-4">

      <h5 class="mb-4">{{ $route.params.bookingId }} &nbsp; {{ booking.customerName }}</h5>

      <div class="row mb-3">
        <div class="col">
          <small class="text-muted">Email</small>
          <div>{{ booking.email }}</div>
        </div>
        <div class="col">
          <small class="text-muted">Telefoni number</small>
          <div>{{ booking.phoneNumber }}</div>
        </div>
        <div class="col">
          <small class="text-muted">Asukoht</small>
          <div>{{ booking.address }}</div>
        </div>
        <div class="col-auto">
          <a :href="`https://maps.google.com/?q=${booking.latitude},${booking.longitude}`"
             target="_blank"
             class="btn btn-outline-secondary">Vaata kaardil</a>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col">
          <small class="text-muted">Sündmuse aeg</small>
          <div>{{ booking.bookingDate }}</div>
        </div>
        <div class="col">
          <small class="text-muted">Sündmuse tüüp</small>
          <div>{{ booking.bookingType }}</div>
        </div>
        <div class="col">
          <small class="text-muted">Pakett</small>
          <div>{{ booking.packageType }}</div>
        </div>
        <div class="col-auto invisible">
          <button class="btn btn-outline-secondary">placeholder</button>
        </div>
      </div>

      <div class="mb-4">
        <small class="text-muted">Lisainfo</small>
        <div>{{ booking.bookingInfo }}</div>
      </div>

      <div class="d-flex justify-content-end gap-2">
        <button class="btn btn-outline-danger" @click="cancel">Tühista</button>
        <button class="btn btn-outline-secondary" @click="$router.push('/customer-bookings')">Sulge</button>
      </div>

    </div>
  </div>
</template>

<script>
import BookingService from '@/api-services/BookingService.js'

export default {
  name: 'CustomerBookingView',
  data() {
    return {
      booking: {},
    }
  },
  mounted() {
    BookingService.getBookingById(this.$route.params.bookingId)
      .then((response) => {
        this.booking = response.data
      })
  },
  methods: {
    cancel() {
      BookingService.cancelBooking(this.$route.params.bookingId)
        .then(() => {
          this.$router.push('/customer-bookings')
        })
    },
  },
}
</script>
