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

package samples.ec01.command.member;

import java.util.List;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.MemberAgree;

@WebApi(
		name = "samples/ec01/member/inputMemberInfo", 
		displayName = "会員情報入力WebAPI", 
		accepts = RequestType.REST_JSON, 
		methods = MethodType.GET, 
		privilaged = true, 
		results = { InputMemberInfoCommand.RESULT_MEMBER_AGREE })
@CommandClass(
		name = "samples/ec01/member/InputMemberInfoCommand", 
		displayName = "会員情報入力コマンド")
public class InputMemberInfoCommand implements Command {

	public static final String RESULT_MEMBER_AGREE = "memberAgree";

	@Override
	public String execute(RequestContext request) {
		// 会員の規約の呼び出し
		List<MemberAgree> memberAgree = EntityDaoHelper.searchEntity(MemberAgree.DEFINITION_NAME, null,
				MemberAgree.CONTENT);
		if (memberAgree.size() > 0) {
			request.setAttribute(RESULT_MEMBER_AGREE, memberAgree.get(0));
		}

		return Constants.CMD_EXEC_SUCCESS;
	}
}
