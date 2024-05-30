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
                <router-link class="breadcrumb-item text-primary" v-bind:to="{name: 'inputMemberInfo'}">{{$t("samples.ec01.member.regist.title")}}</router-link>
                <span class="breadcrumb-item active">{{$t("samples.ec01.member.registConfirm.title")}}</span>
            </nav>
        </div>
        <div class="col-12">
            <span class="h4">{{$t("samples.ec01.member.registConfirm.title")}}</span>
            <form class="custome-confirm-form mt-3">
                <div class="form-group">
                    <div class="alert alert-info w-100" role="alert">
                        <p class="mb-0">
                            <strong>{{$t("samples.ec01.member.registConfirm.registMsg.p1")}}</strong>
                        </p>
                        <p class="mb-0">
                            <strong>{{$t("samples.ec01.member.registConfirm.registMsg.p2")}}</strong>
                        </p>
                    </div>
                    <div class="card col-12 bg-light">
                        <div class="card-body">
                            <div class="row mt-3 border-bottom">
                                <div class="col-12 col-md-4">
                                    <span class="text-muted font-weight-bold">{{$t("samples.ec01.member.regist.userId")}}</span>
                                </div>
                                <div class="col-12 col-md-8">{{userBean.userId}}</div>
                            </div>
                            <div class="row mt-3 border-bottom">
                                <div class="col-12 col-md-4">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.registConfirm.fullName")}}</span>
                                </div>
                                <div class="col-12 col-md-3">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.regist.familyName")}}</span>
                                    &nbsp;{{userBean.familyName}}
                                </div>
                                <div class="col-12 col-md-3">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.regist.firstName")}}</span>
                                    &nbsp;{{userBean.firstName}}
                                </div>
                            </div>
                            <div class="row mt-3 border-bottom" v-if="locale == 'ja' || locale === undefined">
                                <div class="col-12 col-md-4">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.registConfirm.fullNameKana")}}</span>
                                </div>
                                <div class="col-12 col-md-3">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.regist.familyNameKana")}}</span>
                                    &nbsp;{{userBean.familyNameKana}}
                                </div>
                                <div class="col-12 col-md-3">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.regist.firstNameKana")}}</span>
                                    &nbsp;{{userBean.firstNameKana}}
                                </div>
                            </div>
                            <div class="row mt-3 border-bottom">
                                <div class="col-12 col-md-4">
                                    <span class="text-muted  font-weight-bold">{{$t("samples.ec01.member.regist.mail")}}</span>
                                </div>
                                <div class="col-12 col-md-8">{{userBean.mail}}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group mt-4">
                    <div class="col-12 text-center">
                        <button type="button" class="btn btn-dark" v-on:click="registMemberInfo()">{{$t("samples.ec01.member.registConfirm.regist")}}</button>
                    </div>
                </div>
            <output-token ref="token"></output-token>
            </form>
        </div>
    </div>
</div>
</template>

<script>
import { Custom } from "../../mixins/Custom";
import { Consts } from "../../mixins/Consts";
import OutputToken from "../token/OutputToken.vue";

export default {
  name: "RegistConfirm",
  props: ["userBean"],
  mixins: [Custom, Consts],
  components: {
    outputToken: OutputToken
  },
  methods: {
    registMemberInfo: function() {
      var url = this.apiRegistMemberInfo();
      var data = this.populatePostData();
      this.$http.post(url, data)
        .then(response => {
          var commandResult = response.data;
          if (commandResult.status == 'SUCCESS') {
            this.$router.replace({ name: 'registSuccess', params: {status: commandResult.status}});
          } else if (commandResult.status == 'ERROR') {
            this.result = commandResult.result;
            this.$ref.token.reload();
          }
        });
    },
    populatePostData: function() {
      var token = this.$refs.token.get();
      var data = {};
      data[token.name] = token.value;
      return data;
    }
  },
  beforeRouteEnter: function(to, from, next) {
    // 不正な画面遷移が発生したと判断
    if(['regist'].indexOf(from.name) == -1 || to.params.userBean === undefined) {
      next(new Error('samples.ec01.exception.invalidTransition'));
    } else {
      next();
    }
  }
};
</script>

<style>

</style>
