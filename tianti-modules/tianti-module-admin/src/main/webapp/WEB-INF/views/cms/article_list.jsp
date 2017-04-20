<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ include file="../common/common.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${menu_name} - ${title }</title>
</head>

<body <%@ include file="../common/skin.jsp" %>>
	<%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>

	<div data-ui="header" class="J_header">
        <div class="h_left">
            <div class="l_nav">
                <div class="n_item
                    <c:if test="${articleQueryDTO.type == null || articleQueryDTO.type == '' }">current</c:if>
                ">
                    <div class="i_default"><a href="${ctx }/cms/article/list"><span>全部文章（
                    <c:choose>
                      <c:when test="${statisMap.totalCount != null }">
                         ${statisMap.totalCount}
                      </c:when>
                      <c:otherwise>
                        0
                      </c:otherwise>
                    </c:choose>   
                                          ）</span><b></b></a></div>
                </div>
                <div class="n_item
                    <c:if test="${articleQueryDTO.type == 'zhiding' }">current</c:if>
                ">
                    <div class="i_default"><a href="${ctx }/cms/article/list?type=zhiding"><span>置顶文章（
                     <c:choose>
                      <c:when test="${statisMap.zhidingCount != null }">
                         ${statisMap.zhidingCount}
                      </c:when>
                      <c:otherwise>
                        0
                      </c:otherwise>
                    </c:choose>        
                                         ）</span><b></b></a></div>
                </div>
                <div class="n_item
                    <c:if test="${articleQueryDTO.type == 'shenhe' }">current</c:if>
                ">
                    <div class="i_default"><a href="${ctx }/cms/article/list?type=shenhe"><span>审核列表（
                    <c:choose>
                      <c:when test="${statisMap.shenheCount != null }">
                         ${statisMap.shenheCount}
                      </c:when>
                      <c:otherwise>
                        0
                      </c:otherwise>
                    </c:choose>    
                                          ）</span><b></b></a></div>
                </div>
                <div class="n_item
                    <c:if test="${articleQueryDTO.type == 'laji' }">current</c:if>
                ">
                    <div class="i_default"><a href="${ctx }/cms/article/list?type=laji"><span>垃圾箱（
                    <c:choose>
                      <c:when test="${statisMap.deleteCount != null }">
                         ${statisMap.deleteCount}
                      </c:when>
                      <c:otherwise>
                        0
                      </c:otherwise>
                    </c:choose>    ）</span><b></b></a></div>
                </div>
            </div> 
        </div>
    </div>

    <div class="J_content">
		
		<div class="mt20 plr20">	
		    <form action="${ctx }/cms/article/list" id="myForm" method="POST">
		        <input type="hidden" name="type" value="${type }" />
		        <div class="J_toolsBar clearfix">
					<div class="t_text ml10">
	                	<input placeholder="标题" type="text" style="width:200px" id="title" name="title" value="${articleQueryDTO.title }">
	                </div>
	                <div class="t_text ml10">
	                	<input placeholder="发布人" type="text" style="width:200px" id="publisher" name="publisher" value="${articleQueryDTO.publisher }">
	                </div>
	                <div class="t_text ml10">
	                	<input placeholder="开始时间" type="text" id="startDate" name="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${articleQueryDTO.startDate }">
	                </div>
	                <div class="t_text ml10">
	                	<input placeholder="结束时间" type="text" id="endDate" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${articleQueryDTO.endDate }">
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
	               	<div class="t_button ml10">
	               		<a class="abtn red" href="javascript:myAduit('','1');">
	               		     <i class="icon"></i>审核通过
	               		</a>
	               	</div>
	               	<div class="t_button ml10">
	               		<a class="abtn blue" href="javascript:myAduit('','0');">
	               		     <i class="icon"></i>审核不通过
	               		</a>
	               	</div>
	               	<div class="t_button ml10">
	               		<a class="abtn red" href="javascript:myTop('','1');">
	               		     <i class="icon"></i>置顶
	               		</a>
	               	</div>
	               	<div class="t_button ml10">
	               		<a class="abtn blue" href="javascript:myTop('','0');">
	               		     <i class="icon"></i>取消置顶
	               		</a>
	               	</div>
	               	<div class="t_label ml10">
	               		记录数：<font color="red" id="totalRecode">${page.totalCount}</font>
	               	</div>
				</div>
				<input type="hidden" id="isCreateDateSort" name="isCreateDateSort" value="${articleQueryDTO.createDateSortCss }" />
				<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden" name="currentPage" value="1"/>
			</form>
				
			<div class="J_table mt20">
                 <div class="t_table">
                     <table>
                         <thead>
                             <tr>
                                 <td>
                                   <input type="checkbox" name="selectAll" id="selectAll" onclick="selectAllIds();" />
                                 </td>
                                 <td>
                                 	<span>标题</span>
                                 </td>
                                 <td>
                                    <span>栏目</span>
                                 </td>
                                 <td>
                                    <span>发布者</span>
                                 </td>
                                 <td>
                                    <span>阅读数</span>
                                 </td>
                                 <td id="createDateTd"
                                 <c:choose>
                                    <c:when test = "${articleQueryDTO.createDateSortCss != null && articleQueryDTO.createDateSortCss != ''  }">
                                       class = "${articleQueryDTO.createDateSortCss}"
                                    </c:when>
                                    <c:otherwise>
                                      class="down"
                                    </c:otherwise>
                                  </c:choose>
                                   data-type="sort">
                                     <a href="javascript:doCreateDateSort();">
                                      <span>发布日期</span><i></i>
                                     </a>
                                 </td>
                                 <td>
                                     <span>是否审核</span>
                                 </td>
                                 <td>
                                     <span>是否置顶</span>
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
                         	<c:forEach items="${page.list }" var="p">
	                            <tr >
	                                 <td>
	                                     <div class="t_text tc">
	                                         <input type="checkBox" name="selecteId" value="${p.id }" />
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <a href="javascript:view('${p.id }');">${p.title }</a>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         ${p.columnInfo.name }
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         ${p.publisher }
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <c:choose>
	                                            <c:when test="${p.viewCount != null }">
	                                               ${p.viewCount }
	                                            </c:when>
	                                            <c:otherwise>
	                                               0
	                                            </c:otherwise>
	                                         </c:choose>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <fmt:formatDate value="${p.createDate }" pattern="yyyy-MM-dd HH:mm"/>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <c:choose>
	                                         	<c:when test="${p.isAudit eq 'true' }">
	                                         		是
	                                         	</c:when>
	                                         	<c:otherwise>
	                                         	  <font color="red">否</font>
	                                         	</c:otherwise>
	                                         </c:choose>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <c:choose>
	                                         	<c:when test="${p.isTop eq 'true' }">
	                                         	           是
	                                         	</c:when>
	                                         	<c:otherwise>
	                                         		<font color="red">否</font>
	                                         	</c:otherwise>
	                                         </c:choose>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_text tc">
	                                         <c:choose>
	                                         	<c:when test="${p.deleteFlag eq '0' }">
	                                         		正常
	                                         	</c:when>
	                                         	<c:otherwise>
	                                         		<font color="red">删除</font>
	                                         	</c:otherwise>
	                                         </c:choose>
	                                     </div>
	                                 </td>
	                                 <td>
	                                     <div class="t_link">
	                                          <a href="javascript:myEdit('${p.id }');"><i class="icon"></i>编辑</a>
		                                      <c:choose>
		                                         <c:when test="${p.deleteFlag eq '0' }">
		                                         	<a href="javascript:updDeleteFlag('${p.id }', '1');"><i class="icon"></i>删除</a>
		                                         </c:when>
		                                         <c:otherwise>
		                                         	<a href="javascript:updDeleteFlag('${p.id }', '0');"><i class="icon"></i>恢复</a>
		                                         </c:otherwise>
		                                      </c:choose>
	                                     </div>
	                                 </td>
	                             </tr>
                             </c:forEach>
                         </tbody>
                     </table>
                 </div>
                 <%@ include file="../common/pager.jsp"%>
             </div>			
		</div>
    </div>
	
	<%@ include file="../common/footer.jsp" %>
	<script type="text/javascript">

		function updDeleteFlag(id, deleteFlag){
			var ids;
			if(id){
				ids = new Array();
				ids.push(id);
			}else{
				ids = getSelectIds();
			}
			
			var msg = deleteFlag == 1 ? '删除' : '恢复';
			
			if(!ids || ids.length == 0){
				layer.msg('请选择要'+msg+'记录');
				return;
			}
			
			layer.confirm('确定要' + msg + '记录吗？', function(index){
				layer.close(index);
				
				var loadIdx = layer.load();
				$.ajax({
					url : '${ctx}/cms/article/ajax/delete',
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
		
		function myAduit(id, auditFlag){
			var ids;
			if(id){
				ids = new Array();
				ids.push(id);
			}else{
				ids = getSelectIds();
			}
			
			var msg = auditFlag == 1 ? '审核' : '取消审核';
			
			if(!ids || ids.length == 0){
				layer.alert('请选择要'+msg+'记录');
				return;
			}
			
			layer.confirm('确定要' + msg + '记录吗？', function(index){
				layer.close(index);
				
				var loadIdx = layer.load();
				$.ajax({
					url : '${ctx}/cms/article/ajax/audit',
					type : 'post',
					data : {
						'ids' : ids,
						'auditFlag' : auditFlag
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
		
        function myTop(id, topFlag){
        	var ids;
			if(id){
				ids = new Array();
				ids.push(id);
			}else{
				ids = getSelectIds();
			}
			
			var msg = topFlag == 1 ? '置顶' : '取消置顶';
			
			if(!ids || ids.length == 0){
				layer.alert('请选择要'+msg+'记录');
				return;
			}
			
			layer.confirm('确定要' + msg + '记录吗？', function(index){
				layer.close(index);
				
				var loadIdx = layer.load();
				$.ajax({
					url : '${ctx}/cms/article/ajax/top',
					type : 'post',
					data : {
						'ids' : ids,
						'topFlag' : topFlag
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
		
		function getSelectIds(){
			var ids = new Array();
			$('input[name="selecteId"]:checked').each(function(idx, obj){
				ids.push($(obj).attr('value'));
			});
			return ids;
		}
		
		 //全选
	    function selectAllIds(){
	  	  var selAll = document.getElementById("selectAll");
	  	  var selectIdArr = $(":input[name='selecteId']");
	  	  if(selAll.checked){
	  		  if(selectIdArr.length > 0){
	  			  for(var i=0;i<selectIdArr.length;i++){
	  				  selectIdArr[i].checked = true;
	  			  }
	  		  }
	  	  }else{
	  		  if(selectIdArr.length > 0){
	  			  for(var i=0;i<selectIdArr.length;i++){
	  				  selectIdArr[i].checked = false;
	  			  }
	  		  }
	  	  }
	    }
		 
	    function myQuery(currentPage){
	    	$('input[name="currentPage"]').val(currentPage);
	    	$('#myForm').submit();
	    }
	    
	    //点击标题预览
		function view(id){
			
		}
	    
	    //编辑
		function myEdit(id){
			if(!id){
				id = '';
			}
	    	window.location.href='${ctx}/cms/article/edit?id='+id;
		}
				
		function doViewSort(){
			var viewClass = $('#viewSortTd').attr("class");
			var viewSort = '';
			if(viewClass == 'down'){
				$('#viewSortTd').removeClass('down');
				$('#viewSortTd').addClass('up');
				viewSort = "up";
			}else{
				$('#viewSortTd').removeClass('up');
				$('#viewSortTd').addClass('down');
				viewSort = "down";
			}
			$("#viewSort").val(viewSort);
			myQuery(1);
		}
		
		function doCreateDateSort(){
			var createDateClass = $('#createDateTd').attr("class");
			var isCreateDateSort = '';
			if(createDateClass == 'down'){
				$('#createDateTd').removeClass('down');
				$('#createDateTd').addClass('up');
				isCreateDateSort = "up";
			}else{
				$('#createDateTd').removeClass('up');
				$('#createDateTd').addClass('down');
				isCreateDateSort = "down";
			}
			$("#isCreateDateSort").val(isCreateDateSort);
			myQuery(1);
		}
		
		function exportData(){
			   var type = '${type}';
			   var title = $("#title").val();
			   var startDate = $("#startDate").val();
			   var endDate = $("#endDate").val();
			   var isReadSort = $("#isReadSort").val();
			   var viewSort = $("#viewSort").val();
			   var isCreateDateSort = $("#isCreateDateSort").val();
			   window.location.href = "${ctx}/tie/export?title="+title+"&type="+type+"&startDate="+startDate+"&endDate="+endDate+"&isReadSort="+isReadSort+"&viewSort="+viewSort+"&isCreateDateSort="+isCreateDateSort;
		}
</script>
	

</body>
</html>