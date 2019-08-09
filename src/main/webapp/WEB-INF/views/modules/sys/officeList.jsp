<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报单位管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/list">申报单位列表</a></li>
		<li><a href="${ctx}/sys/office/form">申报单位添加</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>申报单位</th><th>备注</th><th>操作</th></tr></thead>
		<tbody>
			<c:forEach items="${list}" var="office">
			<tr>
				<td>${office.name}</td>
				<td>${office.remarks}</td>
				<td>
    				<a href="${ctx}/sys/office/form?id=${office.id}">修改</a>
					<a href="${ctx}/sys/office/delete?id=${office.id}" onclick="return confirmx('确认要删除该机构吗？', this.href)">删除</a>

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>