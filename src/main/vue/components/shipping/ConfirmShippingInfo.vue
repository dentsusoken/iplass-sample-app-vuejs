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
  <form class="custom-form">
    <div class="form-group row">
      <div class="col-12 d-none d-md-block">
        <span class="h4"> </span>
        <nav class="breadcrumb all-breadcrumb">
          <router-link class="breadcrumb-item text-primary" :to="{ name: 'top' }">
            {{ $t('samples.ec01.all.breadcrumb.home') }}
          </router-link>
          <a
            class="breadcrumb-item text-primary"
            href="_blank"
            @click.prevent="editShippingInfo()"
            >{{ $t('samples.ec01.shipping.input.title') }}</a
          >
          <span class="breadcrumb-item active">
            {{ $t('samples.ec01.shipping.confirm.title') }}
          </span>
        </nav>
      </div>
      <div class="col-12 col-md-12">
        <h5>{{ $t('samples.ec01.shipping.confirm.title') }}</h5>
        <div class="card col bg-light">
          <div class="card-body pl-0 text-left" style="position: relative">
            <div v-if="localShippingBean !== null">
              <div class="row">
                <div class="col-12 col-md-12 text-muted fw-bold">
                  {{ $t('samples.ec01.shipping.confirm.addressInfo') }}
                </div>
                <div class="col-12 col-md-4 text-muted fw-bold shipping-contract-label">
                  {{ $t('samples.ec01.shipping.input.mail') }}
                </div>
                <div class="col-12 col-md-8 shipping-contract-text">
                  {{ localShippingBean.mail }}
                </div>
                <div class="col-12 col-md-4 text-muted fw-bold shipping-contract-label">
                  {{ $t('samples.ec01.shipping.confirm.fullName') }}
                </div>
                <div class="col-12 col-md-8 shipping-contract-text">
                  {{ localShippingBean.familyName }}&nbsp;{{ localShippingBean.firstName }}
                </div>
                <template v-if="locale == 'ja' || locale === undefined">
                  <div class="col-12 col-md-4 text-muted fw-bold shipping-contract-label">
                    {{ $t('samples.ec01.shipping.confirm.fullNameKana') }}
                  </div>
                  <div class="col-12 col-md-8 shipping-contract-text">
                    {{ localShippingBean.familyNameKana }}&nbsp;{{
                      localShippingBean.firstNameKana
                    }}
                  </div>
                </template>
                <div>
                  <button
                    type="button"
                    class="btn btn-link shipping-contract-edit"
                    @click="editShippingInfo()"
                  >
                    {{ $t('samples.ec01.shipping.confirm.edit') }}
                  </button>
                </div>
                <div class="col-12 col-md-4 text-muted fw-bold shipping-contract-label">
                  {{ $t('samples.ec01.shipping.input.address') }}
                </div>
                <div class="col-12 col-md-8 shipping-contract-text">
                  {{ localShippingBean.address }}
                </div>
                <div class="col-12 col-md-4 text-muted fw-bold shipping-contract-label">
                  {{ $t('samples.ec01.shipping.input.tel') }}
                </div>
                <div class="col-12 col-md-8 shipping-contract-text">
                  {{ localShippingBean.tel }}
                </div>
              </div>
            </div>
            <div v-else>
              <p>Loading...</p>
            </div>
          </div>
        </div>
      </div>
      <div class="col-12 mt-4 text-center clearfix">
        <div class="float-md-right">
          <button type="button" class="btn btn-dark btn-lg" @click="doOrder()">
            {{ $t('samples.ec01.shipping.confirm.order') }}
          </button>
        </div>
        <div class="text-md-start mt-2">
          <button type="button" class="btn btn-link btn-sm" @click="editShippingInfo()">
            &lt;{{ $t('samples.ec01.shipping.confirm.return') }}
          </button>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import { Custom } from '../../mixins/Custom'
import { Consts } from '../../mixins/Consts'
import { tokenService } from '../utils/tokenService'

export default {
  name: 'ConfirmShippingInfo',
  mixins: [Custom, Consts],
  beforeRouteUpdate(to, from, next) {
    // 不正な画面遷移が発生したと判断
    if (
      ['inputShippingInfo', 'inputShippingInfoNoMember', 'editShippingInfo'].indexOf(from.name) ==
        -1 ||
      to.params.shippingBean === undefined
    ) {
      next(new Error('samples.ec01.exception.invalidTransition'))
    } else {
      next()
    }
  },
  props: {
    shippingBean: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      localShippingBean: {
        mail: '',
        tel: '',
        address: '',
        familyName: '',
        firstName: '',
        familyNameKana: '',
        firstNameKana: ''
      },
      token: {
        name: '',
        value: ''
      }
    }
  },
  created() {
    const decodedData = JSON.parse(decodeURIComponent(this.$route.query.shippingBean))
    this.localShippingBean = decodedData
  },
  methods: {
    async doOrder() {
      const url = this.apiDoOrder()
      const data = this.populatePostData()
      const headers = {
        ...tokenService.getTokenHeader()
      }
      const response = await this.$http.post(url, data, { headers })
      const commandResult = response.data
      if (commandResult.status == 'SUCCESS') {
        this.$router.replace({ name: 'orderSuccess', params: { status: commandResult.status } })
        this.$emitter.emit('confirmShippingInfo.order.success')
      } else if (commandResult.status == 'ERROR') {
        const newToken = await tokenService.fetchToken(this.apiOutputToken())
        if (newToken) {
          this.token = newToken
        }
      }
    },
    populatePostData() {
      const data = {}
      data[this.token.name] = this.token.value
      return data
    },
    editShippingInfo() {
      const encodedData = encodeURIComponent(JSON.stringify(this.localShippingBean))
      this.$router.replace({ name: 'editShippingInfo', query: { editShippingBean: encodedData } })
    }
  }
}
</script>

<style></style>
