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
                <router-link class="breadcrumb-item text-primary" :to="{name: 'top'}">{{$t("samples.ec01.all.breadcrumb.home")}}</router-link>
                <span class="breadcrumb-item active">{{$t("samples.ec01.inquiry.regist.title")}}</span>
            </nav>
        </div>
        <div class="col-12">
            <span class="h4">{{$t("samples.ec01.inquiry.regist.title")}}</span>
            <form class="custom-form mt-3">
                <div class="form-group row">
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="familyName" class="col-form-label label-hidden">{{$t("samples.ec01.inquiry.regist.familyName")}}</label>
                            <input v-model="inquiryBean.familyName" type="text" class="form-control border rounded input-hint-visible" name="familyName" :placeholder="$t('samples.ec01.inquiry.regist.familyName')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.familyName" :key="message">{{message}}<br/></template></small>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="firstName" class="col-form-label label-hidden">{{$t("samples.ec01.inquiry.regist.firstName")}}</label>
                            <input v-model="inquiryBean.firstName" type="text" class="form-control border rounded input-hint-visible" name="firstName" :placeholder="$t('samples.ec01.inquiry.regist.firstName')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.firstName" :key="message">{{message}}<br/></template></small>
                        </div>
                    </div>
                    <template v-if="locale == 'ja' || locale === undefined">
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="familyNameKana" class="col-form-label label-hidden">{{$t("samples.ec01.inquiry.regist.familyNameKana")}}</label>
                            <input v-model="inquiryBean.familyNameKana" type="text" class="form-control border rounded input-hint-visible" name="familyNameKana" :placeholder="$t('samples.ec01.inquiry.regist.familyNameKana')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.familyNameKana" :key="message">{{message}}<br/></template></small>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 mt-3">
                        <div>
                            <label for="firstNameKana" class="col-form-label label-hidden">{{$t("samples.ec01.inquiry.regist.firstNameKana")}}</label>
                            <input v-model="inquiryBean.firstNameKana" type="text" class="form-control border rounded input-hint-visible" name="firstNameKana" :placeholder="$t('samples.ec01.inquiry.regist.firstNameKana')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.firstNameKana" :key="message">{{message}}<br/></template></small>
                        </div>
                    </div>
                    </template>
                    <div class="col-12 col-md-12 mt-3">
                        <div>
                            <label for="mail" class="col-form-label label-hidden">{{$t("samples.ec01.inquiry.regist.mail")}}</label>
                            <input v-model="inquiryBean.mail" type="text" class="form-control border rounded input-hint-visible" name="mail" :placeholder="$t('samples.ec01.inquiry.regist.mail')">
                            <small class="form-text text-danger"><template v-for="message in errorsMap.mail" :key="message">{{message}}<br/></template></small>
                        </div>
                    </div>
                    <div class="col-12 col-md-12 mt-3">
                        <div>
                            <label for="content" class="col-form-label label-hidden">{{$t("samples.ec01.inquiry.regist.content")}}</label>
                            <textarea v-model="inquiryBean.content" class="form-control" name="content" rows="5" :placeholder="$t('samples.ec01.inquiry.regist.content')"></textarea>
                            <small class="form-text text-danger"><template v-for="message in errorsMap.content" :key="message">{{message}}<br/></template></small>
                        </div>
                    </div>
                </div>
                <div class="form-group mt-4">
                    <div class="col-12 text-center">
                        <button type="button" class="btn btn-dark" @click="doInquiry()">{{$t("samples.ec01.inquiry.regist.doRegist")}}</button>
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
    name: 'RegistInquiry',
    components: {
        'outputToken': OutputToken
    },
    mixins: [Custom, Consts],
    data() {
        return {
            inquiryBean: {},
            errorsMap: {}
        }
    },
    mounted(){
        this.initFormInputText('.custom-form');
    },
    methods: {
        doInquiry() {
            var url = this.apiDoInquiry();
            var data = this.populatePostData();
            this.$http.post(url, data)
                .then((response) => {
                    var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS') {
                        this.$router.replace({name: "inquirySuccess", params: {status: commandResult.status}});
                    } else if (commandResult.status == 'ERROR') {
                        this.errorsMap = this.convertToErrorsMap(commandResult.result.errors);
                        this.$refs.token.reload();
                    }
                });
        },
        populatePostData() {
            var token = this.$refs.token.get();
            var data = {
                familyName: this.inquiryBean.familyName,
                firstName: this.inquiryBean.firstName,
                familyNameKana: this.inquiryBean.familyNameKana,
                firstNameKana: this.inquiryBean.firstNameKana,
                mail: this.inquiryBean.mail,
                content: this.inquiryBean.content,
            };
            data[token.name] = token.value;
            return data;
        }
    }
}
</script>

<style>

</style>
