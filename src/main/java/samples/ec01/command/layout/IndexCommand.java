/*
 * Copyright (C) 2018 INFORMATION SERVICES INTERNATIONAL - DENTSU, LTD. All Rights Reserved.
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

package samples.ec01.command.layout;

import java.util.List;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.action.ActionMapping;
import org.iplass.mtp.command.annotation.action.Result;
import org.iplass.mtp.command.annotation.action.Result.Type;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.bean.CartBean;
import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.Category;
import samples.ec01.utils.Consts;

@ActionMapping(
		name = "samples/ec01/", 
		displayName = "index画面", 
		privilaged = true, 
		result = @Result(
				type = Type.JSP, 
				value = "/jsp/samples/ec01/index.jsp"),
		command= {})
@WebApi(
		name = "samples/ec01/layout/defaultLayout", 
		displayName = "標準レイアウトWebAPI", 
		accepts = RequestType.REST_JSON, 
		methods = MethodType.GET, 
		privilaged = true, 
		results = { 
				IndexCommand.RESULT_CATEGORY_LIST, 
				IndexCommand.RESULT_TOTAL_AMOUNT })
@CommandClass(
		name = "samples/ec01/layout/IndexCommand", 
		displayName = "標準レイアウトコマンド")
public class IndexCommand implements Command {

	public static final String RESULT_CATEGORY_LIST = "categoryList";
	public static final String RESULT_TOTAL_AMOUNT = "totalAmount";

	@Override
	public String execute(RequestContext request) {
		String[] properites = new String[] { Category.OID, Category.NAME };
		List<Category> categoryList = EntityDaoHelper.searchDistinctEntity(Category.DEFINITION_NAME, null, properites);
		if (categoryList.size() > 0) {
			request.setAttribute(RESULT_CATEGORY_LIST, categoryList);
		}
		// セッション情報からカートの中身の金額をセットする
		CartBean cart = (CartBean) request.getSession().getAttribute(Consts.CART_BEAN_SESSION_KEY);
		long totalAmount = 0;
		if (cart != null) {
			totalAmount = cart.getTotalAmount();
		}
		request.setAttribute(RESULT_TOTAL_AMOUNT, totalAmount);

		return Constants.CMD_EXEC_SUCCESS;
	}
}
