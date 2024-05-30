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
<div class="container">
    <div class="row">
        <div class="col-12">
            <nav class="navbar navbar-expand-md navbar-light layout-navbar">
                <!-- 大きい画面で表示 start-->
                <div class="d-none d-md-block">
                    <div class="form-inline">
                        <button class="btn bg-transparent btn-lg" type="button" v-on:click="search()">
                            <span class="oi oi-magnifying-glass" title="search" aria-hidden="true"></span>
                        </button>
                        <input id="txtKeyword" type="text" class="form-control mr-md-2 layout-navbar-text border-0" name="productName" placeholder="Search" v-model="keyword">
                    </div>
                </div>
                <!-- 大きい画面で表示 end-->
                <!-- 小さい画面で表示  start-->
                <div class="d-block d-md-none">
                    <router-link class="btn bg-transparent btn-lg text-dark" v-bind:to="{name: 'fullTextSearch'}">
                        <span class="oi oi-magnifying-glass" title="search" aria-hidden="true"></span>
                    </router-link>
                </div>
                <!-- 小さい画面で表示  end-->
                <!-- 大きい画面で表示 start-->
                <div class="d-none d-md-block">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <router-link class="btn btn-lg text-dark" v-bind:to="{name: 'cartInfo'}">
                                <span class="oi oi-cart"></span>
                                    {{$t("samples.ec01.layout.defaultLayout.cart")}}
                                <span cart-data="totalAmount">({{totalAmount}})</span>
                            </router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="btn btn-lg text-dark" v-bind:to="{name: 'login'}">{{$t("samples.ec01.layout.defaultLayout.checkOut")}}</router-link>
                        </li>
                    </ul>
                </div>
                <!-- 大きい画面で表示 end-->
                <!-- 小さい画面で表示  start-->
                <div class="d-block d-md-none">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <router-link class="btn btn-lg text-dark" v-bind:to="{name: 'cartInfo'}">
                                <span class="oi oi-cart"></span><span cart-data="totalAmount">({{totalAmount}})</span>
                            </router-link>
                        </li>
                    </ul>
                </div>
                <!-- 小さい画面で表示  end-->
            </nav>
        </div>
    </div>
    <div class="row">
        <div class="col-12 border-top text-center text-md-left">
            <p class="h1 font-weight-bold my-3">iPLAss.</p>
        </div>
    </div>
    <!-- 小さい画面で表示  start-->
    <div class="row">
        <div class="col-12 d-block d-md-none">
            <div class="navbar-light border-top text-center py-2">
                <button class="navbar-toggler hidden-md-up border-0" type="button" data-toggle="collapse" data-target="#collapsibleNavId"
                    aria-controls="collapsibleNavId" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="collapsibleNavId">
                    <ul class="list-group mt-2 d-block d-md-none">
                        <li class="list-group-item border-0">
                            <a class="nav-link text-dark font-weight-bold" href="#/">{{$t("samples.ec01.layout.defaultLayout.home")}}</a>
                        </li>
                        <li class="list-group-item border-0">
                            <router-link class="nav-link text-dark" v-bind:to="{name: 'category', query: {categoryId: 'all'}}">{{$t("samples.ec01.product.category.all")}}</router-link>
                        </li>
                        <li class="list-group-item border-0" v-for="category in categoryList" v-bind:key="category.oid">
                            <router-link class="nav-link text-dark" v-bind:to="{name: 'category', query: {categoryId: category.oid}}">{{category.name}}</router-link>
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
                    <div class="list-group list-group-item-dark list-group-flush">
                        <router-link v-bind:to="{name: 'top'}" class="list-group-item list-group-item-action font-weight-bold border-top">{{$t("samples.ec01.layout.defaultLayout.home")}}</router-link>
                        <template v-for="category in categoryList" v-bind:key="category.oid">
                            <router-link v-bind:to="{name: 'category', query: {categoryId: category.oid}}" class="list-group-item list-group-item-action">{{category.name}}</router-link>
                        </template>
                    </div>
                </div>
            </div>
        </div>
        <router-view></router-view>
    </div>
    <hr>
    <div class="row">
        <div class="col-12">
            <ul class="list-group">
                <li class="list-group-item border-0 font-weight-bold">Links</li>
                <li class="list-group-item border-0">
                    <router-link v-bind:to="{name: 'newInfo'}" class="text-dark">{{$t("samples.ec01.layout.defaultLayout.news")}}</router-link>
                </li>
                <li class="list-group-item border-0">
                    <router-link v-bind:to="{name: 'fullTextSearch'}" class="text-dark">{{$t("samples.ec01.layout.defaultLayout.search")}}</router-link>
                </li>
                <li class="list-group-item border-0">
                    <router-link v-bind:to="{name: 'inquiry'}" class="text-dark">{{$t("samples.ec01.layout.defaultLayout.inquiry")}}</router-link>
                </li>
                <li class="list-group-item border-0">
                    <router-link v-bind:to="{name: 'tradeLaw'}" class="text-dark">{{$t("samples.ec01.layout.defaultLayout.SCTAInfo")}}</router-link>
                </li>
            </ul>
        </div>
    </div>
    <div class="row layout-footer bg-dark">
        <div class="col-md-12 text-light text-left">Copyright（c）2018 MTP Demo Store all rights reserved.</div>
    </div>
</div>
</template>

<script>
import $ from 'jquery'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import '../../styles/open-iconic-bootstrap.min.css'
import '../../styles/bookstore.css'
import {Consts} from '../../mixins/Consts'

export default {
    name: 'DefaultLayout',
    mixins: [Consts],
    data: function() {
        return {
            totalAmount: "",
            categoryList: [],
            productName: ""
        }
    },
    methods: {
        loadContent: function() {
            var url = this.apiDefaultLayout();
            this.$http.get(url)
                .then((response) => {
                var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS') {
                        this.totalAmount = commandResult.totalAmount;
                        this.categoryList = commandResult.categoryList;
                    } else {
                        console.log(response);
                    }
                });  
        },
        search: function() {
            this.$router.push({name: 'searchResult', query: {keyword: this.keyword}});
        }
    },
    created: function() {
        // カートに入れた商品件数の更新
        this.$bus.$on('cart.totalAmount.refresh', (totalAmount) => {
            this.totalAmount = totalAmount;
        })
        // カテゴリリストを子インスタンスに渡す
        this.$bus.$on('category.categoryList.request', () => {
            this.$bus.$emit('category.categoryList.response', this.categoryList);
        });
        this.$bus.$on('fullTextSearch.categoryList.request', () => {
            this.$bus.$emit('fullTextSearch.categoryList.response', this.categoryList);
        });
        this.loadContent();
    },
    mounted: function() {
        // Enterキーを押すと、検索処理を実行する
        $("#txtKeyword").keydown((e) => {
            if( e.keyCode == 13 ){
                this.search();
            }
        });
    }
}
</script>

<style>

</style>
