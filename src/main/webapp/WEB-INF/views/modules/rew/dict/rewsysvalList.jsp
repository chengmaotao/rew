<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投票数目管理</title>
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
		<li class="active"><a href="${ctx}/dict/rewsysval/">投票数目列表</a></li>
		<c:if test="${fn:length(page.list) == 0}">
			<li><a href="${ctx}/dict/rewsysval/form">投票数目添加</a></li>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="rewsysval" action="${ctx}/dict/rewsysval/" method="post" class="">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>初审总投票数</th>
				<th>初审特等奖数</th>
				<th>初审一等奖数</th>
				<th>复审第一轮投票</th>
				<th>复审第二轮投票</th>
				<th>复审第三轮投票</th>
				<th>评审年度</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewsysval">
			<tr>
				<td>${rewsysval.csdyltp }</td>
				<td>${rewsysval.csdeltpt }</td>
				<td>${rewsysval.csdeltpy }</td>
				<td>${rewsysval.fsdyltp }</td>
				<td>${rewsysval.fsdeltp }</td>
				<td>${rewsysval.fsdsltp }</td>
				<td>${rewsysval.currentYear }</td>
				<td>
    				<a href="${ctx}/dict/rewsysval/form?id=${rewsysval.id}">修改</a>
					<!-- <a href="${ctx}/dict/rewsysval/delete?id=${rewsysval.id}" onclick="return confirmx('确认要删除该投票数目吗？', this.href)">删除</a>-->
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>