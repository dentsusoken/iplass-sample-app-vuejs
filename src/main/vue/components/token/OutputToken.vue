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
    
</template>

<script>
import {Consts} from '../../mixins/Consts'

export default {
    name: 'OutputToken',
    mixins: [Consts],
    data: function() {
        return {
            token: {
                name: "",
                value: ""
            }
        }
    },
    created: function() {
        this.reload();
    },
    methods: {
        // トークンを取得する
        get: function() {
            return this.token;
        },
        // トークンをリロードする
        reload: function() {
            var url = this.apiOutputToken();
            var data = {};
            this.$http.post(url, data)
            .then((response) => {
                var commandResult = response.data;
                if (commandResult.status == 'SUCCESS'){
                    this.token.name = commandResult.tokenName;
                    this.token.value = commandResult.tokenValue;
                } 
            });
        }
    }
}
</script>

<style>

</style>
