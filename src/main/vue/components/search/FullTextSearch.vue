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
          <span class="breadcrumb-item active">{{ $t('samples.ec01.search.title') }}</span>
        </nav>
      </div>
    </div>
    <div id="searchResultDiv" class="row">
      <div class="col-12">
        <h4>{{ $t('samples.ec01.search.title') }}</h4>
        <div class="input-group col-12">
          <input
            v-model="productName"
            type="text"
            class="form-control"
            placeholder="Search for..."
            @keydown.enter="fullTextSearch"
          />
          <div class="dropdown float-end">
            <button
              id="categoryList"
              class="btn btn-outline-dark dropdown-toggle"
              type="button"
              data-bs-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
              category-item-selected="all"
            >
              {{ selectedCategory.name }}
            </button>
            <div class="dropdown-menu" aria-labelledby="categoryList">
              <a class="dropdown-item" href="javascript:void(0);" @click="dropdownSelect('all')">
                {{ $t('samples.ec01.product.category.all') }}
              </a>
              <div class="dropdown-divider"></div>
              <a
                v-for="c in categoryList"
                :key="c.oid"
                class="dropdown-item"
                href="javascript:void(0);"
                @click="dropdownSelect(c)"
              >
                {{ c.name }}
              </a>
            </div>
          </div>
          <span class="input-group-btn pl-2">
            <button class="btn btn-secondary" type="button" @click="fullTextSearch">
              <span class="oi oi-magnifying-glass" title="search" aria-hidden="true"></span>
            </button>
          </span>
        </div>
      </div>
    </div>
    <div v-if="fullSearchResult.length > 0">
      <div id="searchResultDiv" class="row">
        <div class="col-12 mb-2">
          <h4>{{ $t('samples.ec01.search.result') + productName }}</h4>
        </div>
        <div v-for="result in fullSearchResult" :key="result.oid" class="col-12 col-md-4">
          <div class="card border-light border-0">
            <a :href="result.detailUrl" class="h-100">
              <img
                class="card-img-top img-thumbnail img-fluid all-product-img"
                :src="result.imageUrl"
                :alt="result.name"
              />
            </a>
            <div class="card-body pt-md-1 text-center">
              <div>
                <a :href="result.detailUrl" class="card-link text-dark">{{ result.name }}</a>
              </div>
              <div class="all-price">
                <span>{{ result.price }}</span>{{ yen }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="col-12">
      <small class="form-text text-danger">{{ helpMessage }}</small>
    </div>
  </div>
</template>

<script>
import { Consts } from '../../mixins/Consts'

export default {
  name: 'FullTextSearch',
  mixins: [Consts],
  data() {
    return {
      productName: '',
      categoryList: [],
      fullSearchResult: [],
      helpMessage: '',
      selectedCategory: { oid: 'all', name: this.$t('samples.ec01.product.category.all') }
    }
  },
  computed: {
    categoryOid() {
      return this.selectedCategory.oid
    }
  },
  created() {
    this.loadCategoryList()
  },
  methods: {
    loadCategoryList() {
      this.$emitter.on('fullTextSearch.categoryList.response', (categoryList) => {
        this.categoryList = categoryList
      })
      this.$emitter.emit('fullTextSearch.categoryList.request')
    },
    fullTextSearch() {
      if (this.productName === '') {
        this.helpMessage = this.$t('samples.ec01.search.nokeyword')
        return false
      }
      var url = this.apiFulltextSearch()
      var data = { productName: this.productName, categoryOid: this.categoryOid }
      this.$http
        .post(url, data)
        .then((response) => {
          var commandResult = response.data
          if (commandResult.status == 'SUCCESS') {
            if (commandResult.defaultResult != null && commandResult.defaultResult.length > 0) {
              this.fullSearchResult = this.transformSearchResults(commandResult.defaultResult)
              this.helpMessage = ''
            } else {
              this.helpMessage =
                this.$t('samples.ec01.search.keyword') +
                this.productName +
                ', ' +
                this.$t('samples.ec01.search.noResult')
              this.fullSearchResult = []
            }
          } else {
            console.log(response)
          }
        })
        .catch((error) => {
          var errorResult = error.response.data
          if (errorResult.exceptionType != null) {
            alert(
              this.$t('samples.ec01.search.jsError') +
                errorResult.exceptionType +
                '\n' +
                errorResult.exceptionMessage
            )
            return
          }
        })
    },
    dropdownSelect(category) {
      if (typeof category === 'string') {
        // "all" を選択した場合
        this.selectedCategory = { oid: 'all', name: this.$t('samples.ec01.product.category.all') }
      } else {
        // カテゴリを選択した場合
        this.selectedCategory = category
      }
    },
    transformSearchResults(entities) {
      return entities.map((entity) => ({
        oid: entity.oid,
        name: entity.name,
        price: isNaN(entity.price) ? '' : entity.price,
        imageUrl: this.imgUrl(entity.productImg),
        detailUrl: `#/product/detail?productId=${entity.oid}`
      }))
    }
  }
}
</script>

<style></style>
