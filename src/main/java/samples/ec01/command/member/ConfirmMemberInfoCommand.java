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

import java.util.List;

import jakarta.validation.groups.Default;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.webapi.RestJson;
import org.iplass.mtp.command.annotation.webapi.WebApi;
import org.iplass.mtp.command.annotation.webapi.WebApiTokenCheck;
import org.iplass.mtp.command.beanmapper.BeanParamMapper;
import org.iplass.mtp.command.beanmapper.MappingException;
import org.iplass.mtp.web.template.TemplateUtil;
import org.iplass.mtp.webapi.definition.MethodType;
import org.iplass.mtp.webapi.definition.RequestType;

import samples.ec01.bean.UserBean;
import samples.ec01.bean.validator.group.JapaneseChecks;
import samples.ec01.dao.EntityDaoHelper;
import samples.ec01.entity.MemberAgree;
import samples.ec01.utils.Consts;

@WebApi(
		name = "samples/ec01/member/confirmMemberInfo", 
		displayName = "会員情報確認アクション", 
		accepts = RequestType.REST_JSON,
		restJson = @RestJson(parameterName = "param"),
		methods = MethodType.POST, 
		privileged = true, 
		tokenCheck = @WebApiTokenCheck(
				executeCheck = true, 
				consume = true, 
				exceptionRollback = true),  
		results = {
				ConfirmMemberInfoCommand.RESULT_MAPPING_RESULT, 
				ConfirmMemberInfoCommand.RESULT_MEMBER_AGREE })
@CommandClass(
		name = "samples/ec01/member/ConfirmMemberInfoCommand", 
		displayName = "会員情報確認コマンド")
public class ConfirmMemberInfoCommand implements Command {

	private final BeanParamMapper mapper = new BeanParamMapper().withValidation()
			.whitelistPropertyNameRegex("^(userId|mail|familyName(Kana)?|firstName(Kana)?)$");
	public static final String RESULT_MEMBER_AGREE = "memberAgree";
	public static final String RESULT_MAPPING_RESULT = "result";

	@Override
	public String execute(RequestContext request) {
		String result = Constants.CMD_EXEC_SUCCESS;
		// 入力チェック
		try {
			request.getSession().setAttribute(Consts.USER_BEAN_SESSION_KEY, null);
			UserBean userBean = new UserBean();
			// 日本語専用"name_kana"取得フォーム
			if (Consts.LANGUAGE_JA.equals(TemplateUtil.getLanguage()) || TemplateUtil.getLanguage() == null) {
				mapper.populate(userBean, request.getParamMap(), Default.class, JapaneseChecks.class);
			} else {
				mapper.populate(userBean, request.getParamMap(), Default.class);
			}
			request.getSession().setAttribute(Consts.USER_BEAN_SESSION_KEY, userBean);
		} catch (MappingException e) {
			request.setAttribute(RESULT_MAPPING_RESULT, e.getResult());
			result = Constants.CMD_EXEC_ERROR;
		}
		if (Constants.CMD_EXEC_ERROR.equals(result)) {
			// 会員の規約の呼び出し
			String[] properties = new String[] { MemberAgree.CONTENT };
			List<MemberAgree> memberAgree = EntityDaoHelper.searchEntity(MemberAgree.DEFINITION_NAME, null, properties);
			if (memberAgree.size() > 0) {
				request.setAttribute(RESULT_MEMBER_AGREE, memberAgree.get(0));
			}
		}

		return result;
	}
}
