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
          <b-btn variant="info" size="sm">
            Saglabāt starpliktuvē
          </b-btn>
        </b-col>
      </b-row>
    </b-col>
    <b-col cols="6">
      <b-card-title>Nosaukums: {{ entry.title }}</b-card-title>
      <b-card-sub-title class="mt-3">Filtrs: {{ entry.filterId }}</b-card-sub-title>
      <b-card-sub-title class="my-3">Izveidots: {{ entry.createdAt }}</b-card-sub-title>
      <b-btn :to="`/${entry.id}`" variant="info" block>
        Paskatīties
      </b-btn>
    </b-col>
  </b-row>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'nuxt-property-decorator'

interface Image {
  id: string
  image: string
  title: string
  filterId: number
  createdAt: string
}

@Component
export default class GalleryPage extends Vue {
  @Prop() readonly entry!: Image
}
</script>
