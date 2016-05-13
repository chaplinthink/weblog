<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Welcome</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="tablecloth/tablecloth/tablecloth.css" rel="stylesheet"
	type="text/css" media="screen" />
<script type="text/javascript" src="tablecloth/tablecloth/tablecloth.js"></script>
<style>
body {
	margin: 0;
	padding: 0;
	background: #f1f1f1;
	font: 70% Arial, Helvetica, sans-serif;
	color: #555;
	line-height: 150%;
	text-align: left;
}

a {
	text-decoration: none;
	color: #057fac;
}

a:hover {
	text-decoration: none;
	color: #999;
}

h1 {
	font-size: 140%;
	margin: 0 20px;
	line-height: 80px;
}

h2 {
	font-size: 120%;
}

#container {
	margin: 0 auto;
	width: 680px;
	background: #fff;
	padding-bottom: 20px;
}

#content {
	margin: 0 20px;
}

p.sig {
	margin: 0 auto;
	width: 680px;
	padding: 1em 0;
}

form {
	margin: 1em 0;
	padding: .2em 20px;
	background: #eee;
}
</style>
</head>
<body>
	<div id="container">
		<div style="width: 15%; float: right">
			<!-- 这里的href跟form 表单提交时有区别的,  href必须写出action所在的详细地址 -->
			<a>欢迎:${admin.userName}</a>|<a href="a_logout.action">登出</a>
		</div>
		<h1>基于hadoop的web日志分析平台</h1>
		<div id="content">
			<h2>web日志 KPI分析结果列表</h2>
			<table cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>logdate</th>
						<th>pv</th>
						<th>reguser</th>
						<th>ip</th>
						<th>jumper</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${ empty result}">
						<tr>
							<td colspan="4"><h2>没有数据</h2></td>
						</tr>
					</c:if>
					<c:if test="${ not empty result }">
						<c:forEach items="${result}" var="rs">
							<tr>
								<td>${rs.logdate}</td>
								<td>${rs.pv}</td>
								<td>${rs.reguser}</td>
								<td>${rs.ip}</td>
								<td>${rs.jumper}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>