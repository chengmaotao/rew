<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript" src="${ctxStatic}/bootstrap/select/js/bootstrap-select.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/select/css/bootstrap-select.css">
	
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
		
		
	    // 根据Id 获得培训产品信息
	      function queryProjectInfoByGroup(obj){
	            var uid= obj.options[obj.selectedIndex].value;
	             $.ajax({  
	                      type: "post",
	                      url: "${ctx}/zszj/rewidea/xmmctreeData", 
	                      data:  {"id":uid},
	                      success: function(msg){  
	                         refrePxcpBj(msg);      	
	                      } ,
	                      error: function (XMLHttpRequest, textStatus, errorThrown) { 
	                                  alert(errorThrown); 
	                      } 
	             });
	         }
	    
	  	    // 刷新项目名称下拉框
			function refrePxcpBj(pxcpBjList){
				$("#s2id_projectid ul.select2-choices li.select2-search-choice").remove(); 
		        
				$("#projectid").empty();
				
		        var pxcpBjSelect = "";
		        for(var i = 0; i < pxcpBjList.length; i++){
		          pxcpBjSelect = pxcpBjSelect + "<option value="+pxcpBjList[i]['projectid'] +">"+pxcpBjList[i]['projectname']+"</option>";
		        }
		        $("#projectid").append(pxcpBjSelect);
			}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/zszj/rewidea/zxlist">主审专家对应项目列表</a></li>
		<li class="active"><a href="${ctx}/zszj/rewidea/zxform">主审专家对应项目添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rewidea" action="${ctx}/zszj/rewidea/zxsave" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		


		<div class="control-group">
			<label class="control-label">主审专家：</label>		
			<div class="controls">
		        <form:select path="userid" class="input-xlarge required" onchange="queryProjectInfoByGroup(this)">
         	   	 	<form:option value="" label="请选择"/>
               	 	<form:options items="${zszjList }" itemLabel="name" itemValue="id" htmlEscape="false"/>
        		</form:select>
		        <span class="help-inline"><font color="red">*</font> </span>
	        </div>
      </div>

	<div class="control-group">
		<label class="control-label">项目名称：</label>		
		<div class="controls">
			<select id="projectid" name="projectids" class="input-xlarge bla bli required" multiple="multiple" data-live-search="true">
				
		        <c:forEach items="${zszjProject }" var="pro">
		        	<option value="${pro.projectid}">${pro.projectname}</option>
		        </c:forEach>
		    </select>
	        <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>