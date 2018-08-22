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

package samples.ec01.command.search;

import java.util.List;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.RestJson;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.entity.query.condition.predicate.Like;
import org.iplass.mtp.entity.query.condition.predicate.Like.MatchPattern;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.Product;

@WebApi(
		name = "samples/ec01/search/doSearch", 
		displayName = "検索用WebAPI", 
		accepts = RequestType.REST_JSON,
		restJson = @RestJson(parameterName = "param"),
		methods = MethodType.POST,
		privilaged = true, 
		results = {
				KeywordSearchCommand.RESULT_KEYWORD,
				KeywordSearchCommand.RESULT_PRODUCT_LIST})
@CommandClass(
		name = "samples/ec01/search/KeywordSearchCommand", 
		displayName = "検索用コマンド")
public class KeywordSearchCommand implements Command {

	public static final String RESULT_PRODUCT_LIST = "productList";
	public static final String RESULT_KEYWORD = "keyword";
	private final String PARAM_PRODUCT_NAME = "productName";

	@Override
	public String execute(RequestContext request) {
		// 検索商品名
		String productName = request.getParam(PARAM_PRODUCT_NAME);
		Like like = new Like(Product.NAME, productName, MatchPattern.PARTIAL);
		String[] properties = new String[] { Product.OID, Product.NAME, Product.PRODUCT_IMG, Product.PRICE };
		List<Product> productList = EntityDaoHelper.searchDistinctEntity(Product.DEFINITION_NAME, like, properties);
		if (productList.size() > 0) {
			request.setAttribute(RESULT_PRODUCT_LIST, productList);
		}
		request.setAttribute(RESULT_KEYWORD, productName);

		return Constants.CMD_EXEC_SUCCESS;
	}
}
