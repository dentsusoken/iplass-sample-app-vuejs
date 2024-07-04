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
  <div class="col-sm-12 col-md-9">
    <div class="row">
      <div class="col-12">
        <div class="border-top"></div>
        <nav class="breadcrumb all-breadcrumb">
          <router-link class="breadcrumb-item text-primary" :to="{ name: 'top' }">
            {{ $t('samples.ec01.all.breadcrumb.home') }}
          </router-link>
          <span class="breadcrumb-item active">{{ $t('samples.ec01.cart.info.title') }}</span>
        </nav>
      </div>
    </div>
    <div v-if="!cartBean || cartBean.cartItems.length == 0" class="row cart-table">
      <div class="col-12 text-left">
        <p>{{ $t('samples.ec01.cart.info.emptyMsg.p1') }}</p>
        <p>
          {{ $t('samples.ec01.cart.info.emptyMsg.p2') }}
          <router-link class="text-dark" :to="{ name: 'top' }">
            {{ $t('samples.ec01.cart.info.emptyMsg.p3') }}
          </router-link>
        </p>
      </div>
    </div>
    <form v-else id="cartInfoForm">
      <div class="row cart-table">
        <div class="col-12 d-none d-md-block">
          <div class="row">
            <div class="col-md-3 fw-bold text-center">
              {{ $t('samples.ec01.cart.info.product') }}
            </div>
            <div class="col-md-3 fw-bold"></div>
            <div class="col-md-2 fw-bold">{{ $t('samples.ec01.cart.info.price') }}</div>
            <div class="col-md-2 fw-bold">{{ $t('samples.ec01.cart.info.quantity') }}</div>
            <div class="col-md-2 fw-bold">{{ $t('samples.ec01.cart.info.subtotal') }}</div>
          </div>
        </div>
        <div class="col-12">
          <!--cart item info start-->
          <div v-for="(item, i) in cartBean.cartItems" :key="item.productId" class="row mb-4">
            <div class="col-12 col-md-3 text-center text-md-start cart-table-item">
              <div class="w-100">
                <router-link
                  class="img-fluid"
                  :to="{ name: 'detail', query: { productId: item.productId } }"
                >
                  <img
                    :src="imgUrl(productMap[item.productId].productImg)"
                    alt=""
                    class="img-thumbnail w-100"
                  />
                </router-link>
              </div>
            </div>
            <div class="col-12 col-md-3 text-center text-md-start cart-table-item">
              <div class="w-100">
                <h6>{{ productMap[item.productId].name }}</h6>
                <p>
                  <a
                    class="text-dark"
                    href="#"
                    role="button"
                    @click.prevent="deleteCartItem(item.productId)"
                  >
                    {{ $t('samples.ec01.cart.info.remove') }}
                  </a>
                </p>
              </div>
            </div>
            <div class="col-3 d-block d-md-none cart-table-item">
              <p class="w-100 fw-bold">{{ $t('samples.ec01.cart.info.price') }}</p>
            </div>
            <div class="col-8 col-md-2 cart-table-item">
              <p class="w-100">
                {{ productMap[item.productId].price }}{{ $t('samples.ec01.all.yen') }}
              </p>
            </div>
            <div class="col-3 d-block d-md-none cart-table-item">
              <p class="w-100 fw-bold">{{ $t('samples.ec01.cart.info.quantity') }}</p>
            </div>
            <div class="col-8 col-md-2 cart-table-item clearfix">
              <div class="w-100">
                <input
                  v-model="item.value"
                  type="text"
                  class="form-control float-end"
                  :name="'cartItems[' + i + '].value'"
                  cart-data="value"
                />
              </div>
              <input
                type="hidden"
                :name="'cartItems[' + i + '].productId'"
                :value="item.productId"
                cart-data="productId"
              />
            </div>
            <div class="col-3 d-block d-md-none cart-table-item">
              <p class="w-100 fw-bold">{{ $t('samples.ec01.cart.info.total') }}</p>
            </div>
            <div class="col-8 col-md-2 cart-table-item">
              <p class="w-100">
                {{ productMap[item.productId].price * item.value }}{{ $t('samples.ec01.all.yen') }}
              </p>
            </div>
          </div>
          <hr class="mb-3" />
        </div>
      </div>
      <div class="row mt-3">
        <div class="col-md-12 text-center text-md-end">
          <p class="fw-bold">
            {{ $t('samples.ec01.cart.info.total') }}：&nbsp; {{ cartBean.totalPrice }}
            {{ $t('samples.ec01.all.yen') }}
          </p>
          <button type="button" class="btn btn-outline-dark" @click="updateCart()">
            <span
              class="oi oi-reload"
              :title="$t('samples.ec01.cart.info.refresh')"
              aria-hidden="true"
            />
          </button>
          <router-link class="btn btn-outline-dark" :to="{ name: 'top' }" role="button">
            {{ $t('samples.ec01.cart.info.return') }}
          </router-link>
        </div>
      </div>
    </form>
    <div v-if="cartBean !== undefined && cartBean.cartItems.length > 0" class="row mt-3">
      <div class="col-md-12 text-center text-md-end">
        <router-link class="btn btn-dark btn-lg" :to="{ name: 'login' }" role="button">
          {{ $t('samples.ec01.cart.info.checkOut') }}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { Consts } from '../../mixins/Consts'

export default {
  name: 'CartInfo',
  mixins: [Consts],
  data() {
    return {
      cartBean: {
        cartItems: [],
        totalPrice: ''
      },
      productMap: {}
    }
  },
  created() {
    this.loadContent()
  },
  methods: {
    loadContent() {
      var url = this.apiCartInfo()
      this.$http.get(url).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.cartBean = commandResult.cartBean
          this.productMap = commandResult.productMap
        }
      })
    },
    updateCart() {
      let err = false
      this.cartBean.cartItems.forEach((item) => {
        if (isNaN(item.value) || !/^[1-9]\d*|0$/.test(item.value)) {
          err = true
        }
      })
      if (err) {
        alert(this.$t('samples.ec01.cart.info.notNum'))
        return false
      }
      var url = this.apiUpdateCartInfo()
      var data = this.populateCartUpdateData()
      this.$http.post(url, data).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.cartBean = commandResult.cartBean
          // カートに入れた商品件数の更新
          this.$emitter.emit('cart.totalAmount.refresh', this.cartBean.totalAmount)
        }
      })
    },
    populateCartUpdateData() {
      const data = {}
      this.cartBean.cartItems.forEach((item, i) => {
        data[`cartItems[${i}].productId`] = item.productId
        data[`cartItems[${i}].value`] = item.value
      })
      return data
    },
    deleteCartItem(productId) {
      var url = this.apiDeleteCartInfo(productId)
      var data = { deleteId: productId }
      this.$http.post(url, data).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.cartBean = commandResult.cartBean
          // カートに入れた商品件数の更新
          this.$emitter.emit('cart.totalAmount.refresh', this.cartBean.totalAmount)
        }
      })
    }
  }
}
</script>

<style></style>
