
var PAGE_SIZE = 10;


/**
 * 搜集数据
 * @param form
 * @returns {Object}
 */
function getFormData(form){
	var data = new Object();
	var names = new Array();
	var s = '';
	$.each($(form).find('input,select'), function(idx, obj){
		var n = $(obj).attr('name');
		if(n && s.indexOf(n) == -1){
			names.push(n);
			s += n + ',';
		}
	});
	
	//取值
	$.each(names, function(idx, n){
		var els = $(form).find('input[name="'+n+'"],select[name="'+n+'"]');
		if(els.length == 1){
			var type = $(els[0]).attr('type');
			if(type == 'checkbox' || type == 'radio'){
				if($(els[0]).attr('checked')){
					data[n] = $(els[0]).val(); 
				}
			}else{
				data[n] = $(els[0]).val();
			}
		}else if(els.length > 1){
			data[n] = new Array();
			
			$.each(els, function(idx, el){
				var type = $(el).attr('type');
				if(type && (type == 'checkbox' || type == 'radio')){
					if($(el).attr('checked')){
						data[n].push($(el).val());
					}
				}else{
					var v = $(el).val();
					if(v){
						data[n].push(v);
					}
				}
			});
			
		}
	});
	
	return data;
}


/**
 * 格式化日期
 * @param d 值 有可能是Date类型
 * @param f	格式 yy-mm-dd hh:ii:ss
 * @returns
 */
function parseDate(d, f){
	if(!d){
		return '';
	}
	if(!f){
		f = 'yy-mm-dd';
	}
	if(f instanceof Date){
		return $.formatDateTime(f, d);
	}else if(typeof(d) == 'string'){
		if(d.indexOf('-') > -1){
			d = d.replace(/-/g,   "/");
		}
		return $.formatDateTime(f, new Date(d));
	}else if(typeof(d) == "number"){
		var date = new Date();
		date.setTime(d);
		return $.formatDateTime(f, new Date(date));
	}
}

function parseDateTime(d){
	return parseDate(d, 'yy-mm-dd hh:ii:ss');
}

function parseDateToMinus(d){
	return parseDate(d, 'yy-mm-dd hh:ii');
}

function parseDateTohi(d){
	return parseDate(d, 'hh:ii');
}

/**
 * 
 * @param val
 * @returns
 */
function proVal(val){
	if(val == 0){
		return val;
	}
	if(!val){
		return '';
	}
	if(val == 'null'){
		return '';
	}
	if(val == undefined){
		return '';
	}
	if(val == 'undefined'){
		return '';
	}
	return val;
}

//判断正整数 
function checkRate($input) {
	
	if(!$input.val()){
		return true;
	}
	
	var re = /^[1-9]+[0-9]*]*$/; 
	
	if (!re.test($input.val())){ 
		$input.focus(); 
		return false; 
	}
	
	return true;
} 

function checkAllClick(chk, name){
	$('input[name="'+name+'"]:checkbox').attr('checked', chk.checked);
	$('input[name="'+$(chk).attr('name')+'"]').attr('checked', chk.checked);
}

/**
 *  下载文件
 */
function download(url, data, method){
	if(url){
		var inputs = '';
		
		if(data){
			for(var p in data){
				var val = data[p];
				if(val instanceof Array){
					$.each(val , function(idx, v){
						inputs +=  '<input type="hidden" name="' + p + '" value="' + v + '" />';
					});
				}else{
					inputs +=  '<input type="hidden" name="' + p + '" value="' + val + '" />';
				}
			}
		}
		
		$('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>')
			.appendTo('body').submit().remove();
	}
}

function defaultAreaGD($area){
	var areaId;
	$area.find('option').each(function(idx, obj){
		var txt = $(obj).text();
		if(txt && txt.indexOf('广东') > -1){
			areaId = $(obj).val();
			return true;
		}
	});
	
	if(areaId){
		$area.val(areaId).change();
	}
}

function changeProvince(_this, cityName, allValue, formId){
	var provinceAreaId = $(_this).val();
	var $city;
	if(formId){
		$city = $('#'+formId+' select[name="'+cityName+'"').html('');
	}else{
		$city = $('select[name="'+cityName+'"').html('');
	}
	if(allValue){
		$city.append('<option value="">'+allValue+'</option>');
	}
	if(provinceAreaId){
		$.ajax({
			url : _ctx + '/common/ajax/get_city',
			data : {
				provinceAreaId : provinceAreaId
			},
			type : 'post',
			async : false,
			success : function(result){
				if(result.success){
					$.each(result.data, function(idx, obj){
						$city.append('<option value="'+obj.id+'">'+obj.name+'</option>');
					});
					$city.change();
				}else{
					layer.msg('查询城市失败');
				}
			}
		});
	}else{
		$city.change();
	}
	
}


function setLocationCurrentPage(currentPage){
	var data = new Object();
	data['currentPage'] = currentPage;
	setLocationHash(data);
}

function getLocationCurrentPage(){
	var data = getLocationHashData();
	return data['currentPage'];
}

/**
 * 获取浏览器上的hash
 */
function getLocationHashData(){
	var hash = $.trim(window.location.hash);
	var data = new Object();
	if(hash){
		var p = hash.substring(1);
		if(p){
			var params = p.split('&');
			for(var i in params){
				var param = $.trim(params[i]);
				var idx = params[i].indexOf('=');
				if(idx > 0){
					var key = $.trim(param.substring(0, idx));
					var value = $.trim(param.substring(idx+1));
					if(value){
						if(data[key]){
							if(data[key] instanceof Array){
								data[key].push(value);
							}else{
								var arr = new Array();
								arr.push(data[key]);
								arr.push(value);
								data[key] = arr;
							}
						}else{
							data[key] = value;
						}
					}
				}
			}
		}
	}
	
	return data;
}

function setLocationHash(data){
	var hash = '';
	if(data){
		var arr = new Array();
		for(var i in data){
			if(data[i] instanceof Array){
				$.each(data[i], function(idx, val){
					if(val){
						arr.push(i + '=' + $.trim(v));
					}
				});
			}else{
				if(data[i]){
					arr.push(i + '=' + $.trim(data[i]));
				}
			}
		}
		hash = arr.join('&');
	}
	if(hash){
		hash = '#' + hash;
	}
	window.location.hash = hash;
}

/*-------------------------------------------*/
function defaultImg(img){
	$(img).attr('src', _ctx + '/static/images/J_null.png');
}

function showNoData($div){
	var $noData = $div.find('#noDataDiv');
	if($noData && $noData.length > 0){
		$noData.show();
	}else{
		var html = '<div class="J_null mt40" id="noDataDiv">';
			html += '<img src="'+_ctx+'/static/images/null.png">';
			html += '<p>暂无相关数据</p>';
			html += '</div>';
		$div.append(html);
	}
}

function hideNoData($div){
	$('#noDataDiv').hide();
}

