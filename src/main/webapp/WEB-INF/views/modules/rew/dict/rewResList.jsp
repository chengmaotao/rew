<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目流程结果管理</title>
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
		<li class="active"><a href="${ctx}/dict/rewRes/">项目流程结果列表</a></li>
		
		<c:if test="${fn:length(page.list) == 0}">
			<li><a href="${ctx}/dict/rewRes/form">项目流程结果添加</a></li>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="rewRes" action="${ctx}/dict/rewRes/" method="post" class="">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>初审是否结束</th>
				<th>复审是否结束</th>
				<th>年度</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewRes">
			<tr>
				<td>${rewRes.csres == '0' ? '未结束' : '结束' }</td>
				<td>
					${rewRes.fsres == '0' ? '未结束' : '结束' }
				</td>
				<td>${rewRes.currentYear }</td>
				<td>
    				<a href="${ctx}/dict/rewRes/form?id=${rewRes.id}">修改</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>