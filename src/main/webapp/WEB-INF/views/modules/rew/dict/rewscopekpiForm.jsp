<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评价指标管理</title>
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
		
		// 检验项数  是否已经存在，是否大于0
		var checkSortNumRes = true;
		function checkSortNum(obj){
			
			var tid = $("#rewcategoryId").val();
			var sortNum = obj.value;
			
			if(sortNum == null || sortNum == '' || sortNum == undefined){
				checkSortNumRes = false;
				$("#btnSubmit").attr("disabled",true)
				return;
			}
			if(Number(sortNum) == 0){
				checkSortNumRes = false;
				$("#sortNumFont").text("项数为大于0的整数");
				$("#btnSubmit").attr("disabled",true)
				return;
			}
			
			$.ajax({  
	              type: "post",
	              url: "${ctx}/dict/rewscopekpi/checkSortNum", 
	              data: {"categoryid" : tid,"sortNum" : sortNum},
	              success: function(tempRes){
					 if(tempRes){
						 checkSortNumRes = false;
						 $("#sortNumFont").text("该申报类别下 已经设置了项数:" + sortNum);
						 $("#btnSubmit").attr("disabled",true)
						 return;
					 }
					 checkSortNumRes = true;
					 if(checkSortNumRes && checkKpiweightRes){
						 $("#btnSubmit").removeAttr("disabled")
					 }
	              },
	              error: function (XMLHttpRequest, textStatus, errorThrown) { 
	                          alert(errorThrown); 
	              }
	           }); 
		}
		function clearSortNum(){
			$("#sortNumFont").text("*");
		}
		
		
		
		// 检验 权重 是否大于0
		var checkKpiweightRes = true;
		function checkKpiweight(obj){
			var tempKpiweight = obj.value;
			if(tempKpiweight == null || tempKpiweight == '' || tempKpiweight == undefined){
				checkKpiweightRes = false;
				$("#btnSubmit").attr("disabled",true)
				return;
			}
			if(Number(tempKpiweight) == 0){
				checkKpiweightRes = false;
				$("#kpiweightFont").text("权重为大于0的整数");
				$("#btnSubmit").attr("disabled",true)
				return;
			}
			
			checkKpiweightRes = true;
			 if(checkSortNumRes && checkKpiweightRes){
				 $("#btnSubmit").removeAttr("disabled")
			 }
		}
		
		function clearKpiweight(){
			$("#kpiweightFont").text("*");
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/dict/rewscopekpi/">评价指标列表</a></li>
		<li class="active"><a href="${ctx}/dict/rewscopekpi/form?id=${rewscopekpi.id}">评价指标${not empty rewscopekpi.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" name="inputForm" modelAttribute="rewscopekpi" action="${ctx}/dict/rewscopekpi/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">申报类别：</label>
			<div class="controls">
		        	<form:select path="categoryid" class="required input-xlarge" >
      					<form:option value="" label="请选择"/>
      					<form:options items="${categorys }" itemLabel="categoryname" itemValue="id" htmlEscape="false"/>
   					</form:select>
		        <span class="help-inline"><font color="red">*</font> </span>
	        </div>
		</div>
		
		<div class="control-group">
			<label class="control-label">评价指标：</label>
			<div class="controls">
				<form:input path="kpicategory" htmlEscape="false" maxlength="25" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评价指标详细内容：</label>
			<div class="controls">
				<form:textarea path="kpidetailed" htmlEscape="false" cssClass="input-xlarge" rows="6"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权重：</label>
			<div class="controls">
				<form:input path="kpiweight" htmlEscape="false" maxlength="3" class="input-xlarge required" onkeyup="testCode(this)" onblur="checkKpiweight(this)" onfocus="clearKpiweight()"/>
				<span class="help-inline"><font color="red" id="kpiweightFont">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权重划分详细内容：</label>
			<div class="controls">
				<form:textarea path="scopelevel" htmlEscape="false" cssClass="input-xlarge" rows="4"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项数：</label>
			<div class="controls">
				<form:input path="sortNum" htmlEscape="false" maxlength="2"  class="input-xlarge required" onkeyup="testCode(this)" onblur="checkSortNum(this)" onfocus="clearSortNum()"/>
				<span class="help-inline"><font color="red" id="sortNumFont">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>