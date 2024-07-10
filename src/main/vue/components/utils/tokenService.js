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

import axios from 'axios'
import { tokenStore } from '../stores/token'

const PARAMETER_NAME = '_t'
const HEADER_NAME = 'X-Transaction-Token'

export const tokenService = {
  async fetchToken(apiUrl) {
    const response = await axios.post(
      apiUrl,
      {},
      {
        headers: {
          'Content-Type': 'application/json',
          'X-Requested-With': 'XMLHttpRequest'
        }
      }
    )
    const commandResult = response.data
    if (commandResult.status == 'SUCCESS') {
      const token = {
        name: PARAMETER_NAME,
        value: commandResult.tokenValue
      }
      tokenStore.setToken(token.value)
      return token
    }

    return null
  },
  getTokenHeader() {
    return {
      [HEADER_NAME]: tokenStore.getToken()
    }
  }
}
