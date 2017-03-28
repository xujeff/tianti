<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${menu_name }- ${title }</title>
</head>
<body <%@ include file="../common/skin.jsp" %>>
    <%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>
	<div class="J_content">
		<div class="mt10 plr20">
			<div class="J_title">
				<div class="t_token"></div>
				<div class="t_text">皮肤选择</div>
			</div>
			<div data-ui="skinList" class="J_skinList">
                <div class="s_inner clearfix">
                    <div data-class="blue" 
                    <c:choose>
						<c:when test="${sessionScope.session_login_user.currentSkin != 'skin_blue' }">
					       class="i_item"
					     </c:when>
						<c:otherwise>
					       class="i_item current"
					     </c:otherwise>
					</c:choose>
                    >
                        <div class="i_picture">
                            <a href="javascript:;" title="此皮肤为天梯蓝风格">
                                <img src="${ctx }/static/images/skin/blue.jpg" alt="此皮肤为天梯蓝风格" />
                            </a>
                        </div>
                        <div class="i_mask">
                            <i class="icon"></i>
                        </div>
                        <div class="i_name">
                                                                 天梯蓝(默认)
                        </div>
                        
                    </div>
                    <div data-class="red" 
                    <c:choose>
						<c:when test="${sessionScope.session_login_user.currentSkin == 'skin_red' }">
					       class="i_item current"
					     </c:when>
						<c:otherwise>
					       class="i_item"
					     </c:otherwise>
					</c:choose>
                    >
                        <div class="i_picture">
                            <a href="javascript:;">
                                <img src="${ctx }/static/images/skin/red.jpg" alt="此皮肤为天梯红风格" />
                            </a>
                        </div>
                        <div class="i_mask">
                            <i class="icon"></i>
                        </div>
                        <div class="i_name">
                                                                天梯红
                        </div>
                        
                    </div>
                    <div data-class="" class="i_item">
                        <div class="i_picture">
                            <a href="javascript:;">
                                <img src="${ctx }/static/images/skin/more.jpg"  alt="更多皮肤敬请期待" />
                            </a>
                        </div>
                        <div class="i_mask">
                            <i class="icon"></i>
                        </div>
                    </div>
                </div>
            </div>
		</div>
	</div>
	<script type="text/javascript">
        $(function () {
            $(document.body).on("click", ".J_skinList .s_inner .i_item", function () {
                var data_class = $(this).attr("data-class");
                if (data_class) {
                    $("body").attr("class", "skin_" + data_class);
                    $(this).addClass("current").siblings().removeClass("current");
                    $.ajax({
        				url : '${ctx}/user/ajax/upd/skin',
        				type : 'post',
        				data : {
        					'skin' : "skin_"+data_class
        				},
        				traditional : true,
        				success : function(result){
        					if(result.success){
        						layer.alert('选择成功');
        					}else{
        						layer.alert('操作失败');
        					}
        				}
        			});
                }
            });
        });
    </script>
	
</body>
</html>