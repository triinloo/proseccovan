import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegisterView from '@/views/RegisterView.vue'
import CustomerBookingsView from '@/views/CustomerBookingsView.vue'
import CustomerBookingView from '@/views/CustomerBookingView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
    },
    {
      path: '/customer-bookings',
      name: 'customer-bookings',
      component: CustomerBookingsView,
    },

    { path: '/customer-booking/:bookingId',
      name: 'customer-booking',
      component: CustomerBookingView
    },

    {
      path: '/booking-form',
      name: 'booking-form',
      component: () => import('../views/BookingFormView.vue'),
    },
    {
      path: '/events',
      name: 'events',
      component: () => import('../views/EventView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/error',
      name: 'error',
      component: () => import('../views/ErrorView.vue'),
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/error',
    },
  ],
})

export default router
