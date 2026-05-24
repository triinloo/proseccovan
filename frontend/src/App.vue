<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom px-3 mb-5">
    <RouterLink class="navbar-brand" to="/">Prosecco Van</RouterLink>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navMenu"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navMenu">
      <div class="navbar-nav mx-auto">
        <RouterLink v-if="role === 'ADMIN'" class="nav-link" to="/admin-bookings">Broneeringud</RouterLink>
        <RouterLink v-else class="nav-link" to="/booking-form">Broneeri</RouterLink>
        <RouterLink class="nav-link" :to="role === 'ADMIN' ? '/admin-events' : '/events'">Sündmused</RouterLink>
      </div>
      <div class="d-flex align-items-center gap-2">
        <RouterLink v-if="!userId" class="btn btn-outline-dark" to="/login">Logi sisse / registreeri</RouterLink>
        <template v-else>
          <RouterLink v-if="role !== 'ADMIN'" to="/customer-bookings" class="btn btn-outline-dark d-flex align-items-center">
            <PhUser :size="20" />
          </RouterLink>
          <button class="btn btn-outline-dark" @click="logout">Logi välja</button>
        </template>
      </div>
    </div>
  </nav>

  <RouterView />
</template>

<script>
import { mapState, mapActions } from 'pinia'
import { AuthService } from '@/auth/AuthService.js'
import { PhUser } from '@phosphor-icons/vue'

export default {
  name: 'App',
  components: { PhUser },
  computed: {
    ...mapState(AuthService, ['role', 'userId']),
  },
  methods: {
    ...mapActions(AuthService, ['clearAuth']),
    logout() {
      this.clearAuth()
      this.$router.push('/login')
    },
  },
}
</script>

<style></style>
