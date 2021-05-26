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
        <!--        <b-col cols="6">-->
        <!--          <base-slider v-model="image.processing.contrast" init-value="100" label="Kontrasts" />-->
        <!--        </b-col>-->
        <!--        <b-col cols="6">-->
        <!--          <base-slider v-model="image.processing.brightness" init-value="100" max="1000" label="Spilgtums" />-->
        <!--        </b-col>-->
        <!--        <b-col cols="6">-->
        <!--          <base-slider v-model="image.processing.hueRotate" init-value="0" max="360" label="Krāsa" />-->
        <!--        </b-col>-->
        <!--        <b-col cols="6">-->
        <!--          <base-slider v-model="image.processing.saturate" init-value="100" label="Piesātinājums" />-->
        <!--        </b-col>-->
        <!--        <b-col cols="6">-->
        <!--          <base-slider v-model="image.processing.blur" init-value="0" label="Blur" />-->
        <!--        </b-col>-->
        <!--        <b-col cols="6">-->
        <!--          <base-slider-->
        <!--            v-model="image.processing.grayscale"-->
        <!--            init-value="0"-->
        <!--            label="Pelēktons"-->
        <!--          />-->
        <!--        </b-col>-->
        <!--        <b-col cols="6">-->
        <!--          <base-slider-->
        <!--            v-model="image.processing.invert"-->
        <!--            init-value="0"-->
        <!--            label="Invertēt"-->
        <!--          />-->
        <!--        </b-col>-->
        <b-col cols="6">
          <label style="width: 100%">
            Filtrs <base-required />
            <b-form-select v-model="image.filterId" placeholder="Filtrs">
              <b-form-select-option :value="1">
                ASCII
              </b-form-select-option>
              <b-form-select-option :value="2">
                Braila raksts
              </b-form-select-option>
            </b-form-select>
          </label>
        </b-col>
        <b-col style="margin-top: 18px" cols="6">
          <transition name="fade">
            <template v-if="image.filterId === 2">
              <b-checkbox v-model="image.braille.dither">
                Uztraukums (Dither)
              </b-checkbox>
            </template>
          </transition>
          <transition name="fade">
            <template v-if="image.filterId === 2">
              <b-checkbox v-model="image.braille.invert">
                Invertēt
              </b-checkbox>
            </template>
          </transition>
        </b-col>
        <b-col cols="6">
          <b-btn :disabled="generatingArt || !imageSrc || !image.filterId" class="mt-4" variant="info" block @click="initGenerator(imageSrc, image.filterId)">
            Augšupielādēt
          </b-btn>
        </b-col>
      </b-row>
      <transition name="fade">
        <b-row class="mt-3" v-if="generatedImage">
          <b-col cols="6">
            <label style="width: 100%">
              Nosaukums <base-required />
              <b-form-input v-model="image.title" />
            </label>
          </b-col>
          <b-col cols="6">
            <b-btn :disabled="!image.title" block variant="info" class="mt-4" @click="saveImage">
              Saglabāt
            </b-btn>
          </b-col>
        </b-row>
      </transition>
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
    <b-col class="mt-4" cols="12">
      <b-textarea v-model="generatedImage" rows="40" readonly />
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
    title: '',
    src: null,
    width: 50,
    processing: {
      contrast: '100',
      brightness: '100',
      hueRotate: '0',
      saturate: '100',
      blur: '0',
      grayscale: '0',
      invert: '0'
    },
    filterId: null,
    braille: {
      invert: false,
      dither: false
    },
  }

  imageSrc: any = null
  generatingArt = false

  generatedImage = ''

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

  async initGenerator (imageUrl: string, filterId: number) {
    this.generatingArt = true

    switch (filterId) {
      case 1:
        await this.imageToAscii(imageUrl)
        break
      case 2:
        await this.imageToBraill(imageUrl)
        break
      default:
        break
    }

    this.$copyText(this.generatedImage).then(() => {
      alert('Saglabāts starpliktuvē')
    }).catch((e) => {})

    this.generatingArt = false
  }

  async imageToAscii (imageURL: string) {
    const getAsciiImage = require('get-ascii-image')

    const config = {
      maxWidth: this.image.width
    }

    await getAsciiImage(imageURL, config)
      .then((ascii: string) => {
        // @ts-ignore
        this.generatedImage = ascii
      })
      .catch((err: any) => {
        console.log(err)
      })
  }

  async imageToBraill (imageURL: string) {
    const { braillefy } = require('img2braille-web')

    const asciiOpts = this.image.braille

    this.generatedImage = await braillefy(imageURL, this.image.width, asciiOpts)
  }

  async saveImage () {
    if (confirm('Vai tiešām vēlaties saglabāt šo attēlu datu bāzē?')) {
      const image = {
        title: this.image.title,
        width: this.image.width,
        filterId: this.image.filterId,
        image: this.imageSrc,
        createdAt: new Date(),
        processedImage: this.generatedImage
      }

      await this.$store.dispatch('api/saveImage', image).then(() => {
        this.$router.push('/')
        alert('Saglabāts datu bāzē')
      }).catch((e) => {
        alert('Kļūda')
        console.log(e)
      })
    }
  }
}
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
