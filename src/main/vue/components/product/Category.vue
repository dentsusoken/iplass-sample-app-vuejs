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
          <span :id="categoryId" class="breadcrumb-item active">{{ categoryNameLocale }}</span>
        </nav>
      </div>
    </div>
    <div class="row d-none d-md-block">
      <div class="col-12 clearfix">
        <span class="h4">{{ categoryNameLocale }}</span>
        <div class="dropdown float-end">
          <button
            id="categoryList"
            class="btn btn-outline-dark dropdown-toggle"
            type="button"
            data-bs-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            {{ selectedCategory.name }}
          </button>
          <div class="dropdown-menu" aria-labelledby="categoryList">
            <router-link
              class="dropdown-item"
              :to="{ name: 'category', query: { categoryId: 'all' } }"
            >
              {{ $t('samples.ec01.product.category.all') }}
            </router-link>
            <div class="dropdown-divider"></div>
            <router-link
              v-for="c in categoryList"
              :key="c.oid"
              class="dropdown-item"
              :to="{ name: 'category', query: { categoryId: c.oid } }"
            >
              {{ c.name }}
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="row mt-4">
      <div v-for="p in productList" :key="p.oid" class="col-12 col-md-4">
        <div class="card border-light border-0">
          <router-link :to="{ name: 'detail', query: { productId: p.oid } }" class="h-100">
            <img
              class="card-img-top img-thumbnail img-fluid all-product-img"
              :src="imgUrl(p.productImg)"
              :alt="p.name"
            />
          </router-link>
          <div class="card-body pt-md-1 text-center">
            <div>
              <router-link
                :to="{ name: 'detail', query: { productId: p.oid } }"
                class="card-link text-dark"
                >{{ p.name }}</router-link
              >
            </div>
            <div class="all-price">{{ p.price }}{{ $t('samples.ec01.all.yen') }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-12 all-pagination-list">
        <!-- ページング -->
        <pagination
          v-bind="{ pagination: pagination, paginationUrl: paginationUrl }"
          :id="page"
        ></pagination>
      </div>
    </div>
  </div>
</template>

<script>
import Pagination from '../ui/Pagination.vue'
import { Consts } from '../../mixins/Consts'
import emitter from '../../eventBus'

export default {
  name: 'Category',
  components: { pagination: Pagination },
  mixins: [Consts],
  beforeRouteUpdate(to, from, next) {
    this.categoryId = to.query.categoryId
    this.page = to.query.page
    this.loadCategoryList()
    this.loadContent()
    next()
  },
  data() {
    return {
      categoryName: '',
      categoryList: [],
      productList: [],
      pagination: {},
      selectedCategory: { oid: 'all', name: this.$t('samples.ec01.product.category.all') }
    }
  },
  computed: {
    categoryNameLocale() {
      return this.categoryName
    },
    pageIndex() {
      return this.page === undefined ? 0 : this.page
    },
    paginationUrl() {
      return this.categoryUrl(this.categoryId)
    }
  },
  watch: {
    categoryName(val) {
      if (val === 'all') {
        this.categoryName = this.$t('samples.ec01.product.category.all')
      }
    }
  },
  created() {
    this.categoryId = this.$route.query.categoryId
    this.page = this.$route.query.page
    this.loadCategoryList()
    this.loadContent()
  },
  methods: {
    loadContent() {
      var url = this.apiCategory(this.categoryId, this.pageIndex)
      this.$http.get(url).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.categoryId = commandResult.categoryId
          this.categoryName = commandResult.categoryName
          this.productList = commandResult.productList
          this.pagination = commandResult.pagination
        } else {
          console.log(response)
        }
      })
    },
    loadCategoryList() {
      emitter.on('category.categoryList.response', (categoryList) => {
        this.categoryList = categoryList
      })
      emitter.emit('category.categoryList.request')
    }
  }
}
</script>

<style></style>
