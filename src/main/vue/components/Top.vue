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
	<div class="border-top mb-3"></div>
	<div class="row">
		<div class="col-12 col-md-4" v-for="product in productList" v-bind:key="product.oid">
			<div class="card border-light border-0">
				<router-link v-bind:to="{name: 'detail', query: {productId: product.oid}}" class="h-100">
					<img class="card-img-top img-thumbnail img-fluid all-product-img" v-bind:src="imgUrl(product.productImg)" v-bind:alt="product.name">
				</router-link>
				<div class="card-body pt-md-1 text-center">
					<div>
						<router-link v-bind:to="{name: 'detail', query: {productId: product.oid}}" class="card-link text-dark">{{product.name}}</router-link>
					</div>
					<div class="all-price">{{product.price}}{{$t("samples.ec01.all.yen")}}</div>
				</div>
			</div>
		</div>
	</div>
</div>
</template>

<script>
import {Consts} from '../mixins/Consts'

export default {
	name: 'Top',
	mixins: [Consts],
    data: function() {
        return {
            productList : []
        }
	},
	created: function() {
		var url = this.apiTop();
		this.$http.get(url)
			.then((response) => {
				var commandResult = response.data;
				if(commandResult.status == 'SUCCESS') {
					this.productList = commandResult.productList;
				} else {
					console.log(response);
				}
			});
	}
}
</script>

<style>

</style>
