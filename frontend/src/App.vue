<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
    <RouterLink class="navbar-brand" to="/">Prosecco Van</RouterLink>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-center" id="navMenu">
      <div class="navbar-nav">
        <RouterLink class="nav-link" to="/">Avaleht</RouterLink>
        <RouterLink class="nav-link" to="/about">Meist</RouterLink>
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

<style>

</style>