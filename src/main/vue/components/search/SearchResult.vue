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

<template>
<div class="col-sm-12 col-md-9">
    <div class="row">
        <div class="col-12">
            <div class="border-top"></div>
            <nav class="breadcrumb all-breadcrumb">
                <router-link class="breadcrumb-item text-primary" v-bind:to="{name: 'top'}">{{$t("samples.ec01.all.breadcrumb.home")}}</router-link>
                <span class="breadcrumb-item active">{{$t("samples.ec01.search.keyword")}}</span>
            </nav>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-12">
            <h4>{{$t("samples.ec01.search.result")}}{{keyword}}</h4>
        </div>
        <div class="col-12 col-md-4" v-for="p in productList" v-bind:key="p.oid">
            <div class="card border-light border-0">
                <router-link v-bind:to="{name: 'detail', query: {productId: p.oid}}" class="h-100">
                    <img class="card-img-top img-thumbnail img-fluid all-product-img" v-bind:src="imgUrl(p.productImg)" v-bind:alt="p.name" />
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
</div>
</template>

<script>
import {Consts} from '../../mixins/Consts'

export default {
    name: 'SearchResult',
    mixins: [Consts],
    props: ['keyword'],
    data: function() {
        return {
            productList: []
        }
    },
    methods: {
        loadContent: function() {
            var url = this.apiDoSearch();
            var data = {productName: this.keyword};
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS'){
                        this.keyword = commandResult.keyword;
                        this.productList = commandResult.productList;
                    }
                });   
        }
    },
    created: function() {
        this.loadContent();
    },
    beforeRouteUpdate: function(to, from, next) {
        this.keyword = to.query.keyword;
        this.loadContent();
        next();
    }
}
</script>

<style>

</style>
