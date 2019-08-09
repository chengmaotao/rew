<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>初审第一轮投票管理</title>

	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message != null and message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			}
			$("#myFormId").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
		
		function submitFormByGroup(){
			$("#searchForm").submit();
        	return false;
		}

		

	</script>
</head>
<body>
<c:if test="${iscs }">
	<form:form id="searchForm" modelAttribute="rewcsfirstvote" action="${ctx}/csfirst/initrewcsfirstvote/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form"><%--
			<li><label>项目名称：</label> 
     			<form:select path="projectid" class="input-xlarge2" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${projects }" itemLabel="projectname" itemValue="projectid" htmlEscape="false"/>
     			</form:select>
		    </li>
		    --%>
		    <li><label>专业组：</label> 
				<form:radiobuttons path="groupId" items="${groupList}" itemLabel="groupname" itemValue="id" htmlEscape="false" onclick="submitFormByGroup()"/>
				<%--
				<span><input type="radio" id="radionAllId" name="groupId" value="" onclick="submitFormByGroup()"><label for="radionAllId">所有组</label></span>
				--%>
		    </li>
		    <%-- 
		    <li><label>同意：</label> 
				<form:radiobuttons path="isAgreeKey" items="${isAgree}" itemLabel="value" itemValue="key" htmlEscape="false" onclick="submitFormByGroup()"/>
		    </li>		
			<li class="clearfix"></li>
			--%>
		</ul>
	</form:form>
	<%--<sys:message content="${message}"/>
	--%>
	<shiro:hasPermission name="dyltp"><label style="text-align: right; display: block; margin-right: 20px"> <b>投票总数：${allNum } /已投票数：${fn:length(voteds)}</b></label></shiro:hasPermission>
	<input type="hidden" value="${num }" id="synumId"> <%--还可以提交的数目 --%>
	
	<form action="${ctx}/csfirst/initrewcsfirstvote/saveAllData" id="myFormId" name="myFormName" method="post">
	
		<input type="hidden" name="groupId" value="${hidGroupId }">
		<input type="hidden" name="isAgreeKey" value="${hidIsagree }">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>初审第一轮序号</th>
				    <th>专业组</th>				    
				    <th>申报类别</th>
				    <th>项目名称</th>
				    <th>评分</th>
				    <th>推荐意见</th>
				    <shiro:hasPermission name="dyltp"><th>同意</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="rewcsfirstvote" varStatus="s">
				<tr>
				    <td>
				    	${(page.pageNo - 1) * page.pageSize + s.count }
						<input type="hidden" name="csfirstvotes[${s.index }].id" value="${rewcsfirstvote.id }">
				    </td>
				    <td>
				    	${rewcsfirstvote.groupName }		    
					    <c:if test="${rewcsfirstvote.groupSonName != null and  rewcsfirstvote.groupSonName != ''}"> 
					      -- ${rewcsfirstvote.groupSonName }
					    </c:if>
				    </td>				    
				    <td>${rewcsfirstvote.categoryName}</td>
				    <td>
				    	${fns:getProjectByProjectId(rewcsfirstvote.projectid).projectname}
				    	<input type="hidden" name="csfirstvotes[${s.index }].projectid" value="${rewcsfirstvote.projectid }">
				    </td>
				   
				    <td>
				    	${rewcsfirstvote.scope}
				    	<input type="hidden" name="csfirstvotes[${s.index }].scope" value="${rewcsfirstvote.scope}">
				    </td>
				    <td> 
				    	${fns:getAwardLevelById(rewcsfirstvote.recomIdea).levelname}
				    	<input type="hidden" name="csfirstvotes[${s.index }].recomIdea" value="${rewcsfirstvote.recomIdea}">
				    </td>
				    
				     <shiro:hasPermission name="dyltp"><td>${rewcsfirstvote.isagree =="1" ? "同意" : ""} </td></shiro:hasPermission>
				    
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form>
	<div class="pagination">${page}</div>
</c:if>
	<div class="rewFoot">${rewFoot }</div>

	<script type="text/javascript">
		
		function page(n,s){
			synums = $("#synumId").val();
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	
	</script>
</body>
</html>