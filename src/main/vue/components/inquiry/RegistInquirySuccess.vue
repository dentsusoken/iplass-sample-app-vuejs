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
                <span class="breadcrumb-item active">{{$t("samples.ec01.inquiry.regist.title")}}</span>
                <span class="breadcrumb-item active">{{$t("samples.ec01.inquiry.registSuccess.title")}}</span>
            </nav>
        </div>
        <div class="col-12">
            <span class="h4">{{$t("samples.ec01.inquiry.registSuccess.title")}}</span>
            <div class="mt-3">
                <div class="alert alert-success" role="alert">
                    <p class="mb-0">
                        <strong>{{$t("samples.ec01.inquiry.registSuccess.msg.p1")}}</strong>
                    </p>
                    <p class="mb-0">
                        <strong>{{$t("samples.ec01.inquiry.registSuccess.msg.p2")}}</strong>
                    </p>
                </div>
            </div>
            <div class="col-12 mt-5 text-center">
                <router-link class="btn btn-dark" v-bind:to="{name: 'top'}" replace>{{$t("samples.ec01.inquiry.registSuccess.return")}}</router-link>
            </div>
        </div>
    </div>
</div>    
</template>

<script>
export default {
    name: 'RegistInquirySuccess',
    props: ['status'],
    beforeRouteEnter: function(to, from, next) {
        // 不正な画面遷移が発生したと判断
		if(['inquiry'].indexOf(from.name) == -1 || to.params.status !="SUCCESS") {
			next(new Error('samples.ec01.exception.invalidTransition'));
		} else {
            next();
        }
	}
};
</script>

<style>

</style>
