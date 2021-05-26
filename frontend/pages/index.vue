<template>
  <b-row>
    <b-col cols="12">
      <b-btn to="/create" variant="info" block>
        Izveidot savu
      </b-btn>
    </b-col>
    <b-col v-for="entry in gallery" :key="entry.title" class="pt-4" cols="12">
      <gallery-entry :entry="entry" />
      <b-card class="mt-4" bg-variant="info" border-variant="info" style="height: 3px; width: 100%" />
    </b-col>
  </b-row>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'
import GalleryEntry from '~/components/GalleryEntry.vue'

interface Image {
  image: string
  title: string
  filterId: number
  createdAt: string
}

@Component({
  components: { GalleryEntry }
})
export default class GalleryPage extends Vue {
  get gallery (): Image[] {
    return this.$store.state.api.images
  }

  async mounted () {
    await this.$store.dispatch('api/getAllImages')
  }
}
</script>
