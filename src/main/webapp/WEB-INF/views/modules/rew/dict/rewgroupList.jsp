<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业组管理</title>
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
		<li class="active"><a href="${ctx}/dict/rewgroup/">专业组列表</a></li>
		<li><a href="${ctx}/dict/rewgroup/form">专业组添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="rewgroup" action="${ctx}/dict/rewgroup/" method="post" class="">
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
			    <th style="min-width: 100px;max-width: 100px;width: 100px">专业组</th>
			    <th>评审范围</th>
			    <th>状态</th>
				<th style="min-width: 80px;max-width: 80px;width: 80px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewgroup">
			<tr>
			    <td style="min-width: 100px;max-width: 100px;width: 100px">${rewgroup.groupname }</td>
			    <td>${rewgroup.reviewscope }</td>
			    <td>${rewgroup.isenable == '1' ? "启用" : "禁用" }</td>
				<td style="min-width: 80px;max-width: 80px;width: 80px">
    				<a href="${ctx}/dict/rewgroup/form?id=${rewgroup.id}">修改</a>
					<%--<a href="${ctx}/dict/rewgroup/delete?id=${rewgroup.id}" onclick="return confirmx('确认要删除该专业组吗？', this.href)">删除</a>
				--%></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>