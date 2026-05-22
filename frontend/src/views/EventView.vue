<template>
  <div class="container py-4">
    <h4 class="text-center mb-4">Sündmused</h4>

    <div class="mb-4">
      <label class="form-label small">Toimumise aeg</label>
      <select class="form-select w-auto" v-model="selectedSeason" @change="fetchEvents">
        <option value="Kõik">Kõik</option>
        <option value="SUVI">Suvi</option>
        <option value="SÜGIS">Sügis</option>
        <option value="KEVAD">Kevad</option>
        <option value="TALV">Talv</option>
      </select>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
      <div class="col" v-for="event in events" :key="event.eventId">
        <div class="card h-100">
          <img
            v-if="!brokenImages[event.eventName]"
            :src="getEventImage(event.eventName)"
            class="card-img-top"
            :alt="event.eventName"
            style="height: 200px; object-fit: cover"
            @error="brokenImages[event.eventName] = true"
          />
          <div v-else class="bg-light d-flex align-items-center justify-content-center" style="height: 200px">
            <ph-image :size="48" color="#aaa" />
          </div>
          <div class="card-body">
            <h6 class="card-title fw-bold">{{ event.eventName }}</h6>
            <p class="card-text text-muted small">{{ event.eventDescription }}</p>
            <p class="mb-1 small">
              <ph-calendar-blank :size="16" class="me-1" />
              {{ event.eventStartDate }}
            </p>
            <p class="mb-0 small">
              <ph-map-pin :size="16" class="me-1" />
              {{ event.eventLocation }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import EventService from '@/api-services/EventService.js'
import { PhCalendarBlank, PhMapPin, PhImage } from '@phosphor-icons/vue'

export default {
  name: 'EventsView',
  components: { PhCalendarBlank, PhMapPin, PhImage },
  data() {
    return {
      events: [],
      selectedSeason: 'Kõik',
      brokenImages: {},
    }
  },
  mounted() {
    this.fetchEvents()
  },
  methods: {
    async fetchEvents() {
      const response = await EventService.getEvents(this.selectedSeason)
      this.events = response.data
    },
    getEventImage(name) {
      return new URL(`../assets/${name}.png`, import.meta.url).href
    },
  },
}
</script>
