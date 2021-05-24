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
  image = ''

  async created () {
    const id = this.$route.params.id
    if (this.$config.objectIdRegExp.test(id)) {
      this.image = id

      // await this.$axios.get(`/gallery/${id}`).then((r) => {
      //   this.image = r.data
      // }).catch((e) => {
      //   this.$router.push('/')
      //   alert(e)
      // })
    } else {
      await this.$router.push('/')
      alert('Invalid image ID')
    }
  }
}
</script>
