<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>	
<html>
<head>
	<title>专家评分管理</title>
	
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
	
	<c:choose>
		<c:when test="${isShow }">
			<form:form id="searchForm" modelAttribute="rewprojectscope" action="${ctx}/pszj/rewprojectscope/" method="post" class="breadcrumb form-search">
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
			<form:form id="searchForm" modelAttribute="rewprojectscope" action="${ctx}/pszj/rewprojectscope/" method="post" class="">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			</form:form>		
		</c:otherwise>
	</c:choose>
	
	 <sys:message content="${message}"/> 
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>	   
			    <th rowspan="2">序号</th>
			    <th rowspan="2">专业组</th>
			    <th rowspan="2">申报类别</th>
			    <th rowspan="2">项目名称</th>
			    <th colspan="5" style="text-align: center;">评价指标</th>
			    <th rowspan="2">总分</th>
			    <c:if test="${isShow }">
			    	<shiro:hasPermission name="gly"><th rowspan="2">评分专家</th></shiro:hasPermission>
			    </c:if>
			    <c:if test="${!isShow }">
					<shiro:hasPermission name="pszj"><th rowspan="2">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
			<tr>
				<th>(一)</th>
				<th>(二)</th>
				<th>(三)</th>
				<th>(四)</th>
				<th>(五)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="rewprojectscope" varStatus="s">
				<tr>
				    <td>${s.count }</td>
			    	<td>
			    		${rewprojectscope.groupName }		    
					    <c:if test="${rewprojectscope.groupSonName != null and  rewprojectscope.groupSonName != ''}"> 
					      -- ${rewprojectscope.groupSonName }
					    </c:if>
			    	</td>
			    	<td>${rewprojectscope.categoryName }</td>
			    	<td>${fns:getProjectByProjectId(rewprojectscope.projectid).projectname}</td>
			    	<td>${rewprojectscope.kpiweight1 }</td>
			    	<td>${rewprojectscope.kpiweight2 }</td>
			    	<td>${rewprojectscope.kpiweight3 }</td>
			    	<td>${rewprojectscope.kpiweight4 }</td>
			    	<td>${rewprojectscope.kpiweight5 }</td>
			    	<td>${rewprojectscope.kpiweightAll }</td>
			    	<c:if test="${isShow }">
			    		<shiro:hasPermission name="gly"><td>${fns:getUserById(rewprojectscope.createBy).name}</td></shiro:hasPermission>
			    	</c:if>
			    	<c:if test="${!isShow }">
				    	<shiro:hasPermission name="pszj">
					    	<td>
			    				<a href="${ctx}/pszj/rewprojectscope/form?id=${rewprojectscope.id}&projectId=${rewprojectscope.projectid}">评分</a>
			    				&nbsp;&nbsp;
							    <a href="${ctx}/pszj/rewprojectscope/form?id=${rewprojectscope.id}&projectId=${rewprojectscope.projectid}">修改</a>
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