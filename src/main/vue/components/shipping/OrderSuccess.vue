<!-- 
  Copyright (C) 2018 DENTSU SOKEN INC. All Rights Reserved.
  
  Unless you have purchased a commercial license,
  the following license terms apply:
  
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.
  
  You should have received a copy of the GNU Affero General Public License
  along with this program. If not, see &lt;https://www.gnu.org/licenses/&gt;.
-->

<template>
  <div class="row">
    <div class="col-12 d-none d-md-block">
      <span class="h4"> </span>
      <nav class="breadcrumb all-breadcrumb">
        <router-link class="breadcrumb-item text-primary" :to="{ name: 'top' }">
          {{ $t('samples.ec01.all.breadcrumb.home') }}
        </router-link>
        <span class="breadcrumb-item active">{{ $t('samples.ec01.shipping.input.title') }}</span>
        <span class="breadcrumb-item active">{{ $t('samples.ec01.shipping.confirm.title') }}</span>
        <span class="breadcrumb-item active">{{ $t('samples.ec01.shipping.success.title') }}</span>
      </nav>
    </div>
    <div class="col-12 col-md-12">
      <span class="h4">{{ $t('samples.ec01.shipping.success.title') }}</span>
      <div class="alert alert-success mt-3" role="alert">
        <p class="mb-0">
          <strong>{{ $t('samples.ec01.shipping.success.msg.p1') }}</strong>
        </p>
        <p class="mb-0">
          <strong>{{ $t('samples.ec01.shipping.success.msg.p2') }}</strong>
        </p>
      </div>
    </div>
    <div class="col-12 mt-4 text-center clearfix">
      <div class="float-md-end">
        <router-link class="btn btn-dark" :to="{ name: 'top' }" replace>
          {{ $t('samples.ec01.shipping.success.return') }}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OrderSuccess',
  beforeRouteUpdate(to, from, next) {
    // 不正な画面遷移が発生したと判断
    if (['confirmShippingInfo'].indexOf(from.name) == -1 || to.params.status != 'SUCCESS') {
      next(new Error('samples.ec01.exception.invalidTransition'))
    } else {
      next()
    }
  },
  props: {
    status: {
      type: String,
      default: ''
    }
  }
}
</script>

<style></style>
