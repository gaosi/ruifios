var timeout = false; //启动及关闭刷新页面数据

$.extend({ 
	
	//获取服务url地址
    context: (function() {
        var href = jQuery("link[type='text/css'][href]").attr("href");
        var ctx = location.pathname.substring(0, location.pathname.indexOf("/", 1));
        return href.substring(href.indexOf(ctx), href.indexOf("/css"));
    })(),

    isEmpty: function(obj) {
        if (typeof obj["length"] != "undefined") {
            return !obj["length"];
        }else if (jQuery.isPlainObject(obj)) {
            return jQuery.isEmptyObject(obj);
        } else if (jQuery.isArray(obj) || jQuery.type(obj) == "string") {
            return obj.length == 0;
        } else {
            return !!!obj;
        }
    },

    rgbToHex: function(rgb) {
        if (!rgb || rgb == "transparent")
            return rgb;
        var re = /^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/;
        var group = re.exec(rgb);
        return group ? "#" + parseInt(group[1]).toString(16) + parseInt(group[2]).toString(16) + parseInt(group[3]).toString(16) : rgb;
    },

	echarts:function(a,b){
		$.ajax({ 
		    type:"POST", 
			url:b, 
			async:false,
			contentType:"application/json",               
			success:function(data){ 
				return drawEchart(a, data); 
			},
			error: function (error) {
				timeout = true;
				console.log(error);
			}
		});
	},
	
	drawEchart: function(a,b){
		try{				
			var chart = echarts.init(document.getElementById(a));
			chart.setTheme(theme);
			if(typeof b === 'string')
				eval( "b = " + b );
			chart.setOption(b); 
			//chart.restore(); //echarts3 不支持
			chart.resize();
			//保存echarts对象，自适应屏幕大小
			echartsMap[a] = chart;
			
			return chart;
		}catch (e) {
			timeout = true;
			console.log(e);
		}
	}
});


jQuery.xhrPool = [];
jQuery.xhrPool.unload = false;
jQuery.xhrPool.abortAll = function() {
    jQuery(this).each(function(idx, jqXHR) {
        jqXHR.abort();
    });
    jQuery.xhrPool.length = 0;
}
jQuery(window).bind(jQuery.browser.msie ? "unload.ajaxabort" : "beforeunload.ajaxabort", function() {
    jQuery.xhrPool.abortAll();
    jQuery.xhrPool.unload = true;
});
jQuery.ajaxSetup({
    traditional: true
});
jQuery.ajaxPrefilter(function(options, originalOptions, xhr) {
    var orierror = options.error;
    var statusTable = {};
    statusTable[401] = function (xhr, status, error) {//not login
        window.location.reload();
    };
    statusTable[421] = statusTable[401];
    statusTable[403] = '没有操作权限';
    statusTable[404] = '访问的地址未找到';
    statusTable[420] = '节点连接超时，可能已经停机或者网络故障，请稍后再试';
    //statusTable[500] = '服务器内部错误';
    statusTable[0] = statusTable[12029] = function(xhr, status, error) {
        if (xhr.statusText != "abort" && !$.xhrPool.unload) {//过滤掉由于页面切换造成的ajax请求中断错误，依赖$.xhrPool.abortAll
            $.globalMarker.show(messages.Terminate);
        }
        window.console && console.log($.toJSON(xhr));
    };
    statusTable[12002] = statusTable[12030] = statusTable[12031] = statusTable[12152] = function(xhr, status, error) {
        //ref: http://msdn.microsoft.com/en-us/library/windows/desktop/aa383770%28v=vs.85%29.aspx
        //ref: http://stackoverflow.com/questions/872206/http-status-code-0-what-does-this-mean-in-ms-xmlhttp
        $.globalMarker.show(messages.NetUnstable);
        window.console && console.log($.toJSON(xhr));
    };
   options.error = function(xhr, status, error) {

       var isFetchConfs = false;
       if (this.data) {
           if (this.data instanceof FormData)
               isFetchConfs = this.data.get('validate-type') == 'fetch-validate-confs';
           else
               isFetchConfs = this.data.indexOf('validate-type=fetch-validate-confs') > -1;
       }

       //handle specified error status code
       if ( !isFetchConfs && xhr.status in statusTable) {
           var handler = statusTable[xhr.status];
           switch (typeof handler) {
               case 'string':
                   $.globalMarker.show($.mergeErrMsg(this, handler));
                   break;
               case 'function':
                   handler.call(this, xhr, status, error);
                   break;
           }
           xhr.globalError = false;
           return;
       }

       if (typeof orierror == 'array')
           for (var i = 0; i < orierror.length; i++) {
               orierror[i].call(this, xhr, status, error);
           }
       else if (typeof orierror == 'function')
           orierror.call(this, xhr, status, error);
   };

    var method = options.type || options.method || 'get';
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    if (method.toUpperCase() == 'POST' && token && header && ( !originalOptions['headers'] || !originalOptions.headers[header] ) ) {
        xhr.setRequestHeader(header, token);
    }
});
jQuery(document).ajaxSend(function(event, xhr, settings) {
    jQuery.xhrPool.push(xhr);
    if (typeof settings.clearError == "undefined" || settings.clearError === true)
        $.errorPlacer.clear(settings.errorPlacer);
});
jQuery(document).ajaxComplete(function(event, xhr, settings) {
    var index = jQuery.xhrPool.indexOf(xhr);
    if (index > -1)
        jQuery.xhrPool.splice(index, 1);
});
jQuery(document).ajaxError(function(event, xhr, settings, exception) {
    if (typeof xhr.globalError != "undefined" && !xhr.globalError)
        return;

    var mime = xhr.getResponseHeader("content-type");
    if (mime && mime.indexOf("text/html") > -1) {
        window.console && console.log(xhr.status);
        document.write(xhr.responseXML ? xhr.responseXML : xhr.responseText);
        return;
    }

    //handle readable exceptions
    var $form, validator, formerrs = {}, errors = [], method = 'show';
    if (settings.xhrFields && settings.xhrFields.ajaxform) {//$.ajaxSubmit error
        $form = $(settings.xhrFields.ajaxform);
    } else if (settings.type.toLowerCase() == 'post' && this.target && (this.target.tagname == 'form' || $(this.target).closest('form').length > 0)) {
        $form = $(this.target).closest('form');
    }

    if ($form)
        validator = $form.data('validator');

    var errmsgs;
    if (settings.xhrFields && typeof settings.xhrFields.errorCodes != 'undefined') {
        errmsgs = (settings.xhrFields.errorCodes);
    } else if (xhr.responseJSON && xhr.responseJSON.errors) {
        errmsgs = xhr.responseJSON.errors;
    } else if (xhr.responseText) {
        var e = $.parseJSON(xhr.responseText);
        if ($.isException(e) && typeof e.errors != 'undefined') {
            errmsgs = e.errors;
        } else if (typeof e == 'string' || typeof e == 'number')
            errmsgs = '错误代码:' + e;
    }

    if (errmsgs) {
        if (typeof errmsgs == 'string')
            errmsgs = [errmsgs];
        $.each(errmsgs, function() {
            switch (typeof this) {
                case 'string':
                    errors.push(this);
                    break;
                case 'object':
                    if (typeof this.resource != 'undefined' && !this.resource) {
                        if (typeof this.name == 'string' && $.trim(this.name).length > 0) {
                            var key = this.name + ( this.idx > 0 ? ':eq(' + this.idx + ')' : '');
                            var value = formerrs[key];
                            if (!value) {
                                value = [];
                                formerrs[key] = value;
                            }
                            value.push(this.key);
                        } else
                            errors.push(this.key);
                    }
                    break;
            }
        });
    }

    if (validator && !$.isEmptyObject(formerrs)) {
        validator.showErrors(formerrs);
    } else if (!$.isEmptyObject(formerrs)) {
        for (var key in formerrs)
            errors.push(formerrs[key]);
    }

    if (errors.length > 0) {
        $.errorPlacer[method](errors, settings.errorPlacer);
    }
    if (errors.length == 0 && $.isEmptyObject(formerrs)) {
        //common handle process, show error message/code without detail or with encrypted detail
        $.globalMarker.show('操作失败');
    }
}).ajaxSuccess(function(event, xhr, settings) {
    $.globalMarker.clear();

    var message = $.mergeSuccessMsg(settings);
    if (message)
        $.globalMarker.show(message);
});
