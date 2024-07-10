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
          <span class="breadcrumb-item active">{{ $t('samples.ec01.member.regist.title') }}</span>
        </nav>
      </div>
      <div class="col-12">
        <span class="h4">{{ $t('samples.ec01.member.regist.title') }}</span>
        <form class="custom-form mt-3">
          <div class="form-group row">
            <div class="col-12">
              <div>
                <label for="userId" class="col-form-label label-hidden">
                  {{ $t('samples.ec01.member.regist.userId') }}
                </label>
                <input
                  v-model="userBean.userId"
                  type="text"
                  class="form-control border rounded input-hint-visible"
                  name="userId"
                  :placeholder="$t('samples.ec01.member.regist.userId')"
                />
                <small class="form-text text-danger">
                  <template v-for="message in errorsMap.userId" :key="message">
                    {{ message }}<br />
                  </template>
                </small>
              </div>
            </div>
            <div class="col-12 col-md-6 mt-3">
              <div>
                <label for="familyName" class="col-form-label label-hidden">
                  {{ $t('samples.ec01.member.regist.familyName') }}
                </label>
                <input
                  v-model="userBean.familyName"
                  type="text"
                  class="form-control border rounded input-hint-visible"
                  name="familyName"
                  :placeholder="$t('samples.ec01.member.regist.familyName')"
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
                  {{ $t('samples.ec01.member.regist.firstName') }}
                </label>
                <input
                  v-model="userBean.firstName"
                  type="text"
                  class="form-control border rounded input-hint-visible"
                  name="firstName"
                  :placeholder="$t('samples.ec01.member.regist.firstName')"
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
                    {{ $t('samples.ec01.member.regist.familyNameKana') }}
                  </label>
                  <input
                    v-model="userBean.familyNameKana"
                    type="text"
                    class="form-control border rounded input-hint-visible"
                    name="familyNameKana"
                    :placeholder="$t('samples.ec01.member.regist.familyNameKana')"
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
                    {{ $t('samples.ec01.member.regist.firstNameKana') }}
                  </label>
                  <input
                    v-model="userBean.firstNameKana"
                    type="text"
                    class="form-control border rounded input-hint-visible"
                    name="firstNameKana"
                    :placeholder="$t('samples.ec01.member.regist.firstNameKana')"
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
                <label for="mail" class="col-form-label label-hidden">
                  {{ $t('samples.ec01.member.regist.mail') }}
                </label>
                <input
                  v-model="userBean.mail"
                  type="text"
                  class="form-control border rounded input-hint-visible"
                  name="mail"
                  :placeholder="$t('samples.ec01.member.regist.mail')"
                />
                <small class="form-text text-danger">
                  <template v-for="message in errorsMap.mail" :key="message">
                    {{ message }}<br />
                  </template>
                </small>
              </div>
            </div>
          </div>
          <!-- eslint-disable-next-line vue/no-v-html -->
          <div class="row memberAgreeContent" v-html="memberAgree.content"></div>
          <div class="form-group mt-4">
            <div class="col-12 text-center">
              <button type="button" class="btn btn-dark" @click="confirmMemberInfo()">
                {{ $t('samples.ec01.member.regist.doRegist') }}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { Custom } from '../../mixins/Custom'
import { Consts } from '../../mixins/Consts'
import { tokenService } from '../utils/tokenService'

export default {
  name: 'Regist',
  mixins: [Custom, Consts],
  data() {
    return {
      userBean: {
        userId: '',
        familyName: '',
        firstName: '',
        familyNameKana: '',
        firstNameKana: '',
        mail: ''
      },
      memberAgree: {},
      errorsMap: {},
      token: {
        name: '',
        value: ''
      }
    }
  },
  created() {
    const url = this.apiInputMemberInfo()
    this.$http.get(url).then((response) => {
      const commandResult = response.data
      if (commandResult.status == 'SUCCESS') {
        this.memberAgree = commandResult.memberAgree
      } else {
        console.log(response)
      }
    })
  },
  mounted() {
    this.initFormInputText('.custom-form')
  },
  methods: {
    async confirmMemberInfo() {
      const url = this.apiConfirmMemberInfo()
      const data = this.populatePostData()
      const headers = {
        ...tokenService.getTokenHeader()
      }
      const response = await this.$http.post(url, data, { headers })
      const commandResult = response.data
      if (commandResult.status == 'SUCCESS') {
        const encodedData = encodeURIComponent(JSON.stringify(this.userBean))
        this.$router.replace({ name: 'registConfirm', query: { userBean: encodedData } })
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
        userId: this.userBean.userId,
        familyName: this.userBean.familyName,
        firstName: this.userBean.firstName,
        familyNameKana: this.userBean.familyNameKana,
        firstNameKana: this.userBean.firstNameKana,
        mail: this.userBean.mail
      }
      data[this.token.name] = this.token.value
      return data
    }
  }
}
</script>

<style></style>
