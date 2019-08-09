<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>主审专家对应项目管理</title>
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
		<li class="active"><a href="${ctx}/zszj/rewidea/zxlist">主审专家对应项目列表</a></li>
		<li><a href="${ctx}/zszj/rewidea/zxform">主审专家对应项目添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="rewidea" action="${ctx}/zszj/rewidea/zxlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>主审专家：</label><form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		    <li><label>项目名称：</label><form:input path="projectName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>主审专家</th>
			    <th>项目名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewzx">
			<tr>
			    <td>${rewzx.userName }</td>
			    <td>${rewzx.projectName }</td>
				<td>
					<a href="${ctx}/zszj/rewidea/zxdelete?projectid=${rewzx.projectid}&userid=${rewzx.userid}" onclick="return confirmx('确认要删除该主审专家对应的项目吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>