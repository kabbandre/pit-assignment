<template>
  <b-row>
    <b-col cols="6">
      <b-row>
        <b-col cols="12">
          <b-card border-variant="info" no-body style="border-radius: 20px; overflow: hidden">
            <b-img :src="entry.image" style="width: 100%; height: 300px; object-fit: cover" />
          </b-card>
        </b-col>
        <b-col class="text-right pt-3">
          <b-btn variant="info" size="sm" @click="copyAsciiArt()">
            Saglabāt starpliktuvē
          </b-btn>
        </b-col>
      </b-row>
    </b-col>
    <b-col cols="6">
      <b-card-title>Nosaukums: {{ entry.title }}</b-card-title>
      <b-card-sub-title class="mt-3">
        Filtrs: {{ entry.filterId === 2 ? 'Braila raksts' : 'ASCII' }}
      </b-card-sub-title>
      <b-card-sub-title class="my-3">
        Izveidots: {{ new Date(entry.createdAt).toLocaleString() }}
      </b-card-sub-title>
      <b-btn variant="info" block @click="$bvModal.show(`bv-modal-${entry._id}`)">
        Paskatīties
      </b-btn>
      <b-modal :id="`bv-modal-${entry._id}`" size="xl" hide-header hide-footer>
        <div class="d-block text-center">
          <b-textarea style="text-align: center;" rows="32" readonly :value="entry.processedImage" />
        </div>
        <b-button class="mt-3" variant="info" block @click="copyAsciiArt">
          Saglabāt
        </b-button>
      </b-modal>
    </b-col>
  </b-row>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'nuxt-property-decorator'

interface Image {
  _id: string
  image: string
  title: string
  filterId: number
  createdAt: string
  processedImage: string
}

@Component
export default class GalleryPage extends Vue {
  @Prop() readonly entry!: Image

  copyAsciiArt () {
    this.$copyText(this.entry.processedImage).then(() => {
      alert('Saglabāts starpliktuvē')
    }).catch((e) => {})
  }
}
</script>
