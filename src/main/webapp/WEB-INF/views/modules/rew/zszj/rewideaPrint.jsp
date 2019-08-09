<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>	
<html>
	<head>
		<%--<meta name="decorator" content="default"/>--%>
		<title>海洋工程科学技术奖评审意见表</title>
		<link href="${ctxStatic}/zkr/cxs/css/sea.css" type="text/css" rel="stylesheet" /> 
		<style type="text/css">
			.container table td.grade>p>b.check{background:url(${ctxStatic}/images/check.jpg);}
			img{vertical-align:top;}
		</style>
	</head>
	<body>
	<div id="printqy">
		<h1>海洋工程科学技术奖评审意见表</h1>
		<div class="container" style="margin-bottom: 0px">
			<h3>项目编号：${project.projectid }</h3>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="left">专业组</td>
					<td>${project.groupName }</td>
				</tr>
				<tr>
					<td class="left">项目名称</td>
					<td>${project.projectname }</td>
				</tr>
				<tr>
					<td class="left">申报单位</td>
					<td>${fns:getOfficeById(project.companyId)}</td>
				</tr>
				<tr>
					<td class="left left_last" rowspan="2">评审意见</td>
					<td id="suggest">
						<div class="grade">
							<p class="first">奖励等级</p>
							<c:if test="${rewidea.levelid =='1' }">
								<p><span>特等</span><b><img alt="" src="${ctxStatic}/images/check.jpg"></b></p>
								<p><span>一等</span><b></b></p>
							    <p><span>二等</span><b></b></p>
							    <p style="border-right: none;"><span>不奖</span><b></b></p>
							</c:if>
							<c:if test="${rewidea.levelid =='2' }">
								<p><span>特等</span><b></b></p>
								<p><span>一等</span><b><img alt="" src="${ctxStatic}/images/check.jpg"></b></p>
							    <p><span>二等</span><b></b></p>
							    <p style="border-right: none;"><span>不奖</span><b></b></p>
							</c:if>
							<c:if test="${rewidea.levelid =='3' }">
								<p><span>特等</span><b></b></p>
								<p><span>一等</span><b></b></p>
							    <p><span>二等</span> <b><img alt="" src="${ctxStatic}/images/check.jpg"></b> </p>
							    <p style="border-right: none;"><span>不奖</span><b></b></p>
							</c:if>
							<c:if test="${rewidea.levelid =='4' }">
								<p><span>特等</span><b></b></p>
								<p><span>一等</span><b></b></p>
							    <p><span>二等</span><b></b></p>
							    <p style="border-right: none;"><span>不奖</span><b><img alt="" src="${ctxStatic}/images/check.jpg"></b></p>
							</c:if>
						</div>
						<div class="right_last">
							<p>${rewidea.revidea }</p>
							<div>
								<span>专家（签字）：</span>
								<p>年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</p>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<p id="aaa">中国海洋工程咨询协会制</p>
		</div>
	</div>
	    <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT>
		<script type="text/javascript">
			function printit(){
				wb.execwb(6,6);
				//wb.execwb(7,1)
				//window.opener=null;			
				//window.close();
				
				

			}
			printit();
		</script>
	</body>
</html>
