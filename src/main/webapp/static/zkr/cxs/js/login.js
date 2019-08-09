/*function chkName(username){	// 登录页面账号验证
	if(username.value==''){
		alert('账号不能为空');
		username.focus();
		return false;
	}
	return true;
}
function chkPwd(password){	// 登录页面密码验证
	if(password.value==''){
		alert('密码不能为空');
		password.focus();
		return false;
	}
	return true;
}
window.onload=function(){
	// 登录页面验证
	var form=document.forms[0];
	form[0].onblur=function(){
		chkName(form.username);
	}
	form[1].onblur=function(){
		chkPwd(form.password);
	}
	form.elements[2].onclick=function(){
		if(chkName(form.username)&&chkPwd(form.password)){
			this.parentNode.submit();
		}
	}
	// 登录页面验证结束
}*/