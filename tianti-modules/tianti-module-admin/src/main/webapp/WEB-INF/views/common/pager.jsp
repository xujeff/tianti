<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<style type="text/css">
.pageLink {
	border: 1px solid #dddddd;
	padding: 4px 12px;
	text-decoration: none;
}

.selectPageLink {
	border: 1px solid #0088cc;
	padding: 4px 12px;
	color: #0088cc;
	background-color: #dddddd;
	text-decoration: none;
}
</style>

<script type="text/javascript">
$(function(){
	$("a[name='first']").bind("click",function(event){
		event.preventDefault();
		$("input[name='currentPage']").val(1);
		pageAction();
	});
	$("a[name='prev']").bind("click",function(event){
		event.preventDefault();
		if($("input[name='currentPage']").val()>1){
			$("input[name='currentPage']").val(parseInt($("input[name='currentPage']").val())-1);
		}else{
			$("input[name='currentPage']").val($("input[name='currentPage']").val());
		}
		pageAction();
	});
	$("a[name='next']").bind("click",function(event){
		event.preventDefault();
		var totalPage = parseInt($("#totalPage").val());
		var currentPage = parseInt($("input[name='currentPage']").val());
		if(totalPage>currentPage){
			$("input[name='currentPage']").val(currentPage+1);
		}else{
			$("input[name='currentPage']").val(currentPage);
		}
		pageAction();
	});
	$("a[name='doNumberPage']").bind("click",function(event){
		event.preventDefault();
		$("input[name='currentPage']").val($(this).html());
		pageAction();
	});
	$("a[name='last']").bind("click",function(event){
		event.preventDefault();
		$("input[name='currentPage']").val($("#totalPage").val());
		pageAction();
	});
	
	$('a[name="doMyPage"]').bind("click",function(e){
		$("input[name='currentPage']").val($("#myPageNumber").val());
		pageAction();
	});
});
function pageAction(){
	var _form = $("form");
	_form.attr("action",$("button[method='list']").attr("method"));
	_form.submit();
}
function toEditCurrPage(url){
	
	var _form = $("form");
	_form.attr("action",url);
	_form.submit();
}
</script>

</head>

<body>
	<!-- 分页标签 -->
	<div style="text-align: right; border: 0;padding: 4px 12px;" class="pageDiv">
		<input type="hidden" name="currentPage" id="currentPage" value="${page.currentPage }">
		<input type="hidden" id="totalPage" value="${page.totalPage }">
		<pg:pager url="${pageContext.request.contextPath}?currentPage=${pageNumber}" items="${page.totalCount}"
			export="currentPageNumber=pageNumber"
			maxPageItems="${page.pageSize}" maxIndexPages="1000" isOffset="true">
					总共：${page.totalCount}条,共:${page.totalPage}页
					<pg:param name="cc" />
			<input type="text" id="myPageNumber" style="width:2%" value="${page.currentPage }" style="color:green;">
			<a name="doMyPage" class="pageLink">跳转</a>
			<pg:first>
				<a name="first" href="#" class="pageLink">首页</a>
			</pg:first>
			<c:if test="${page.currentPage != 1 && page.totalPage > 0 }">
			  <a name="prev" href="#" class="pageLink">上一页</a>
			</c:if>
			<pg:pages>
				<c:choose>
					<c:when test="${page.currentPage==pageNumber}">
						<span class="selectPageLink">${pageNumber}</span>
					</c:when>
					<c:otherwise>
						<c:if test="${(pageNumber-page.currentPage lt 5) and (pageNumber-page.currentPage gt -5)}">
							<a name="doNumberPage" href="#" class="pageLink">${pageNumber}</a>
						</c:if>
					</c:otherwise>
				</c:choose>
			</pg:pages>
			<c:if test="${page.currentPage != page.totalPage  && page.totalPage > 0 }">
			   <a name="next" href="#" class="pageLink">下一页</a>
			</c:if>
			<pg:last>
				<a name="last" href="#" class="pageLink">尾页</a>
			</pg:last>
		</pg:pager>
	</div>
</body>
</html>
