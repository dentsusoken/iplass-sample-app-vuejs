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

package samples.ec01.command.product;

import java.util.List;
import java.util.stream.Stream;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.RestJson;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.entity.query.Limit;
import org.iplass.mtp.entity.query.condition.expr.And;
import org.iplass.mtp.entity.query.condition.predicate.Equals;
import org.iplass.mtp.entity.query.condition.predicate.In;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.command.exception.EntityDataNotFoundException;
import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.Category;
import samples.ec01.entity.Product;
import samples.ec01.entity.ProductSubInfo;

@WebApi(
		name = "samples/ec01/product/detail", 
		displayName = "詳細画面用アクション", 
		accepts = RequestType.REST_JSON,
		restJson = @RestJson(parameterName = "param"),
		methods = MethodType.GET, 
		privileged = true, 
		results = {
				DetailCommand.RESULT_PRODUCT_INFO,
				DetailCommand.RESULT_SUB_INFO_LIST,
				DetailCommand.RESULT_PRODUCT_LIST })
@CommandClass(
		name = "samples/ec01/product/DetailCommand", 
		displayName = "商品詳細コマンド")
public class DetailCommand implements Command {

	public static final String RESULT_PRODUCT_INFO = "productInfo";
	public static final String RESULT_SUB_INFO_LIST = "subInfoList";
	public static final String RESULT_PRODUCT_LIST = "productList";
	private final String PARAM_PRODUCT_ID = "productId";
	private final String PRODUCT_CATEGORY_OID = Product.CATEGORY + "." + Category.OID;
	private final int RESULT_LIMIT = 3;

	@Override
	public String execute(RequestContext request) {
		String productId = request.getParam(PARAM_PRODUCT_ID);

		// 対象カテゴリの商品一覧の検索
		Product product = EntityDaoHelper.load(Product.DEFINITION_NAME, productId);
		if (product == null) {
			throw new EntityDataNotFoundException(PARAM_PRODUCT_ID + ": " + productId);
		}
		request.setAttribute(RESULT_PRODUCT_INFO, product);
		// 商品サブ情報 の取得
		ProductSubInfo[] subInfo = product.getSubInfo();
		if (subInfo != null) {
			Object[] subOids = Stream.of(subInfo).map(sub -> sub.getOid()).toArray();
			In in = new In(ProductSubInfo.OID, subOids);
			String[] properties = new String[] { ProductSubInfo.NAME, ProductSubInfo.DESCRIPTION };
			List<ProductSubInfo> subInfoList = EntityDaoHelper.searchDistinctEntity(ProductSubInfo.DEFINITION_NAME, in,
					properties);
			request.setAttribute(RESULT_SUB_INFO_LIST, subInfoList);
		}

		// オススメ商品の検索
		Equals recommendCond = new Equals(Product.RECOMMEND, true);
		// カテゴリ一致している製品
		Category category = product.getCategory();
		Equals categoryCond = new Equals(PRODUCT_CATEGORY_OID, category.getOid());
		// 検索処理の実行
		And conds = new And(recommendCond, categoryCond);
		Limit limit = new Limit(RESULT_LIMIT);
		String[] propertis = new String[] { Product.OID, Product.NAME, Product.PRICE, Product.PRODUCT_IMG };
		List<Product> productList = EntityDaoHelper.searchDistinctEntity(Product.DEFINITION_NAME, conds, limit, propertis);
		if (productList.size() > 0) {
			request.setAttribute(RESULT_PRODUCT_LIST, productList);
		}

		return Constants.CMD_EXEC_SUCCESS;
	}
}
