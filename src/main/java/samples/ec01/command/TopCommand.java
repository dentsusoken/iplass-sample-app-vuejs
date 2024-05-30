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

package samples.ec01.command;

import java.util.List;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.entity.query.Limit;
import org.iplass.mtp.entity.query.condition.predicate.Equals;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.Product;
import samples.ec01.utils.Consts;

@WebApi(
		name = "samples/ec01/top", 
		displayName = "デモトップ画面表示WebAPI", 
		accepts = RequestType.REST_JSON, 
		methods = MethodType.GET, 
		privileged = true,
		results = { TopCommand.RESULT_PRODUCT_LIST })
@CommandClass(
		name = "samples/ec01/TopCommand", 
		displayName = "デモトップ画面表示コマンド")
public class TopCommand implements Command {

	public static final String RESULT_PRODUCT_LIST = "productList";

	@Override
	public String execute(RequestContext request) {
		// オススメ商品の検索
		Equals cond = new Equals(Product.RECOMMEND, true);
		Limit limit = new Limit(Consts.PAGE_SHOWN_LIMIT);
		String[] properties = new String[] { Product.OID, Product.NAME, Product.PRICE, Product.PRODUCT_IMG };
		List<Product> productList = EntityDaoHelper.searchDistinctEntity(Product.DEFINITION_NAME, cond, limit,
				properties);
		if (productList.size() > 0) {
			request.setAttribute(RESULT_PRODUCT_LIST, productList);
		}

		return Constants.CMD_EXEC_SUCCESS;
	}
}
