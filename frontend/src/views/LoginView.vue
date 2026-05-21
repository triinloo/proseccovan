<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-3">
        <h4 class="mb-5">Logi sisse / registreeri</h4>
        <AlertError :error-message="errorMessage" />
        <div class="form-floating mb-3">
          <input
            id="email"
            v-model="email"
            type="email"
            class="form-control"
            placeholder=" "
          />
          <label for="email">E-post</label>
        </div>
        <div class="form-floating mb-3">
          <input
            id="password"
            v-model="password"
            type="password"
            class="form-control"
            placeholder=" "
          />
          <label for="password">Parool</label>
        </div>
        <div class="d-flex gap-2">
          <button class="btn btn-dark w-100" @click="login">Logi sisse</button>
          <button class="btn btn-outline-secondary w-100" @click="$router.push('/register')">Pole kontot? Registreeri</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import LoginService from '@/api-services/LoginService.js'
import { useAuthStore } from '@/auth/AuthService.js'

export default {
  name: 'LoginView',
  components: { AlertError },
  data() {
    return {
      email: '',
      password: '',
      errorMessage: '',
    }
  },
  methods: {
    login() {
      this.errorMessage = ''
      LoginService.login(this.email, this.password)
        .then((response) => {
          const authStore = useAuthStore()
          authStore.setAuth(response.data.userId, response.data.roleName)
          this.$router.push('/')
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message ?? 'Sisselogimine ebaõnnestus'
        })
    },
  },
}
</script>
