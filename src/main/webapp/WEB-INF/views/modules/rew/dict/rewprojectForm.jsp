<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				
				rules: {
					projectid: {remote: "${ctx}/dict/rewproject/checkProjectId?oldProjectId=" + encodeURIComponent('${rewproject.projectid}')}
				},
				messages: {
					projectid: {remote: "项目编号已存在"}
				},
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/dict/rewproject/">项目列表</a></li>
		<li class="active"><a href="${ctx}/dict/rewproject/form?id=${rewproject.id}">项目${not empty rewproject.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rewproject" action="${ctx}/dict/rewproject/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls"><%--
			    <input type="hidden" value="${rewproject.projectid }" name="oldProjectId">
				--%><form:input path="projectid" htmlEscape="false" maxlength="15" class="input-xlarge required" onkeyup="testId(this)" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectname" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">专业组:</label>
			<div class="controls">
                <form:radiobuttons path="groupId" items="${groupList}" itemLabel="groupname" itemValue="id" htmlEscape="false" cssClass="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		
		
		<div class="control-group">
			<label class="control-label">申报类别：</label>		
			<div class="controls">
			
				<form:select path="categoryid" class="required input-xlarge" >
    				<form:option value="" label="请选择"/>
    				<form:options items="${categorys }" itemLabel="categoryname" itemValue="id" htmlEscape="false"/>
 				</form:select>
			
		        <%--<sys:treeselect id="category" name="categoryid" value="${rewproject.categoryid }" 
		         labelName="groupname" labelValue="${fns:getCagegoryById(rewproject.categoryid).categoryname}"
		          title="申报类别" url="/dict/rewcategory/treeData" cssClass="required input-xlarge" notAllowSelectParent="true" allowClear="true"/>
		        --%><span class="help-inline"><font color="red">*</font> </span>
	        </div>
      </div>
		
		
	<div class="control-group">
		<label class="control-label">申报单位：</label>		
		<div class="controls">
	        <%--<sys:treeselect id="company" name="companyId" value="${rewproject.companyId }" 
		         labelName="companyName" labelValue="${fns:getOfficeById(rewproject.companyId).name}"
		          title="申报单位" url="/dict/rewproject/treeData" cssClass="required input-xlarge" notAllowSelectParent="true" allowClear="true"/>
	        --%>
	        <form:select path="companyId" class="required input-xlarge" >
    				<form:option value="" label="请选择"/>
    				<form:options items="${companys }" itemLabel="name" itemValue="id" htmlEscape="false"/>
 				</form:select>
	        <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    
    <div class="control-group">
		<label class="control-label">申报奖励等级：</label>		
		<div class="controls">
	        <form:input path="awardlevel" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>