
<template>
  <div class="container">
    <h4 class="text-center mb-4">Broneering</h4>
    <AlertError :error-message="errorMessage" />
    <div class="card p-4">

      <h5 class="mb-4">{{ booking.bookingId }} &nbsp; {{ booking.customerName }}</h5>

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
          <button
            v-if="booking.latitude && booking.longitude"
            class="btn btn-outline-secondary"
            data-bs-toggle="modal"
            data-bs-target="#mapModal"
          >
            Vaata kaardil
          </button>
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

      <MapModal
        v-if="booking.latitude && booking.longitude"
        :latitude="String(booking.latitude)"
        :longitude="String(booking.longitude)"
      />

      <div class="d-flex justify-content-end gap-2">
        <button class="btn btn-outline-secondary" @click="sendEmail">Saada email</button>
        <button class="btn btn-outline-dark" @click="$router.push('/admin-bookings')">Sulge</button>
        <button v-if="booking.bookingStatus === 'OOTEL'" class="btn btn-success" @click="confirm">Kinnita</button>
        <button v-if="booking.bookingStatus === 'OOTEL' || booking.bookingStatus === 'KINNITATUD'" class="btn btn-danger" @click="cancel">Tühista</button>
      </div>

    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import BookingService from '@/api-services/BookingService.js'
import MapModal from '@/components/modals/MapModal.vue'

export default {
  name: 'AdminBookingView',
  components: { AlertError, MapModal },
  data() {
    return {
      booking: {},
      errorMessage: '',
    }
  },
  mounted() {
    BookingService.getAdminBookingById(this.$route.params.bookingId)
      .then((response) => {
        this.booking = response.data
      })
      .catch(() => {
        this.errorMessage = 'Broneeringu laadimine ebaõnnestus'
      })
  },
  methods: {
    confirm() {
      BookingService.confirmBooking(this.$route.params.bookingId)
        .then(() => {
          this.$router.push('/admin-bookings')
        })
        .catch(() => {
          this.errorMessage = 'Broneeringu kinnitamine ebaõnnestus'
        })
    },
    cancel() {
      BookingService.adminCancelBooking(this.$route.params.bookingId)
        .then(() => {
          this.$router.push('/admin-bookings')
        })
        .catch(() => {
          this.errorMessage = 'Broneeringu tühistamine ebaõnnestus'
        })
    },
    sendEmail() {
      this.errorMessage = 'Email funktsionaalsus pole veel saadaval'
    },
  },
}
</script>
