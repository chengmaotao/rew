<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>推荐意见投票结果管理</title>

	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
	
		$(document).ready(function() {
			trCorlor();
			if(${message != null and message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			}
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
<c:if test="${iscs }">
	<form:form id="searchForm" modelAttribute="rewscopekpi" action="${ctx}/zyzz/rewrecomidea/result" method="post" class="">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
			    <th>专业组</th>
			    <th>申报类别</th>
			    <th>项目名称</th>
			    <th>小组推荐意见</th>
			    <th>评分</th>
			    <th>总票数/投票数</th>
			    <th>是否通过</th>
			   <c:if test="${!isShow }">
			    	<shiro:hasPermission name="zyzz2"><th>操作</th></shiro:hasPermission>
			    </c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewrecomidea" varStatus="s">
			<tr>
				<td>${s.count }</td>
				<td>
					${rewrecomidea.groupName }		    
				    <c:if test="${rewrecomidea.groupSonName != null and  rewrecomidea.groupSonName != ''}"> 
				      -- ${rewrecomidea.groupSonName }
				    </c:if>
				</td>
				<td>${rewrecomidea.categoryName }</td>
				<td>${fns:getProjectByProjectId(rewrecomidea.projectid).projectname}</td>
				<td> ${fns:getAwardLevelById(rewrecomidea.recomidea).levelname}</td>
				<td>${rewrecomidea.scope }</td>
				
                <td>${rewrecomidea.allNum }/${rewrecomidea.num }</td>
                <td>${rewrecomidea.result }</td>
                <c:if test="${!isShow }">
	                 <shiro:hasPermission name="zyzz2">
		                <td>
		                	<a href="${ctx}/zyzz/rewrecomidea/myForm?id=${rewrecomidea.id}&projectId=${rewrecomidea.projectid}">修改</a>
		                </td>
	                </shiro:hasPermission>
                </c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</c:if>
</body>
</html>