import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
    },
    {
      path: '/customer-bookings',
      name: 'customer-bookings',
      component: () => import('@/views/CustomerBookingsView.vue'),
    },
    {
      path: '/customer-booking/:bookingId',
      name: 'customer-booking',
      component: () => import('@/views/CustomerBookingView.vue'),
    },
    {
      path: '/admin-bookings',
      name: 'admin-bookings',
      component: () => import('../views/AdminBookingsView.vue'),
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
