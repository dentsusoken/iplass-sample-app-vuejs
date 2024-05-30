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

package samples.ec01.command.member;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.ManagerLocator;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.CommandInvoker;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.RestJson;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.bean.CartBean;
import samples.ec01.utils.Consts;

@WebApi(
		name = "samples/ec01/member/doLogin", 
		displayName = "ログイン認証処理WebAPI", 
		accepts = RequestType.REST_JSON, 
		methods = MethodType.POST, 
		privileged = true, 
		restJson = @RestJson(parameterName = "param"), 
		results = { Consts.LOGIN_REDIRECT_PATH_ATTRIBUTE })
@CommandClass(
		name = "samples/ec01/member/DoLoginCommand", 
		displayName = "ログイン認証コマンド")
public class DoLoginCommand implements Command {
	
	public static final String RESULT_CART_BEAN = "cartBean";

	@Override
	public String execute(RequestContext request) {
		// ログインするとSessionが初期化されるためCart情報を保持
		CartBean cartBean = (CartBean) request.getSession().getAttribute(Consts.CART_BEAN_SESSION_KEY);
		// ログイン処理
		CommandInvoker invoke = ManagerLocator.getInstance().getManager(CommandInvoker.class);
		String result = invoke.execute(Consts.LOGIN_COMMAND_PATH, request);
		// 商品情報を再設定
		request.getSession().setAttribute(Consts.CART_BEAN_SESSION_KEY, cartBean);
		// リダイレクト設定
		if (Constants.CMD_EXEC_SUCCESS.equals(result)) {
			request.setAttribute(Consts.LOGIN_REDIRECT_PATH_ATTRIBUTE, "/shipping/inputShippingInfo");
		}
		return result;
	}
}
