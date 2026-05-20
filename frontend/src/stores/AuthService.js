import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const userId = ref(null)
  const role = ref(null)

  function setAuth(newUserId, newRole) {
    userId.value = newUserId
    role.value = newRole
  }

  function clearAuth() {
    userId.value = null
    role.value = null
  }

  return { userId, role, setAuth, clearAuth }
})