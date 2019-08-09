<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>复审第三轮投票结果</title>

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
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				    <th>序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>申报单位</th>
				    <th>项目名称</th>
				    <th>总人数/投票数目</th>
				    <th>第三轮复审结果</th>
				</tr>
		</thead>
		<tbody>
		<c:forEach items="${results}" var="rewcsRes" varStatus="s">
			<c:choose>
				<c:when test="${rewcsRes.levelId =='3' }">
					<!-- 二等奖 -->
					<tr style="background-color:#FFCCCC;">
				</c:when>

				<c:otherwise>
					<!-- 没有评选上二授奖 -->
					<tr>					
				</c:otherwise>					
			</c:choose>
			    <td>${s.count }</td>
				
			    <td>${fns:getGroupById(rewcsRes.groupId).groupname} 
			    </td>
			    <td>${fns:getCagegoryById(rewcsRes.categoryId).categoryname}</td>
			    <td>${fns:getOfficeById(rewcsRes.companyId).name}</td>
			    <td>  ${rewcsRes.projectname }</td>
			    <td>${allNum}/${rewcsRes.num }</td>
			    <td>${rewcsRes.levelName }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>
</body>
</html>