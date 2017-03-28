<%@ page language="java" pageEncoding="UTF-8"%>

<c:choose>
	<c:when
		test="${sessionScope.session_login_user.currentSkin != null && sessionScope.session_login_user.currentSkin != '' }">
       class="${sessionScope.session_login_user.currentSkin}"
     </c:when>
	<c:otherwise>
       class="skin_blue"
     </c:otherwise>
</c:choose>
