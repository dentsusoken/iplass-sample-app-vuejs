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
                <span class="breadcrumb-item active">{{$t("samples.ec01.layout.defaultLayout.SCTAInfo")}}</span>
            </nav>
        </div>
        <div class="col-12">
            <span class="h4">{{$t("samples.ec01.layout.defaultLayout.SCTAInfo")}}</span>
            <div class="form-group mt-3">
                <div class="alert alert-info w-100" role="alert">
                    <p class="mb-0">
                        <strong>
                            {{shopInfo.shopName}}
                        </strong>
                    </p>
                    <p class="mb-0">
                        <strong>
                            {{shopInfo.shopNameSub}}
                        </strong>
                    </p>
                </div>
                <div class="card col-12 bg-light">
                    <div class="card-body">
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.dealer")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.dealer}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.administrator")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.administrator}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.postNo")}}</span>
                            </div>
                            <div class="col-12 col-md-8"><template v-for="postNo in tradeLaw.postNo">{{postNo}}&nbsp;</template></div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.address")}}</span>
                            </div>
                            <div class="col-12 col-md-8"><template v-for="address in tradeLaw.address">{{address}}&nbsp;</template></div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.tel")}}</span>
                            </div>
                            <div class="col-12 col-md-8"><template v-for="tel in tradeLaw.tel">{{tel}}&nbsp;</template></div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.fax")}}</span>
                            </div>
                            <div class="col-12 col-md-8"><template v-for="fax in tradeLaw.fax">{{fax}}&nbsp;</template></div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.mail")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.mail}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">URL</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.url}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.feeRequired")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.feeRequired}}</div>
                        </div>                      
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.orderingMethod")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.howToOrder}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.paymentMethod")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.howToPayment}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.paymentDate")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.paymentDeadline}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.deliveryTime")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.deliveryTime}}</div>
                        </div>
                        <div class="row mt-3 border-bottom">
                            <div class="col-12 col-md-4 fw-bold">
                                <span class="text-muted">{{$t("samples.ec01.shop.tradeLaw.exchanged")}}</span>
                            </div>
                            <div class="col-12 col-md-8">{{tradeLaw.exchanged}}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="mt-5">
                <div class="col-12 mt-5 text-center">
                <router-link class="btn btn-dark" :to="{name: 'top'}">{{$t("samples.ec01.shop.tradeLaw.back")}}</router-link>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {Consts} from '../../mixins/Consts'

export default {
    name: 'TradeLaw',
    mixins: [Consts],
    data: function() {
        return {
            shopInfo: {},
            tradeLaw: {}
        }
    },
    created: function() {
        this.loadContent();
    },
    methods: {
        loadContent: function() {
            var url = this.apiTradeLaw();
            this.$http.get(url)
                .then((response) => {
                var commandResult = response.data;
                    if(commandResult.status == 'SUCCESS') {
                        this.shopInfo = commandResult.shopInfo;
                        this.tradeLaw = commandResult.tradeLaw;
                    } else {
                        console.log(response);
                    }
                });
        }
    }
}
</script>

<style>

</style>
