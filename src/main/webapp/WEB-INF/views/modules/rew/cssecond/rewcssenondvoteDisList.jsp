<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>初审第二轮投票管理</title>
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
	<form:form id="searchForm" modelAttribute="rewcssenondvote" action="${ctx}/cssecond/initrewcssenondvote/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form"><%--
			<li><label>项目名称：</label> 
     			<form:select path="projectid" class="input-xlarge2" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${projects }" itemLabel="projectname" itemValue="projectid" htmlEscape="false"/>
     			</form:select>
		    </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
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
	
	--%><shiro:hasPermission name="deltp">
			<label style="text-align: right; display: block; margin-right: 20px"> 
			 <b>特等奖投票总数：${allNum0 } /已投票数：${fn:length(voted0s)}&nbsp;&nbsp;</b> 
			 <b>一等奖投票总数：${allNum1 } /已投票数：${fn:length(voted1s)}</b></label>
	   </shiro:hasPermission>
	<input type="hidden" value="${num0 }" id="synumId0"> <%--特等奖还可以提交的数目 --%>
	<input type="hidden" value="${num1 }" id="synumId1"> <%--特等奖还可以提交的数目 --%>
	<form action="${ctx}/cssecond/initrewcssenondvote/saveAllData" id="myFormId" name="myFormName" method="post">
	
		<input type="hidden" name="groupId" value="${hidGroupId }">
		<input type="hidden" name="isAgreeKey" value="${hidIsagree }">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					    <th>初审第二轮序号</th>
					    <th>专业组</th>	
					    <th>申报类别</th>
					    <th>项目名称</th>
					    <th>第一轮票数</th>
					    
					     <shiro:hasPermission name="deltp">
						    <th>特等奖</th>
						    <th>一等奖</th>
					    </shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="rewcssenondvote" varStatus="s">
				<tr>
	                	<td>
					    	${(page.pageNo - 1) * page.pageSize + s.count }
							<input type="hidden" name="cssenondvotes[${s.index }].id" value="${rewcssenondvote.id }">
					    </td>
					    <td>
					    	${rewcssenondvote.groupName }		    
						    <c:if test="${rewcssenondvote.groupSonName != null and  rewcssenondvote.groupSonName != ''}"> 
						      -- ${rewcssenondvote.groupSonName }
						    </c:if>
					    </td>
					    <td>${rewcssenondvote.categoryName}</td>
					    <td>
					    	${fns:getProjectByProjectId(rewcssenondvote.projectid).projectname}
					    	<input type="hidden" name="cssenondvotes[${s.index }].projectid" value="${rewcssenondvote.projectid }">
					    </td>
					    
					    <td>${rewcssenondvote.countNum}</td>

						<shiro:hasPermission name="deltp">					    
						    <td>
								${rewcssenondvote.isagree0 =="1" ? "同意" : ""}
						    </td>
						    <td>
								${rewcssenondvote.isagree1 =="1" ? "同意" : ""}
							</td>
					    </shiro:hasPermission>
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
			synums0 = $("#synumId0").val();
			synums1 = $("#synumId1").val();
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	
	</script>
</body>
</html>