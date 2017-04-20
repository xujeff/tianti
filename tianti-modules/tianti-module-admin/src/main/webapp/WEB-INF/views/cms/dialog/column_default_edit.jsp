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
		<input type="hidden" name="columnLevel" value="${columnLevel }"/>
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
						<td class="l_title ">栏目层级:</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="w200 ml10">
                                     <label>
                                     	<c:choose>
                                     	  <c:when test="${columnLevel == 'level0' }">
                                     	             一级
                                     	  </c:when>
                                     	  <c:when test="${columnLevel == 'level1' }">
                                     	             二级
                                     	  </c:when>
                                     	</c:choose>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <c:if test="${columnLevel == 'level1'}">
	                     <tr>
							<td class="l_title ">一级栏目:</td>
	                         <td>
	                             <div class="t_opacitySelect ml10">
						              <select name="parentId">
						                   <c:forEach items="${rootCoulumnInfoList }" var="c">
						                	<option value="${c.id }"
						                	   <c:if test="${c.id == rootColumnId }">selected</c:if>
						                	>${c.name }</option>
						                   </c:forEach>
						              </select>
						        </div>
	                         </td>
	                     </tr>
                     </c:if>
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
		
	});
</script>
</body>
</html>