<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐意见投票结果管理</title>
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
	<form:form id="inputForm" modelAttribute="rewrecomidea" action="${ctx}/zyzz/rewrecomidea/mySave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="token"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">专业组：</label>
			<div class="controls">
				<label>${project.groupName }</label>
			</div>
		</div>
				
		<div class="control-group">
			<label class="control-label">申报类别：</label>
			<div class="controls">
				<label>${fns:getCagegoryById(project.categoryid).categoryname}</label>
			</div>
		</div>
		
		<input type="hidden" value="${project.projectid }" name="projectid">
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<label>${project.projectname }</label>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">评分：</label>
			<div class="controls">
				<label>${rewrecomidea.scope }</label>
			</div>
		</div>
		
		<input type="hidden"  name="hidRecomidea" value="${rewrecomidea.recomidea}"/>
		<div class="control-group">
			<label class="control-label">小组推荐意见:</label>
			<div class="controls">
				<form:radiobuttons path="recomidea" items="${LevelList}" itemLabel="levelname" itemValue="id" htmlEscape="false" cssClass="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">推荐意见投票结果:</label>
			<div class="controls">
				<label>${rewrecomidea.result }</label>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>