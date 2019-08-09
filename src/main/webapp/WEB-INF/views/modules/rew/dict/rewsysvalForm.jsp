<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投票数目管理</title>
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
		<li><a href="${ctx}/dict/rewsysval/">投票数目列表</a></li>
		<li class="active"><a href="${ctx}/dict/rewsysval/form?id=${rewsysval.id}">投票数目${not empty rewsysval.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rewsysval" action="${ctx}/dict/rewsysval/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">初审总投票数：</label>
			<div class="controls">
				<form:input path="csdyltp" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">初审特等奖数：</label>
			<div class="controls">
				<form:input path="csdeltpt" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">初审一等奖数：</label>
			<div class="controls">
				<form:input path="csdeltpy" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复审第一轮投票：</label>
			<div class="controls">
				<form:input path="fsdyltp" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复审第二轮投票：</label>
			<div class="controls">
				<form:input path="fsdeltp" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复审第三轮投票：</label>
			<div class="controls">
				<form:input path="fsdsltp" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>