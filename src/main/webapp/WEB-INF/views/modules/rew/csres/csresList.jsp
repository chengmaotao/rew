<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>初审投票结果</title>
	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message != null and message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			}
		});
		
		function submitFormByLevel(){
			$("#searchForm").submit();
        	return false;
		}
		
		function printit(url){
			var h = window.screen.height - 10;
			var w = window.screen.width - 10;
			var par = $('input[name="levelId"]:checked').val();
			if(par == undefined){
				par = "";
			}
			url = url + "?levelId=" + par
			window.open(url,"","top=0,left=0,height=" + h + ",width=" + w + ",toolbar=no,menubar=no,resizable=yes,location=no,status=yes,scrollbars=yes")
		}
	</script>
</head>
<body>
<c:if test="${iscs }">
	<form:form id="searchForm" modelAttribute="rewcsResult" action="${ctx}/csres/res/levelList" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
		    <li><label>奖励等级：</label> 
				<form:radiobuttons path="levelId" items="${awardLevelList}" itemLabel="levelname" itemValue="id" htmlEscape="false" onclick="submitFormByLevel()"/>
				<%-- 
			    <span><input type="radio" id="radionAllId" name="levelId" value="" onclick="submitFormByLevel()"><label for="radionAllId">所有等级</label></span>
			    --%>
		    </li>
		    <c:if test="${isShow }">
		    	<li class="btns">&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="button" onclick="printit('${ctx}/csres/res/print')" value="打印"/></li>
		    </c:if>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
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
			<c:forEach items="${results}" var="rewcsRes" varStatus="s">
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
			    
			    <td>${allNum}/${rewcsRes.num }</td>
			    <td>${rewcsRes.levelName }</td>
			</tr>
			</c:forEach>
		</table>
</c:if>
</body>
</html>