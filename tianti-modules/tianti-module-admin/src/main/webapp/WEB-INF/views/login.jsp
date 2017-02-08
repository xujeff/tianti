<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ include file="common/common.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录 - ${title }</title>
</head>

<script type="text/javascript">

function mySubmit(){
	
	var username = $.trim($('#username').val());
	var pwd = $.trim($('#pwd').val());
	
	if(!username){
		layer.alert('请输入账号');
		return;
	}
	
	if(!pwd){
		layer.aletr('请输入密码');
		return;
	}
	
	$('#mySubmit').submit();
}

$(function(){
	
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	mySubmit();
	    }
	}
	
});

</script>

<body>
    <div class="J_loginMain">
        <div class="l_inner">
        	<form action="${ctx }/do_login" method="post" id="mySubmit">
	            <div class="i_main">
	                <div class="m_txt">
	                	<c:choose>
	                		<c:when test="${not empty msg }">${msg }</c:when>
	                		<c:otherwise>天梯管理系统</c:otherwise>
	                	</c:choose>
	                </div>
	                <div class="m_input">
	                    <input placeholder="请输入帐号" type="text" name="username" id="username"/>
	                </div>
	                <div class="m_input">
	                    <input placeholder="请输入密码" type="password" name="pwd" id="pwd"/>
	                </div>
	                <div class="m_btn">
	                    <a href="javascript:mySubmit();">登录</a>
	                </div>
	            </div>
            </form>
        </div>
    </div>
	
	<%@ include file="common/footer.jsp" %>
</body>
</html>