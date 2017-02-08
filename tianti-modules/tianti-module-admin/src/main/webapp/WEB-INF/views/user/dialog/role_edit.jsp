<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<html>

<%@ include file="../../common/jstl.jsp"%>

<script type="text/javascript">

//表单验证
$(function(){
	
	$('#editForm').validator({
		isShowMsg : false,
		isShowIcon : false,
		fields : {
			name : '角色名称:required;length[~50]'
		},
		valid : function(form){
			var laodIdx = layer.load();
			
			var rescoureIds = getResourceIds();
			
			$('#editForm').ajaxSubmit({
				data : {
					'rescoureIds' : rescoureIds
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


var zTreeObj;
var setting = {
		data : {
			simpleData: {
				enable: true
			}
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "ps" }
		}
};

var resourceJson = '${resourceJson}';

var zNodes = JSON.parse(resourceJson);

$(function(){
	
	 zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
	
});

function getResourceIds(){
	var resourceIds = new Array();
	
	var nodes = zTreeObj.getCheckedNodes(true);
	if(nodes){
		for(var i in nodes){
			resourceIds.push(nodes[i].id);
		}
	}
	
	return resourceIds;
}


</script>

<head>
</head>

<body>

	
	<div id="addForm" class="l_role_from mt20">
		<form action="${ctx }/user/ajax/save_role" id="editForm" method="post">
		<input type="hidden" name="id" value="${role.id }"/>
		<div class="l_role_left">
			<div class="J_formTable">
				<table class="not_hightlight">
					<tr>
						<td class="l_title w60"><b class="cRed">*</b> 名称</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <label>
                                         <input type="text" name="name" maxlength="50" value="${role.name }"/>
                                     </label>
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
					 	<td class="l_title">描述</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_textarea w200 ml10">
                                     <textarea name="description">${role.description }</textarea>
                                 </div>
                             </div>
                         </td>
                     </tr>
				</table>
			</div>
		</div>
		<div class="l_role_right">
			<div>选择菜单</div>
			<div class="l_role_tree" style="height: 260px; overflow: auto;">
				<ul id="ztree" class="ztree"></ul>
			</div>
		</div>
		</form>
	</div>

</body>
</html>