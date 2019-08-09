<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>打印初审投票结果</title>

	<%--<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>	--%>
</head>
<body>
	<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT>
	
	    
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>项目名称</th>
				    <th>申报单位</th>
				    
				    <th>总人数/投票数目</th>
				    <th>初审结果</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${printList}" var="rewcsRes" varStatus="s">
				<c:choose>
					<c:when test="${rewcsRes.levelId =='1' }">
						<!-- 特等奖 -->
						<tr style="background-color:red;">
					</c:when>
					<c:when test="${rewcsRes.levelId =='2' }">
						<!-- 一等奖 -->
						<tr style="background-color:yellow;">
					</c:when>
					<c:when test="${rewcsRes.levelId =='3' }">
						<!-- 二等奖 -->
						<tr style="background-color:#FFCCCC;">
					</c:when>
					<c:otherwise>
						<!-- 不授奖 -->
						<tr>					
					</c:otherwise>					
				</c:choose>
			
			    <td>${(page.pageNo - 1) * page.pageSize + s.count }</td>
			    <td>${fns:getGroupById(rewcsRes.groupId).groupname} 
			    </td>
			    <td>${fns:getCagegoryById(rewcsRes.categoryId).categoryname}</td>
			    <td>  ${rewcsRes.projectname }</td>
			    <td>${fns:getOfficeById(rewcsRes.companyId).name}</td>
			    
			    <td>${rewcsRes.allNum}/${rewcsRes.num }</td>
			    <td>${fns:getAwardLevelById(rewcsRes.levelId).levelname}</td>
			</tr>
			</c:forEach>
		</table>
	<script type="text/javascript">
		function printit(){
			wb.execwb(6,6);
		}
		printit();
	</script>
</body>
</html>