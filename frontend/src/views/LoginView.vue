<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-3">
        <h3 class="mb-3">Logi sisse / registreeri</h3>
        <AlertError :error-message="errorMessage" />
        <div class="form-floating mb-3">
          <input
          v-model="email"
          type="email">
        </div>
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
