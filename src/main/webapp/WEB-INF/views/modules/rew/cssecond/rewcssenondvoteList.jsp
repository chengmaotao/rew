<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>初审第二轮投票管理</title>
	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message != null and message != '' }){
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
			
			
			$("#contentTable").on("click","tr td.tdj",function(e){

				if(e.target.type != 'checkbox'&& e.target.type != 'select-one'){
					var cbx = $(this).find("input[type=checkbox]");
					
					if($(cbx).attr("disabled") != 'disabled'){
						if($(cbx).attr("checked")){
							$(cbx).removeAttr("checked");
							checkedNum('0',cbx);
						}else{
							$(cbx).attr("checked",true);
							checkedNum('0',cbx);
						}
					}
				}
			});
			
			$("#contentTable").on("click","tr td.ydj",function(e){

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
	<form:form id="searchForm" modelAttribute="rewcssenondvote" action="${ctx}/cssecond/initrewcssenondvote/" method="post" class="breadcrumb form-search">
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
	<%--<sys:message content="${message}"/>
	
	--%> <c:if test="${!isShow }"><shiro:hasPermission name="deltp">
			<label style="text-align: right; display: block; margin-right: 20px"> 
			 <b>特等奖投票总数：${allNum0 } /已投票数：${fn:length(voted0s)}&nbsp;&nbsp;</b> 
			 <b>一等奖投票总数：${allNum1 } /已投票数：${fn:length(voted1s)}</b></label>
	   </shiro:hasPermission></c:if>
	<input type="hidden" value="${num0 }" id="synumId0"> <%--特等奖还可以提交的数目 --%>
	<input type="hidden" value="${num1 }" id="synumId1"> <%--特等奖还可以提交的数目 --%>
	<form action="${ctx}/cssecond/initrewcssenondvote/saveAllData" id="myFormId" name="myFormName" method="post">
		
		<input type="hidden" name="groupId" value="${hidGroupId }">
		<input type="hidden" name="isAgreeKey" value="${hidIsagree }">
		<input type="hidden" name="token" value="${token }">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					    <th>初审第二轮序号</th>
					    <th>专业组</th>						    
					    <th>申报类别</th>
					    <th>项目名称</th>
					    <th>小组推荐意见</th>
					    <th>第一轮票数</th>
					    <c:if test="${!isShow }">
						    <shiro:hasPermission name="deltp">
							    <th>特等奖</th>
							    <th>一等奖</th>
						    </shiro:hasPermission>
					    </c:if>
					    <c:if test="${isShow }">
							<shiro:hasPermission name="gly5">					    
								<th>特等奖</th>
						    	<th>一等奖</th>
                                <th>评审专家</th></shiro:hasPermission>
					    </c:if>
				</tr>
			</thead>
			<tbody>
			<c:if test="${!isShow }">
				<c:forEach items="${page.list}" var="rewcssenondvote" varStatus="s">
					<tr>
	                	<td>
					    	${(page.pageNo - 1) * page.pageSize + s.count }
							<input type="hidden" name="cssenondvotes[${s.index }].id" value="${rewcssenondvote.id }">
					    </td>
					    <td>
					    	${rewcssenondvote.groupName }		    
					    </td>					    
					    <td>${rewcssenondvote.categoryName}</td>
					    <td>
					    	${fns:getProjectByProjectId(rewcssenondvote.projectid).projectname}
					    	<input type="hidden" name="cssenondvotes[${s.index }].projectid" value="${rewcssenondvote.projectid }">
					    </td>
					    
					    <td>${fns:getAwardLevelById(rewcssenondvote.recomIdea).levelname}</td>
					    <td>${rewcssenondvote.countNum}</td>
					    <shiro:hasPermission name="deltp">
							<c:choose>
					        	<c:when test="${rewcssenondvote.recomIdea != '4' and rewcssenondvote.senondShow }">
						        	<c:choose>
								    	<c:when test="${rewcssenondvote.isagree0  == '1'}">
								    		<td class="tdj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree0" name="cssenondvotes[${s.index }].isagree0" checked="checked" onclick="checkedNum('0',this)"></td>
							    			<c:choose>
										    	<c:when test="${rewcssenondvote.isagree1  == '1'}">
										    		<td class="ydj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree1" name="cssenondvotes[${s.index }].isagree1" checked="checked" disabled="disabled"></td>
										    	</c:when>
										    	<c:otherwise>
										    		<td class="ydj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree1" name="cssenondvotes[${s.index }].isagree1" disabled="disabled"></td>
										    	</c:otherwise>
											</c:choose>
								    	</c:when>
								    	<c:otherwise>			    		
							    			<c:choose>
										    	<c:when test="${rewcssenondvote.isagree1  == '1'}">
										    	    <td class="tdj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree0" name="cssenondvotes[${s.index }].isagree0" disabled="disabled"></td>
										    		<td class="ydj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree1" name="cssenondvotes[${s.index }].isagree1" checked="checked" onclick="checkedNum('1',this)"></td>
										    	</c:when>
										    	<c:otherwise>
										    	    <td class="tdj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree0" name="cssenondvotes[${s.index }].isagree0" onclick="checkedNum('0',this)"></td>
										    		<td class="ydj"><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree1" name="cssenondvotes[${s.index }].isagree1" onclick="checkedNum('1',this)"></td>
										    	</c:otherwise>
											</c:choose>
								    	</c:otherwise>
									</c:choose>
									
									
					        	</c:when>
					        	<c:otherwise>
						        	<c:choose>
								    	<c:when test="${rewcssenondvote.isagree0  == '1'}">
								    		<td><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree0" name="cssenondvotes[${s.index }].isagree0" checked="checked" disabled="disabled"></td>
								    	</c:when>
								    	<c:otherwise>
								    		<td><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree0" name="cssenondvotes[${s.index }].isagree0" disabled="disabled"></td>
								    	</c:otherwise>
									</c:choose>
									<c:choose>
								    	<c:when test="${rewcssenondvote.isagree1  == '1'}">
								    		<td><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree1" name="cssenondvotes[${s.index }].isagree1" checked="checked" disabled="disabled"></td>
								    	</c:when>
								    	<c:otherwise>
								    		<td><input type="checkbox" value="1" id="cssenondvotes[${s.index }].isagree1" name="cssenondvotes[${s.index }].isagree1" disabled="disabled"></td>
								    	</c:otherwise>
									</c:choose>
					        	</c:otherwise>
					        </c:choose>
						</shiro:hasPermission>
					</tr>
			 	</c:forEach>
			</c:if>
		 	<c:if test="${isShow }">
		 		<c:forEach items="${page.list}" var="rewcssenondvote" varStatus="s">
					<tr>
	                	<td>
					    	${(page.pageNo - 1) * page.pageSize + s.count }
							<input type="hidden" name="cssenondvotes[${s.index }].id" value="${rewcssenondvote.id }">
					    </td>
					    <td>
					    	${rewcssenondvote.groupName }		    
					    </td>
					    <td>${rewcssenondvote.categoryName}</td>
					    <td>
					    	${fns:getProjectByProjectId(rewcssenondvote.projectid).projectname}
					    	<input type="hidden" name="cssenondvotes[${s.index }].projectid" value="${rewcssenondvote.projectid }">
					    </td>
					    
					    <td>${fns:getAwardLevelById(rewcssenondvote.recomIdea).levelname}</td>
					    <td>${rewcssenondvote.countNum}</td>
					    <shiro:hasPermission name="gly5">					    
						    <td>
								${rewcssenondvote.isagree0 =="1" ? "同意" : ""}
						    </td>
						    <td>
								${rewcssenondvote.isagree1 =="1" ? "同意" : ""}
							</td>
							<td>${fns:getUserById(rewcssenondvote.createBy).name}</td>
						</shiro:hasPermission>
					  </tr>
				 </c:forEach>
		 
		 	</c:if>
			</tbody>
		</table>
		<c:if test="${fn:length(page.list) > 0 && !isGlyRole }">
			<input id="btnSubmit" class="btn btn-primary saveBtn" type="submit" value="保 存"/>
		</c:if>
	</form>
	<div class="pagination">${page}</div>
</c:if>	
	<script type="text/javascript">
		var synums0 = $("#synumId0").val();
		var synums1 = $("#synumId1").val();
		function checkedNum(kbn,obj){
			if(kbn == '0'){
				// 特等奖
				if($(obj).is(':checked')){
					synums0 = Number(synums0) - 1;
					
					if(Number(synums0) < 0){
						
						alert("特等奖同意的投票总数 已达到最高(" + ${allNum0 }  +"),不允许选中;如果需要继续提交，请先取消一个");
						$(obj).removeAttr("checked");
						synums0 = Number(synums0) + 1;
						return;
					}
					var tempId = mySub($(obj).attr("name")) + "1";
					document.getElementById(tempId).disabled=true;
				}else{
					var tempId = mySub($(obj).attr("name")) + "1";
					document.getElementById(tempId).disabled=false;
					synums0 = Number(synums0) + 1;
				}
			}else if(kbn == '1'){
				// 一等奖
				if($(obj).is(':checked')){
					
					synums1 = Number(synums1) - 1;
					
					if(Number(synums1) < 0){
						
						alert("一等奖同意的投票总数 已达到最高(" + ${allNum1 }  +"),不允许选中;如果需要继续提交，请先取消一个");
						$(obj).removeAttr("checked");
						synums1 = Number(synums1) + 1;
						return;
					}
					var tempId = mySub($(obj).attr("name")) + "0";
					document.getElementById(tempId).disabled=true;
				}else{
					var tempId = mySub($(obj).attr("name")) + "0";
					document.getElementById(tempId).disabled=false;
					synums1 = Number(synums1) + 1;
				}
			}
		}
		
		function mySub(obj){
			var len = obj.length;
			return obj.substr(0,len - 1);
		}
		
		function page(n,s){
			synums0 = $("#synumId0").val();
			synums1 = $("#synumId1").val();
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	
	</script>
</body>
</html>