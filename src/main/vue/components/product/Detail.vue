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
          <router-link class="breadcrumb-item text-primary" :to="{ name: 'top' }">{{
            $t('samples.ec01.all.breadcrumb.home')
          }}</router-link>
          <span :id="productId" class="breadcrumb-item active">{{ productInfo.name }}</span>
        </nav>
      </div>
    </div>
    <div class="row text-center text-md-start">
      <div class="col-sm-12 col-md-5">
        <img :src="imgUrl(productInfo.productImg)" class="img-thumbnail w-100" />
      </div>
      <div class="col-sm-12 col-md-7">
        <h4>{{ productInfo.name }}</h4>
        <p class="fw-bold">
          {{ $t('samples.ec01.product.detail.price') }}：
          <span class="all-price">{{ productInfo.price }}{{ $t('samples.ec01.all.yen') }}</span>
          （{{ $t('samples.ec01.product.detail.priceWithoutTax') }} {{ priceWithoutTax
          }}{{ $t('samples.ec01.all.yen') }}）
        </p>
        <!-- eslint-disable-next-line vue/no-v-html -->
        <p v-html="regInfo"></p>
        <!-- 商品サブ情報 -->
        <p class="h5">
          <template v-for="sub in subInfoList" :key="sub.oid">
            <span class="badge badge-info" :title="sub.description">{{ sub.name }}</span
            >&nbsp;
          </template>
        </p>
        <div :class="{ 'd-none': alreadyAdded }">
          <button type="button" class="btn btn-outline-dark my-2" @click="inputCartInfo()">
            {{ $t('samples.ec01.product.detail.addToCart') }}
          </button>
        </div>
        <div cart-data="viewCart" :class="{ 'd-none': !alreadyAdded }">
          <router-link class="btn btn-dark my-2" :to="{ name: 'cartInfo' }">{{
            $t('samples.ec01.product.detail.viewCart')
          }}</router-link>
          <router-link class="btn btn-outline-dark my-2" :to="{ name: 'top' }">{{
            $t('samples.ec01.product.detail.continueShopping')
          }}</router-link>
        </div>
      </div>
    </div>
    <div class="row mt-5 border-bottom">
      <div class="col-12 detail-description">
        <!-- eslint-disable-next-line vue/no-v-html -->
        <p v-html="productInfo.description"></p>
      </div>
    </div>
    <!-- おすすめ -->
    <div class="row">
      <div class="col-12 mt-4">
        <h4>{{ $t('samples.ec01.product.detail.recommend') }}</h4>
        <div class="row">
          <div v-for="product in productList" :key="product.oid" class="col-12 col-md-4">
            <div class="card border-light border-0">
              <router-link
                :to="{ name: 'detail', query: { productId: product.oid } }"
                class="h-100"
              >
                <img
                  class="card-img-top img-thumbnail img-fluid all-product-img"
                  :src="imgUrl(product.productImg)"
                  :alt="product.name"
                />
              </router-link>
              <div class="card-body pt-md-1 text-center">
                <div>
                  <router-link
                    :to="{ name: 'detail', query: { productId: product.oid } }"
                    class="card-link text-dark"
                    >{{ product.name }}</router-link
                  >
                </div>
                <div class="all-price">{{ product.price }}{{ $t('samples.ec01.all.yen') }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Consts } from '../../mixins/Consts'
import emitter from '../../eventBus'

export default {
  name: 'Detail',
  mixins: [Consts],
  beforeRouteUpdate(to, from, next) {
    this.productId = to.query.productId
    this.loadContent()
    // カートの中を見るなどのボタンを非表示
    this.alreadyAdded = false
    next()
  },
  data() {
    return {
      alreadyAdded: false,
      productInfo: {},
      subInfoList: {},
      productList: {}
    }
  },
  computed: {
    regInfo() {
      if (this.productInfo.regInfo === undefined) {
        return ''
      } else {
        return this.productInfo.regInfo
          .replace(/\r\n/g, '<BR>')
          .replace(/\n/g, '<BR>')
          .replace(/\r/g, '<BR>')
          .replace(/\s/g, '&nbsp;')
      }
    },
    priceWithoutTax() {
      return Math.round(parseInt(this.productInfo.priceWithoutTax))
    }
  },
  created() {
    this.productId = this.$route.query.productId
    this.loadContent()
  },
  methods: {
    loadContent() {
      var url = this.apiDetail(this.productId)
      this.$http.get(url).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.productInfo = commandResult.productInfo
          this.subInfoList = commandResult.subInfoList
          this.productList = commandResult.productList
        } else {
          alert()
          console.log(response)
        }
      })
    },
    inputCartInfo() {
      var url = this.apiInputCartInfo()
      var data = { productId: this.productId }
      var config = {
        contentType: 'application/json',
        headers: { 'X-Requested-With': 'XMLHttpRequest' }
      }
      this.$http
        .post(url, data, config)
        .then((response) => {
          var commandResult = response.data
          if (commandResult.status == 'SUCCESS') {
            var totalAmount = commandResult.totalAmount
            // カートの中を見るなどのボタンの表示
            this.alreadyAdded = true
            // カートに入れた商品件数の更新
            emitter.emit('cart.totalAmount.refresh', totalAmount)
          }
        })
        .catch((error) => {
          var errorResult = error.response.data
          if (errorResult.exceptionType != null) {
            alert(
              this.$t('samples.ec01.product.detail.jsError') +
                errorResult.exceptionType +
                '\n' +
                errorResult.exceptionMessage
            )
            return
          }
        })
    }
  }
}
</script>

<style></style>
