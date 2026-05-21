<template>
  <div v-if="errorMessage" class="alert alert-danger d-flex align-items-center" role="alert">
  {{errorMessage}}
  </div>
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
