<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-4">
        <h4 class="mb-4">Logi sisse / registreeri</h4>
        <AlertError :error-message="errorMessage" />
        <div class="card">
          <div class="card-body p-4 text-start">
            <div class="mb-4">
              <label for="email" class="form-label">E-mail</label>
              <input
                id="email"
                v-model="email"
                type="email"
                :class="['form-control', { 'is-invalid': errorMessage }]"
                placeholder="Sisesta e-mail"
              />
            </div>
            <div class="mb-4">
              <label for="password" class="form-label">Parool</label>
              <input
                id="password"
                v-model="password"
                type="password"
                :class="['form-control', { 'is-invalid': errorMessage }]"
                placeholder="Sisesta parool"
              />
            </div>
            <div class="d-flex gap-2 mt-4">
              <button class="btn btn-dark w-100" @click="login">Sisene</button>
              <button class="btn btn-outline-dark w-100" @click="$router.push('/register')">Pole kontot? Registreeri</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import LoginService from '@/api-services/LoginService.js'
import { AuthService } from '@/auth/AuthService.js'

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
      if (this.email === '' || this.password === '') {
        this.errorMessage = 'Täida kõik väljad'
        return
      }
      LoginService.login(this.email, this.password)
        .then((response) => {
          const authStore = AuthService()
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

<style scoped>
.form-control.is-invalid {
  background-image: none;
}
</style>
