<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<html>

<%@ include file="../../common/jstl.jsp"%>

<script type="text/javascript">

var roleChosen;

//表单验证
$(function(){
	
	roleChosen = $(".chosen-select").chosen({
		no_results_text: "未找到权限",
		width : '100%'
	});
	
	$('#editForm').validator({
		fields : {
			name : '角色名称:required;length[~50]'
		},
		valid : function(form){
			var laodIdx = layer.load();
			
			$('#editForm').ajaxSubmit({
				data : {
				},
				traditional : true,
				success : function(result){
					layer.close(laodIdx);
					if(result.success){
						layer.alert('保存成功', function(){
							window.location.reload();
						});
					}else{
						layer.alert(result.msg);
					}
				}
			});
		}
	});
	
});


</script>

<head>
</head>

<body>

	
	<div id="addForm" class="mgt40">
		<form action="${ctx }/user/ajax/save" id="editForm" method="post">
		<input type="hidden" name="id" value="${user.id }"/>
		<div class="">
			<div class="J_formTable l_form_table">
				<table class="not_hightlight">
					<tr>
						<td class="l_title w200"><b class="cRed">*</b> 账号</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<c:choose>
                                     		<c:when test="${not empty user }">
                                     			<input type="text" name="username" data-rule="账号:required;" value="${user.username }" readonly="readonly"/>
                                     		</c:when>
                                     		<c:otherwise>
                                     			<input type="text" name="username" data-rule="账号:required;username;remote[${ctx }/user/ajax/validator/username]" value="" />
                                     		</c:otherwise>
                                     	</c:choose>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "><b class="cRed">*</b> 密码</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<c:choose>
                                     		<c:when test="${not empty user }">
                                     			<input type="password"placeholder="修改时输入" name="password" data-rule="密码:password;" value="" />
                                     		</c:when>
                                     		<c:otherwise>
                                     			<input type="password" name="password" data-rule="密码:required;password;" value="" />
                                     		</c:otherwise>
                                     	</c:choose>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "> 真实姓名</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<input type="text" name="realName" data-rule="真实姓名:length[~20];" value="${user.realName }" />
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "> 手机号码</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<input type="text" name="mobile" data-rule="手机号码:mobile;" value="${user.mobile }" />
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "> 权限</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_label ml10" style="width: 220px;">
									<select data-placeholder="选择权限" multiple class="chosen-select" name="roleId" data-rule="权限:required;">
										<c:forEach items="${roles }" var="r">
											<c:set var="selected"/>
											<c:forEach items="${user.roles }" var="ur">
												<c:if test="${ur.id eq r.id }">
													<c:set var="selected" value="selected=\"selected\""/>
												</c:if>
											</c:forEach>
											<option value="${r.id }" ${selected }>${r.name }</option>
										</c:forEach>
									</select>
								</div>
                             </div>
                         </td>
                     </tr>
                     
				</table>
			</div>
		</div>
		</form>
	</div>

</body>
</html>