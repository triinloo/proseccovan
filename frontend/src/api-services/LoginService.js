import axios from 'axios'

export default {
  login(email, password) {
    return axios.post('/api/login', { email, password })
  },
}
