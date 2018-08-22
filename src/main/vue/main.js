require('es6-promise/auto');
/*
 * Copyright (C) 2018 INFORMATION SERVICES INTERNATIONAL - DENTSU, LTD. All Rights Reserved.
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
import Vue from 'vue'
import VueI18n from 'vue-i18n'
import VueRouter from 'vue-router'
import axios from 'axios'
import Messages from "./scripts/iplass-wtp-messages"
import {routes} from "./scripts/routes"

// 外部引数
// tcPath: テナントコンテキストパス
// lang: 言語設定

// Event Busの設定
Vue.prototype.$bus = new Vue();
// Axiosの設定
Vue.prototype.$http = axios.create({
  //  contentType: 'application/json',
    headers: {'X-Requested-With': 'XMLHttpRequest'}
});
// 言語の設定
Vue.use(VueI18n);
const i18n = new VueI18n({
  locale: lang, 
  messages: Messages
});
// ルーターの設定
Vue.use(VueRouter);
const router = new VueRouter({
  routes
});
// ルートナビゲーション中にエラーが検出されたときに呼び出されるコールバック
router.onError((error) => {
  router.replace({name: 'genericError', params: {'exception': error.message}});
});
const app = new Vue({
  el: '#app',
  i18n: i18n,
  router: router,
  methods: {
    setupAxiosErrorInterceptors: function() {
      this.$http.interceptors.response.use((response) => {
        return response;
      }, (error) => {
        console.log(error);
        var errorResult = error.response.data;
        if (errorResult.exceptionType != null) {
          var exception = errorResult.exceptionType;
          this.$router.push({name: 'genericError', params: {'exception': exception}});
        }
        return Promise.reject(error);
      })
    }
  },
  created: function() {
    // axiosにインターセプターを設定
    this.setupAxiosErrorInterceptors();
  }
});