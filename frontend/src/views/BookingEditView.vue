<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-8">
        <h4 class="mb-4">Muuda broneeringut</h4>
        <AlertError :error-message="errorMessage" />
        <div class="card">
          <div class="card-body p-4 text-start">
            <div class="row">
              <div class="col-6">
                <div class="mb-3">
                  <label for="email" class="form-label">Email</label>
                  <input
                    id="email"
                    v-model="email"
                    type="email"
                    class="form-control"
                    placeholder="Sisesta e-mail"
                  />
                </div>
                <div class="mb-3">
                  <label for="phoneNumber" class="form-label">Telefoni number</label>
                  <input
                    id="phoneNumber"
                    v-model="phoneNumber"
                    type="tel"
                    class="form-control"
                    placeholder="Sisesta telefoninumber"
                  />
                </div>
                <div class="mb-3">
                  <label for="bookingDate" class="form-label">Sündmuse aeg</label>
                  <input
                    id="bookingDate"
                    v-model="bookingDate"
                    type="date"
                    class="form-control"
                  />
                </div>
              </div>
              <div class="col-6">
                <div class="mb-3">
                  <label for="address" class="form-label">Aadress</label>
                  <div class="d-flex gap-2">
                    <input
                      id="address"
                      v-model="address"
                      type="text"
                      class="form-control"
                      placeholder="Sisesta aadress"
                    />
                    <button
                      type="button"
                      class="btn btn-outline-secondary text-nowrap"
                      :disabled="!address || geocoding"
                      @click="findCoordinates"
                    >
                      {{ geocoding ? 'Otsib...' : 'Leia koordinaadid' }}
                    </button>
                  </div>
                  <div v-if="geocodeError" class="text-danger small mt-1">{{ geocodeError }}</div>
                </div>
                <div class="mb-3">
                  <label class="form-label">Asukoht (koordinaadid)</label>
                  <div class="d-flex gap-2 align-items-center">
                    <input
                      id="latitude"
                      v-model="latitude"
                      type="text"
                      class="form-control"
                      placeholder="Laiuskraad (latitude)"
                    />
                    <input
                      id="longitude"
                      v-model="longitude"
                      type="text"
                      class="form-control"
                      placeholder="Pikkuskraad (longitude)"
                    />
                    <button
                      type="button"
                      class="btn btn-outline-secondary text-nowrap"
                      data-bs-toggle="modal"
                      data-bs-target="#mapModal"
                    >
                      Vaata kaardilt
                    </button>
                  </div>
                </div>
                <div class="mb-3">
                  <label class="form-label">Vali pakett</label>
                  <div v-for="pkg in packages" :key="pkg.value" class="form-check mb-2">
                    <input
                      :id="pkg.value"
                      v-model="packageType"
                      type="radio"
                      :value="pkg.value"
                      class="form-check-input"
                    />
                    <label :for="pkg.value" class="form-check-label">{{ pkg.label }}</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="mt-4 d-flex gap-2">
              <button class="btn btn-dark w-100" @click="save">Salvesta</button>
              <button class="btn btn-outline-dark w-100" @click="$router.push('/customer-booking/' + $route.params.bookingId)">Tühista</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <MapModal
    :latitude="latitude"
    :longitude="longitude"
    :interactive="true"
    @location-selected="onLocationSelected"
  />
</template>

<script>
import axios from 'axios'
import AlertError from '@/components/alerts/AlertError.vue'
import MapModal from '@/components/modals/MapModal.vue'
import BookingService from '@/api-services/BookingService.js'
import { AuthService } from '@/auth/AuthService.js'

export default {
  name: 'BookingEditView',
  components: { AlertError, MapModal },
  data() {
    return {
      email: '',
      phoneNumber: '',
      bookingDate: '',
      address: '',
      latitude: '',
      longitude: '',
      packageType: '',
      errorMessage: '',
      geocodeError: '',
      geocoding: false,
      packages: [
        { value: 'MINI', label: 'Mini (min 20 inimest)' },
        { value: 'MIDI', label: 'Midi (20-40 inimest)' },
        { value: 'MAXI', label: 'Maxi (40-65 inimest)' },
      ],
    }
  },
  mounted() {
    BookingService.getBookingById(this.$route.params.bookingId)
      .then((response) => {
        const b = response.data
        this.email = b.email
        this.phoneNumber = b.phoneNumber
        this.address = b.address
        this.latitude = b.latitude ?? ''
        this.longitude = b.longitude ?? ''
        this.packageType = b.packageType
        const parts = b.bookingDate.split('/')
        this.bookingDate = `${parts[2]}-${parts[1]}-${parts[0]}`
      })
      .catch(() => {
        this.errorMessage = 'Broneeringu laadimine ebaõnnestus'
      })
  },
  methods: {
    async findCoordinates() {
      this.geocodeError = ''
      this.geocoding = true
      try {
        const response = await axios.get('https://nominatim.openstreetmap.org/search', {
          params: { q: this.address, format: 'json', limit: 1 },
          headers: { 'Accept-Language': 'et' },
        })
        if (response.data.length === 0) {
          this.geocodeError = 'Aadressi ei leitud'
          return
        }
        this.latitude = response.data[0].lat
        this.longitude = response.data[0].lon
      } catch {
        this.geocodeError = 'Koordinaatide otsimine ebaõnnestus'
      } finally {
        this.geocoding = false
      }
    },
    onLocationSelected({ latitude, longitude }) {
      this.latitude = latitude
      this.longitude = longitude
    },
    save() {
      this.errorMessage = ''
      if (!this.email || !this.phoneNumber || !this.bookingDate || !this.address || !this.packageType) {
        this.errorMessage = 'Täida kõik kohustuslikud väljad'
        return
      }
      const authStore = AuthService()
      BookingService.updateBooking(this.$route.params.bookingId, {
        userId: authStore.userId,
        email: this.email,
        phoneNumber: this.phoneNumber,
        bookingDate: this.bookingDate,
        address: this.address,
        latitude: this.latitude || null,
        longitude: this.longitude || null,
        packageType: this.packageType,
      })
        .then(() => {
          this.$router.push('/customer-booking/' + this.$route.params.bookingId)
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Salvestamine ebaõnnestus'
        })
    },
  },
}
</script>