<template>
  <p v-if="errorMessage" class="text-danger text-start mb-3">
    <span class="circle-icon">!</span> {{ errorMessage }}
  </p>
</template>

<!--<AlertError :error-message="errorMessage" />-->

<script>
export default {
  name: 'AlertError',
  props: {
    errorMessage: String,
  },
}
</script>

<style scoped>
.circle-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 1.1em;
  height: 1.1em;
  border-radius: 2px;
  border: 1.5px solid currentColor;
  font-size: 0.85em;
  font-weight: bold;
}
</style>
