/*
 * Copyright (C) 2018 DENTSU SOKEN INC. All Rights Reserved.
 *
 * Unless you have purchased a commercial license,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
import { createApp } from 'vue'
import App from './App.vue'
import { createI18n } from 'vue-i18n'
import axios from 'axios'
import Messages from './scripts/iplass-wtp-messages'
import router from './router'
import mitt from 'mitt'

// 外部引数
// tcPath: テナントコンテキストパス
// lang: 言語設定

// Event Busの設定
const emitter = mitt()

// 言語の設定
const i18n = createI18n({
  locale: lang,
  messages: Messages
})

// ルートナビゲーション中にエラーが検出されたときに呼び出されるコールバック
router.onError((error) => {
  router.replace({ name: 'genericError', params: { exception: error.message } })
})

// アプリケーションインスタンスの生成
const app = createApp(App, {
  onExpand() {},
  created() {
    // axiosにインターセプターを設定
    this.setupAxiosErrorInterceptors()
  },
  methods: {
    setupAxiosErrorInterceptors() {
      this.$http.interceptors.response.use(
        (response) => {
          return response
        },
        (error) => {
          console.log(error)
          var errorResult = error.response.data
          if (errorResult.exceptionType != null) {
            var exception = errorResult.exceptionType
            this.$router.push({ name: 'genericError', params: { exception: exception } })
          }
          return Promise.reject(error)
        }
      )
    }
  }
})

app.config.globalProperties.emitter = emitter

// Axiosの設定
app.config.globalProperties.$http = axios.create({
  headers: { 'X-Requested-With': 'XMLHttpRequest' }
})

app.use(i18n)
app.use(router)
app.mount('#app')
