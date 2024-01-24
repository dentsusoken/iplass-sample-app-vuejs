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

export const Consts = {
    data: function () {
        return {
            tcPath: tcPath,
            path: {
                productImg: tcPath + '/samples/ec01/resource/bin?type=productImg&id=',
                category: '/product/category?categoryId=',
                newInfo: '/news/newInfo?page='
            },
            apiPath: {
                defaultLayout: tcPath + '/api/samples/ec01/layout/defaultLayout',
                shippingLayout: tcPath + '/api/samples/ec01/layout/shippingLayout',
                top: tcPath + '/api/samples/ec01/top',
                cartInfo: tcPath + '/api/samples/ec01/cart/cartInfo',
                inputCartInfo: tcPath + '/api/samples/ec01/cart/inputCartInfo',
                updateCartInfo: tcPath + '/api/samples/ec01/cart/updateCartInfo',
                deleteCartInfo: tcPath + '/api/samples/ec01/cart/deleteCartInfo?deleteId=',
                doInquiry: tcPath + '/api/samples/ec01/inquiry/doInquiry',
                doLogin: tcPath + '/api/samples/ec01/member/doLogin',
                inputMemberInfo: tcPath + '/api/samples/ec01/member/inputMemberInfo',
                confirmMemberInfo: tcPath + '/api/samples/ec01/member/confirmMemberInfo',
                registMemberInfo: tcPath + '/api/samples/ec01/member/registMemberInfo',
                newInfo: tcPath + '/api/samples/ec01/news/newInfo?page=',
                category: tcPath + '/api/samples/ec01/product/category?categoryId=',
                detail: tcPath + '/api/samples/ec01/product/detail?productId=',
                fulltextSearch: tcPath + '/api/samples/ec01/search/fulltextSearch',
                doSearch: tcPath + '/api/samples/ec01/search/doSearch',
                inputShippingInfo: tcPath + '/api/samples/ec01/shipping/inputShippingInfo',
                inputShippingInfoNoMember: tcPath + '/api/samples/ec01/shipping/inputShippingInfoNoMember',
                confirmShippingInfo: tcPath + '/api/samples/ec01/shipping/confirmShippingInfo',
                doOrder: tcPath + '/api/samples/ec01/shipping/doOrder',
                tradeLaw: tcPath + '/api/samples/ec01/shop/tradeLaw',
                token: tcPath + '/api/samples/ec01/token/outputToken'
            }
        };
    },
    methods: {
        imgUrl: function(productImg) {
            if (this.tcPath === undefined || productImg === undefined) {
                return "";
            } else {
                return this.path.productImg + productImg.lobId;
            }
        },
        categoryUrl: function(categoryId) {
            return this.path.category + categoryId + '&page=';
        },
        newInfoUrl: function(){
            return this.path.newInfo;
        },
        apiDefaultLayout: function() {
            return this.apiPath.defaultLayout;
        },
        apiShippingLayout: function() {
            return this.apiPath.shippingLayout;
        },
        apiTop: function() {
            return this.apiPath.top;
        },
        apiCartInfo: function() {
            return this.apiPath.cartInfo;
        },
        apiInputCartInfo: function(productId) {
            return this.apiPath.inputCartInfo;
        },
        apiUpdateCartInfo: function() {
            return this.apiPath.updateCartInfo;
        },
        apiDeleteCartInfo: function(productId) {
            return this.apiPath.deleteCartInfo + productId;
        },
        apiDoInquiry: function() {
            return this.apiPath.doInquiry;
        },
        apiDoLogin: function() {
            return this.apiPath.doLogin;
        },
        apiInputMemberInfo: function() {
            return this.apiPath.inputMemberInfo;
        },
        apiConfirmMemberInfo: function() {
            return this.apiPath.confirmMemberInfo;
        },
        apiRegistMemberInfo: function() {
            return this.apiPath.registMemberInfo;
        },
        apiNewInfo: function(pageIndex) {
            return this.apiPath.newInfo + pageIndex;
        },
        apiCategory: function(categoryId, pageIndex) {
            return this.apiPath.category + categoryId + '&page=' + pageIndex;
        },
        apiDetail: function(productId) {
            return this.apiPath.detail + productId;
        },
        apiFulltextSearch: function() {
            return this.apiPath.fulltextSearch;
        },
        apiDoSearch: function() {
            return this.apiPath.doSearch;
        },
        apiInputShippingInfo: function() {
            return this.apiPath.inputShippingInfo;
        },
        apiInputShippingInfoNoMember: function() {
            return this.apiPath.inputShippingInfoNoMember;
        },
        apiConfirmShippingInfo: function() {
            return this.apiPath.confirmShippingInfo;
        },
        apiDoOrder: function() {
            return this.apiPath.doOrder;
        },
        apiTradeLaw: function() {
            return this.apiPath.tradeLaw;
        },
        apiOutputToken: function() {
            return this.apiPath.token;
        }
    }
}