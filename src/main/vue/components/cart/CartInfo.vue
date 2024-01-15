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
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

<template>
<div class="col-sm-12 col-md-9">
    <div class="row">
        <div class="col-12">
            <div class="border-top"></div>
            <nav class="breadcrumb all-breadcrumb">
                <router-link class="breadcrumb-item text-primary" v-bind:to="{name: 'top'}">{{$t("samples.ec01.all.breadcrumb.home")}}</router-link>
                <span class="breadcrumb-item active">{{$t("samples.ec01.cart.info.title")}}</span>
            </nav>
        </div>
    </div>
    <div class="row cart-table" v-if="cartBean === undefined || cartBean.cartItems.length == 0">
        <div class="col-12 text-left">
            <p>{{$t("samples.ec01.cart.info.emptyMsg.p1")}}</p>
            <p>{{$t("samples.ec01.cart.info.emptyMsg.p2")}}<router-link class="text-dark" v-bind:to="{name: 'top'}">{{$t("samples.ec01.cart.info.emptyMsg.p3")}}</router-link></p>
        </div>
    </div>
    <form id="cartInfoForm" v-else>
        <div class="row cart-table">
            <div class="col-12 d-none d-md-block">
                <div class="row">
                    <div class="col-md-3 font-weight-bold text-center">{{$t("samples.ec01.cart.info.product")}}</div>
                    <div class="col-md-3 font-weight-bold"></div>
                    <div class="col-md-2 font-weight-bold">{{$t("samples.ec01.cart.info.price")}}</div>
                    <div class="col-md-2 font-weight-bold">{{$t("samples.ec01.cart.info.quantity")}}</div>
                    <div class="col-md-2 font-weight-bold">{{$t("samples.ec01.cart.info.subtotal")}}</div>
                </div>
            </div>
            <div class="col-12">
                <!--cart item info start-->
                <div class="row mb-4" v-for="(item,i) in cartBean.cartItems" v-bind:key="item.productId">
                    <div class="col-12 col-md-3 text-center text-md-left cart-table-item">
                        <div class="w-100">
                            <router-link class="img-fluid" v-bind:to="{name: 'detail', query: {productId: item.productId}}">
                                <img v-bind:src="imgUrl(productMap[item.productId].productImg)" alt="" class="img-thumbnail w-100">
                            </router-link>
                        </div>
                    </div>
                    <div class="col-12 col-md-3 text-center text-md-left cart-table-item">
                        <div class="w-100">
                            <h6>{{productMap[item.productId].name}}</h6>
                            <p>
                                <a class="text-dark" href="#" v-on:click.prevent="deleteCartItem(item.productId)" role="button">{{$t("samples.ec01.cart.info.remove")}}</a>
                            </p>
                        </div>
                    </div>
                    <div class="col-3 d-block d-md-none cart-table-item">
                        <p class="w-100 font-weight-bold">{{$t("samples.ec01.cart.info.price")}}</p>
                    </div>
                    <div class="col-8 col-md-2 cart-table-item">
                        <p class="w-100">{{productMap[item.productId].price}}{{$t("samples.ec01.all.yen")}}</p>
                    </div>
                    <div class="col-3 d-block d-md-none cart-table-item">
                        <p class="w-100 font-weight-bold">{{$t("samples.ec01.cart.info.quantity")}}</p>
                    </div>
                    <div class="col-8 col-md-2 cart-table-item clearfix">
                        <div class="w-100">
                            <input type="text" class="form-control float-right" v-bind:name="'cartItems[' + i + '].value'" v-model="item.value" cart-data="value">
                        </div>
                        <input type="hidden" v-bind:name="'cartItems[' + i + '].productId'" v-bind:value="item.productId" cart-data="productId">
                    </div>
                    <div class="col-3 d-block d-md-none cart-table-item">
                        <p class="w-100 font-weight-bold">{{$t("samples.ec01.cart.info.total")}}</p>
                    </div>
                    <div class="col-8 col-md-2 cart-table-item">
                        <p class="w-100">{{productMap[item.productId].price * item.value}}{{$t("samples.ec01.all.yen")}}</p>
                    </div>
                </div>
                <hr class="mb-3">
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-md-12 text-center text-md-right">
                <p class="font-weight-bold">
                    {{$t("samples.ec01.cart.info.total")}}：&nbsp;
                    {{cartBean.totalPrice}}{{$t("samples.ec01.all.yen")}}
                </p>
                <button type="button" class="btn btn-outline-dark" v-on:click="updateCart()">
                    <span class="oi oi-reload" v-bind:title="$t('samples.ec01.cart.info.refresh')" aria-hidden="true" />
                </button>
                <router-link class="btn btn-outline-dark" v-bind:to="{name: 'top'}" role="button">{{$t("samples.ec01.cart.info.return")}}</router-link>
            </div>
        </div>
    </form>
    <div class="row mt-3" v-if="cartBean !== undefined && cartBean.cartItems.length > 0">
        <div class="col-md-12 text-center text-md-right">
            <router-link class="btn btn-dark btn-lg" v-bind:to="{name: 'login'}" role="button">{{$t("samples.ec01.cart.info.checkOut")}}</router-link>
        </div>
    </div>
</div>
</template>

<script>
import {Consts} from '../../mixins/Consts'
import $ from 'jquery'

export default {
    name: 'CartInfo',
    mixins: [Consts],
    data: function() {
        return {
            cartBean: {
                cartItems: [],
                totalPrice: ""
            },
            productMap: {}
        }
    },
    methods: {
        loadContent: function() {
            var url = this.apiCartInfo();
            this.$http.get(url)
                .then((response) => {
                    var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS'){
                        this.cartBean = commandResult.cartBean;
                        this.productMap = commandResult.productMap;
                    }
                });
        },
        updateCart: function() {
            var err = false;
            $('input[type=text][cart-data=value]').each(function(){
                var str = this.value;
                if(isNaN(str) || !/^[1-9]\d*|0$/.test(str)) {
                    err = true;
                }
            });
            if (err){
                alert(this.$t('samples.ec01.cart.info.notNum'));
                return false;
            }
            var url = this.apiUpdateCartInfo();
            var data = this.populateCartUpdateData();
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS'){
                        this.cartBean = commandResult.cartBean;
                        // カートに入れた商品件数の更新
                        this.$bus.$emit("cart.totalAmount.refresh", this.cartBean.totalAmount);
                    }
                });
        },
        populateCartUpdateData: function() {
            var data = {};
            $('input[type=hidden][cart-data=productId]').each(function(){
                data[this.name] = this.value;
            })
            $('input[type=text][cart-data=value]').each(function(){
                data[this.name] = this.value;
            })
            return data;
        },
        deleteCartItem: function(productId) {
            var url = this.apiDeleteCartInfo(productId);
            var data = {deleteId: productId};
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS'){
                        this.cartBean = commandResult.cartBean;
                        // カートに入れた商品件数の更新
                        this.$bus.$emit("cart.totalAmount.refresh", this.cartBean.totalAmount);
                    }
                });
        }
    },
    created: function() {
        this.loadContent();
    }
}
</script>

<style>

</style>
