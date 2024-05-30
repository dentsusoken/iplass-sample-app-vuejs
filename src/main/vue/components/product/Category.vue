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
 * along with this program. If not, see &lt;https://www.gnu.org/licenses/&gt;.
 */

<template>
<div class="col-sm-12 col-md-9">
    <div class="row">
        <div class="col-12">
            <div class="border-top"></div>
            <nav class="breadcrumb all-breadcrumb">
                <router-link class="breadcrumb-item text-primary" v-bind:to="{name: 'top'}">{{$t("samples.ec01.all.breadcrumb.home")}}</router-link>
                <span class="breadcrumb-item active">{{categoryNameLocale}}</span>
            </nav>
        </div>
    </div>
    <div class="row d-none d-md-block">
        <div class="col-12 clearfix">
            <span class="h4">{{categoryNameLocale}}</span>
            <div class="dropdown float-right">
                <button class="btn btn-outline-dark dropdown-toggle" type="button" id="categoryList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">{{$t("samples.ec01.product.category.title")}}</button>
                <div class="dropdown-menu" aria-labelledby="categoryList">
                    <router-link class="dropdown-item" v-bind:to="{name: 'category', query: {categoryId: 'all'}}">{{$t("samples.ec01.product.category.all")}}</router-link>
                    <div class="dropdown-divider"></div>
                    <router-link class="dropdown-item" v-for="c in categoryList" v-bind:key="c.oid" v-bind:to="{name: 'category', query: {categoryId: c.oid}}">{{c.name}}</router-link>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-12 col-md-4" v-for="p in productList" v-bind:key="p.oid">
            <div class="card border-light border-0">
                <router-link v-bind:to="{name: 'detail', query: {productId: p.oid}}" class="h-100">
                    <img class="card-img-top img-thumbnail img-fluid all-product-img" v-bind:src="imgUrl(p.productImg)" v-bind:alt="p.name">
                </router-link>
                <div class="card-body pt-md-1 text-center">
                    <div>
                        <router-link v-bind:to="{name: 'detail', query: {productId: p.oid}}" class="card-link text-dark">{{p.name}}</router-link>
                    </div>
                    <div class="all-price">{{p.price}}{{$t("samples.ec01.all.yen")}}</div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12 all-pagination-list" >
            <!-- ページング -->
            <pagination v-bind="{pagination: pagination, paginationUrl: paginationUrl}"></pagination>
        </div>
    </div>
</div>
</template>

<script>
import Pagination from '../ui/Pagination.vue'
import {Consts} from '../../mixins/Consts'

export default {
    name: 'Category',
    components: {'pagination': Pagination},
    mixins: [Consts],
    props: ['categoryId', 'page'],
    data: function() {
        return {
            categoryName: "",
            categoryList: [],
            productList: [],
            pagination: {}
        }
    },
    methods: {
        loadContent : function() {
            var url = this.apiCategory(this.categoryId, this.pageIndex);
            this.$http.get(url)
                .then((response) => {
                var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS') {
                        this.categoryId = commandResult.categoryId;
                        this.categoryName = commandResult.categoryName;
                        this.productList = commandResult.productList;
                        this.pagination = commandResult.pagination;
                    } else {
                        console.log(response);
                    }
                });
        },
        loadCategoryList: function() {
            this.$bus.$on('category.categoryList.response', (categoryList) => {
                this.categoryList = categoryList;
            });
            this.$bus.$emit('category.categoryList.request');
        }
    },
    created: function() {
        this.loadCategoryList();
        this.loadContent();
    },
    beforeRouteUpdate: function(to, from, next) {
        this.categoryId = to.query.categoryId;
        this.page = to.query.page;
        this.loadCategoryList();
        this.loadContent();
        next();
    },
    computed: {
        categoryNameLocale: function() {
            // カテゴリが選択されてない場合
            if(this.categoryName == 'all'){
                this.categoryName = this.$t('samples.ec01.product.category.all');
            }
            return this.categoryName;
        },
        pageIndex: function() {
            return this.page === undefined ? 0 : this.page;
        },
        paginationUrl: function() {
            return this.categoryUrl(this.categoryId);
        }
    }
}
</script>

<style>

</style>
