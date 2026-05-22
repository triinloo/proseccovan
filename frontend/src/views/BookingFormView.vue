<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-8">
        <h4 class="mb-4">Broneeri Prosecco Van oma sündmusele</h4>
        <AlertError :error-message="errorMessage" />
        <div class="card">
          <div class="card-body p-4 text-start">
            <p class="mb-3 text-start text-muted">Palun täida allolev vorm ja me võtame sinuga peatselt ühendust.</p>
            <div class="row">
              <div class="col-6">
                <div class="mb-3">
                  <label for="customerName" class="form-label">Ees- ja perekonnanimi</label>
                  <input
                    id="customerName"
                    v-model="customerName"
                    type="text"
                    class="form-control"
                    placeholder="Sisesta nimi"
                  />
                </div>
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
                <div class="mb-3">
                  <label for="bookingInfo" class="form-label">Lisainfo</label>
                  <textarea
                    id="bookingInfo"
                    v-model="bookingInfo"
                    class="form-control"
                    rows="3"
                    placeholder="Lisainfo"
                  />
                </div>
              </div>
              <div class="col-6">
                <div class="mb-3">
                  <label for="bookingType" class="form-label">Sündmuse tüüp</label>
                  <input
                    id="bookingType"
                    v-model="bookingType"
                    type="text"
                    class="form-control"
                    placeholder="nt. sünnipäev, pulm"
                  />
                </div>
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
            <div class="mt-4">
              <button class="btn btn-dark w-100" @click="submitBooking">Esita päring</button>
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
  name: 'BookingFormView',
  components: { AlertError, MapModal },
  data() {
    return {
      customerName: '',
      bookingType: '',
      email: '',
      phoneNumber: '',
      bookingDate: '',
      address: '',
      bookingInfo: '',
      packageType: '',
      latitude: '',
      longitude: '',
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
    submitBooking() {
      this.errorMessage = ''
      if (
        this.customerName === '' ||
        this.bookingType === '' ||
        this.email === '' ||
        this.phoneNumber === '' ||
        this.bookingDate === '' ||
        this.address === '' ||
        this.packageType === ''
      ) {
        this.errorMessage = 'Täida kõik väljad'
        return
      }
      const authStore = AuthService()
      BookingService.createBooking({
        userId: authStore.userId,
        customerName: this.customerName,
        email: this.email,
        phoneNumber: this.phoneNumber,
        bookingType: this.bookingType,
        bookingDate: this.bookingDate,
        address: this.address,
        latitude: this.latitude,
        longitude: this.longitude,
        packageType: this.packageType,
        bookingInfo: this.bookingInfo,
      })
        .then(() => {
          this.$router.push('/customer-bookings')
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Päring ebaõnnestus'
        })
    },
  },
}
</script>
