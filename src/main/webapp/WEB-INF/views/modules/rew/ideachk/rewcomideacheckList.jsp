<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>推荐意见投票管理</title>

	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message != ''}){
				if(${message != null}){
					top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
				}
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
			
			// 
			$("#contentTable").on("click","tr",function(e){
				if(e.target.type != 'checkbox'&& e.target.type != 'select-one'){
					var cbx = $(this).find("input[type=checkbox]");
					if($(cbx).attr("checked")){
						$(cbx).removeAttr("checked");
					}else{
						$(cbx).attr("checked",true);
					}
				}
			});
			
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
		<c:when test="${isShow }">
			<form:form id="searchForm" modelAttribute="rewcomideacheck" action="${ctx}/ideachk/rewcomideacheck/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<ul class="ul-form">
					
					<li><label>项目名称：</label> 
		     			<form:select path="projectid" >
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
			<form:form id="searchForm" modelAttribute="rewcomideacheck" action="${ctx}/ideachk/rewcomideacheck/" method="post" class="">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			</form:form>			
		</c:otherwise>
	</c:choose>


	
	<sys:message content="${message}"/>
	
	<form action="${ctx}/ideachk/rewcomideacheck/saveAllData" id="myFormId" name="myFormName" method="post">
		<input type="hidden" name="token" value="${token }">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>项目名称</th>		  
				    <th>评分</th>
				    <th>推荐意见</th>
				    
				    <c:choose>
				    	<c:when test="${isShow }">
				    		<shiro:hasPermission name="gly7"><th>评审专家</th></shiro:hasPermission>
				    		<shiro:hasPermission name="gly7"><th>同意</th></shiro:hasPermission>
				    	</c:when>
				    	<c:otherwise>
				    		<shiro:hasPermission name="pszj2"><th>同意</th></shiro:hasPermission>
				    	</c:otherwise>
				    </c:choose>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="rewcomideacheck" varStatus="s">
				<tr>
					<td>
						${s.count }
						<input type="hidden" name="comideaChecks[${s.index }].id" value="${rewcomideacheck.id }">
					</td>
					<td>
						${rewcomideacheck.groupName }		    
					    <c:if test="${rewcomideacheck.groupSonName != null and  rewcomideacheck.groupSonName != ''}"> 
					      -- ${rewcomideacheck.groupSonName }
					    </c:if>
					</td>
					<td>${rewcomideacheck.categoryName }</td>
					<td>
						${fns:getProjectByProjectId(rewcomideacheck.projectid).projectname}
						<input type="hidden" name="comideaChecks[${s.index }].projectid" value="${rewcomideacheck.projectid }">
					</td>
					<td>${rewcomideacheck.scope }</td>
					<td>${fns:getAwardLevelById(rewcomideacheck.recomidea).levelname}</td>
					
					 <c:choose>
				    	<c:when test="${isShow }">
				    		<shiro:hasPermission name="gly7"><td>${fns:getUserById(rewcomideacheck.createBy).name}</td></shiro:hasPermission>
				    		<shiro:hasPermission name="gly7"><td>${rewcomideacheck.isagree =="1" ? "同意" : ""}</td></shiro:hasPermission>
				    	</c:when>
				    	<c:otherwise>
				    	<shiro:hasPermission name="pszj2">
						    <td>
							    <c:choose>
							    	<c:when test="${rewcomideacheck.recomidea != null }">
								    	<c:choose>
									    	<c:when test="${rewcomideacheck.isagree  == '1'}">
									    		<input type="checkbox" value="1" name="comideaChecks[${s.index }].isagree" checked="checked" >
									    	</c:when>
									    	<c:otherwise>
									    		<input type="checkbox" value="1" name="comideaChecks[${s.index }].isagree">
									    	</c:otherwise>
										</c:choose>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
									    	<c:when test="${rewcomideacheck.isagree  == '1'}">
									    		<input type="checkbox" value="1" name="comideaChecks[${s.index }].isagree" checked="checked" disabled="disabled">
									    	</c:when>
									    	<c:otherwise>
									    		<input type="checkbox" value="1" name="comideaChecks[${s.index }].isagree" disabled="disabled">
									    	</c:otherwise>
										</c:choose>
							    	</c:otherwise>
							    </c:choose>
						    </td>						 
					    </shiro:hasPermission>
				    	</c:otherwise>
				    </c:choose>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:if test="${fn:length(page.list) > 0 && !isGlyRole }">
			<input id="btnSubmit" class="btn btn-primary saveBtn" type="submit" value="保 存"/>
		</c:if>
	</form>
	<div class="pagination">${page}</div>
</c:if>
</body>
</html>