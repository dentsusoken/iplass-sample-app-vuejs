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

import { createRouter, createWebHistory } from 'vue-router'
import DefaultLayout from '../components/layout/DefaultLayout.vue'
import ShippingLayout from '../components/layout/ShippingLayout.vue'
import Top from '../components/Top.vue'
import Detail from '../components/product/Detail.vue'
import Category from '../components/product/Category.vue'
import NewInfo from '../components/news/NewInfo.vue'
import Login from '../components/member/Login.vue'
import Regist from '../components/member/Regist.vue'
import RegistConfirm from '../components/member/RegistConfirm.vue'
import RegistSuccess from '../components/member/RegistSuccess.vue'
import TradeLaw from '../components/shop/TradeLaw.vue'
import FullTextSearch from '../components/search/FullTextSearch.vue'
import SearchResult from '../components/search/SearchResult.vue'
import CartInfo from '../components/cart/CartInfo.vue'
import RegistInquiry from '../components/inquiry/RegistInquiry.vue'
import RegistInquirySuccess from '../components/inquiry/RegistInquirySuccess.vue'
import GenericError from '../components/error/GenericError.vue'
import InputShippingInfo from '../components/shipping/InputShippingInfo.vue'
import ConfirmShippingInfo from '../components/shipping/ConfirmShippingInfo.vue'
import OrderSuccess from '../components/shipping/OrderSuccess.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
        path: '/', component: DefaultLayout, props: {},
        children: [
            { path: 'top', name: 'top', component: Top, props: {} },
            { path: 'news/newInfo', name: 'newInfo', component: NewInfo, props: route => ({ page: route.query.pageId }) },
            { path: 'product/detail', name: 'detail', component: Detail, props: route => ({ productId: route.query.productId }) },
            { path: 'product/category', name: 'category', component: Category, props: route => ({ categoryId: route.query.categoryId, page: route.query.page }) },
            { path: 'member/login', name: 'login', component: Login, props: {} },
            { path: 'member/inputMemberInfo', name: 'regist', component: Regist, props: {} },
            { path: 'member/confirmMemberInfo', name: 'registConfirm', component: RegistConfirm, props: true },
            { path: 'member/registMemberSuccess', name: 'registSuccess', component: RegistSuccess, props: true },
            { path: 'search/fullTextSearch', name: 'fullTextSearch', component: FullTextSearch, props: {} },
            { path: 'search/searchResult', name: 'searchResult', component: SearchResult, props: route => ({ keyword: route.query.keyword }) },
            { path: 'shop/tradeLaw', name: 'tradeLaw', component: TradeLaw, props: {} },
            { path: 'cart/cartInfo', name: 'cartInfo', component: CartInfo, props: {} },
            { path: 'inquiry/inquiry', name: 'inquiry', component: RegistInquiry, props: {} },
            { path: 'inquiry/inquirySuccess', name: 'inquirySuccess', component: RegistInquirySuccess, props: true },
            { path: 'error/genericError', name: 'genericError', component: GenericError, props: true }
        ]
    },
    {
        path: '/shipping', component: ShippingLayout, props: {},
        children: [
            { path: 'inputShippingInfo', name: 'inputShippingInfo', component: InputShippingInfo, props: {} },
            { path: 'inputShippingInfoNoMember', name: 'inputShippingInfoNoMember', component: InputShippingInfo, props: {} },
            { path: 'editShippingInfo', name: 'editShippingInfo', component: InputShippingInfo, props: true },
            { path: 'confirmShippingInfo', name: 'confirmShippingInfo', component: ConfirmShippingInfo, props: true },
            { path: 'orderSuccess', name: 'orderSuccess', component: OrderSuccess, props: true }
        ]
    }
  ]
})

export default router
