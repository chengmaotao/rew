<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报单位管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		
		
		var res = true;
		function checkName(officeName){	
			
			var tempId = $("#hidnameid").val();			
			if(tempId != "" && $.trim(officeName) == tempId){
				res = true;
				return;
			}	
			
			$.ajax({  
	            type: "post",
	            url: "${ctx}/sys/office/checkOfficeName?time=" + new Date().getTime(), 
	            data:  {"name":officeName},
	            async: false,
	            success: function(msg){
	            	if(msg){
	            		res = false;
						$("#ajaxCheckId").empty();
						$("#ajaxCheckId").append("该申报单位名称已经存在");
						return;
	            	}else{
	            		res = true;
	            		return;
	            	}
	            },
	            error:function(msg){
	            	top.$.jBox.tip("系统错误，保存失败。","1",{persistent:true,opacity:0});
	            }
			});
			
		}
		
		function onfocus_(){
			$("#ajaxCheckId").empty();
			$("#ajaxCheckId").append("*");
		}
		
		function onsublmit(){
			return res;
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/office/list">申报单位列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}">申报单位${not empty office.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		
		
		<div class="control-group">
			<label class="control-label">申报单位:</label>
			<div class="controls">
			<%--
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			--%>
				<form:input path="name" htmlEscape="false" maxlength="50" class="required input-xlarge" onblur="checkName(this.value)" onfocus="onfocus_()"/>
				<input type="hidden" id="hidnameid" name="hidname" value="${office.name }">
				<span class="help-inline"><font color="red" id="ajaxCheckId">*</font> </span>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return onsublmit()"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>