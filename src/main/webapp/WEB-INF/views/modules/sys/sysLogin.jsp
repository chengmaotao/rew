<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
		/*公共样式*/
		body,h2,p{margin:0;padding:0;}
		body{font-family:'微软雅黑','黑体',Arial;color:#333;background:url(${ctxStatic}/zkr/cxs/images/top_bg.jpg) no-repeat -90px 0;}
		#container{width:40%;margin:0 auto;margin-top:180px;padding-bottom:40px;text-align:center;}
		#container label{font-size:20px;}
		#container input{width:185px;height:35px;border:1px solid #a9a9a9;margin-left:20px;margin-bottom:20px;font-size:12px;color:#333;padding-left:10px;}
		#container form h2{padding-top:20px;padding-bottom:20px;}
		#container #btn{width:150px;font-weight:bold;font-size:16px;color:#fff;border:none;border-radius:5px;background-color:#3274c2;margin-left:40px;padding-right:10px;}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({	//表单form
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写帐号."},password: {required: "请填写密码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div id="container">
		<div class="header">
			<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
				<label id="loginError" class="error">${message}</label>
			</div>
		</div>
		<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
			<h2>科学技术奖评审登录</h2>
			<label>帐号<input type="text" id="username" name="username" value="${username}"></label><br>
			<label>密码<input type="password" id="password" name="password"></label><br>
			<input type="submit" id="btn" value="登 录"/>&nbsp;&nbsp;
		</form>
	</div>
	
	
	 <div id="footer" class="row-fluid" style="position: absolute;bottom: 20px ">
	 	<center> Copyright &copy; 中科软科技股份有限公司</center>
	 </div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>