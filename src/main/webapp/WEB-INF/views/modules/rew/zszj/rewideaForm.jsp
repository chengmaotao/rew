<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评审意见管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
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
		<li><a href="${ctx}/zszj/rewidea/">评审意见列表</a></li>
		<li class="active"><a href="${ctx}/zszj/rewidea/form?id=${rewidea.id}&projectId=${project.projectid}">评审意见${not empty rewidea.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rewidea" action="${ctx}/zszj/rewidea/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
                <input readonly="readonly" class="input-xlarge required" name="projectid" value="${project.projectid }">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
                <input readonly="readonly" class="input-xlarge required" id="tempProjectName" value="${project.projectname }">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div><%--
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
			    <input type="hidden" id="oldProjectId" value="${rewidea.projectid }" >
				<sys:treeselectSBXM id="project" name="projectid" value="${rewidea.projectid }" 
		           labelName="projectName" labelValue="${fns:getProjectByProjectId(rewidea.projectid).projectname}"
		           title="申报项目" url="/dict/rewproject/treeDataByGroup" cssClass="input-xlarge" notAllowSelectParent="true" allowClear="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		--%><div class="control-group">
			<label class="control-label">专业组：</label>
			<div class="controls">
				<input readonly="readonly" class="input-xlarge required" id="tempGroupId" value="${project.groupName }" >
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">申报单位：</label>
			<div class="controls">
				<input readonly="readonly" class="input-xlarge required" id="tempCompanyId" value="${fns:getOfficeById(project.companyId)}">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		
		<div class="control-group">
			<label class="control-label">奖励等级:</label>
			<div class="controls">
				<form:radiobuttons path="levelid" items="${levelList}" itemLabel="levelname" itemValue="id" htmlEscape="false" cssClass="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评审意见：</label>
			<div class="controls">
			    <form:textarea path="revidea" htmlEscape="false" rows="8" cssClass="input-xlarge" />
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>