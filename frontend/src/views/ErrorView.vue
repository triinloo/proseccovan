<script>
export default {
  name: 'ErrorView',
}
</script>

<template>
  <div class="d-flex flex-column align-items-center pt-5">
    <div class="error-banner rounded px-5 py-4 mb-5">
      <h1 class="mb-0">Ups! Midagi läks valesti</h1>
    </div>

    <div class="glass-wrapper">
      <img src="@/assets/Broken.png" alt="Katki klaas" class="broken-glass" />
    </div>
  </div>
</template>

<style scoped>
.error-banner {
  background-color: #d4888a;
  color: #2c1f1f;
  width: 60%;
  text-align: center;
}

.glass-wrapper {
  display: flex;
  justify-content: center;
}

.broken-glass {
  width: 220px;
}
</style>
