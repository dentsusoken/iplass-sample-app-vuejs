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
                <span class="breadcrumb-item active">{{$t("samples.ec01.search.title")}}</span>
            </nav>
        </div>
    </div>
    <div class="row" id="searchResultDiv">
        <div class="col-12">
            <h4>{{$t("samples.ec01.search.title")}}</h4>
            <div class="input-group col-12">
                <input type="text" class="form-control" v-model="productName" placeholder="Search for...">
                <div class="dropdown float-right">
                    <button class="btn btn-outline-dark dropdown-toggle" type="button" id="categoryList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" category-item-selected="all">{{$t("samples.ec01.product.category.title")}}</button>
                    <div class="dropdown-menu" aria-labelledby="categoryList">
                        <a class="dropdown-item" href="javascript:void(0);" v-on:click="dropdownSelect" category-item-value="all">{{$t("samples.ec01.product.category.all")}}</a>
                        <div class="dropdown-divider"></div>
                        <template v-for="c in categoryList" v-bind:key="c.oid">
                        <a class="dropdown-item" href="javascript:void(0);" v-on:click="dropdownSelect" v-bind:category-item-value="c.oid">{{c.name}}</a>
                        </template>
                    </div>
                </div>
                <span class="input-group-btn pl-2">
                    <button class="btn btn-secondary" type="button" v-on:click="fullTextSearch()">
                        <span class="oi oi-magnifying-glass" title="search" aria-hidden="true"></span>
                    </button>
                </span>
            </div>
            <div class="input-group col-12">
                <small class="form-text text-danger" v-html="helpMessage"></small>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {Consts} from '../../mixins/Consts'
import $ from 'jquery'

export default {
    name: 'FullTextSearch',
    mixins: [Consts],
    data: function() {
            return {
                productName: "",
                categoryList: [],
                fullSearchResult: "",
                helpMessage: ""
            }
    },
    computed: {
      categoryOid: function() {
        return $("#categoryList").attr("category-item-selected");
      }
    },
    methods: {
        loadCategoryList: function() {
            this.$bus.$on('fullTextSearch.categoryList.response', (categoryList) => {
                this.categoryList = categoryList;
            });
            this.$bus.$emit('fullTextSearch.categoryList.request');
        },
        fullTextSearch: function() {
            if (this.productName == "") {
                this.helpMessage = this.$t('samples.ec01.search.nokeyword');
                return false;
            }
            var url = this.apiFulltextSearch();
            var data = {productName: this.productName, categoryOid: this.categoryOid};
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;              
                    if(commandResult.status == 'SUCCESS') {
                        if(commandResult.defaultResult != null && commandResult.defaultResult.length > 0){
                            this.fullSearchResult = this.ListSearchResult(commandResult.defaultResult, this.productName);
                            $('#searchResultDiv').html(this.fullSearchResult);
                        }
                        else{
                            this.helpMessage = this.$t('samples.ec01.search.keyword') + this.productName + ", " + this.$t('samples.ec01.search.noResult');
                        }
                    } else {
                        console.log(response);
                    }
                })
                .catch((error) => {
                    var errorResult = error.response.data;
                    if (errorResult.exceptionType != null) {
                        alert(this.$t('samples.ec01.search.jsError') + errorResult.exceptionType + "\n" + errorResult.exceptionMessage);
                        return;
                    }
                });
        },
        dropdownSelect: function(event) {
            var t = $(event.target);
            var v = t.attr("category-item-value");
            $("#categoryList").text(t.html()).attr("category-item-selected", v);
        },
        ListSearchResult: function(entities, productName){
            var yen = this.$t('samples.ec01.all.yen');
            var html =  "<div class=\"col-12 mb-2\">";
                html += "	<h4>" + this.$t('samples.ec01.search.result') + productName + "</h4>";
                html += "</div>";
            for(var i =0; i < entities.length; i++){
                var name = entities[i].name;
                var price = isNaN(entities[i].price)? "" : entities[i].price;
                var imageUrl = this.imgUrl(entities[i].productImg);
                var detailUrl = "#/product/detail?productId=" + entities[i].oid;
                html += "<div class=\"col-12 col-md-4\">";
                html += "	<div class=\"card border-light border-0\">";
                html += "	    <a href=\"" + detailUrl + "\" class=\"h-100\">";
                html += "	       <img class=\"card-img-top img-thumbnail img-fluid all-product-img\" src=" + imageUrl + " alt=\"" + name + "\">";
                html += "	    </a>";
                html += "	    <div class=\"card-body pt-md-1 text-center\">";
                html += "	       <div>";
                html += "	           <a href=\""+ detailUrl +"\" class=\"card-link text-dark\">" + name + "</a>";
                html += "	        </div>";
                html += "	        <div class=\"all-price\">";
                html += "              <span>" + price + "</span>" + yen;
                html += "	        </div>";
                html += "	    </div>";
                html += "	</div>";
                html += "</div>";
            }
            return html;
        }
    },
    created: function() {
        this.loadCategoryList();
    }
}
</script>

<style>

</style>
