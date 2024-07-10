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

export const tokenStore = {
  state: {
    token: '',
    fixToken: ''
  },
  PARAMETER_NAME: '_t',
  HEADER_NAME: 'X-Transaction-Token',
  setToken(token) {
    this.state.token = token
  },
  setFixToken(fixToken) {
    this.state.fixToken = fixToken
  },
  getToken() {
    return this.state.token
  },
  getFixToken() {
    return this.state.fixToken
  },
  getTokenHeader() {
    return {
      [this.HEADER_NAME]: this.getToken()
    }
  },
  getFixTokenHeader() {
    return {
      [this.HEADER_NAME]: this.getFixToken()
    }
  }
}
