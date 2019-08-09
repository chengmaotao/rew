<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>复审第一轮投票管理</title>



	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message !=null && message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			}
			
			$("#myFormId").validate({
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
			
			$("#contentTable").on("click","tr",function(e){
				if(e.target.type != 'checkbox'&& e.target.type != 'select-one'){
					var cbx = $(this).find("input[type=checkbox]");
					if($(cbx).attr("checked")){
						$(cbx).removeAttr("checked");
						checkedNum('1',cbx);
					}else{
						$(cbx).attr("checked",true);
						checkedNum('1',cbx);
					}
				}
			});
		});		
	</script>
</head>
<body>
<c:if test="${isfs }">
    
    <c:if test="${isShow }">
    	<form:form id="searchForm" modelAttribute="rewfsdyl" action="${ctx}/fs/fsdyl/initrewfsdyl/list" method="post" class="breadcrumb form-search">
    		<ul class="ul-form">
	    		<li><label>项目名称：</label> 
	     			<form:select path="projectid" >
	        			<form:option value="" label="请选择"/>
	        			<form:options items="${projects }" itemLabel="projectName" itemValue="projectid" htmlEscape="false"/>
	     			</form:select>
			    </li>
			    <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		    </ul>
    	</form:form>
    </c:if>
    
    
    <c:if test="${!isShow }"><shiro:hasPermission name="fsdylpszj"><label style="text-align: right; display: block; margin-right: 20px"> <b>投票总数：${allNum } /已投票数：${fn:length(fsdyltpNums)}</b></label></shiro:hasPermission></c:if>
	<input type="hidden" value="${num }" id="synumId"> <%--还可以提交的数目 --%>
	<form action="${ctx}/fs/fsdyl/initrewfsdyl/saveAllData" method="post" id="myFormId" name="myFormName">
		<input type="hidden" name="token" value="${token }">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>复审第一轮序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>项目名称</th><%--
				    <th>初审结果</th>
				    <th>初审投票数/总票数</th>
				    --%><c:choose>
				    	<c:when test="${isShow }">
				    		 <shiro:hasPermission name="fsdylgly"><th>评审专家</th></shiro:hasPermission>
				    		 <shiro:hasPermission name="fsdylgly"><th>同意授特等奖</th></shiro:hasPermission>
				    	</c:when>
				    	<c:otherwise>
				    		<shiro:hasPermission name="fsdylpszj"><th>同意授特等奖</th></shiro:hasPermission>
				    	</c:otherwise>
				    </c:choose>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${fsdylList}" var="rewfsdyl" varStatus="s">
				<tr>
					<td>${s.count } <input type="hidden" name="fsdyls[${s.index }].id" value="${rewfsdyl.id }"></td>
					<td>${fns:getGroupById(rewfsdyl.groupId).groupname} </td>
					<td>${fns:getCagegoryById(rewfsdyl.categoryId).categoryname}</td>
					<td>
						${rewfsdyl.projectName }
						<input type="hidden" name="fsdyls[${s.index }].projectid" value="${rewfsdyl.projectid }">
					</td>
					<%--
					<td>${fns:getAwardLevelById(rewfsdyl.csresLevelId).name}</td>
					<td>${csresAllNum }/${csresNum }</td>
					
					 --%>
					 <c:choose>
				    	<c:when test="${isShow }">
				    		<shiro:hasPermission name="fsdylgly"><td>${fns:getUserById(rewfsdyl.createBy).name}</td></shiro:hasPermission>
				    		<shiro:hasPermission name="fsdylgly"><td>${rewfsdyl.isAgree =="1" ? "同意" : ""}</td></shiro:hasPermission>
				    	</c:when>
				    	<c:otherwise>
					    	<shiro:hasPermission name="fsdylpszj">
								<td>
									<c:choose>
										<c:when test="${rewfsdyl.isAgree == '1'}">
											<input type="checkbox" value="1" name="fsdyls[${s.index }].isAgree" checked="checked" onclick="checkedNum('1',this)">
										</c:when>
										<c:otherwise>
											<input type="checkbox" value="1" name="fsdyls[${s.index }].isAgree" onclick="checkedNum('1',this)">
										</c:otherwise>
									</c:choose>
								</td>
						    </shiro:hasPermission>
				    	</c:otherwise>
				    </c:choose>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	
		<c:if test="${!isShow and fn:length(fsdylList) > 0}">
			<shiro:hasPermission name="fsdylpszj">
				<input id="btnSubmit" class="btn btn-primary saveBtn" type="submit" value="保 存"/>
			</shiro:hasPermission>
		</c:if>
	</form>
</c:if>
	<script type="text/javascript">
		var synums = $("#synumId").val();
		function checkedNum(kbn,obj){
			if(kbn == '1'){
				if($(obj).is(':checked')){
					synums = Number(synums) - 1;
					
					if(Number(synums) < 0){
						
						alert("同意的投票总数 已达到最高(" + ${allNum }  +"),不允许选中;如果需要继续提交，请先取消一个");
						$(obj).removeAttr("checked");
						synums = Number(synums) + 1;
						return true;
					}
				}else{
					synums = Number(synums) + 1;
				}
			}
			return false;
		}
	</script>
</body>
</html>