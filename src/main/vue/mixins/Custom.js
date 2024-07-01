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

export const Custom = {
    methods: {
        // 入力フォームのテキストボックスに入力ヒント用ラベルの表示と非表示を制御するスクリプト
        initFormInputText(selector) {
            const forms = document.querySelectorAll(selector);
            forms.forEach(form => {
                const inputs = form.querySelectorAll("input[type='text'], input[type='password'], textarea");
                inputs.forEach(input => {
                    const label = input.parentElement.querySelector("label");

                    const updateLabelVisibility = () => {
                        const text = input.value;
                        if (text.length > 0) {
                            input.classList.remove("input-hint-visible");
                            input.classList.add("input-hint-hidden");
                            label.classList.remove("label-hidden");
                            label.classList.add("label-visible");
                        } else {
                            input.classList.remove("input-hint-hidden");
                            input.classList.add("input-hint-visible");
                            label.classList.remove("label-visible");
                            label.classList.add("label-hidden");
                        }
                    };

                    input.addEventListener('keyup', updateLabelVisibility);
                    updateLabelVisibility();
                });
            });
        },
        // BeanValidationが生成したエラーリストをMapに変換する
        convertToErrorsMap(errors) {
            const errorsMap = {};
            if (errors && errors.length > 0) {
                errors.forEach(error => {
                    const key = error.propertyPath;
                    const val = error.errorMessages;
                    errorsMap[key] = val;
                });
            }
            return errorsMap;
        }
    },
    computed: {
        locale() {
            return this.$i18n.locale;
        }
    }
}
