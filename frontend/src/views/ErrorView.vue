<script>
import AlertError from '@/components/alerts/AlertError.vue'

export default {
  name: 'ErrorView',
  components: { AlertError },
}
</script>

<template>
  <div class="d-flex flex-column align-items-center pt-3">
    <AlertError error-message="Ups! Midagi läks valesti" />

    <div class="glass-wrapper mt-4">
      <img src="@/assets/Broken.png" alt="Katki klaas" class="broken-glass" />
    </div>
  </div>
</template>

<style scoped>
.glass-wrapper {
  display: flex;
  justify-content: center;
}

.broken-glass {
  width: 150px;
}
</style>
