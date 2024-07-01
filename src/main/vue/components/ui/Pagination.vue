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
<nav aria-label="Page navigation" v-if="pagination !== undefined">
 <ul class="pagination pagination-md justify-content-center">
 	<!-- 前へ -->
	<li class="page-item">
	    <a class="page-link border-0 text-dark disabled" aria-label="Previous" v-if="currentPage - 1 < 0">
        	<span aria-hidden="true">&laquo;{{$t("samples.ec01.all.pagination.prev")}}</span>
			<span class="visually-hidden">Previous</span>
	    </a>
	    <router-link class="page-link border-0 text-dark" v-bind:to="{path: paginationUrl + (currentPage - 1)}" aria-label="Previous" v-else>
			<span aria-hidden="true">&laquo;{{$t("samples.ec01.all.pagination.prev")}}</span>
			<span class="visually-hidden">Previous</span>
		</router-link>
	</li>
<!-- ページ番号 -->
<template v-for="(page, i) in totalPage">
    <li class="page-item disabled" v-bind:key="i" v-if="i == currentPage">
        <a class="page-link border-0 text-dark bg-light">{{i + 1}}</a>
    </li>  
    <li class="page-item" v-else-if="i < 3 || i > totalPage - 3">
        <router-link class="page-link border-0 text-dark" v-bind:to="{path: paginationUrl + i}">{{i + 1}}</router-link>
    </li>
    <li class="page-item" v-else-if="i == 3">
        <a class="page-link border-0 text-dark">......</a>
    </li>
</template>
    <!-- 次へ -->
    <li class="page-item">
        <a class="page-link border-0 text-dark disabled" aria-label="Next" v-if="currentPage + 1 >= totalPage">
    		<span aria-hidden="true">{{$t("samples.ec01.all.pagination.next")}}</span>
    		<span class="visually-hidden">Next</span>
        </a>
        <router-link class="page-link border-0 text-dark" v-bind:to="{path: paginationUrl + (currentPage + 1)}" aria-label="Next" v-else>
            <span aria-hidden="true">{{$t("samples.ec01.all.pagination.next")}}</span>
    		<span class="visually-hidden">Next</span> 
        </router-link>
    </li>
 </ul>
</nav>
</template>

<script>
export default {
    name: 'Pagination',
    props: ['pagination', 'paginationUrl'],
    computed: {
        currentPage() {
            return this.pagination === undefined ? 0 : this.pagination.currentPage;
        },
        totalPage() {
            return this.pagination === undefined ? 0 : this.pagination.totalPage;
        }
    }
}
</script>

<style>

</style>
