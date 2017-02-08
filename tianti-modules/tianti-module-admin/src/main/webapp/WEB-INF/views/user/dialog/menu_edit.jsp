<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<html>

<%@ include file="../../common/jstl.jsp"%>

<script type="text/javascript">

var parentChosen;

//表单验证
$(function(){

	parentChosen = $(".chosen-select").chosen({
		no_results_text: "未找到父菜单",
		width : '100%'
	});
	
	$('#editForm').validator({
		ignore: ':hidden',
		fields : {
			name : '名称:required;length[~20]',
			orderNo : '序号:integer[+]',
			type : '类型:required;',
			parentId : '父菜单:required;'
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
	
	$('input[name="type"]').on('click', function(){
		var val = $('input[name="type"]:checked').val();
		if(val == 'page'){
			$('#parentMenuSelect').show();
		}else{
			$('#parentMenuSelect').hide();
		}
	});
	
	
	var type = '${resource.type}';
	if(type){
		$('input[name="type"][value="'+type+'"]').click();
		if(type == 'page'){
			$('#parentMenuSelect').show();
		}else{
			$('#parentMenuSelect').hide();
		}
	}
	
});


</script>

<head>
</head>

<body>

	
	<div id="addForm" class="mgt40">
		<form action="${ctx }/user/ajax/save_menu" id="editForm" method="post">
		<input type="hidden" name="id" value="${resource.id }"/>
		<div class="">
			<div class="J_formTable l_form_table">
				<table class="not_hightlight">
					<tr>
						<td class="l_title w200"><b class="cRed">*</b> 名称</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                 	<input type="text" name="name" value="${resource.name }" maxlength="20"/>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "> 图标</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<input type="text" name="icon" value="${resource.icon }" maxlength="20"/>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "> URL</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<input type="text" name="url" value="${resource.url }" maxlength="200"/>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "> 序号</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                     	<input type="text" name="orderNo" value="${resource.orderNo }" maxlength="200"/>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "><b class="cRed">*</b> 类型</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_check w200 ml10">
                                     <label>
                                     	<input type="radio" name="type" value="module"/>模块
                                     </label>
                                     <label>
                                     	<input type="radio" name="type" value="page" checked="checked"/>页面
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr id="parentMenuSelect">
						<td class="l_title "><b class="cRed">*</b> 父菜单</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_label ml10" style="width: 220px;">
									<select data-placeholder="选择父菜单" class="chosen-select" name="parentId">
										<c:forEach items="${modelResources }" var="r">
											<c:set var="selected"/>
											<c:if test="${resource.parent.id eq r.id }">
												<c:set var="selected" value="selected=\"selected\""/>
											</c:if>
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