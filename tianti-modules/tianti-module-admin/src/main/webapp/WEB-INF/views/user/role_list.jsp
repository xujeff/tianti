<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色列表 - ${title }</title>
</head>
<link href="${ctx }/static/plugins/zTree_v3/css/zTreeStyle.css" rel="stylesheet" />
<body <%@ include file="../common/skin.jsp" %>>
	<%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>
    <div class="J_content">
		<div class="mt20 plr20">
		  <form action="${ctx }/user/role_list" id="queryForm" method="POST">
	        <div class="J_toolsBar clearfix">
				<div class="t_label">角色名称</div>
				<div class="t_text ml10">
                	<input placeholder="请输入角色名称" type="text" name="name" value="${roleQueryDTO.name }"/>
                </div>
                <div class="t_button mgl30">
               		<a class="abtn red" href="javascript:myQuery();">
               		    <i class="icon"></i>查询
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn blue" href="javascript:myEdit();">
               		    <i class="icon"></i>新增
               		</a>
               	</div>
               	<div class="t_label ml10">
					记录数：<label style="color: red;" id="total">${page.totalCount }</label>
				</div>
			</div>
			<div class="J_table mt20">
                 <div class="t_table">
                     <table>
                         <thead>
                             <tr>
                                 <td>
                                 	<span>序号</span>
                                 </td>
                                 <td>
                                     <span>名称</span>
                                 </td>
                                 <td>
                                     <span>描述</span>
                                 </td>
                                 <td>
                                 	 <span>创建时间</span>
                                 </td>
                                 <td>
                                 	 <span>状态</span>
                                 </td>
                                 <td>
                                     <span>操作</span>
                                 </td>
                             </tr>
                         </thead>
                         <tbody>
                            <c:choose>
                              <c:when test="${page.list != null && page.totalCount > 0 }">
                                 <c:forEach items="${page.list }" var="r" varStatus="status">
		                             <tr>
		                                 <td class="first">
		                                 	 <div class="t_text tc">
		                                        ${status.index+1 }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                        ${r.name }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         ${r.description }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         <fmt:formatDate value="${r.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
		                                     </div>
		                                 </td>
		                                 <td>
		                                 	<div class="t_text tc">
		                                 		<c:choose>
		                                         	<c:when test="${r.deleteFlag eq '0' }">
		                                         		<label class="normal_flag">正常</label>
		                                         	</c:when>
		                                         	<c:otherwise>
		                                         		<label class="delete_flag">删除</label>
		                                         	</c:otherwise>
		                                         </c:choose>
		                                 	</div>
		                                 </td>
		                                 <td>
		                                     <div class="t_link">
		                                         <a href="javascript:myEdit('${r.id }');"><i class="icon"></i>编辑</a>
		                                         <c:choose>
		                                         	<c:when test="${r.deleteFlag eq '0' }">
		                                         		<a href="javascript:updDeleteFlag('${r.id }', '1');"><i class="icon"></i>删除</a>
		                                         	</c:when>
		                                         	<c:otherwise>
		                                         		<a href="javascript:updDeleteFlag('${r.id }', '0');"><i class="icon"></i>恢复</a>
		                                         	</c:otherwise>
		                                         </c:choose>
		                                     </div>
		                                 </td>
		                             </tr>
	                             </c:forEach>
                              </c:when>
                              <c:otherwise>
                                  <tr>
                                    <td colspan="6">
	                                  <div class="J_null mt40">
								            <img src="${ctx }/static/images/null.png">
								            <p>暂无相关数据</p>
								      </div>
								    </td>
							      </tr>
                              </c:otherwise>
                            </c:choose>
                         </tbody>
                     </table>
                 </div>
                 <%@ include file="../common/pager.jsp"%>
             </div>
           </form>
		</div>
    </div>
<script type="text/javascript" src="${ctx }/static/plugins/zTree_v3/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
	function myEdit(id){
		var loadIdx = layer.load();
		var title = '添加角色';
		if(!id){
			id = '';
		}else{
			title = '修改角色';
		}
		$.post('${ctx}/user/dialog/role_edit?id='+id, {}, function(str){
			
			layer.close(loadIdx);
			
			layer.open({
				title : title,
				type : 1,
				area : ['600px', '400px'],
				content : str,
				btn : ['确定', '取消'],
				yes : function(index, layero){
					mySubmit();
				},
				btn2 : function(index, layero){
				    layer.close(index);
				}
			});
		});
	}
	
	function mySubmit(){
		$('#editForm').submit();
	}
	
	function myQuery(){
		$('#queryForm').submit();
	}
	
	
	function updDeleteFlag(id, deleteFlag){
		var ids = new Array();
		ids.push(id);
		
		var content = '';
		if(deleteFlag == '0'){
			content = '确定要恢复数据吗？';
		}else{
			content = '确定要删除数据吗？';
		}
		
		layer.confirm(content, function(index){
			layer.close(index);
			
			var loadIdx = layer.load();
			$.ajax({
				url : '${ctx}/user/ajax/upd_role/delete_flag',
				type : 'post',
				data : {
					'ids' : ids,
					'deleteFlag' : deleteFlag
				},
				traditional : true,
				success : function(result){
					layer.close(loadIdx);
					if(result.success){
						layer.alert('操作成功', function(){
							window.location.reload();
						});
					}else{
						layer.alert('操作失败');
					}
				}
			});
		});
	}
</script>
</body>
</html>