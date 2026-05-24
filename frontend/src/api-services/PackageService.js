import axios from 'axios'

export default {
  getPackages() {
    return axios.get('/api/packages')
  },
}