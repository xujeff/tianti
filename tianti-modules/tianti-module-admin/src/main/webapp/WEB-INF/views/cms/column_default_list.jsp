<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp" %>
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
		  <form action="${ctx }/cms/column/list" id="queryForm">
		    <input type="hidden" name="rootColumnId" value="${columnInfoQueryDTO.rootColumnId }" >
	        <div class="J_toolsBar clearfix">
	            <div class="t_text ml10">
                	<input placeholder="请输入栏目编码" type="text" name="code" value="${columnInfoQueryDTO.code }"/>
                </div>
				<div class="t_text ml10">
                	<input placeholder="请输入栏目名称" type="text" name="name" value="${columnInfoQueryDTO.name }"/>
                </div>
                <div class="t_button ml10">
               		<a class="abtn red" href="javascript:myQuery();">
               		     <i class="icon"></i>查询
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn blue" href="javascript:myEdit('','level1');">
               		     <i class="icon"></i>新增二级栏目
               		</a>
               	</div>
			</div>
			<div class="J_column">	
			 <div class="c_left">
			   <div class="J_filterGroup">
                   <div class="f_toolbar">
                       <div class="t_text">一级栏目</div>
                   </div>
                   <div class="f_main">
                            <div class="m_list" id="columnContent">
	                            <c:forEach items="${rootCoulumnInfoList }" var="c">
	                                <div class="m_item 
	                                 <c:if test="${c.id == columnInfoQueryDTO.rootColumnId }">open</c:if>
	                                 ">
	                                 <div class="i_default
	                                 <c:if test="${c.id == columnInfoQueryDTO.rootColumnId }">current</c:if>
	                                 ">
                                        <a href="${ctx }/cms/column/list?rootColumnId=${c.id}">${c.name }</a>
                                     </div>
                                    </div>
	                            </c:forEach>
                               
                            </div>
                            <div class="m_btn">
                                <a href="javascript:myEdit('','level0');"><i class="icon"></i><span>添加</span></a>
                                <a href="javascript:myEdit('${columnInfoQueryDTO.rootColumnId }','level0');"><i class="icon"></i><span>修改</span></a>
                                <a href="javascript:delColumn('${columnInfoQueryDTO.rootColumnId }');"><i class="icon"></i><span>删除</span></a>
                            </div>
                    </div>
               </div>
			  </div>
		      <div class="c_right">
			    <div class="J_table mt20">
                 <div class="t_table">
                     <table>
                         <thead>
                             <tr>
                                 <td>
                                 	<span>序号</span>
                                 </td>
                                 <td>
                                     <span>编码</span>
                                 </td>
                                 <td>
                                     <span>名称</span>
                                 </td>
                                 <td>
                                     <span>所属层级</span>
                                 </td>
                                 <td>
                                 	 <span>父级名称</span>
                                 </td>
                                 <td>
                                 	 <span>渠道用途</span>
                                 </td>
                                 <td>
                                 	 <span>排序</span>
                                 </td>
                                 <td>
                                 	 <span>状态</span>
                                 </td>
                                 <td>
                                 	 <span>创建时间</span>
                                 </td>
                                 <td>
                                     <span>操作</span>
                                 </td>
                             </tr>
                         </thead>
                         <tbody>
                         	<c:forEach items="${list }" var="c" varStatus="status">
	                             <tr>
	                                 <td class="first">
	                                 	 <div class="t_text tc">
	                                         ${status.index+1 }
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
                                        	<span>${c.code }</span>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
                                        	<span>${c.name }</span>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
                                        	<span>
                                        	<c:choose>
                                        	  <c:when test="${c.level == 0 }">
                                        	           一级
                                        	  </c:when>
                                        	  <c:otherwise>
                                        	           二级
                                        	  </c:otherwise>
                                        	</c:choose>
                                        	</span>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
                                        	<span>
                                        	  <c:choose>
                                        	     <c:when test="${c.parent != null }">
                                        	         ${c.parent.name }
                                        	     </c:when>
                                        	     <c:otherwise>
                                        	                       无
                                        	     </c:otherwise>
                                        	  </c:choose>
                                        	</span>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <c:choose>
	                                         	<c:when test="${c.channel == 0 }">PC渠道</c:when>
	                                         	<c:when test="${c.channel == 1 }">H5渠道</c:when>
	                                         	<c:otherwise>其他</c:otherwise>
	                                         </c:choose>
	                                     </div>
	                                 </td>
	                                 
	                                 <td>
	                                     <div class="t_text tc">
	                                         <span>${c.orderNo }</span>
	                                     </div>
	                                 </td>
	                                 <td>
	                                 	<div class="t_text tc">
	                                 		<c:choose>
	                                         	<c:when test="${c.deleteFlag eq '0' }">
	                                         		<label class="normal_flag">正常</label>
	                                         	</c:when>
	                                         	<c:otherwise>
	                                         		<label class="delete_flag">删除</label>
	                                         	</c:otherwise>
	                                         </c:choose>
	                                 	</div>
	                                 </td>
	                                 <td>
		                                  <div class="t_text tc">
		                                         <fmt:formatDate value="${c.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
		                                  </div>
		                             </td>
	                                 <td>
	                                     <div class="t_link">
	                                         <a href="javascript:myEdit('${c.id }','level1');"><i class="icon"></i>编辑</a>
	                                         <c:choose>
	                                         	<c:when test="${c.deleteFlag eq '0' }">
	                                         		<a href="javascript:updDeleteFlag('${c.id }', '1');"><i class="icon"></i>删除</a>
	                                         	</c:when>
	                                         	<c:otherwise>
	                                         		<a href="javascript:updDeleteFlag('${c.id }', '0');"><i class="icon"></i>恢复</a>
	                                         	</c:otherwise>
	                                         </c:choose>
	                                     </div>
	                                 </td>
	                             </tr>
                             </c:forEach>
                         </tbody>
                     </table>
                 </div>
             </div>
            </div>
           </div>
           </form>
		</div>
    </div>
<script src="${ctx }/static/plugins/chosen_v1.6.2/chosen.jquery.js"></script>
<script type="text/javascript">
    //添加/修改栏目
	function myEdit(id,columnLevel){
		var rootColumnId = '${columnInfoQueryDTO.rootColumnId}';
		var loadIdx = layer.load();
		var title = '添加栏目';
		if(!id){
			id = '';
		}else{
			title = '修改栏目';
		}
		$.post('${ctx}/cms/column/edit?id='+id+'&columnLevel='+columnLevel+'&rootColumnId='+rootColumnId, {}, function(str){
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
	
    //删除栏目
	function delColumn(rootColumnId){
    	if(rootColumnId == ""){
    		layer.alert('请选择需要删除的栏目');
    	}else{
    		var content = '确定要删除该栏目吗？';
    		layer.confirm(content, function(index){
    			layer.close(index);
    			var loadIdx = layer.load();
    			$.ajax({
    				url : '${ctx}/cms/column/ajax/delete',
    				type : 'post',
    				data : {
    					'ids' : rootColumnId,
    					'deleteFlag' : '1'
    				},
    				traditional : true,
    				success : function(result){
    					layer.close(loadIdx);
    					if(result.success){
    						layer.alert('操作成功', function(){
    							window.location.href="${ctx}/cms/column/list";
    						});
    					}else{
    						layer.alert('操作失败');
    					}
    				}
    			});
    		});
    	}
	}
	
    //编辑提交
	function mySubmit(){
		$('#editForm').submit();
	}
	
	//查询
	function myQuery(){
		$('#queryForm').submit();
	}
	
	//删除/恢复数据
	function updDeleteFlag(id, deletFlag){
		var ids = new Array();
		ids.push(id);
		
		var content = '';
		if(deletFlag == '0'){
			content = '确定要恢复数据吗？';
		}else{
			content = '确定要删除数据吗？';
		}
		layer.confirm(content, function(index){
			layer.close(index);
			var loadIdx = layer.load();
			$.ajax({
				url : '${ctx}/cms/column/ajax/delete',
				type : 'post',
				data : {
					'ids' : ids,
					'deleteFlag' : deletFlag
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