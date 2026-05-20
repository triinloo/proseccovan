<template>
  <main>
    <div class="hero">
      <div class="hero-content text-center">
        <h2>Mullid, naer ja mälestused!</h2>
        <h2>Broneeri ProseccoVan ja tee oma pidu unustumatuks!</h2>
        <h5>Toome parima prosecco otse sinu üritusele.</h5>
        <h5>Ideaalne pulmadeks, pidudeks ja firmaüritusteks.</h5>
        <RouterLink to="/booking-form" class="btn btn-dark mt-3">Broneeri</RouterLink>
      </div>
    </div>
  </main>
</template>

<style scoped>
.hero {
  background-image: url('@/assets/c3b56b3b-ccfc-4a9e-9569-cda335a8aa2e.webp');
  background-size: cover;
  background-position: center;
  min-height: 90vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hero-content {
  background-color: rgba(255, 255, 255, 0.7);
  padding: 2rem;
  border-radius: 1rem;
  max-width: 700px;
}
</style>

<script>
export default {
  name: 'HomeView',
}
</script>
