<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<html>

<%@ include file="../../common/jstl.jsp"%>
<head>
</head>

<body>
	
	<div id="addForm" class="mgt40">
		<form action="${ctx }/cms/column/ajax/save" id="editForm" method="post">
		<input type="hidden" name="id" value="${columnInfo.id }"/>
		<div class="">
			<div class="J_formTable l_form_table">
				<table class="not_hightlight">
				    <tr>
						<td class="l_title w200"><b class="cRed">*</b> 栏目编码:</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                         <c:choose>
                                            <c:when test="${columnInfo == null  }">
                                               <input type="text" name="code" maxlength="20" data-rule="栏目编码:required;code;remote[${ctx }/cms/column/ajax/validator/code]"  value="${columnInfo.code }"/>
                                            </c:when>
                                            <c:otherwise>
                                               <input type="text" name="code" maxlength="20" readonly="readonly" value="${columnInfo.code }"/>
                                            </c:otherwise>
                                         </c:choose>
                                         
                                     </label>
                                 </div>
                             </div>
                         </td>
                    </tr>
					<tr>
						<td class="l_title w200"><b class="cRed">*</b> 栏目名称:</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                         <input type="text" name="name" maxlength="32" value="${columnInfo.name }"/>
                                     </label>
                                 </div>
                             </div>
                         </td>
                    </tr>
                    <tr>
						<td class="l_title "><b class="cRed">*</b> 栏目层级</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_check w200 ml10">
                                     <label>
                                     	<input type="radio" name="columnLevel" value="level0"/>一级
                                     </label>
                                     <label>
                                     	<input type="radio" name="columnLevel" value="level1" checked="checked"/>二级
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr id="parentColumnSelect">
						<td class="l_title "><b class="cRed">*</b> 一级栏目</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_label ml10" style="width: 220px;">
									<select data-placeholder="选择父栏目" class="chosen-select" name="parentId">
										<c:forEach items="${rootCoulumnInfoList }" var="c">
											<c:set var="selected"/>
											<c:if test="${columnInfo.parent.id eq c.id }">
												<c:set var="selected" value="selected=\"selected\""/>
											</c:if>
											<option value="${c.id }" ${selected }>${c.name }</option>
										</c:forEach>
									</select>
								</div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title w200"> 序号:</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                         <input type="text" name="orderNo" maxlength="50" value="${columnInfo.orderNo }" maxlength="200"/>
                                     </label>
                                 </div>
                             </div>
                         </td>
                    </tr>
				</table>
			</div>
		</div>
		</form>
	</div>
<script type="text/javascript">
	var parentChosen;
	
	//表单验证
	$(function(){
	
		parentChosen = $(".chosen-select").chosen({
			no_results_text: "未找到父栏目",
			width : '100%'
		});
		
		$('#editForm').validator({
			ignore: ':hidden',
			fields : {
				code : '栏目编码:required;length[~20]',
				name : '栏目名称:required;length[~32]',
				orderNo : '序号:integer[+]',
				columnLevel : '栏目层级:required;',
				parentId : '父栏目:required;'
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
		
		$('input[name="columnLevel"]').on('click', function(){
			var val = $('input[name="columnLevel"]:checked').val();
			if(val == 'level1'){
				$('#parentColumnSelect').show();
			}else{
				$('#parentColumnSelect').hide();
			}
		});
		
		
		var level = '${columnInfo.level}';
		if(level != null && level != ""){
			$('input[name="columnLevel"][value="level'+level+'"]').click();
			if(level == '1'){
				$('#parentColumnSelect').show();
			}else{
				$('#parentColumnSelect').hide();
			}
		}
		
	});
</script>
</body>
</html>