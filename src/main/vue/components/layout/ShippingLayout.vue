<!-- 
  Copyright (C) 2018 DENTSU SOKEN INC. All Rights Reserved.
  
  Unless you have purchased a commercial license,
  the following license terms apply:
  
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.
  
  You should have received a copy of the GNU Affero General Public License
  along with this program. If not, see &lt;https://www.gnu.org/licenses/&gt;.
-->

<template>
  <div class="container">
    <div class="row">
      <div class="col-12 d-block d-md-none pt-5">
        <p class="h4">{{ $t('samples.ec01.layout.shipping.title') }}</p>
      </div>
      <div class="col-12 d-block d-md-none bg-light pt-3 pr-4 clearfix">
        <button
          id="toggle-btn"
          class="border-0 bg-transparent float-left"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#cartTab"
          aria-controls="cartTab"
          aria-expanded="false"
          aria-label="Toggle"
          @click="toggleCart()"
        >
          <span class="oi oi-cart"></span>
          {{ $t('samples.ec01.layout.shipping.viewCart') }}
          <span
            :class="{ oi: true, 'oi-caret-bottom': !expanded, 'oi-caret-top': expanded }"
          ></span>
        </button>
        <p class="float-end">{{ totalPrice }}{{ $t('samples.ec01.all.yen') }}</p>
      </div>
      <div
        id="cartTab"
        class="col-12 col-md-6 bg-light collapse d-md-block offset-md-6 pt-md-5 custom-form-right-container"
      >
        <div>
          <table class="table custom-cart-table">
            <tbody>
              <tr v-for="item in cartItems" :key="item.productId">
                <td scope="row">
                  <div style="width: 80px; position: relative">
                    <img
                      :src="imgUrl(productMap[item.productId].productImg)"
                      class="rounded"
                      :alt="productMap[item.productId].name"
                    />
                    <span class="badge badge-dark badge-pill">{{ item.value }}</span>
                  </div>
                </td>
                <td>
                  <p>{{ productMap[item.productId].name }}</p>
                  <p class="cate">{{ productMap[item.productId].category.name }}</p>
                </td>
                <td class="w-25 text-right">
                  {{ productMap[item.productId].price * item.value
                  }}{{ $t('samples.ec01.all.yen') }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div>
          <table class="table custom-total-table">
            <tbody>
              <!-- 商品合計 -->
              <tr>
                <td scope="row" class="w-25 text-left">
                  {{ $t('samples.ec01.layout.shipping.subtotal') }}
                </td>
                <td class="w-75">{{ totalPrice }}{{ $t('samples.ec01.all.yen') }}</td>
              </tr>
              <tr>
                <td scope="row" class="w-25 text-left border-top-0">
                  {{ $t('samples.ec01.layout.shipping.fee') }}
                </td>
                <td class="w-75 border-top-0">--</td>
              </tr>
              <tr>
                <td scope="row" class="w-25 text-left h4">
                  {{ $t('samples.ec01.layout.shipping.total') }}
                </td>
                <td class="w-75 fw-bold h4">{{ totalPrice }}{{ $t('samples.ec01.all.yen') }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="col-12 col-md-6 pt-4 custom-form-left-container">
        <RouterView />
        <div class="row layout-footer shipping-layout-footer">
          <div class="col-12 text-light text-left bg-dark">
            Copyright（c）2018 MTP Demo Store all rights reserved.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import '../../styles/open-iconic-bootstrap.min.css'
import '../../styles/bookstore.css'
import { Consts } from '../../mixins/Consts'
import mitt from 'mitt'

// Event Busの設定
const emitter = mitt()

export default {
  name: 'ShippingLayout',
  mixins: [Consts],
  data() {
    return {
      expanded: false,
      cartBean: {
        cartItems: []
      },
      productMap: {}
    }
  },
  computed: {
    totalPrice() {
      return this.cartBean === undefined ? '' : this.cartBean.totalPrice
    },
    cartItems() {
      return this.cartBean === undefined ? [] : this.cartBean.cartItems
    }
  },
  created() {
    emitter.on('confirmShippingInfo.order.success', () => {
      this.cartBean = undefined
    })
    this.loadContent()
  },
  methods: {
    loadContent() {
      var url = this.apiShippingLayout()
      this.$http.get(url).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.cartBean = commandResult.cartBean
          this.productMap = commandResult.productMap
        }
      })
    },
    toggleCart() {
      this.expanded = !this.expanded
    }
  }
}
</script>

<style></style>
