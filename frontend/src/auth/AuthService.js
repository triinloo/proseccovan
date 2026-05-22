import { defineStore } from 'pinia'
import { ref } from 'vue'

export const AuthService = defineStore('auth', () => {
  const userId = ref(localStorage.getItem('userId') ? Number(localStorage.getItem('userId')) : null)
  const role = ref(localStorage.getItem('role') || null)

  function setAuth(newUserId, newRole) {
    userId.value = newUserId
    role.value = newRole
    localStorage.setItem('userId', newUserId)
    localStorage.setItem('role', newRole)
  }

  function clearAuth() {
    userId.value = null
    role.value = null
    localStorage.removeItem('userId')
    localStorage.removeItem('role')
  }

  return { userId, role, setAuth, clearAuth }
})
