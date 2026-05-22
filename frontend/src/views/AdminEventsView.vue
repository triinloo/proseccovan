<template>
  <div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h4 class="mb-0">Sündmused</h4>
      <RouterLink to="/admin-event-form" class="btn btn-warning d-flex align-items-center gap-1">
        Lisa uus sündmus <PhPlus :size="18" />
      </RouterLink>
    </div>

    <AlertError :error-message="errorMessage" />

    <div class="row row-cols-1 row-cols-md-3 g-4">
      <div class="col" v-for="event in events" :key="event.eventId">
        <div class="card h-100">
          <img
            v-if="!brokenImages[event.eventId]"
            :src="getEventImage(event.eventName)"
            class="card-img-top"
            :alt="event.eventName"
            style="height: 200px; object-fit: cover"
            @error="brokenImages[event.eventId] = true"
          />
          <div v-else class="bg-light d-flex align-items-center justify-content-center" style="height: 200px">
            <PhImage :size="48" color="#aaa" />
          </div>
          <div class="card-body">
            <h6 class="card-title fw-bold">{{ event.eventName }}</h6>
            <p class="card-text text-muted small">{{ event.eventDescription }}</p>
            <p class="mb-1 small">
              <PhCalendarBlank :size="16" class="me-1" />{{ event.eventStartDate }}
            </p>
            <p class="mb-0 small">
              <PhMapPin :size="16" class="me-1" />{{ event.eventLocation }}
            </p>
          </div>
          <div class="card-footer bg-white border-top-0 d-flex gap-2 justify-content-end">
            <RouterLink :to="`/admin-event-form/${event.eventId}`" class="btn btn-sm btn-outline-secondary">
              <PhPencilSimple :size="16" />
            </RouterLink>
            <button class="btn btn-sm btn-outline-danger" @click="deleteEvent(event.eventId)">
              <PhTrash :size="16" />
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import AdminEventService from '@/api-services/AdminEventService.js'
import { PhPlus, PhImage, PhCalendarBlank, PhMapPin, PhPencilSimple, PhTrash } from '@phosphor-icons/vue'

export default {
  name: 'AdminEventsView',
  components: { AlertError, PhPlus, PhImage, PhCalendarBlank, PhMapPin, PhPencilSimple, PhTrash },
  data() {
    return {
      events: [],
      brokenImages: {},
      errorMessage: '',
    }
  },
  mounted() {
    this.fetchEvents()
  },
  methods: {
    fetchEvents() {
      AdminEventService.getAdminEvents()
        .then((res) => {
          this.events = res.data
        })
        .catch(() => {
          this.errorMessage = 'Sündmuste laadimine ebaõnnestus'
        })
    },
    deleteEvent(eventId) {
      AdminEventService.deleteEvent(eventId)
        .then(() => {
          this.events = this.events.filter((e) => e.eventId !== eventId)
        })
        .catch(() => {
          this.errorMessage = 'Sündmuse kustutamine ebaõnnestus'
        })
    },
    getEventImage(name) {
      return new URL(`../assets/${name}.png`, import.meta.url).href
    },
  },
}
</script>
