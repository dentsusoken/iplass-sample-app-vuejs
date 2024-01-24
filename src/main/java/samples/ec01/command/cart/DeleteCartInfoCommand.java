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

package samples.ec01.command.cart;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.RestJson;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.bean.CartBean;
import samples.ec01.command.exception.EntityDataNotFoundException;
import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.Product;
import samples.ec01.utils.Consts;

@WebApi(
		name = "samples/ec01/cart/deleteCartInfo", 
		displayName = "カート情報削除WebAPI", 
		accepts = RequestType.REST_JSON,
		restJson = @RestJson(parameterName = "param"),		
		methods = MethodType.POST, 
		privilaged = true, 
		synchronizeOnSession = true, 
		results = Consts.CART_BEAN_SESSION_KEY)
@CommandClass(
		name = "samples/ec01/cart/DeleteCartInfoCommand", 
		displayName = "カート情報削除コマンド")
public class DeleteCartInfoCommand implements Command {

	private final String PARAM_DELETE_ID = "deleteId";

	@Override
	public String execute(RequestContext request) {
		// カートから削除された商品Id
		String deleteId = request.getParam(PARAM_DELETE_ID);
		// セッションからカート情報を取得する
		CartBean cartBean = (CartBean) request.getSession().getAttribute(Consts.CART_BEAN_SESSION_KEY);
		if (cartBean != null && cartBean.getCartSize() > 0) {
			Product product = EntityDaoHelper.load(Product.DEFINITION_NAME, deleteId);
			if (product == null) {
				throw new EntityDataNotFoundException(PARAM_DELETE_ID + ": " + deleteId);
			}
			Long price = product.getPrice();
			cartBean.removeCartItem(deleteId, price);
		}
		request.setAttribute(Consts.CART_BEAN_SESSION_KEY, cartBean);
		return Constants.CMD_EXEC_SUCCESS;
	}
}
