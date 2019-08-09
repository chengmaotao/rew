<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖励等级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dict/rewawardlevel/">奖励等级列表</a></li>
		<li><a href="${ctx}/dict/rewawardlevel/form">奖励等级添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="rewawardlevel" action="${ctx}/dict/rewawardlevel/" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/><%--
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	--%></form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>奖励等级</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewawardlevel">
			<tr>
			    <td>${rewawardlevel.levelname }</td>
				<td>
    				<a href="${ctx}/dict/rewawardlevel/form?id=${rewawardlevel.id}">修改</a>
					<a href="${ctx}/dict/rewawardlevel/delete?id=${rewawardlevel.id}" onclick="return confirmx('确认要删除该奖励等级吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>