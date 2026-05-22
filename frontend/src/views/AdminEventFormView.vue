<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-5">
        <h4 class="mb-4">Sündmused</h4>
        <AlertError :error-message="errorMessage" />
        <div class="card">
          <div class="card-body p-4 text-start">

            <div class="mb-3">
              <label class="form-label">Sündmuse nimi</label>
              <input
                v-model="eventName"
                type="text"
                class="form-control"
                placeholder="Sisesta sündmuse nimi"
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Sündmuse asukoht</label>
              <input
                v-model="eventLocation"
                type="text"
                class="form-control"
                placeholder="Linn/Maakond"
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Sündmuse aeg</label>
              <div class="d-flex align-items-center gap-2">
                <input v-model="eventStartDate" type="date" class="form-control" />
                <span class="text-muted">–</span>
                <input v-model="eventEndDate" type="date" class="form-control" />
              </div>
            </div>

            <div class="mb-3">
              <label class="form-label">Sündmuse kirjeldus</label>
              <textarea
                v-model="eventDescription"
                class="form-control"
                rows="3"
                placeholder="Lisa täpsustused andmed sündmuse kohta"
              />
            </div>

            <div class="mb-4">
              <input ref="fileInput" type="file" accept="image/*" class="d-none" @change="onFileChange" />
              <img
                v-if="imageData"
                :src="imageData"
                class="img-thumbnail d-block mb-2"
                style="max-height: 150px"
                alt="Pilt"
              />
              <div class="d-flex align-items-center gap-2">
                <span class="form-label mb-0">Lisa pilt</span>
                <button type="button" class="btn btn-outline-secondary btn-sm p-1" @click="$refs.fileInput.click()">
                  <PhPlus :size="16" />
                </button>
              </div>
            </div>

            <div class="d-flex justify-content-center gap-2">
              <button v-if="!eventId" class="btn btn-warning" @click="submitCreate">Lisa sündmus</button>
              <button v-else class="btn btn-warning" @click="submitUpdate">Muuda</button>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import AdminEventService from '@/api-services/AdminEventService.js'
import { AuthService } from '@/auth/AuthService.js'
import { PhPlus } from '@phosphor-icons/vue'

export default {
  name: 'AdminEventFormView',
  components: { AlertError, PhPlus },
  data() {
    return {
      eventId: null,
      eventName: '',
      eventLocation: '',
      eventStartDate: '',
      eventEndDate: '',
      eventDescription: '',
      imageData: '',
      errorMessage: '',
    }
  },
  mounted() {
    const id = this.$route.params.eventId
    if (id) {
      this.eventId = Number(id)
      AdminEventService.getEvent(this.eventId)
        .then((res) => {
          const e = res.data
          this.eventName = e.eventName
          this.eventLocation = e.eventLocation
          this.eventStartDate = this.toInputDate(e.eventStartDate)
          this.eventEndDate = this.toInputDate(e.eventEndDate)
          this.eventDescription = e.eventDescription
          this.imageData = e.imageData ?? ''
        })
        .catch(() => {
          this.errorMessage = 'Sündmuse laadimine ebaõnnestus'
        })
    }
  },
  methods: {
    toInputDate(ddMMyyyy) {
      if (!ddMMyyyy) return ''
      const [d, m, y] = ddMMyyyy.split('-')
      return `${y}-${m}-${d}`
    },
    onFileChange(e) {
      const file = e.target.files[0]
      if (!file) return
      const reader = new FileReader()
      reader.onload = (ev) => {
        this.imageData = ev.target.result
      }
      reader.readAsDataURL(file)
    },
    buildPayload() {
      const authStore = AuthService()
      return {
        userId: authStore.userId,
        eventName: this.eventName,
        eventLocation: this.eventLocation,
        eventStartDate: this.eventStartDate,
        eventEndDate: this.eventEndDate,
        eventDescription: this.eventDescription,
        imageData: this.imageData || null,
      }
    },
    submitCreate() {
      this.errorMessage = ''
      if (!this.eventName || !this.eventLocation || !this.eventStartDate || !this.eventEndDate) {
        this.errorMessage = 'Täida kõik kohustuslikud väljad'
        return
      }
      AdminEventService.createEvent(this.buildPayload())
        .then(() => {
          this.$router.push('/events')
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Sündmuse loomine ebaõnnestus'
        })
    },
    submitUpdate() {
      this.errorMessage = ''
      if (!this.eventId) return
      AdminEventService.updateEvent(this.eventId, this.buildPayload())
        .then(() => {
          this.$router.push('/events')
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Sündmuse uuendamine ebaõnnestus'
        })
    },
  },
}
</script>
