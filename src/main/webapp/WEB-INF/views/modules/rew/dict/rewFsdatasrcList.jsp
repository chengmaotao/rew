<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>复审数据</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		
		function submitFormByLevel(){
			$("#searchForm").submit();
        	return false;
		}
		
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
		<form id="importForm" action="${ctx}/dict/rewFsdatasrc/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/dict/rewFsdatasrc/import/template">下载模板</a>
		</form>
	</div>
	<form:form id="searchForm" modelAttribute="rewFsdatasrc" action="${ctx}/dict/rewFsdatasrc/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		
			 <li><label>奖励等级：</label> 
				<form:radiobuttons path="recomidea" items="${awardLevelList}" itemLabel="levelname" itemValue="id" htmlEscape="false" onclick="submitFormByLevel()"/>
		    </li>
		    <li>
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
				<th>奖励等级</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewFsdatasrc">
			<tr>
				<td>
					${rewFsdatasrc.projectid}
				</td>
				<td>
					${rewFsdatasrc.projectName}
				</td>
				<td>
					${rewFsdatasrc.levelName}
				</td>			
				<td>
					<a href="${ctx}/dict/rewFsdatasrc/delete?id=${rewFsdatasrc.id}" onclick="return confirmx('确认要删除该复审数据吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>