]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/role/">角色列表</a></li><%--
		<c:if test="${isadmin }">	<li><a href="${ctx}/sys/role/form">角色添加</a></li></c:if>
	--%></ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>角色名称</th><th>操作</th></tr>
		<c:forEach items="${list}" var="role">
			<tr>
				<td><a href="form?id=${role.id}">${role.name}</a></td>
				
				<td>
				 <c:if test="${isadmin }">	
				 	<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a> 
				 	<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
				 	<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</c:if>
					<c:if test="${!isadmin }">
						<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>