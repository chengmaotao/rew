<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>复审第一轮投票管理</title>
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
	<shiro:hasPermission name="fsdylpszj"><label style="text-align: right; display: block; margin-right: 20px"> <b>投票总数：${allNum } /已投票数：${fn:length(fsdyltpNums)}</b></label></shiro:hasPermission>
	<input type="hidden" value="${num }" id="synumId"> <%--还可以提交的数目 --%>
	<form action="${ctx}/fs/fsdyl/initrewfsdyl/saveAllData" method="post" id="myFormId" name="myFormName">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>复审第一轮序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>项目名称</th><%--
				    <th>初审结果</th>
				    <ch>初审投票数/总票数</ch>
					--%><shiro:hasPermission name="fsdylpszj"><th>同意授特等奖</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${fsdylList}" var="rewfsdyl" varStatus="s">
				<tr>
					<td>${s.count } <input type="hidden" name="fsdyls[${s.index }].id" value="${rewfsdyl.id }"></td>
					<td>${fns:getGroupById(rewfsdyl.groupId).groupname} </td>
					<td>${fns:getCagegoryById(rewfsdyl.categoryId).categoryname}</td>
					<td>
						${rewfsdyl.projectName }
					</td><%--
					<td>${fns:getAwardLevelById(rewfsdyl.csresLevelId).name}</td>
					<td>${csresAllNum }/${csresNum }</td>
					--%><shiro:hasPermission name="fsdylpszj"><td>${rewfsdyl.isAgree =="1" ? "同意" : ""}</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form>
</c:if>
	<div class="rewFoot">${rewFoot }</div>
</body>
</html>