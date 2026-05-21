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
        <RouterLink class="nav-link" to="/booking-form">Broneeri</RouterLink>
        <RouterLink class="nav-link" to="/events">Sündmused</RouterLink>
      </div>
      <div>
        <RouterLink v-if="!authStore.userId" class="btn btn-outline-dark" to="/login">Logi sisse / registreeri</RouterLink>
        <button v-else class="btn btn-outline-dark" @click="logout">Logi välja</button>
      </div>
    </div>
  </nav>

  <RouterView />
</template>

<script>
import { AuthService } from '@/auth/AuthService.js'

export default {
  name: 'App',
  setup() {
    const authStore = AuthService()
    return { authStore }
  },
  methods: {
    logout() {
      this.authStore.clearAuth()
      this.$router.push('/login')
    },
  },
}
</script>

<style></style>
