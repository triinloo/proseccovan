<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-8">
        <h4 class="mb-2 text-start">Broneeri Prosecco Van oma sündmusele</h4>
        <p class="mb-4 text-start text-muted">Palun täida allolev vorm ja me võtame sinuga peatselt ühendust.</p>
        <AlertError :error-message="errorMessage" />
        <div class="card">
          <div class="card-body p-4 text-start">
            <div class="row">
              <div class="col-7">
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
                  <label for="bookingDate" class="form-label">Sündmuse aeg</label>
                  <input
                    id="bookingDate"
                    v-model="bookingDate"
                    type="date"
                    class="form-control"
                  />
                </div>
                <div class="mb-3">
                  <label for="address" class="form-label">Aadress</label>
                  <input
                    id="address"
                    v-model="address"
                    type="text"
                    class="form-control"
                    placeholder="Sisesta aadress"
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
              <div class="col-5">
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
            <div class="mt-4">
              <button class="btn btn-dark w-100" @click="submitBooking">Esita päring</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import BookingService from '@/api-services/BookingService.js'
import { AuthService } from '@/auth/AuthService.js'

export default {
  name: 'BookingFormView',
  components: { AlertError },
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
      errorMessage: '',
      packages: [
        { value: 'MINI', label: 'Mini (min 20 inimest)' },
        { value: 'MIDI', label: 'Midi (20-40 inimest)' },
        { value: 'MAXI', label: 'Maxi (40-65 inimest)' },
      ],
    }
  },
  methods: {
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
        packageType: this.packageType,
        bookingInfo: this.bookingInfo,
      })
        .then(() => {
          this.$router.push('/')
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Päring ebaõnnestus'
        })
    },
  },
}
</script>
