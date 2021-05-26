<template>
  <b-row>
    <b-col>
      {{ image }} is selected
    </b-col>
  </b-row>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'

@Component
export default class UnicodeImagePage extends Vue {
  get image (): any {
    return this.$store.state.api.image
  }

  async created () {
    const id = this.$route.params.id
    if (this.$config.objectIdRegExp.test(id)) {
      await this.$store.dispatch('api/getOneImage', id).catch((e) => {
        this.$router.push('/')
        alert(e)
      })
    } else {
      await this.$router.push('/')
      alert('Invalid image ID')
    }
  }
}
</script>
