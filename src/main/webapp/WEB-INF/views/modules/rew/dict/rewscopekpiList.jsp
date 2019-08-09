<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评价指标管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dict/rewscopekpi/">评价指标列表</a></li>
		<li><a href="${ctx}/dict/rewscopekpi/form">评价指标添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="rewscopekpi" action="${ctx}/dict/rewscopekpi/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		
		<ul class="ul-form">
		    <li> <label for="rewcategoryName">申报类别: </label><%-- 
		    <sys:treeselect id="rewcategory" name="categoryid" value="${rewscopekpi.categoryid }" 
		         labelName="categoryname" labelValue="${fns:getCagegoryById(rewscopekpi.categoryid).categoryname}"
		          title="申报类别" url="/dict/rewscopekpi/treeData" cssClass="input-small" notAllowSelectParent="true" allowClear="true"/>
		         --%> 
		          
		       		<form:select path="categoryid" class="input-medium" >
      					<form:option value="" label="请选择"/>
      					<form:options items="${categorys }" itemLabel="categoryname" itemValue="id" htmlEscape="false"/>
   					</form:select>
		          </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th style="width: 80px;max-width: 80px;min-width: 80px">申报类别</th>
			    <th>评价指标</th>
			    <th>评价指标详细内容</th>
			    <th>权重</th>
			    <th>权重划分详细内容</th>
			    <th>项数</th>
				<th style="width: 60px;max-width: 60px;min-width: 60px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewscopekpi">
			<tr>
			    
			    <td style="width: 60px;max-width: 60px;min-width: 60px">${fns:getCagegoryById(rewscopekpi.categoryid).categoryname}</td>
			    <td>${rewscopekpi.kpicategory }</td>
			    <td>${rewscopekpi.kpidetailed }</td>
			    <td>${rewscopekpi.kpiweight }</td>
			    <td>${rewscopekpi.scopelevel }</td>
			    <td>${rewscopekpi.sortNum }</td>
				<td style="width: 60px;max-width: 60px;min-width: 60px">
    				<a href="${ctx}/dict/rewscopekpi/form?id=${rewscopekpi.id}">修改</a>
					<a href="${ctx}/dict/rewscopekpi/delete?id=${rewscopekpi.id}" onclick="return confirmx('确认要删除该评价指标吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>