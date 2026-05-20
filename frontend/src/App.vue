<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom px-3">
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
        <RouterLink class="btn btn-outline-dark" to="/login">Logi sisse / registreeru</RouterLink>
      </div>
    </div>
  </nav>


  <RouterView />
</template>

<script>
export default {
  name: 'App',
}
</script>

<style></style>
