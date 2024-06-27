<%--
 Copyright (C) 2018 DENTSU SOKEN INC. All Rights Reserved.

 Unless you have purchased a commercial license,
 the following license terms apply:

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation, either version 3 of the
 License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program. If not, see <https://www.gnu.org/licenses/>.
 --%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ page import="org.iplass.mtp.web.template.TemplateUtil"%>
<%@ taglib prefix="m" uri="http://iplass.org/tags/mtp"%>

<!DOCTYPE html>
<html lang="<%=TemplateUtil.getLanguage() %>">
<%
	request.setAttribute("staticContentPath", TemplateUtil.getStaticContentPath());
%>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- For Compatible -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!-- Optional meta tags -->
    <meta name="keywords" content="キーワードその1,キーワードその2,キーワードその3,キーワードその4,キーワードその5" />
    <meta name="description" content="ご自身のWebサイトの説明を記載します" />
    <link rel="stylesheet" href="${m:esc(staticContentPath)}/styles/open-iconic-bootstrap.min.css?cv=<%=TemplateUtil.getAPIVersion()%>">
    <link rel="stylesheet" href="${m:esc(staticContentPath)}/styles/index.css?cv=<%=TemplateUtil.getAPIVersion()%>">
    <script setup>
        const lang = '<%=TemplateUtil.getLanguage() %>';
        const tcPath = '<%=TemplateUtil.getTenantContextPath() %>';
    </script>
    <script type="module" src="${m:esc(staticContentPath)}/scripts/build.js?cv=<%=TemplateUtil.getAPIVersion()%>"></script>
</head>
<body>
	<div id="app">
        <RouterView />
    </div>
</body>
</html>