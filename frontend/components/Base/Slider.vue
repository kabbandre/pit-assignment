<template>
  <label style="width: 100%">
    {{ label }}
    <transition name="fade">
      <a v-if="+initValue !== +value" @click="$emit('input', initValue)">
        <b-icon-arrow-clockwise variant="info" />
      </a>
    </transition>

    <input
      :value="value"
      type="range"
      :min="min"
      :max="max"
      class="custom-range"
      @input="$emit('input', $event.target.value)"
    >
  </label>
</template>

<script>
import { BIconArrowClockwise } from 'bootstrap-vue'
export default {
  components: {
    BIconArrowClockwise
  },
  props: {
    label: {
      type: String,
      default: ''
    },
    initValue: { type: Number, default: 50 },
    value: {
      type: [String, Number],
      default: '50'
    },
    min: {
      type: String,
      default: '0'
    },
    max: {
      type: String,
      default: '100'
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
