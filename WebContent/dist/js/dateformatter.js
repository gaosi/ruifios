/**
 * 进度条
 */
var progress = '<div class="progress progress-striped active">'+
	'<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">'+
	'<span class="sr-only">60% Complete</span></div></div>';
						

/**
 * 回溯
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
var t = 0, ttimeid, tuuid;
var tparam = {url:{set: "data/retrospectiveByCondition" ,status: "data/getRetrospectiveResultStatus" ,get: "data/getTrafficData"},data: {uuid: '', beginTime: 0, endTime: 0}, id: {tableid:'trafficTable', pageid: 'trafficPager', searchid: ''}, callback: function(t, u){$("#"+t).data("uuid", u);}};

function Recall(options)
{
	$.extend(tparam, options);
	if(tparam.data.uuid != undefined && tparam.data.uuid != '')
	{
		tuuid = tparam.data.uuid;
		getTableData(1);
	}
	else
	{
		tuuid = null;
		$.ajax({
			url: tparam.url.set,
			type: 'post',
			datatype: 'json',
			ansyc: false,
			data: tparam.data,
			success:function(data){
				tuuid = data.uuid;
				console.log('tuuid: '+tuuid);
				$('#'+tparam.id.pageid).attr('style', 'display:none');
				$('#'+tparam.id.tableid).bootstrapTable('load', new Array());
				$('#'+tparam.id.searchid).attr('disabled', 'disabled');//避免多次提交
				//加载查询进度条
				t = 0;
				$('#'+tparam.id.tableid+' .no-records-found td').html(progress);
				ttimeid = true;
				window.setTimeout("Recallrun()",500);
				tparam.callback(tparam.id.searchid, tuuid);
			},
			error:function(error){
				console.log(error);
			}
		});
	}
}

/**
 * 将查询Id保存到表格
 * @param tid
 * @param uuid
 */
function saveUuid(tid, uuid)
{
	$('#'+tid).val(uuid);
}

/**
 * 回调函数包含uuid 开始时间（begin_time_str) 结束时间（end_time_str)
 * @param options
 */
function TimeRecall(options)
{
	$.extend(tparam, options);
	if(tparam.data.uuid != undefined && tparam.data.uuid != '')
	{
		tuuid = tparam.data.uuid;
		getTableData(1);
	}
	else
	{
		$.ajax({
			url: tparam.url.set,
			type: 'post',
			datatype: 'json',
			ansyc: false,
			data: tparam.data,
			success:function(data){
				tuuid = data.uuid;
				$('#'+tparam.id.pageid).attr('style', 'display:none');
				$('#'+tparam.id.tableid).bootstrapTable('load', new Array());
				//加载查询进度条
				t = 0;
				$('#'+tparam.id.tableid+' .no-records-found td').html(progress);
				ttimeid = true;
				Recallrun();
				tparam.callback(tuuid, data.startTimerStr, data.endTimerStr);
			},
			error:function(error){
				console.log(error);
			}
		});
	}
}
	
/**
 * 刷新进度条
 */
function Recallrun()
{
	if(tuuid === null || !ttimeid)
	{
		$('#'+tparam.id.tableid).bootstrapTable('load', new Array());
		return;
	}	
	
	console.log('Recallrun tuuid: '+tuuid);
    t+=5;
    if(t < 100){
    	$('#'+tparam.id.tableid+' .progress-bar').css({'width':t+'%'}).find('span').html(t+"%");
    }
    
    $.ajax({
		url: tparam.url.status,
		type:'post',
		datatype:'json',
		ansyc:false,
		data:{uuid: tuuid},
		success:function(data){
			if(data){
				t=0;
				ttimeid = false;
				
				getTableData(1);
				$('#'+tparam.id.tableid+' .progress-bar').css({'width':'100%'}).find('span').html("100%");
				$('#'+tparam.id.searchid).removeAttr('disabled');
			}
			else
			{
				window.setTimeout("Recallrun()", 500);
			}
		},
		error:function(error){
			ttimeid = false;
		}
	});
}

/**
 * 加载表格数据
 * @param currentPage
 * 	页码
 */
function getTableData(currentPage)
{
	console.log('getTableData tuuid: '+tuuid);
	
	$.ajax({
		url: tparam.url.get,
		type:'post',
		datatype:'json',
		ansyc:false,
		data:{currentPage: currentPage, uuid: tuuid},
		success:function(data){
			$('#'+tparam.id.tableid).bootstrapTable('load', new Array()); //移除表格数据
			if(data != null && data.data != null && data.data.length > 0)
			{
				//刷新表格
				$('#'+tparam.id.tableid).bootstrapTable('load', data.data);
				$('#'+tparam.id.pageid).removeAttr('style');
				var currentPage = data.currentPage;
				var total = data.total;
				$('#'+tparam.id.pageid).pager({ pagenumber: currentPage, pagecount: total, buttonClickCallback: getTableData });
			}
			else
			{
				$('#'+tparam.id.pageid).attr('style', 'display:none'); 
			}
		},
		error:function(error){
			ttimeid = false;
			console.log(error);
		}
	});
}


//告警等级formatter
function levelFormatter(value, row, index){
	if(value==3){
		return '高';
	}else if(value==2){
		return '中';
	}else if(value==1){
		return '低';
	}else{
		return '';
	}
}

// 日志事件级别
function eventLevelFormatter(value, row, index){
	var level = '';
	if(value ==1) {
 	   level = '<span class="label label-info">低</span>';
    }
    else if(value ==2) {
 	   level = '<span class="label label-info" style="background-color:#C0FF3E">中低</span>';
    }
    else if(value ==3) {
 	   level = '<span class="label label-warning" style="background-color:#EEEE00">中</span>';
    }
    else if(value ==4) {
 	   level = '<span class="label label-danger" style="background-color:#FF7F00">中高</span>';
    }
    else if(value ==5) {
 	   level = '<span class="label label-danger" style="background-color:#FF0000">高</span>';
    }
    else {
 	   level = '<span class="label label-success">信息</span>';
    }
	
	return [level].join('');
};

// 描述
function describeFormatter(value, row, index){
	return commentFormat(value);
}

/**
 * 备注
 * @param value
 * @returns
 */
function commentFormat(value){
	if(value==null || typeof value == 'undefined') 
		value = "";
	else{
		value = transformSpecifiedChar(value);
	}
	return ['<div style="position:absolute;width:inherit;overflow:hidden; white-space:nowrap; text-overflow:ellipsis; text-align: left;" title="'+value+'">'+value+'</div>'].join('');
};

/*正则表达式 替换括号,尖括号等*/  
function transformSpecifiedChar(str) {  
    var RexStr = /\<|\>|\"|\'|\&/g  
    str = str.replace(RexStr, function(MatchStr) {  
        switch (MatchStr) {  
        case "<":  
            return "&lt;";  
            break;  
        case ">":  
            return "&gt;";  
            break;  
        case "\"":  
            return "&quot;";  
            break;  
        case "'":  
            return "&#39;";  
            break;  
        case "&":  
            return "&amp;";  
            break;  
        default:  
            break;  
        }  
    })  
    return str;  
}  

//开始时间formatter
function startTimeFormatter(value, row, index){
	if(value!=null || value!=''){
		var time = (new Date(value)).pattern("yyyy-MM-dd HH:mm:ss");
		return time;
	}else{
		return new Date().pattern("yyyy-MM-dd HH:mm:ss");;
	}
}

/** 
 * 对Date的扩展，将 Date 转化为指定格式的String 
 *  月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 *  可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 * eg: 
 * 	(new Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423      
 *  (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
 *  (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
 *  (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
 *  (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
 */        
Date.prototype.pattern=function(fmt) {  
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
};

/**
 * 获取当日零点时间戳
 */
Date.prototype.getWeeHours=function() {
	var date = this.pattern("yyyy-MM-dd");
	var time = new Date(date+" 00:00:00").getTime();
	return time;
};

var iframeResize = function(iframeId){
	var ifram = document.getElementById(iframeId);
	var sub = document.iframes ? document.frames[iframeId]:ifram.contentDocument;
	if(ifram != null && sub != null){
		//var height = sub.body.scrollHeight +"px";
		var height = '0px';
		try {
			height = sub.documentElement.scrollHeight  +"px";
		} catch (e) {
			height = '358px';
		}
		$("#"+iframeId).parent().attr("style", "padding-bottom:"+ height);
	}
};

/**
 * 自定义Map
 **/
var RMap = function(){
	this.container = new Object();
};

//RMap.prototype = new Object();

RMap.prototype.put = function(key, value){
	this.container[key] = value;
};

RMap.prototype.get = function(key){
	return this.container[key];
};


RMap.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for (var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		keyset[count] = key;
		count++;
	}
	return keyset;
};


RMap.prototype.size = function() {
	var count = 0;
	for (var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend'){
			continue;
		}
		count++;
	}
	return count;
};


RMap.prototype.remove = function(key) {
	delete this.container[key];
};

RMap.prototype.toString = function(){
	var str = "";
	for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
		str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
	}
	return str;
};

RMap.prototype.tojson = function(){
	var keys = this.keySet();
	var str = "{";
	for (var i = 0; i < keys.length; i++) {
		var key = keys[i];
		var value = this.get(key);
		if(value != '' && value != 'undefined')
			str += " '" + key + "': '" + value + "', ";
	}
	if(str.length>1)
	{
		str = str.substring(0, str.length-1);
	}
	str += "}";
	
	return  eval('(' + str + ')');
};

/**
 * 自定义查询条件
 */
$(document).ready(function () {
	// 单选
	$('.R_valueList>li').click(function(){
		$(this).parent().find('.selected').removeClass("selected");
		$(this).addClass("selected");
		return false;
	});
		
	// 输入查询条件,取消单选
	$('.input-txt').change(function(){
		$(this).parent().parent().find('.selected').removeClass("selected");
	});

	// 多选
	$(".R_extMultiple").click(function(){
		var $slwrap = $(this).parent().parent();
		$slwrap.toggleClass('multiple');
		
		if($slwrap.find(".C_valueList").length>0)
		{
			if ($slwrap.hasClass('multiple')) {//多选
				$slwrap.find(".C_valueList").attr("style", "display:none;"); 
				$slwrap.find(".M_valueList").attr("style", "display:block;");
			}else{
				$slwrap.find(".C_valueList").attr("style", "display: block;"); 
				$slwrap.find(".M_valueList").attr("style", "display: none;");
			}
		}
	});
	
	/**
	 * 选中协议后显示确认按钮
	 * 
	 * if($(this).parent().find('.selected').length>0){
	 * 	$(this).parent().parent().parent().find('.R_btnsConfirm').removeClass('disabled');
	 * }
	 * else{
	 * 	$(this).parent().parent().parent().find('.R_btnsConfirm').addClass('disabled');
	 * }
	 **/ 
	$('.M_valueList>li').click(function() {//debugger;
		$(this).toggleClass("selected");
		var $p = $(this).parent().parent();
		var d = $(this).attr("value");
		var s = $(this).hasClass('selected');
		$p.find(".C_valueList>li").each(function(i, item) {
			var $item = $(item);
			var l = $item.attr("value");
			if (d == l) { 
				if(s)
				{
					$item.addClass('selected');  
				}
				else{
					$item.removeClass('selected');  
				}
				return false;
			}
		});
		
		return false;
	});
	
	// 多选协议
	$('.C_valueList>li').click(function() {//debugger;
		$(this).toggleClass("selected");
		var $p = $(this).parent().parent();
		var d = $(this).attr("value");
		var s = $(this).hasClass('selected');
		$p.find(".M_valueList>li").each(function(i, item) {
			var $item = $(item);
			var l = $item.attr("value");
			if (d == l) { 
				if(s)
				{
					$item.addClass('selected');  
				}
				else{
					$item.removeClass('selected');  
				}
				return false;
			}
		});
		
		return false;
	});

	// 移除所有类型，突出显示当前选择类型
	$('.R_brandLetter>li').mouseover(function()	
	{
		$('.R_brandLetter>li').removeClass('curr');
		$(this).addClass('curr');
		var $p = $(this).parent().parent();
		var d = $(this).attr('data-initial'); 
		if(d != 0){
			$p.find(".M_valueList>li").each(function(i, item) {
				var $item = $(item);
				var l = $item.attr('data-initial');
				if (d == l) { 
					$item.attr("style", "display:list-item;");     
				}else{
					$item.attr("style", "display:none;");  
				}
			});
		}else{
			$p.find(".M_valueList>li").attr("style", "display:list-item;");  
		}
	});
	
	//点击查询按钮
	$(".search_selector").click(function() {
		var map = new RMap();
		
		var id = $(this).val();
		
		$('.'+id).find(".sl-v-list").each(function(i, item) {
			var $item = $(item);
			var name = $item.attr('id');
			var arr = new Array();  //arr.push
			
			if($item.find('.selected').length>0){
				$item.find('.selected').each(function(index, data) {
					var value = $.trim($(data).attr('value'));
					arr.push(value);
				});
			}else{
				$item.find('.input-txt').each(function(index, data) {
					if($(data).val() != '')
					{
						var value = $.trim($(data).val());  /* //.replace('/', '-'); /\\/g */
						arr.push(value);
					}
				});
			}
			if(arr.length>0)
				map.put(name, arr);
		});
		
		if(map.size()>0){
			var data = map.tojson();
			getData(id, data);
		}else{
			alert("请选择查询条件！");
		}
	});

	// 全部撤消
	$(".fr").click(function() {
		$('.input-txt').val('');
		$('.multiple').removeClass("multiple");
		$('.selected').removeClass("selected");
	});
	
});

$.extend({ getSelector:function(a){
	var rmap = new RMap();
	$(a).find(".sl-v-list").each(function(i, item) {
		var $item = $(item);
		var name = $item.attr('id');
		if(name != 'undefined')
		{
			var arr = new Array();  //arr.push
			
			if($item.find('.selected').length>0){
				$item.find('.selected').each(function(index, data) {
					var value = $.trim($(data).attr('value'));
					arr.push(value);
				});
			}else{
				$item.find('.input-txt').each(function(index, data) {
					var value = $.trim($(data).val());
					if(value != '')
					{ 
						arr.push(value);
					}
				});
			}
			if(arr != 'undefined' && arr.length>0)
				rmap.put(name, arr);
		}
	});
	
	return rmap.tojson();
}
});

