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
      <div class="col-12">
        <nav class="navbar navbar-expand-md navbar-light layout-navbar">
          <!-- 大きい画面で表示 start-->
          <div class="d-none d-md-block">
            <div class="d-flex align-items-center">
              <button class="btn bg-transparent btn-lg" type="button" @click="search()">
                <span class="oi oi-magnifying-glass" title="search" aria-hidden="true"></span>
              </button>
              <input
                id="txtKeyword"
                v-model="keyword"
                type="text"
                class="form-control me-2 layout-navbar-text border-0"
                name="productName"
                placeholder="Search"
                @keydown.enter="search"
              />
            </div>
          </div>
          <!-- 大きい画面で表示 end-->
          <!-- 小さい画面で表示  start-->
          <div class="d-block d-md-none">
            <router-link
              class="btn bg-transparent btn-lg text-dark"
              :to="{ name: 'fullTextSearch' }"
            >
              <span class="oi oi-magnifying-glass" title="search" aria-hidden="true"></span>
            </router-link>
          </div>
          <!-- 小さい画面で表示  end-->
          <!-- 大きい画面で表示 start-->
          <div class="d-none d-md-block">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item">
                <router-link class="btn btn-lg text-dark" :to="{ name: 'cartInfo' }">
                  <span class="oi oi-cart"></span>
                  {{ $t('samples.ec01.layout.defaultLayout.cart') }}
                  <span cart-data="totalAmount">({{ totalAmount }})</span>
                </router-link>
              </li>
              <li class="nav-item">
                <router-link class="btn btn-lg text-dark" :to="{ name: 'login' }">
                  {{ $t('samples.ec01.layout.defaultLayout.checkOut') }}
                </router-link>
              </li>
            </ul>
          </div>
          <!-- 大きい画面で表示 end-->
          <!-- 小さい画面で表示  start-->
          <div class="d-block d-md-none">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item">
                <router-link class="btn btn-lg text-dark" :to="{ name: 'cartInfo' }">
                  <span class="oi oi-cart"></span
                  ><span cart-data="totalAmount">({{ totalAmount }})</span>
                </router-link>
              </li>
            </ul>
          </div>
          <!-- 小さい画面で表示  end-->
        </nav>
      </div>
    </div>
    <div class="row">
      <div class="col-12 border-top text-center text-md-start">
        <p class="h1 fw-bold my-3">iPLAss.</p>
      </div>
    </div>
    <!-- 小さい画面で表示  start-->
    <div class="row">
      <div class="col-12 d-block d-md-none">
        <div class="navbar-light border-top text-center py-2">
          <button
            class="navbar-toggler hidden-md-up border-0"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#collapsibleNavId"
            aria-controls="collapsibleNavId"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="oi oi-menu"></span>
          </button>
          <div id="collapsibleNavId" class="collapse">
            <ul class="list-group mt-2 d-block d-md-none">
              <li class="list-group-item border-0">
                <a class="nav-link text-dark fw-bold" href="#/">
                  {{ $t('samples.ec01.layout.defaultLayout.home') }}
                </a>
              </li>
              <li class="list-group-item border-0">
                <router-link
                  class="nav-link text-dark"
                  :to="{ name: 'category', query: { categoryId: 'all' } }"
                  >{{ $t('samples.ec01.product.category.all') }}</router-link
                >
              </li>
              <li
                v-for="category in categoryList"
                :key="category.oid"
                class="list-group-item border-0"
              >
                <router-link
                  class="nav-link text-dark"
                  :to="{ name: 'category', query: { categoryId: category.oid } }"
                >
                  {{ category.name }}
                </router-link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!-- 小さい画面で表示 end-->
    <div class="row layout-container">
      <div class="col-md-3 d-none d-md-block">
        <div class="row">
          <div class="col-12">
            <div class="list-group list-group-flush">
              <router-link
                :to="{ name: 'top' }"
                class="list-group-item list-group-item-action fw-bold border-top"
              >
                {{ $t('samples.ec01.layout.defaultLayout.home') }}
              </router-link>
              <template v-for="category in categoryList" :key="category.oid">
                <router-link
                  :to="{ name: 'category', query: { categoryId: category.oid } }"
                  class="list-group-item list-group-item-action"
                >
                  {{ category.name }}
                </router-link>
              </template>
            </div>
          </div>
        </div>
      </div>
      <RouterView />
    </div>
    <hr />
    <div class="row">
      <div class="col-12">
        <ul class="list-group">
          <li class="list-group-item border-0 fw-bold py-3">Links</li>
          <li class="list-group-item border-0 py-3">
            <router-link :to="{ name: 'newInfo' }" class="text-dark">
              {{ $t('samples.ec01.layout.defaultLayout.news') }}
            </router-link>
          </li>
          <li class="list-group-item border-0 py-3">
            <router-link :to="{ name: 'fullTextSearch' }" class="text-dark">
              {{ $t('samples.ec01.layout.defaultLayout.search') }}
            </router-link>
          </li>
          <li class="list-group-item border-0 py-3">
            <router-link :to="{ name: 'inquiry' }" class="text-dark">
              {{ $t('samples.ec01.layout.defaultLayout.inquiry') }}
            </router-link>
          </li>
          <li class="list-group-item border-0 py-3">
            <router-link :to="{ name: 'tradeLaw' }" class="text-dark">
              {{ $t('samples.ec01.layout.defaultLayout.SCTAInfo') }}
            </router-link>
          </li>
        </ul>
      </div>
    </div>
    <div class="row layout-footer bg-dark">
      <div class="col-md-12 text-light text-left">
        Copyright（c）2018 MTP Demo Store all rights reserved.
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
import emitter from '../../eventBus'

export default {
  name: 'DefaultLayout',
  mixins: [Consts],
  data() {
    return {
      totalAmount: '',
      categoryList: [],
      productName: '',
      keyword: ''
    }
  },
  created() {
    // カートに入れた商品件数の更新
    emitter.on('cart.totalAmount.refresh', (totalAmount) => {
      this.totalAmount = totalAmount
    })
    // カテゴリリストを子インスタンスに渡す
    emitter.on('category.categoryList.request', () => {
      emitter.emit('category.categoryList.response', this.categoryList)
    })
    emitter.on('fullTextSearch.categoryList.request', () => {
      emitter.emit('fullTextSearch.categoryList.response', this.categoryList)
    })
    this.loadContent()
  },
  methods: {
    loadContent() {
      var url = this.apiDefaultLayout()
      this.$http.get(url).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.totalAmount = commandResult.totalAmount
          this.categoryList = commandResult.categoryList
        } else {
          console.log(response)
        }
      })
    },
    search() {
      this.$router.replace({ name: 'searchResult', query: { keyword: this.keyword } })
    }
  }
}
</script>

<style></style>
