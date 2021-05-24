<template>
  <b-form-row>
    <b-col cols="9">
      <b-row>
        <b-col cols="6">
          <label style="width: 100%">
            Fails <base-required />
            <b-form-file v-model="image.src" accept="image/*" />
          </label>
        </b-col>
        <b-col cols="6">
          <label style="width: 100%">
            Garums <base-required />
            <b-form-input v-model="image.width" type="number" />
          </label>
        </b-col>
        <b-col cols="6">
          <base-slider v-model="image.processing.contrast" init-value="100" label="Kontrasts" />
        </b-col>
        <b-col cols="6">
          <base-slider v-model="image.processing.brightness" init-value="100" max="1000" label="Spilgtums" />
        </b-col>
        <b-col cols="6">
          <base-slider v-model="image.processing.hueRotate" init-value="0" max="360" label="Krāsa" />
        </b-col>
        <b-col cols="6">
          <base-slider v-model="image.processing.saturate" init-value="100" label="Piesātinājums" />
        </b-col>
        <b-col cols="6">
          <base-slider v-model="image.processing.blur" init-value="0" label="Blur" />
        </b-col>
        <b-col cols="6">
          <base-slider
            v-model="image.processing.grayscale"
            init-value="0"
            label="Pelēktons"
          />
        </b-col>
        <b-col cols="6">
          <base-slider
            v-model="image.processing.invert"
            init-value="0"
            label="Invertēt"
          />
        </b-col>
        <b-col align-self="flex-end" cols="6">
          <label style="width: 100%">
            Filtrs <base-required />
            <b-form-select v-model="image.filterId" placeholder="Filtrs">
              <b-form-select-option value="1">
                ASCII
              </b-form-select-option>
              <b-form-select-option value="2">
                Braila raksts
              </b-form-select-option>
            </b-form-select>
          </label>
        </b-col>
        <b-col align-self="flex-end" cols="6">
          <b-btn class="mt-4" variant="info" block>
            Augšupielādēt
          </b-btn>
        </b-col>
      </b-row>
    </b-col>
    <b-col cols="3">
      <b-card border-variant="info" bg-variant="info" no-body style="border-radius: 20px; overflow: hidden">
        <imagvue
          v-model="imageSrc"
          v-bind="image.processing"
          style="width: 100%; height: 250px; object-fit: cover"
        />
      </b-card>
    </b-col>
  </b-form-row>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'nuxt-property-decorator'
import BaseSlider from '@/components/Base/Slider.vue'
import BaseRequired from '@/components/Base/Required.vue'
// @ts-ignore
import imagvue from 'imagvue'

@Component({
  components: {
    BaseSlider,
    BaseRequired,
    imagvue
  }
})
export default class CreateUnicodeImagePage extends Vue {
  image = {
    src: null,
    width: null,
    processing: {
      contrast: '100',
      brightness: '100',
      hueRotate: '0',
      saturate: '100',
      blur: '0',
      grayscale: '0',
      invert: '0'
    },
    filterId: null
  }

  imageSrc: any = null

  @Watch('image.src')
  async onImageChange (newValue: File | null) {
    this.imageSrc = '/loading.gif'

    if (newValue) {
      await this.imageToBase64(newValue).then((r) => {
        this.imageSrc = r
      }).catch(() => {
        alert('Could not load this image')
        this.imageSrc = ''
      })
    } else {
      this.imageSrc = ''
    }
  }

  imageToBase64 (file: File): Promise<string | ArrayBuffer | null> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = () => resolve(reader.result)
      reader.onerror = error => reject(error)
    })
  }
}
</script>
