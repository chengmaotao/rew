<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>复审第二轮投票管理</title>
	
	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message !=null && message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			}
		});
		
	</script>
</head>
<body>
<c:if test="${isfs }">
	<form:form id="searchForm" modelAttribute="rewcsres" action="${ctx}/fs/fsdyl/initrewfsdyl/list2" method="post" class="">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	--%></form:form>

	<shiro:hasPermission name="fsdelpszj"><label style="text-align: right; display: block; margin-right: 20px"> <b>投票总数：${allNum } /已投票数：${fn:length(fsdyltpNums)}</b></label></shiro:hasPermission>
	<input type="hidden" value="${num }" id="synumId"> <%--还可以提交的数目 --%>
	<form action="${ctx}/fs/fsdyl/initrewfsdyl/saveAllData" method="post" id="myFormId" name="myFormName">
		
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>复审第二轮序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>项目名称</th>
				    <%--<shiro:hasPermission name="fsdelgly"><th>评审专家</th></shiro:hasPermission>
					--%><shiro:hasPermission name="fsdelpszj"><th>同意授特等奖</th></shiro:hasPermission>
					<%--<shiro:hasPermission name="fsdelgly"><th>同意授特等奖</th></shiro:hasPermission>
				--%></tr>
			</thead>
			<tbody>
			<c:forEach items="${rewdsdelList}" var="rewfsdyl" varStatus="s">
				<tr>
					<td>${s.count } <input type="hidden" name="fsdyls[${s.index }].id" value="${rewfsdyl.id }"></td>
					<td>${fns:getGroupById(rewfsdyl.groupId).groupname} </td>
					<td>${fns:getCagegoryById(rewfsdyl.categoryId).categoryname}</td>
					<td>
						${rewfsdyl.projectName }
						<%--<input type="hidden" name="fsdyls[${s.index }].projectid" value="${rewfsdyl.projectid }">
					--%></td>
					<%--<shiro:hasPermission name="fsdelgly"><td>${fns:getUserById(rewfsdyl.createBy).name}</td></shiro:hasPermission>
					--%><shiro:hasPermission name="fsdelpszj">
					
							<td>${rewfsdyl.isAgree =="1" ? "同意" : ""}</td>
						
					</shiro:hasPermission>
					<%--<shiro:hasPermission name="fsdelgly"><td>${rewfsdyl.isAgree =="1" ? "同意" : ""}</td></shiro:hasPermission>
				--%></tr>
			</c:forEach>
			</tbody>
		</table>

	</form>
	<%--<div class="pagination">${page}</div>
	
	--%>
</c:if>	
<div class="rewFoot">${rewFoot }</div>

</body>
</html>