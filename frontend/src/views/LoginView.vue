<template>
  <div class="container text-center">
    <div class="col col-4">
      <h2 class="mb-4">Logi sisse/registreeri</h2>
      <div class="col col-6">
        <AlertError :error-message="errorMessage" />
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'

export default {
  name: 'LoginView',
  components: { AlertError },
}
</script>
