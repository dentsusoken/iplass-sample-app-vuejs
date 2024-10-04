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

package samples.ec01.command.token;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.impl.web.token.TokenStore;
import org.iplass.mtp.web.template.TemplateUtil;
import org.iplass.mtp.web.template.TemplateUtil.TokenOutputType;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;


@WebApi(
		name = "samples/ec01/token/outputToken", 
		displayName = "", 
		accepts = RequestType.REST_JSON, 
		methods = MethodType.POST, 
		privileged = true, 
		synchronizeOnSession = true,
		results = { 
				OutputToken.RESULT_TOKEN_NAME,
				OutputToken.RESULT_TOKEN_VALUE })
@CommandClass(
		name = "samples/ec01/token/outputToken", 
		displayName = "トークン出力コマンド")
public class OutputToken implements Command {

	public static final String RESULT_TOKEN_NAME = "tokenName";
	public static final String RESULT_TOKEN_VALUE = "tokenValue";

	@Override
	public String execute(RequestContext request) {
		String value = TemplateUtil.outputToken(TokenOutputType.VALUE, true);
		request.setAttribute(RESULT_TOKEN_NAME, TokenStore.TOKEN_PARAM_NAME);
		request.setAttribute(RESULT_TOKEN_VALUE, value);

		return Constants.CMD_EXEC_SUCCESS;
	}
}
