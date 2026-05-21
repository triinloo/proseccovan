<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-4">
        <h4 class="mb-4">Loo konto</h4>
        <AlertError :error-message="errorMessage" />

        <div class="mb-3 text-start">
          <label class="form-label">Ees- ja perekonnanimi</label>
          <input
            v-model="customerName"
            type="text"
            class="form-control"
            placeholder="Mari Maasikas"
          />
        </div>

        <div class="mb-3 text-start">
          <label class="form-label">E-mail</label>
          <input
            v-model="email"
            type="email"
            class="form-control"
            placeholder="Sisesta oma e-mail"
          />
        </div>

        <div class="mb-3 text-start">
          <label class="form-label">Parool</label>
          <input v-model="password" type="password" class="form-control" />
        </div>

        <div class="mb-4 text-start">
          <label class="form-label">Korda parooli</label>
          <input v-model="confirmPassword" type="password" class="form-control" />
        </div>
        <button @click="register" class="btn btn-dark d-block px-5">Registreeru</button>
      </div>
    </div>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import RegisterService from '@/api-services/RegisterService.js'

export default {
  name: 'RegisterView',
  components: { AlertError },
  data() {
    return {
      customerName: '',
      email: '',
      password: '',
      confirmPassword: '',
      errorMessage: '',
    }
  },
  methods: {
    register() {
      if (this.customerName === '' || this.email === '' || this.password === '' || this.confirmPassword === '') {
        this.errorMessage = 'Täida kõik väljad'
        return
      }
      if (this.password !== this.confirmPassword) {
        this.errorMessage = 'Paroolid ei kattu'
        return
      }
      RegisterService.register(this.customerName, this.email, this.password)
        .then(() => {
          this.$router.push('/login')
        })
        .catch((error) => {
          this.errorMessage = error.response?.data?.message || 'Registreerimine ebaõnnestus'
        })
    },
  },
}
</script>
