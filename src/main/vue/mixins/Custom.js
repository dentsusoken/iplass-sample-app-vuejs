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

import $ from 'jquery'
export const Custom = {
    methods: {
        //入力フォームのテキストボックスに入力ヒント用ラベルの表示と非表示を制御するスクリプト 
        initFormInputText: function (form) {
            $(form).find("input[type='text'],input[type='password'],textarea").keyup(function() {
                var t = $(this);
                var l = t.parent().children("label");
                var text = t.val();
                if (text.length > 0) {
                    t.removeClass("input-hint-visible").addClass("input-hint-hidden");
                    l.removeClass("label-hidden").addClass("label-visible");
                }
                else {
                    t.removeClass("input-hint-hidden").addClass("input-hint-visible");
                    l.removeClass("label-visible").addClass("label-hidden");
                }
            }).trigger("keyup");
        },
        // BeanValidationが生成したエラーリストをMapに変換する
        convertToErrorsMap: function(errors) {
            var errorsMap = {};
            if (errors === undefined || errors.length > 0) {
                for (var i = 0; i < errors.length; i++) {
                    var key = errors[i].propertyPath;
                    var val = errors[i].errorMessages;
                    errorsMap[key] = val;
                }
            }
            return errorsMap;
        }
    },
    computed: {
        locale: function() {
            return this.$i18n.locale;
        }
    }
}