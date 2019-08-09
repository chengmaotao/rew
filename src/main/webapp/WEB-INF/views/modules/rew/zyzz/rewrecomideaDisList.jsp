<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>小组推荐意见管理</title>

	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message != null and message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			
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

	<c:choose>
		<c:when test="isShow">
			<form:form id="searchForm" modelAttribute="rewrecomidea" action="${ctx}/zyzz/rewrecomidea/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<ul class="ul-form">
				   <li><label>项目名称：</label> 
		     			<form:select path="projectid" class="input-xlarge2" >
		        			<form:option value="" label="请选择"/>
		        			<form:options items="${projects }" itemLabel="projectname" itemValue="projectid" htmlEscape="false"/>
		     			</form:select>
				    </li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>		
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="rewrecomidea" action="${ctx}/zyzz/rewrecomidea/" method="post" class="">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			</form:form>		
		</c:otherwise>
	</c:choose>


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
			    <shiro:hasPermission name="gly3"><th>专业组组长</th></shiro:hasPermission>
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
				<shiro:hasPermission name="gly3"><td>${fns:getUserById(rewrecomidea.createBy).name}</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</c:if>
	<div class="rewFoot">${rewFoot }</div>
</body>
</html>