<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>初审第一轮投票管理</title>
	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			trCorlor();
			
			if(${message != null and message != ''}){
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
					
					if($(cbx).attr("disabled") != 'disabled'){
						if($(cbx).attr("checked")){
							$(cbx).removeAttr("checked");
							checkedNum('1',cbx);
						}else{
							$(cbx).attr("checked",true);
							checkedNum('1',cbx);
						}
					}
				}
			});
			
		});

		function submitFormByGroup(){
			
			$("#searchForm").submit();
        	return false;
		}

	</script>
</head>
<body>

<c:if test="${iscs }">
	<form:form id="searchForm" modelAttribute="rewcsfirstvote" action="${ctx}/csfirst/initrewcsfirstvote/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<c:if test="${isShow }">
			<li><label>项目名称：</label> 
     			<form:select path="projectid" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${projects }" itemLabel="projectname" itemValue="projectid" htmlEscape="false"/>
     			</form:select>
		    </li>
		    <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		</c:if>

		    <li><label>专业组：</label> 
				<form:radiobuttons path="groupId" items="${groupList}" itemLabel="groupname" itemValue="id" htmlEscape="false" onclick="submitFormByGroup()"/>
		    </li>

		</ul>
	</form:form>
    <c:if test="${!isShow }"><shiro:hasPermission name="dyltp"><label style="text-align: right; display: block; margin-right: 20px"> <b>投票总数：${allNum } /已投票数：${fn:length(voteds)}</b></label></shiro:hasPermission></c:if>
	<input type="hidden" value="${num }" id="synumId"> <%--还可以提交的数目 --%>
	
	<form action="${ctx}/csfirst/initrewcsfirstvote/saveAllData" id="myFormId" name="myFormName" method="post">
	
		<input type="hidden" name="groupId" value="${hidGroupId }">
		<input type="hidden" name="isAgreeKey" value="${hidIsagree }">
		
		<input type="hidden" name="token" value="${token }">
		
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>初审第一轮序号</th>
				    <th>专业组</th>
				    <th>申报类别</th>
				    <th>项目名称</th>
				    <th>评分</th>
				    <th>推荐意见</th>
				    
				    <c:choose>
				    	<c:when test="${isShow }">
				    		<shiro:hasPermission name="gly4"><th>评审专家</th></shiro:hasPermission>
				    		<shiro:hasPermission name="gly4"><th>同意</th></shiro:hasPermission>
				    	</c:when>
				    	<c:otherwise>
				    		<shiro:hasPermission name="dyltp"><th>同意</th></shiro:hasPermission>
				    	</c:otherwise>
				    </c:choose>

				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="rewcsfirstvote" varStatus="s">
				<tr>
				    <td>
				    	${(page.pageNo - 1) * page.pageSize + s.count }
						<input type="hidden" name="csfirstvotes[${s.index }].id" value="${rewcsfirstvote.id }">
				    </td>
				    <td>
				    	${rewcsfirstvote.groupName }		    
					    <c:if test="${rewcsfirstvote.groupSonName != null and  rewcsfirstvote.groupSonName != ''}"> 
					      -- ${rewcsfirstvote.groupSonName }
					    </c:if>
				    </td>
				    <td>${rewcsfirstvote.categoryName}</td>
				    <td>
				    	${fns:getProjectByProjectId(rewcsfirstvote.projectid).projectname}
				    	<input type="hidden" name="csfirstvotes[${s.index }].projectid" value="${rewcsfirstvote.projectid }">
				    </td>
				   
				    <td>
				    	${rewcsfirstvote.scope}
				    	<input type="hidden" name="csfirstvotes[${s.index }].scope" value="${rewcsfirstvote.scope}">
				    </td>
				    <td> 
				    	${fns:getAwardLevelById(rewcsfirstvote.recomIdea).levelname}
				    	<input type="hidden" name="csfirstvotes[${s.index }].recomIdea" value="${rewcsfirstvote.recomIdea}">
				    </td>
				    <c:choose>
				    	<c:when test="${isShow }">
				    		<shiro:hasPermission name="gly4"><td>${fns:getUserById(rewcsfirstvote.createBy).name}</td></shiro:hasPermission>
				    		<shiro:hasPermission name="gly4"><td>${rewcsfirstvote.isagree =="1" ? "同意" : ""} </td></shiro:hasPermission>
				    	</c:when>
				    	<c:otherwise>
					    	<shiro:hasPermission name="dyltp">
								<td>
									<c:choose>
										<c:when test="${rewcsfirstvote.recomIdea != '4'}">
										    <c:choose>
										    	<c:when test="${rewcsfirstvote.isagree  == '1'}">
										    		<input type="checkbox" value="1" name="csfirstvotes[${s.index }].isagree" checked="checked" onclick="checkedNum('1',this)">
										    	</c:when>
										    	<c:otherwise>
										    		<input type="checkbox" value="1" name="csfirstvotes[${s.index }].isagree" onclick="checkedNum('1',this)">
										    	</c:otherwise>
										    </c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
										    	<c:when test="${rewcsfirstvote.isagree  == '1'}">
										    		<input type="checkbox" value="1" name="csfirstvotes[${s.index }].isagree" disabled="disabled" checked="checked" onclick="checkedNum('3',this)">
										    	</c:when>
										    	<c:otherwise>
										    		<input type="checkbox" value="1" name="csfirstvotes[${s.index }].isagree" disabled="disabled" onclick="checkedNum('3',this)">
										    	</c:otherwise>
										    </c:choose>
											
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
		
		<c:if test="${fn:length(page.list) > 0 && !isGlyRole }">
			<input id="btnSubmit" class="btn btn-primary saveBtn" type="submit" value="保 存"/>
		</c:if>
	</form>
	<div class="pagination">${page}</div>
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
					}
				}else{
					synums = Number(synums) + 1;
				}
			}
		}
		
		function page(n,s){
			synums = $("#synumId").val();
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	
	</script>
</body>
</html>