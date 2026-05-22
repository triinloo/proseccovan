<template>
  <div :id="modalId" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Asukoht kaardil</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" />
        </div>
        <div class="modal-body p-0">
          <div v-if="interactive" class="text-muted small px-3 pt-2 pb-1">
            Klõpsa kaardil asukoha valimiseks
          </div>
          <div :id="mapId" style="height: 450px;" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import markerIcon from 'leaflet/dist/images/marker-icon.png'
import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png'
import markerShadow from 'leaflet/dist/images/marker-shadow.png'

delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconUrl: markerIcon,
  iconRetinaUrl: markerIcon2x,
  shadowUrl: markerShadow,
})

const ESTONIA_CENTER = [58.5953, 25.0136]

export default {
  name: 'MapModal',
  props: {
    latitude: { type: String, default: null },
    longitude: { type: String, default: null },
    modalId: { type: String, default: 'mapModal' },
    interactive: { type: Boolean, default: false },
  },
  emits: ['location-selected'],
  computed: {
    mapId() {
      return `map-${this.modalId}`
    },
    hasCoords() {
      return this.latitude && this.longitude
    },
  },
  mounted() {
    const modalEl = document.getElementById(this.modalId)
    modalEl.addEventListener('shown.bs.modal', () => {
      if (!this.map) {
        const center = this.hasCoords
          ? [parseFloat(this.latitude), parseFloat(this.longitude)]
          : ESTONIA_CENTER
        const zoom = this.hasCoords ? 13 : 7

        this.map = L.map(this.mapId).setView(center, zoom)
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '© OpenStreetMap',
        }).addTo(this.map)

        if (this.hasCoords) {
          this.marker = L.marker(center).addTo(this.map)
        }

        if (this.interactive) {
          this.map.on('click', (e) => {
            const { lat, lng } = e.latlng
            if (this.marker) {
              this.marker.setLatLng([lat, lng])
            } else {
              this.marker = L.marker([lat, lng]).addTo(this.map)
            }
            this.$emit('location-selected', {
              latitude: String(lat),
              longitude: String(lng),
            })
          })
        }
      } else {
        this.map.invalidateSize()
        if (this.hasCoords && this.marker) {
          this.marker.setLatLng([parseFloat(this.latitude), parseFloat(this.longitude)])
          this.map.setView([parseFloat(this.latitude), parseFloat(this.longitude)], 13)
        }
      }
    })
  },
  beforeUnmount() {
    if (this.map) {
      this.map.remove()
      this.map = null
    }
  },
}
</script>