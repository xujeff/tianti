<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${menu_name } - ${title }</title>
</head>
<link href="${ctx }/static/plugins/chosen_v1.6.2/chosen.css" rel="stylesheet" />

<body <%@ include file="../common/skin.jsp" %>>
	<%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>
    <div class="J_content">
		<div class="mt20 plr20">
		  <form action="${ctx }/user/menu_list" id="queryForm">
	        <div class="J_toolsBar clearfix">
				<div class="t_label">菜单名称</div>
				<div class="t_text ml10">
                	<input placeholder="请输入菜单名称" type="text" name="name" value="${name }"/>
                </div>
                <div class="t_button ml10">
               		<a class="abtn red" href="javascript:myQuery();">
               		     <i class="icon"></i>查询
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn blue" href="javascript:myEdit();">
               		     <i class="icon"></i>新增
               		</a>
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
                                     <span>类型</span>
                                 </td>
                                 <td>
                                     <span>链接</span>
                                 </td>
                                 <td>
                                     <span>父菜单</span>
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
                              <c:when test="${(resources)!= null && fn:length(resources) > 0}">
                                 <c:forEach items="${resources }" var="r">
		                             <tr>
		                                 <td class="first">
		                                 	 <div class="t_text tc">
		                                        ${r.orderNo }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
	                                        	<i class="icon">${r.icon }</i>
	                                        	<span>${r.name }</span>
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         <c:choose>
		                                         	<c:when test="${r.type eq 'module' }">模块</c:when>
		                                         	<c:when test="${r.type eq 'page' }">页面</c:when>
		                                         </c:choose>
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         ${r.url }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         <i class="icon">${r.parent.icon }</i>
		                                         <span>${r.parent.name }</span>
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
                                    <td colspan="7">
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
             </div>
            </form>
		</div>
    </div>
<script src="${ctx }/static/plugins/chosen_v1.6.2/chosen.jquery.js"></script>
<script type="text/javascript">
	function myEdit(id){
		var loadIdx = layer.load();
		var title = '添加菜单';
		if(!id){
			id = '';
		}else{
			title = '修改菜单';
		}
		$.post('${ctx}/user/dialog/menu_edit?id='+id, {}, function(str){
			
			layer.close(loadIdx);
			
			layer.open({
				title : title,
				type : 1,
				area : ['700px', '450px'],
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
	
	
	function updDeleteFlag(id, deletFlag){
		var ids = new Array();
		ids.push(id);
		
		var content = '';
		if(status == '0'){
			content = '确定要恢复数据吗？';
		}else{
			content = '确定要删除数据吗？';
		}
		
		layer.confirm(content, function(index){
			layer.close(index);
			
			var loadIdx = layer.load();
			$.ajax({
				url : '${ctx}/user/ajax/upd_menu/delete_flag',
				type : 'post',
				data : {
					'ids' : ids,
					'deletFlag' : deletFlag
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