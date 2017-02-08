<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<div data-ui="header" class="J_header">
        <div class="h_left">
            <div class="l_txt">
            	<c:choose>
            		<c:when test="${not empty head_name }">${head_name }</c:when>
            		<c:otherwise>${menu_name }</c:otherwise>
            	</c:choose>
            </div>
            <div class="l_nav">
            	<c:forEach items="${childMenu }" var="m">
	                <div class="n_item ${m.current }">
	                    <div class="i_default"><a href="${ctx }${m.url}"><span>${m.name }</span><b></b></a></div>
	                </div>
                </c:forEach>
            </div>
        </div>
    </div>
