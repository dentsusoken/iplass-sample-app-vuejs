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
          <span class="breadcrumb-item active">{{ $t('samples.ec01.newinfo.title') }}</span>
        </nav>
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <h4 class="card-title">{{ $t('samples.ec01.newinfo.title') }}</h4>
        <div class="card border-0">
          <div class="card-body">
            <p v-for="news in newsInfoList" :key="news.oid" class="card-text border-bottom">
              {{ news.showDate }}&nbsp;{{ news.content }}
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-12 all-pagination-list">
        <!-- ページング -->
        <pagination v-bind="{ pagination: pagination, paginationUrl: paginationUrl }"></pagination>
      </div>
    </div>
  </div>
</template>

<script>
import Pagination from '../ui/Pagination.vue'
import { Consts } from '../../mixins/Consts'

export default {
  name: 'NewInfo',
  components: { pagination: Pagination },
  mixins: [Consts],
  beforeRouteUpdate(to, from, next) {
    // eslint-disable-next-line vue/no-mutating-props
    this.page = to.query.page
    this.loadContent()
    next()
  },
  props: {
    page: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      pagination: {},
      newsInfoList: []
    }
  },
  computed: {
    pageIndex() {
      return this.page === undefined ? 0 : this.page
    },
    paginationUrl() {
      return this.newInfoUrl()
    }
  },
  created() {
    this.loadContent()
  },
  methods: {
    loadContent() {
      var url = this.apiNewInfo(this.pageIndex)
      this.$http.get(url).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.pagination = commandResult.pagination
          this.newsInfoList = commandResult.newsInfoList
        } else {
          console.log(response)
        }
      })
    }
  }
}
</script>

<style></style>
