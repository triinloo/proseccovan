import axios from "axios";


export default {
  register(customerName, email, password) {
    return axios.post('/api/register', { customerName, email, password })
  },
}

