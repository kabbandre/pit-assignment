export const state = {
  images: [],
  image: {}
}
export const mutations = {
  setImages: (state, payload) => {
    state.images = payload
  },
  setImage: (state, payload) => {
    state.image = payload
  }
}
export const actions = {
  getAllImages ({ commit }) {
    return new Promise((resolve, reject) => {
      this.$axios.get('/').then((r) => {
        commit('setImages', r.data)
        resolve(r)
      }).catch((e) => {
        reject(e)
      })
    })
  },
  getOneImage ({ commit }, id) {
    return new Promise((resolve, reject) => {
      this.$axios.get(`/${id}`).then((r) => {
        commit('setImage', r.data)
        resolve(r)
      }).catch((e) => {
        reject(e)
      })
    })
  },
  saveImage (_, image) {
    return new Promise((resolve, reject) => {
      this.$axios.post('/save', image).then((r) => {
        resolve(r)
      }).catch((e) => {
        reject(e)
      })
    })
  }
}
