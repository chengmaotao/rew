<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
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

	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/dict/rewproject/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/dict/rewproject/import/template">下载模板</a>
		</form>
	</div>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dict/rewproject/">项目列表</a></li>
		<li><a href="${ctx}/dict/rewproject/form">项目添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="rewproject" action="${ctx}/dict/rewproject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		   <li><label>专业组：</label><%--
		   <sys:treeselect id="rewgroup" name="groupId" value="${rewproject.groupId }" 
		         labelName="groupname" labelValue="${fns:getGroupById(rewproject.groupId).groupname}"
		          title="专业组" url="/dict/rewgroup/treeData" cssClass="input-small" notAllowSelectParent="true" allowClear="true"/>
		   --%>
		        <form:select path="groupId" class="input-medium" >
 					<form:option value="" label="请选择"/>
 					<form:options items="${groups }" itemLabel="groupname" itemValue="id" htmlEscape="false"/>
				</form:select>
		   </li>
		    <li><label>申报类别：</label>
		    <%--<sys:treeselect id="category" name="categoryid" value="${rewproject.categoryid }" 
		         labelName="groupname" labelValue="${fns:getCagegoryById(rewproject.categoryid).categoryname}"
		          title="申报类别" url="/dict/rewcategory/treeData" cssClass="input-small" notAllowSelectParent="true" allowClear="true"/>
		    --%>
		    	<form:select path="categoryid" class="input-medium" >
    				<form:option value="" label="请选择"/>
    				<form:options items="${categorys }" itemLabel="categoryname" itemValue="id" htmlEscape="false"/>
 				</form:select>
		    </li>
		    <li><label>申报单位：</label><%--
		    <sys:treeselect id="company" name="companyId" value="${rewproject.companyId }" 
		         labelName="companyName" labelValue="${fns:getOfficeById(rewproject.companyId).name}"
		          title="申报单位" url="/dict/rewproject/treeData" cssClass="input-small" notAllowSelectParent="true" allowClear="true"/>
		    --%>
		    	<form:select path="companyId" class="input-medium" >
    				<form:option value="" label="请选择"/>
    				<form:options items="${companys }" itemLabel="name" itemValue="id" htmlEscape="false"/>
 				</form:select>
		    
		    </li>
		    
		    <li><label>评审年度：</label><%--
		    <sys:treeselect id="company" name="companyId" value="${rewproject.companyId }" 
		         labelName="companyName" labelValue="${fns:getOfficeById(rewproject.companyId).name}"
		          title="申报单位" url="/dict/rewproject/treeData" cssClass="input-small" notAllowSelectParent="true" allowClear="true"/>
		    --%>
		    	<form:select path="currentYear" class="input-medium" >
    				<form:option value="yyyy" label="请选择"/>
    				<form:options items="${yearList }" itemLabel="key" itemValue="key" htmlEscape="false"/>
 				</form:select>
		    
		    </li>
		    
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			</li>
			
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>项目编号</th>
			    <th>项目名称</th>
			    <th>专业组</th>
			    <th>申报类别</th>
			    <th>申报单位</th>
			    <th>申报奖励等级</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewproject">
			<tr>
			    <td>${rewproject.projectid }</td>
			    <td>${rewproject.projectname }</td>
			    <td>${fns:getGroupById(rewproject.groupId).groupname} </td>
			    <td>${fns:getCagegoryById(rewproject.categoryid).categoryname}</td>
			    <td>${fns:getOfficeById(rewproject.companyId).name}</td>
			    <td>${rewproject.awardlevel }</td>
				<td>
    				<a href="${ctx}/dict/rewproject/form?id=${rewproject.id}">修改</a>
					<a href="${ctx}/dict/rewproject/delete?id=${rewproject.id}" onclick="return confirmx('确认要删除该项目吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>