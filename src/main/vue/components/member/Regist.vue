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
                <span class="breadcrumb-item active">{{$t("samples.ec01.member.regist.title")}}</span>
            </nav>
        </div>
        <div class="col-12">
            <span class="h4">{{$t("samples.ec01.member.regist.title")}}</span>
            <form class="custom-form mt-3">
                <div class="form-group row">
                    <div class="col-12">
                        <div>
                            <label for="userId" class="col-form-label label-hidden">{{$t("samples.ec01.member.regist.userId")}}</label>
                            <input type="text" class="form-control border rounded input-hint-visible" name="userId" v-model="userBean.userId" v-bind:placeholder="$t('samples.ec01.member.regist.userId')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.userId">{{message}}<br v-bind:key="message"/></template></small>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="familyName" class="col-form-label label-hidden">{{$t("samples.ec01.member.regist.familyName")}}</label>
                            <input type="text" class="form-control border rounded input-hint-visible" name="familyName" v-model="userBean.familyName" v-bind:placeholder="$t('samples.ec01.member.regist.familyName')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.familyName">{{message}}<br v-bind:key="message"/></template></small>                      
                        </div>
                    </div>
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="firstName" class="col-form-label label-hidden">{{$t("samples.ec01.member.regist.firstName")}}</label>
                            <input type="text" class="form-control border rounded input-hint-visible" name="firstName" v-model="userBean.firstName" v-bind:placeholder="$t('samples.ec01.member.regist.firstName')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.firstName">{{message}}<br v-bind:key="message"/></template></small>
                        </div>
                    </div>
                    <template v-if="locale == 'ja' || locale === undefined">
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="familyNameKana" class="col-form-label label-hidden">{{$t("samples.ec01.member.regist.familyNameKana")}}</label>
                            <input type="text" class="form-control border rounded input-hint-visible" name="familyNameKana" v-model="userBean.familyNameKana" v-bind:placeholder="$t('samples.ec01.member.regist.familyNameKana')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.familyNameKana">{{message}}<br v-bind:key="message"/></template></small>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="firstNameKana" class="col-form-label label-hidden">{{$t("samples.ec01.member.regist.firstNameKana")}}</label>
                            <input type="text" class="form-control border rounded input-hint-visible" name="firstNameKana" v-model="userBean.firstNameKana" v-bind:placeholder="$t('samples.ec01.member.regist.firstNameKana')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.firstNameKana">{{message}}<br v-bind:key="message"/></template></small>
                        </div>
                    </div>
                    </template>
                    <div class="col-12 col-md-12 mt-3">
                        <div>
                            <label for="mail" class="col-form-label label-hidden">{{$t("samples.ec01.member.regist.mail")}}</label>
                            <input type="text" class="form-control border rounded input-hint-visible" name="mail" v-model="userBean.mail" v-bind:placeholder="$t('samples.ec01.member.regist.mail')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.mail">{{message}}<br v-bind:key="message"/></template></small>
                        </div>
                    </div>
                </div>
                <div class="row memberAgreeContent" v-html="memberAgree.content">
                </div>
                <div class="form-group mt-4">
                    <div class="col-12 text-center">
                        <button type="button" class="btn btn-dark" v-on:click="confirmMemberInfo()">{{$t("samples.ec01.member.regist.doRegist")}}</button>
                    </div>
                </div>
            <output-token ref="token"></output-token>
            </form>
        </div>
    </div>
</div>
</template>

<script>
import {Custom} from '../../mixins/Custom'
import {Consts} from '../../mixins/Consts'
import OutputToken from '../token/OutputToken.vue'

export default {
    name: 'Regist',
    mixins: [Custom, Consts],
    components: {'outputToken': OutputToken},
    data: function() {
        return {
            userBean: {
                userId: "", 
                familyName: "", 
                firstName: "", 
                familyNameKana: "", 
                firstNameKana: "", 
                mail: "",
            },
            memberAgree: {},
            errorsMap: {}
        }
    },
    created: function() {
        var url = this.apiInputMemberInfo();
        this.$http.get(url)
			.then((response) => {
                var commandResult = response.data;
				if(commandResult.status == 'SUCCESS') {
                    this.memberAgree = commandResult.memberAgree;
				} else {
					console.log(response);
				}
			});
    },
    mounted: function(){
        this.initFormInputText('.custom-form');
    },
    methods: {
        confirmMemberInfo: function() {
            var url = this.apiConfirmMemberInfo();
            var data = this.populatePostData();
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;
                    if (commandResult.status == 'SUCCESS') {
                        this.$router.replace({name: 'registConfirm', params: {userBean: this.userBean}});
                    } else if (commandResult.status == 'ERROR') {
                        this.errorsMap = this.convertToErrorsMap(commandResult.result.errors);
                        this.$refs.token.reload();
                    }
                });
        },
        populatePostData: function(){
            var data = {
                userId: this.userBean.userId, 
                familyName: this.userBean.familyName, 
                firstName: this.userBean.firstName, 
                familyNameKana: this.userBean.familyNameKana,
                firstNameKana: this.userBean.firstNameKana,
                mail: this.userBean.mail,
            };
            var token = this.$refs.token.get();
            data[token.name] = token.value;
            return data;
        }
    }
}
</script>

<style>

</style>
