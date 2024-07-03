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
  <div class="col-sm-12 col-md-9">
    <div class="row">
      <div class="col-12">
        <div class="border-top"></div>
        <nav class="breadcrumb all-breadcrumb">
          <router-link class="breadcrumb-item text-primary" :to="{ name: 'top' }">
            {{ $t('samples.ec01.all.breadcrumb.home') }}
          </router-link>
          <span class="breadcrumb-item active">{{ $t('samples.ec01.member.login.title') }}</span>
        </nav>
      </div>
      <div class="col-12">
        <span class="h4">{{ $t('samples.ec01.member.login.title') }}</span>
        <form class="custom-form mt-3">
          <div class="form-group row">
            <div class="col-12">
              <div>
                <label for="id" class="col-form-label label-hidden">
                  {{ $t('samples.ec01.member.login.userid') }}
                </label>
                <input
                  v-model="user.id"
                  type="text"
                  name="id"
                  class="form-control border rounded input-hint-visible"
                  :placeholder="$t('samples.ec01.member.login.userid')"
                />
              </div>
            </div>
            <div class="col-12 mt-3">
              <div>
                <label for="password" class="col-form-label label-hidden">
                  {{ $t('samples.ec01.member.login.password') }}
                </label>
                <input
                  v-model="user.password"
                  type="password"
                  name="password"
                  class="form-control border rounded input-hint-visible"
                  :placeholder="$t('samples.ec01.member.login.password')"
                />
              </div>
            </div>
          </div>
          <div class="form-group mt-5">
            <div class="col-12 text-center">
              <button type="button" class="btn btn-dark" @click="login()">
                {{ $t('samples.ec01.member.login.doLogin') }}
              </button>
            </div>
          </div>
          <div class="col-12 text-left">
            <p>{{ $t('samples.ec01.member.login.registMemberInfo') }}</p>
            <p>
              <router-link class="text-dark" :to="{ name: 'regist' }">
                {{ $t('samples.ec01.member.login.registMember') }}
              </router-link>
            </p>
            <p>{{ $t('samples.ec01.member.login.payInfo') }}</p>
            <p>
              <router-link class="text-dark" :to="{ name: 'inputShippingInfoNoMember' }">
                {{ $t('samples.ec01.member.login.pay') }}
              </router-link>
            </p>
          </div>
          <output-token ref="token"></output-token>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { Custom } from '../../mixins/Custom'
import { Consts } from '../../mixins/Consts'
import OutputToken from '../token/OutputToken.vue'

export default {
  name: 'Login',
  components: {
    outputToken: OutputToken
  },
  mixins: [Custom, Consts],
  data() {
    return {
      user: { id: '', password: '' }
    }
  },
  mounted() {
    this.initFormInputText('.custom-form')
  },
  methods: {
    login() {
      var url = this.apiDoLogin()
      var data = this.populatePostData()
      this.$http.post(url, data).then((response) => {
        var commandResult = response.data
        if (commandResult.status == 'SUCCESS') {
          this.$router.push({ path: commandResult['samples.ec01.login.redirect'] })
        } else if (commandResult.status == 'ERROR') {
          this.$router.push({
            name: 'genericError',
            params: { exception: 'org.iplass.mtp.auth.login.LoginFailedException' }
          })
        }
      })
    },
    populatePostData() {
      var token = this.$refs.token.get()
      var data = { id: this.user.id, password: this.user.password }
      data[token.name] = token.value
      return data
    }
  }
}
</script>

<style></style>
