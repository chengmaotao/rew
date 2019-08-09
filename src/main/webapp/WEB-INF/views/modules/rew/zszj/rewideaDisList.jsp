<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
	<title>评审意见管理</title>

	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//top.$.jBox.tip("该菜单 已经被停止使用，不能进行操作","1",{persistent:true,opacity:0});
			trCorlor();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//function myShowModalDia(pars){
		//	window.showModalDialog("${ctxStatic}/zkr/modal.html",pars,"dialogWidth=400px;dialogHeight=300px;help=no;status=no");
		//}
		
		function myShowModalDia(val,lbl){
			$("#myModalLablea").text(lbl + "的评审意见");
			$("#myModalVala").text(val);
		}
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="rewidea" action="${ctx}/zszj/rewidea/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		     
	        
			<li><label>项目名称：</label> 
     			<form:select path="projectid" class="input-xlarge2" >
        			<form:option value="" label="请选择"/>
        			<form:options items="${projects }" itemLabel="projectname" itemValue="projectid" htmlEscape="false"/>
     			</form:select>
		    </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>项目编号</th>
				--%><th>专业组</th>
				<th>申报单位</th>
				<th>项目名称</th>
				<th>奖励等级</th>
				<th>评审意见</th>
				<c:if test="${zyzz}"><th>主审专家</th></c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rewidea">
			<tr>
				<%--<td>${rewidea.projectid }</td>
				--%><td> 
				    ${rewidea.groupName }		    
				    <c:if test="${rewidea.groupSonName != null and  rewidea.groupSonName != ''}"> 
				      -- ${rewidea.groupSonName }
				    </c:if>
				</td>
				
				
				<td>${rewidea.companyName }</td>
				<td>${fns:getProjectByProjectId(rewidea.projectid).projectname}</td>
				
				<td>${fns:getAwardLevelById(rewidea.levelid).levelname}</td>
				<%--<td><span title="${rewidea.revidea }"> <a href="#" onclick="myShowModalDia('${fns:replaceRn(rewidea.revidea)}')" title="详细">${fns:getRewIdea(rewidea.revidea)}</a></span></td>
				--%><td><span title="${rewidea.revidea }"> <a href="#myModal" role="button" data-toggle="modal" title="详细" onclick="myShowModalDia('${fns:replaceRn(rewidea.revidea)}','${fns:getProjectByProjectId(rewidea.projectid).projectname}')">${fns:getRewIdea(rewidea.revidea)}</a></span></td>
				<c:if test="${zyzz }"><td>${fns:getUserById(rewidea.createBy).name}</td></c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
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
	<div class="pagination">${page}</div>
	<div class="rewFoot">${rewFoot }</div>
</body>
</html>