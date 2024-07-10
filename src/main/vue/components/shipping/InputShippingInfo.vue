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
          <span class="breadcrumb-item active">{{ $t('samples.ec01.shipping.input.title') }}</span>
        </nav>
      </div>
      <div class="col-12 col-md-12">
        <h5>{{ $t('samples.ec01.shipping.input.title') }}</h5>
      </div>
      <div class="col-12 col-md-12 mt-3">
        <div>
          <label for="mail" class="col-form-label label-hidden">
            {{ $t('samples.ec01.shipping.input.mail') }}
          </label>
          <input
            v-model="shippingBean.mail"
            type="text"
            class="form-control border rounded input-hint-visible"
            name="mail"
            :placeholder="$t('samples.ec01.shipping.input.mail')"
          />
          <small class="form-text text-danger">
            <template v-for="message in errorsMap.mail" :key="message">
              {{ message }}<br />
            </template>
          </small>
        </div>
      </div>
      <div class="col-12 col-md-6 mt-3">
        <div>
          <label for="familyName" class="col-form-label label-hidden">
            {{ $t('samples.ec01.shipping.input.familyName') }}
          </label>
          <input
            v-model="shippingBean.familyName"
            type="text"
            class="form-control border rounded input-hint-visible"
            name="familyName"
            :placeholder="$t('samples.ec01.shipping.input.familyName')"
          />
          <small class="form-text text-danger">
            <template v-for="message in errorsMap.familyName" :key="message">
              {{ message }}<br />
            </template>
          </small>
        </div>
      </div>
      <div class="col-12 col-md-6 mt-3">
        <div>
          <label for="firstName" class="col-form-label label-hidden">
            {{ $t('samples.ec01.shipping.input.firstName') }}
          </label>
          <input
            v-model="shippingBean.firstName"
            type="text"
            class="form-control border rounded input-hint-visible"
            name="firstName"
            :placeholder="$t('samples.ec01.shipping.input.firstName')"
          />
          <small class="form-text text-danger">
            <template v-for="message in errorsMap.firstName" :key="message">
              {{ message }}<br />
            </template>
          </small>
        </div>
      </div>
      <template v-if="locale == 'ja' || locale === undefined">
        <div class="col-12 col-md-6 mt-3">
          <div>
            <label for="familyNameKana" class="col-form-label label-hidden">
              {{ $t('samples.ec01.shipping.input.familyNameKana') }}
            </label>
            <input
              v-model="shippingBean.familyNameKana"
              type="text"
              class="form-control border rounded input-hint-visible"
              name="familyNameKana"
              :placeholder="$t('samples.ec01.shipping.input.familyNameKana')"
            />
            <small class="form-text text-danger">
              <template v-for="message in errorsMap.familyNameKana" :key="message">
                {{ message }}<br />
              </template>
            </small>
          </div>
        </div>
        <div class="col-12 col-md-6 mt-3">
          <div>
            <label for="firstNameKana" class="col-form-label label-hidden">
              {{ $t('samples.ec01.shipping.input.firstNameKana') }}
            </label>
            <input
              v-model="shippingBean.firstNameKana"
              type="text"
              class="form-control border rounded input-hint-visible"
              name="firstNameKana"
              :placeholder="$t('samples.ec01.shipping.input.firstNameKana')"
            />
            <small class="form-text text-danger">
              <template v-for="message in errorsMap.firstNameKana" :key="message">
                {{ message }}<br />
              </template>
            </small>
          </div>
        </div>
      </template>
      <div class="col-12 col-md-12 mt-3">
        <div>
          <label for="address" class="col-form-label label-hidden">
            {{ $t('samples.ec01.shipping.input.address') }}
          </label>
          <input
            v-model="shippingBean.address"
            type="text"
            class="form-control border rounded input-hint-visible"
            name="address"
            :placeholder="$t('samples.ec01.shipping.input.address')"
          />
          <small class="form-text text-danger">
            <template v-for="message in errorsMap.address" :key="message">
              {{ message }}<br />
            </template>
          </small>
        </div>
      </div>
      <div class="col-12 col-md-12 mt-3">
        <div>
          <label for="tel" class="col-form-label label-hidden">
            {{ $t('samples.ec01.shipping.input.tel') }}
          </label>
          <input
            v-model="shippingBean.tel"
            type="text"
            class="form-control border rounded input-hint-visible"
            name="tel"
            :placeholder="$t('samples.ec01.shipping.input.tel')"
          />
          <small class="form-text text-danger">
            <template v-for="message in errorsMap.tel" :key="message">
              {{ message }}<br />
            </template>
          </small>
        </div>
      </div>
      <div class="col-12 mt-4 text-center clearfix">
        <div class="float-md-right">
          <button type="button" class="btn btn-dark btn-lg" @click="confirmShippingInfo()">
            {{ $t('samples.ec01.shipping.input.delivery') }}
          </button>
        </div>
        <div class="text-md-start mt-2">
          <router-link class="btn btn-sm" :to="{ name: 'cartInfo' }" role="button">
            &lt;
            {{ $t('samples.ec01.shipping.input.return') }}
          </router-link>
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
  name: 'InputShippingInfo',
  mixins: [Custom, Consts],
  props: {
    editShippingBean: {
      type: Object,
      default: () => ({
        mail: '',
        tel: '',
        address: '',
        familyName: '',
        firstName: '',
        familyNameKana: '',
        firstNameKana: ''
      })
    }
  },
  data() {
    return {
      shippingBean: {
        mail: '',
        tel: '',
        address: '',
        familyName: '',
        firstName: '',
        familyNameKana: '',
        firstNameKana: ''
      },
      errorsMap: {},
      token: {
        name: '',
        value: ''
      }
    }
  },
  created() {
    const name = this.$router.currentRoute.name || this.$router.currentRoute.value.name
    if (name == 'inputShippingInfoNoMember') {
      this.loadContentNoMember()
    } else if (name == 'inputShippingInfo') {
      this.loadContent()
    } else if (name == 'editShippingInfo') {
      const decodedData = JSON.parse(decodeURIComponent(this.$route.query.editShippingBean))
      this.shippingBean = decodedData
    }
  },
  mounted() {
    this.initFormInputText('.custom-form')
  },
  methods: {
    loadContent() {
      const url = this.apiInputShippingInfo()
      this.$http.get(url).then((response) => {
        const commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.shippingBean = commandResult.shippingBean
        } else if (commandResult.status == 'ERROR') {
          const redirectURL = commandResult.redirectURL
          this.$router.push(redirectURL)
        }
      })
    },
    loadContentNoMember() {
      const url = this.apiInputShippingInfoNoMember()
      this.$http.get(url).then((response) => {
        const commandResult = response.data
        if (commandResult.status == 'ERROR') {
          const redirectURL = commandResult.redirectURL
          this.$router.push(redirectURL)
        }
      })
    },
    async confirmShippingInfo() {
      const url = this.apiConfirmShippingInfo()
      const data = this.populatePostData()
      const headers = {
        ...tokenService.getTokenHeader()
      }
      const response = await this.$http.post(url, data, { headers })
      const commandResult = response.data
      if (commandResult.status == 'SUCCESS') {
        const encodedData = encodeURIComponent(JSON.stringify(this.shippingBean))
        this.$router.replace({
          name: 'confirmShippingInfo',
          query: { shippingBean: encodedData }
        })
      } else if (commandResult.status == 'ERROR') {
        this.errorsMap = this.convertToErrorsMap(commandResult.result.errors)
        const newToken = await tokenService.fetchToken(this.apiOutputToken())
        if (newToken) {
          this.token = newToken
        }
      }
    },
    populatePostData() {
      const data = {
        mail: this.shippingBean.mail,
        tel: this.shippingBean.tel,
        address: this.shippingBean.address,
        familyName: this.shippingBean.familyName,
        firstName: this.shippingBean.firstName,
        familyNameKana: this.shippingBean.familyNameKana,
        firstNameKana: this.shippingBean.firstNameKana
      }
      data[this.token.name] = this.token.value
      return data
    }
  }
}
</script>

<style></style>
