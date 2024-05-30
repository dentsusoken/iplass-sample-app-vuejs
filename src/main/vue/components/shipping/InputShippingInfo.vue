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
<form class="custom-form">
    <div class="form-group row">
        <div class="col-12 d-none d-md-block">
            <span class="h4"> </span>
            <nav class="breadcrumb all-breadcrumb">
                <router-link class="breadcrumb-item text-primary" v-bind:to="{name: 'top'}">{{$t("samples.ec01.all.breadcrumb.home")}}</router-link>
                <span class="breadcrumb-item active">{{$t("samples.ec01.shipping.input.title")}}</span>
            </nav>
        </div>
        <div class="col-12 col-md-12">
            <h5>{{$t("samples.ec01.shipping.input.title")}}</h5>
        </div>
        <div class="col-12 col-md-12 mt-3">
            <div>
                <label for="mail" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.mail")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="mail" v-model="shippingBean.mail" v-bind:placeholder="$t('samples.ec01.shipping.input.mail')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.mail" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        <div class="col-12 col-md-6 mt-3">
            <div>
                <label for="familyName" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.familyName")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="familyName" v-model="shippingBean.familyName" v-bind:placeholder="$t('samples.ec01.shipping.input.familyName')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.familyName" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        <div class="col-12 col-md-6 mt-3">
            <div>
                <label for="firstName" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.firstName")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="firstName" v-model="shippingBean.firstName" v-bind:placeholder="$t('samples.ec01.shipping.input.firstName')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.firstName" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        <template v-if="locale == 'ja' || locale === undefined">
        <div class="col-12 col-md-6 mt-3">
            <div>
                <label for="familyNameKana" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.familyNameKana")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="familyNameKana" v-model="shippingBean.familyNameKana" v-bind:placeholder="$t('samples.ec01.shipping.input.familyNameKana')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.familyNameKana" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        <div class="col-12 col-md-6 mt-3">
            <div>
                <label for="firstNameKana" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.firstNameKana")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="firstNameKana" v-model="shippingBean.firstNameKana" v-bind:placeholder="$t('samples.ec01.shipping.input.firstNameKana')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.firstNameKana" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        </template>
        <div class="col-12 col-md-12 mt-3">
            <div>
                <label for="address" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.address")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="address" v-model="shippingBean.address" v-bind:placeholder="$t('samples.ec01.shipping.input.address')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.address" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        <div class="col-12 col-md-12 mt-3">
            <div>
                <label for="tel" class="col-form-label label-hidden">{{$t("samples.ec01.shipping.input.tel")}}</label>
                <input type="text" class="form-control border rounded input-hint-visible" name="tel" v-model="shippingBean.tel" v-bind:placeholder="$t('samples.ec01.shipping.input.tel')">
                <small class="form-text text-danger"><template v-for="message in errorsMap.tel" v-bind:key="message">{{message}}<br/></template></small>
            </div>
        </div>
        <div class="col-12 mt-4 text-center clearfix">
            <div class="float-md-right">
                <button type="button" class="btn btn-dark btn-lg" v-on:click="confirmShippingInfo()">{{$t("samples.ec01.shipping.input.delivery")}}</button>
            </div>
            <div class="text-md-left mt-2">
                <router-link class="btn btn-sm" v-bind:to="{name: 'cartInfo'}" role="button">&lt;
                {{$t("samples.ec01.shipping.input.return")}}
                </router-link>
            </div>
        </div>
    </div>
    <output-token ref="token"></output-token>
</form>
</template>

<script>
import {Custom} from '../../mixins/Custom'
import {Consts} from '../../mixins/Consts'
import OutputToken from '../token/OutputToken.vue'

export default {
    name: 'InputShippingInfo',
    mixins: [Custom, Consts],
    components: {
        'outputToken': OutputToken
    },
    props: ['editShippingBean'],
    data: function(){
        return {
            shippingBean: {
                mail: "",
                tel: "",
                address: "",
                familyName: "",
                firstName: "",
                familyNameKana: "",
                firstNameKana: ""
            },
            errorsMap: {}
        }
    },
    methods: {
        loadContent: function() {
            var url = this.apiInputShippingInfo();
            this.$http.get(url)
                .then((response) => {
                    var commandResult = response.data;
                    if (commandResult.status == 'SUCCESS') {
                        this.shippingBean = commandResult.shippingBean;
                    } else if (commandResult.status == 'ERROR') {
                        var redirectURL = commandResult.redirectURL;
                        this.$router.push(redirectURL);
                    }
                });             
        },
        loadContentNoMember: function() {
            var url = this.apiInputShippingInfoNoMember();
            this.$http.get(url)
                .then((response) => {
                    var commandResult = response.data;
                    if (commandResult.status == 'ERROR') {
                        var redirectURL = commandResult.redirectURL;
                        this.$router.push(redirectURL);
                    }
                }); 
        },
        confirmShippingInfo: function() {
            var url = this.apiConfirmShippingInfo();
            var data = this.populatePostData();
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;
                    if (commandResult.status == 'SUCCESS'){
                        this.$router.replace({name: 'confirmShippingInfo', params: {shippingBean: this.shippingBean}});
                    } else if (commandResult.status == 'ERROR') {
                        this.errorsMap = this.convertToErrorsMap(commandResult.result.errors);
                        this.$refs.token.reload();
                    }
                });
        },
        populatePostData: function() {
            var token = this.$refs.token.get();
            var data = {                
                mail: this.shippingBean.mail,
                tel: this.shippingBean.tel,
                address: this.shippingBean.address,
                familyName: this.shippingBean.familyName,
                firstName: this.shippingBean.firstName,
                familyNameKana: this.shippingBean.familyNameKana,
                firstNameKana: this.shippingBean.firstNameKana
            };
            data[token.name] = token.value;
            return data;
        }
    },
    created: function() {
        var name = this.$router.currentRoute.name;
        if(name == "inputShippingInfoNoMember") {
            this.loadContentNoMember();
        } else if (name == "inputShippingInfo") {
            this.loadContent();
        } else if (name == "editShippingInfo") {
            this.shippingBean = this.editShippingBean;
        }
    },
    mounted: function () {
        this.initFormInputText('.custom-form');
    }
}
</script>

<style>

</style>
