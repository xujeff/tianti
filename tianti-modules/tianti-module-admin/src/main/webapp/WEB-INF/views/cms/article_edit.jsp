<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加/编辑文章 - ${title }</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/uploadify/uploadify.css"> 
</head>
<body>

<body <%@ include file="../common/skin.jsp" %>>
	<%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>
  <div class="J_content">
	<div class="mt20 plr20">
	     <div class="J_filter">
	             <table class="not_hightlight" style="width: 100%;">
	                 <tbody>
	                     <tr>
	                         <td class="t_right">
	                         	<div class="fl">
	                         	    <c:choose>
	                         	      <c:when test="${article != null }">
	                         	         <a class="current" href="javascript:;">编辑文章</a>
	                         	      </c:when>
	                         	      <c:otherwise>
	                         	         <a class="current" href="javascript:;">添加文章</a>
	                         	      </c:otherwise>
	                         	    </c:choose>
	                         	</div>
	               				
	               				<div class="fr">
	                           		<a href="javascript:myBack()">
				                    	<i class="icon"></i><span>返回上一页</span>
				                    </a>
	                           	</div>
	                         </td>
	                     </tr>
	                 </tbody>
	             </table>
	      </div>
	    <div class="J_formTable l_form_table mgt40">
		<form action="${ctx}/cms/article/ajax/save" id="editForm" method="post">
		<input type="hidden" name="id" value="${article.id }"/>
		<input type="hidden" name="coverImageUrl" id="coverImageUrl" value="${article.coverImageUrl }"/>
		<table>
		    <tr>
				<td class="l_title w200">一级栏目：<font color="red">*</font></td>
				<td>
					<div class="J_toolsBar fl">
					  <div class="t_opacitySelect ml10">
					   <span>请选择</span>
					   <select id="rootColumnId" name="rootColumnId" onChange = "selectLeafColumn()" style="min-width:200px">
					        <option value="">请选择</option>
		                   <c:forEach items="${rootCoulumnInfoList }" var="c">
		                	<option value="${c.id }"
		                	   <c:if test="${c.id == rootColumnId }">selected="selected"</c:if>
		                	>${c.name }</option>
		                   </c:forEach>
		                </select>
		              </div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="l_title w200">二级栏目：<font color="red">*</font></td>
				<td>
					<div class="J_toolsBar fl">
					  <div class="t_opacitySelect ml10">
					   <span>请选择</span>
					   <select id="leafColumnId" name="leafColumnId" style="min-width:200px">
		                   
		                </select>
		              </div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="l_title w200">文章类型：</td>
				<td>
					<div class="J_toolsBar fl">
					  <div class="t_check ml10">
					     <label><input name="articleType" type="radio" value="contentType" <c:if test="${article.type == null || article.type == 0 }">checked="checked"</c:if> />内容文章</label> 
						 <label><input name="articleType" type="radio" value="hrefType" <c:if test="${article.type == 1 }">checked="checked"</c:if>/>外链文章</label> 
						 <label><input name="articleType" type="radio" value="adType" <c:if test="${article.type == 2 }">checked="checked"</c:if>/>广告位</label> 
		              </div>
					</div>
				</td>
			</tr>
		    <tr>
				<td class="l_title w200">标题：<font color="red">*</font></td>
				<td>
					<div class="J_toolsBar fl">
					    <div class="t_text w600 ml10">
							<label> 
								<input type="text" name="title" value="${article.title }" >
							</label>
						</div>
					</div>
				</td>
			</tr>
									
			<tr id="contentTr">
			    <td class="l_title w200">内容：</td>
				<td>
					<div class="J_toolsBar">
							<label> 
							    <script type="text/plain" id="content" name="content">${article.content}</script>
							</label>
					</div>
				</td>
			</tr>
			
			<tr id="hrefTr" style="display:none">
				<td class="l_title w200">外链：</td>
				<td>
					<div class="J_toolsBar fl">
					    <div class="t_text w600 ml10">
							<label> 
								<input type="text" name="href" value="${article.href }" >
							</label>
						</div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l_title w200">图片：<a href="${ctx }/cms/article/flashView" target="_blank"><font color="red">无法上传？</font></a></td>
				<td>
					<div class="J_toolsBar fl">
					    <div class=" w200 ml10">
							<label> 
								<input type="file" id="attachImage" /> 
							</label>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="l_title"></td>
				<td>
					<div class="J_toolsBar fl">
						<div class="t_img w100 ml10">
						    <c:choose>
						        <c:when test="${article.coverImageUrl != null && article.coverImageUrl != ''}">
						            <img src="${ctx }${article.coverImageUrl}" id="attachURL" width="100px" height="100px" />
						        </c:when>
						        <c:otherwise>
						            <img src="${ctx }/static/images/J_null.png" id="attachURL" width="100px" height="100px" />
						        </c:otherwise>
						    </c:choose>
							
						</div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l_title w200">发布人：</td>
				<td>
					<div class="J_toolsBar fl">
					    <div class="t_text w200 ml10">
							<label> 
								<input type="text" name="publisher" value="${article.publisher }">
							</label>
						</div>
					</div>
				</td>
			</tr>
			
			<tr id="orderNoTr" style="display:none">
				<td class="l_title w200">排序：</td>
				<td>
					<div class="J_toolsBar fl">
					    <div class="t_text w200 ml10">
							<label> 
								<input type="text" name="orderNo" value="${article.orderNo }" >
							</label>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
							<div class="J_toolsBar mgt40">
								<div class="t_buttonGroup">
				                   	<a class="abtn red" href="javascript:mySubmit();">
				                   		<i class="icon"></i>保存
				                   	</a>
				                   	<a class="abtn gray" href="javascript:myBack();">
				                   		<i class="icon"></i>返回
				                   	</a>
				                </div>
							</div>
				</td>
			</tr>
		</table>
		
		</form>
	</div>
  </div>
 </div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/editor_config.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/editor_all.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript">
		//表单验证
		$(function(){
			$('#editForm').validator({
				fields : {
					rootColumnId:'一级栏目:required',
					leafColumnId:'二级栏目:required',
					title : '标题:required'
				},
				valid : function(form){
					$('#editForm').ajaxSubmit({
						success : function(result){
							if(result.success){
								layer.alert('保存成功');
								window.location.href="${ctx}/cms/article/list"
							}
						}
					});
				}
			});	
		});
            
       function myBack(){
    	   window.location.href="${ctx}/cms/article/list";
       }
       
       function mySubmit(){
   		   $('#editForm').submit();
   	   }

       
       function selectLeafColumn(){
    	   var articleId = '${article.id}';
    	   var columnId = '${columnId}'


    	   var rootColumnInfoId = $("#rootColumnId").val();
    	   $.ajax({
				url : '${ctx}/cms/column/ajax/getLeafColumn',
				type : 'get',
				data : {
					'rootColumnInfoId' : rootColumnInfoId
				},
				traditional : true,
				success : function(obj){
					var resultMsg = '<option value="">请选择</option>';
					$("#leafColumnId").html("");

					$("#leafColumnId").append(resultMsg);
					var data = obj.data;
					var dataLength = data.length;
					//加载数据
					for(var i = 0;i<dataLength;i++){
					    var option = $("<option></option>");
					    option.text(data[i].name);
					    option.val(data[i].id);
					    if(data[i].id == columnId){
					        option.attr("selected",true);
						}
                        $("#leafColumnId").append(option);
					}
                    $("#leafColumnId").trigger("change")

				}
			});
       }
    
	   UE.getEditor('content',{
		    initialFrameWidth : 700,
		    initialFrameHeight: 350
	   });
	   
	   $(function(){

			$("#rootColumnId").trigger("change")
			var articleType = '${article.type}';
			if(articleType == 1){
				$('#contentTr').hide();
				$("#orderNoTr").hide();
				$("#hrefTr").show();
			}else if(articleType == 2){
				$('#contentTr').hide();
				$("#orderNoTr").show();
				$("#hrefTr").show();
			}
		})
		
	   $("#attachImage").uploadify(
		{
			'swf' : '${pageContext.request.contextPath}/static/js/uploadify/uploadify.swf',
			'uploader' : '${pageContext.request.contextPath}/upload/uploadAttach',
			'cancelImg' : '${pageContext.request.contextPath}/static/js/uploadify/uploadify-cancel.png',
			'queueID' : 'fileQueue',
			'auto' : true,
			'multi' : false,
			'simUploadLimit' : 1,
			'buttonText' : '上传图片',
			'fileObjName' : 'fileData',
			'width' : 70,
			'height' : 20,
			'uploadLimit':3,
			'onUploadSuccess' : function(file, data, response) {
					if(data != null){
						var attachUrl = '${pageContext.request.contextPath}' + data;								
						$("#attachURL").attr('src',attachUrl); 
						$("#coverImageUrl").val(data);
					}
		 }
	   });
	   
	   $('input[name="articleType"]').on('click', function(){
			var val = $('input[name="articleType"]:checked').val();
			if(val == 'contentType'){
				$('#contentTr').show();
				$("#hrefTr").hide();
				$("#orderNoTr").hide();
			}else if(val == 'hrefType'){
				$('#contentTr').hide();
				$("#orderNoTr").hide();
				$("#hrefTr").show();
			}else if(val == 'adType'){
				$('#contentTr').hide();
				$("#orderNoTr").show();
				$("#hrefTr").show();
			}
	   });
   </script>

</body>
</html>