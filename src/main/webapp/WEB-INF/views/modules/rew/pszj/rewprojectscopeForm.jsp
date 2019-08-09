<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家评分管理</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
		label.temWidth{width: 300px !important;}	
	</style>
	
	<script type="text/javascript">
		$(document).ready(function() {
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
		
		function myShowModalDia(val,lbl){
			$("#myModalLablea").text(lbl);
			$("#myModalVala").text(val);
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="rewprojectscope" action="${ctx}/pszj/rewprojectscope/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="token"/>
		<sys:message content="${message}"/>
		
		
		<div class="control-group">
			<label class="temWidth control-label " >专业组：</label>
			<div class="controls">
				<label>${project.groupName }</label>
			</div>
		</div>
				
		<div class="control-group">
			<label class="temWidth control-label ">申报类别：</label>
			<div class="controls">
				<label>${fns:getCagegoryById(project.categoryid).categoryname}</label>
			</div>
		</div>
		
		<input type="hidden" value="${project.projectid }" name="projectid">
		<div class="control-group">
			<label class="temWidth control-label ">项目名称：</label>
			<div class="controls">
				<label>${project.projectname }</label>
			</div>
		</div>
		
		<div class="control-group">
			<label class="temWidth control-label ">${kpicategory1 }：</label>
			<div class="controls">
		       <form:select path="kpiweight1" class="input-xlarge required" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${kpilist1 }" itemLabel="value" itemValue="key" htmlEscape="false"/>
     			</form:select>
     			<a href="#myModal" role="button" data-toggle="modal" onclick="myShowModalDia('${fns:replaceRn(scopelevel1)}','${kpicategory1 }')">(详细)</a>
		        <span class="help-inline"><font color="red">*</font> </span>
        	</div>
		</div>
		<div class="control-group">
			<label class="temWidth control-label ">${kpicategory2 }：</label>
			<div class="controls">
		       <form:select path="kpiweight2" class="input-xlarge required" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${kpilist2 }" itemLabel="value" itemValue="key" htmlEscape="false"/>
     			</form:select>
     			<a href="#myModal" role="button" data-toggle="modal" onclick="myShowModalDia('${fns:replaceRn(scopelevel2)}','${kpicategory2 }')">(详细)</a>
		        <span class="help-inline"><font color="red">*</font> </span>
        	</div>
		</div>
		<div class="control-group">
			<label class="temWidth control-label ">${kpicategory3 }：</label>
			<div class="controls">
		       <form:select path="kpiweight3" class="input-xlarge required" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${kpilist3 }" itemLabel="value" itemValue="key" htmlEscape="false"/>
     			</form:select>
     			<a href="#myModal" role="button" data-toggle="modal" onclick="myShowModalDia('${fns:replaceRn(scopelevel3)}','${kpicategory3 }')">(详细)</a>
		        <span class="help-inline"><font color="red">*</font> </span>
        	</div>
		</div>
		<div class="control-group">
			<label class="temWidth control-label ">${kpicategory4 }：</label>
			<div class="controls">
		       <form:select path="kpiweight4" class="input-xlarge required" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${kpilist4 }" itemLabel="value" itemValue="key" htmlEscape="false"/>
     			</form:select>
     			<a href="#myModal" role="button" data-toggle="modal" onclick="myShowModalDia('${fns:replaceRn(scopelevel4)}','${kpicategory4 }')">(详细)</a>
		        <span class="help-inline"><font color="red">*</font> </span>
        	</div>
		</div>
		<div class="control-group">
			<label class="temWidth control-label ">${kpicategory5 }：</label>
			<div class="controls">
		       <form:select path="kpiweight5" class="input-xlarge required" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${kpilist5 }" itemLabel="value" itemValue="key" htmlEscape="false"/>
     			</form:select>
     			<a href="#myModal" role="button" data-toggle="modal" onclick="myShowModalDia('${fns:replaceRn(scopelevel5)}','${kpicategory5 }')">(详细)</a>
		        <span class="help-inline"><font color="red">*</font> </span>
        	</div>
		</div>				
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
		<!-- 模态框 -->
		<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 id="myModalLabel"><label id="myModalLablea"></label></h3>
			</div>
			<div class="modal-body">
				<p id="myModalVala"></p>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	<!-- 模态框结束 -->
</body>
</html>