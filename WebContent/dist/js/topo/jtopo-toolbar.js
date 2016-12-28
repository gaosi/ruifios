// 页面工具栏
function showJTopoToobar(stage){
	var toobarDiv = $('<div class="jtopo_toolbar">').html(''
		+'<input type="radio" name="modeRadio" value="normal" checked id="r1"/>'
		+'<label for="r1"> 默认</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="select" id="r2"/><label for="r2"> 框选</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="drag" id="r3"/><label for="r3"> 平移</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="edit" id="r4"/><label for="r4"> 编辑</label>'
		+'&nbsp;&nbsp;<input type="button" id="centerButton" value="居中显示"/>'
		+'<input type="button" id="fullScreenButton" value="全屏显示"/>'
		+'<input type="button" id="zoomOutButton" value=" 放 大 " />'
		+'<input type="button" id="zoomInButton" value=" 缩 小 " />'
		+'&nbsp;&nbsp;<input type="checkbox" id="zoomCheckbox"/><label for="zoomCheckbox">鼠标缩放</label>'
		+'&nbsp;&nbsp;<input type="text" id="findText" value="" onkeydown="findButton.click()">'
		+'<input type="button" id="findButton" value=" 查 询 ">'
		+'<input type="button" id="exportButton" value="导出PNG">'
		+'<input type="button" id="saveButton" value="保存视图">'
		+'<input type="button" id="getButton" value="获取视图">'
		+'<input type="button" id="getNode" value="获取节点">'
		+'<input type="button" id="getLink" value="获取连线">');
		
	$('#content').prepend(toobarDiv);

	// 工具栏按钮处理
	$("input[name='modeRadio']").click(function(){			
		stage.mode = $("input[name='modeRadio']:checked").val();
	});
	$('#centerButton').click(function(){
		stage.centerAndZoom(); //缩放并居中显示
	});
	$('#zoomOutButton').click(function(){
		stage.zoomOut();
	});
	$('#zoomInButton').click(function(){
		stage.zoomIn();
	});
	$('#exportButton').click(function(){
		stage.saveImageInfo();
	});
	$('#zoomCheckbox').click(function(){
		if($('#zoomCheckbox').attr('checked')){
			stage.wheelZoom = 0.85; // 设置鼠标缩放比例
		}else{
			stage.wheelZoom = null; // 取消鼠标缩放比例
		}
	});
	$('#fullScreenButton').click(function(){
		runPrefixMethod(stage.canvas, "RequestFullScreen");
	});

	// 查询
	$('#findButton').click(function(){
		var text = $('#findText').val().trim();
		var nodes = stage.find('node[text="'+text+'"]');
		if(nodes.length > 0){
			var node = nodes[0];
			node.selected = true;
			var location = node.getCenterLocation();
			// 查询到的节点居中显示
			stage.setCenter(location.x, location.y);
			
			function nodeFlash(node, n){
				if(n == 0) {
					node.selected = false;
					return;
				};
				node.selected = !node.selected;
				setTimeout(function(){
					nodeFlash(node, n-1);
				}, 300);
			}
			
			// 闪烁几下
			nodeFlash(node, 6);
		}
	});
}

var runPrefixMethod = function(element, method) {
	var usablePrefixMethod = false;
	["webkit", "moz", "ms", "o", ""].forEach(function(prefix) {
		if (usablePrefixMethod) return;
		if (prefix === "") {
			// 无前缀，方法首字母小写
			method = method.slice(0,1).toLowerCase() + method.slice(1);
		}
		var typePrefixMethod = typeof element[prefix + method];
		if (typePrefixMethod + "" !== "undefined") {
			if (typePrefixMethod === "function") {
				usablePrefixMethod = element[prefix + method]();
			} else {
				usablePrefixMethod = element[prefix + method];
			}
		}
	});

	return usablePrefixMethod;
};

/**
 * 初始化右键菜单
 * eg: initContextMenu(stage); 
 **/
var initContextMenu = function(stage){
	stage.addEventListener("mouseup", function(event){
		
		if(event.button === 2){// 右键
			// 当前位置弹出菜单（div）
			$("#contextmenu").css({
				top: event.pageY,
				left: event.pageX
			}).show();
		}
		if(event.button === 0){// 左键
			// 关闭弹出菜单（div）
			$("#contextmenu").hide();
		}
	});
	stage.addEventListener("mouseout", function(event){
		// 关闭弹出菜单（div）
		$("#contextmenu").hide();
	});
};

/*
<ul id="contextmenu" style="display:none;">	
<li><a>顺时针旋转</a></li>
<li><a>逆时针旋转</a></li>	
<li><a>更改颜色</a></li>
<li><a>放大</a></li>
<li><a>缩小</a></li>	
<li><a>删除该节点</a></li>
</ul>*/
/* 右键菜单处理 */	
$("#contextmenu a").click(function(){
	var text = $(this).text();
	
	if(text == '删除该节点'){
		scene.remove(currentNode);
		currentNode = null;
	}if(text == '撤销上一次操作'){
		currentNode.restore();
	}else{
		currentNode.save();
	}
	
	if(text == '更改颜色'){
		currentNode.fillColor = JTopo.util.randomColor();
	}else if(text == '顺时针旋转'){
		currentNode.rotate += 0.5;
	}else if(text == '逆时针旋转'){
		currentNode.rotate -= 0.5;
	}else if(text == '放大'){
		currentNode.scaleX += 0.2;
		currentNode.scaleY += 0.2;
	}else if(text == '缩小'){
		currentNode.scaleX -= 0.2;
		currentNode.scaleY -= 0.2;
	}
	$("#contextmenu").hide();
});

/*========================================================*/	
/* Check Canvas Support
/*========================================================*/
var isCanvas = function(id){
	var elem = document.createElement('canvas');
	return !!(elem.getContext && elem.getContext('2d'));
};

/***
 * JTopo 画拓扑图
 * debugger;
 * canvas
 */
function drawJtop(data, jid)
{
	var map = {};
	var $this = $("#"+jid)
	var cwidth = $this.width();
	var cheight = $this.height();
	
	//判断当前节点是否为 canvas
	var canvas = document.getElementById(jid);
	if(!(canvas.getContext && canvas.getContext('2d')))
	{
		$this.empty();
		var elem = document.createElement("canvas");
		elem.width = cwidth;
		elem.height = cheight;	
		canvas.appendChild(elem);	
		canvas = elem;	
	}
	
	var center = [cwidth/2, cheight/2];
	var vertical = 0;// y坐标的启始高度
	var gap = 35;// 竖形图上两资源的间隔
	//canvas.width = cwidth;
    var stage = new JTopo.Stage(canvas); // 创建一个舞台对象
  	//showJTopoToobar(stage);//显示工具栏 
	var scene = new JTopo.Scene(stage); // 创建一个场景对象
	scene.background = data.background || '';//../img/bg.png
	
	var rootid = data.rootid;
	var fromTotal = data.from;
	var toTotal = data.to;
	//var rootNode =  new JTopo.CircleNode(rootid);
	//rootNode.radius = 10; // 半径
	//rootNode.font = '14px 微软雅黑'; // 字体
	//rootNode.setLocation(center[0], center[1]);// 圆形坐标位置
	var rootNode =  new JTopo.Node(rootid);
	rootNode.fontColor = "0,0,0";  // 字体颜色
	rootNode.setLocation(center[0], center[1]);// 竖形坐标位置
	//rootNode.setImage("../flash/node.png");
	rootNode.fillColor = "0,255,0";
	rootNode.setSize(16,16);
	scene.add(rootNode);
	map[rootid] = rootNode;
	
	var json = data.data;
	if(json.nodes){
		$(json.nodes).each(function(index, data){
			if(data.id != rootid){
				var node =  new JTopo.CircleNode(data.text);
				node.radius = 10; // 半径
				//node.font = '14px 微软雅黑'; // 字体
				node.fontColor = "0,0,0";  // 字体颜色
				node.setLocation(data.x || 0, data.y || 0);
				//node.setImage(data.image);
				//node.setImage("../flash/node.png");
				//node.layout = {type: 'circle', radius: 100};
				scene.add(node);
				map[data.id] = node;
			}
		});
	}
	var rnum = 0;
	var lnum = 0;
	if(json.links){
		$(json.links).each(function(index, data){
			var from = map[data.source];
			var to = map[data.target];
			if(!from || !to){
				return;
			}
			var link = new JTopo.Link(from, to);
			link.text = data.text;
			
			//link.font = '14px 微软雅黑'; // 字体
			link.fontColor = "0,0,0";  // 字体颜色
			link.textOffsetY = 20; // 文本偏移量（向下n个像素）
			link.arrowsRadius = 3; //箭头大小
			scene.add(link);
			
			// 
			// 
			var r = 200;
			if(data.source == rootid){// from node 是当前查询ip时 to node 应在右边
				/*圆形展示
				 * if(rnum==0&&toTotal%2==1){
					rnum++;
					to.setLocation( center[0]+r, center[1]);
				}else{
					var rx = center[0]+Math.round(r*Math.cos(Math.PI/(2*Math.round((toTotal+1)/2))*( Math.round(rnum/2)*(rnum%2==1?1:-1))) );
					var ry = center[1]-Math.round(r*Math.sin(Math.PI/(2*Math.round((toTotal+1)/2))*( Math.round(rnum/2)*(rnum%2==1?1:-1))) );
					rnum++;
					to.setLocation( rx, ry);
				}*/
				
				// 竖形展示 
				to.setLocation( center[0]+r, vertical+rnum*gap);
				rnum++;
				//to.setLocation(Math.round(Math.random()*(cwidth/2)+(cwidth/2)), Math.round(Math.random()*cheight));
			}else{
				//from.setLocation(Math.round(Math.random()*(cwidth/2)),Math.round(Math.random()*cheight));
				/*圆形展示
				 * if(lnum==0&&fromTotal%2==1){
					lnum++;
					from.setLocation( center[0]-r, center[1]);
				}else{
					var lx = center[0]-Math.round(r*Math.cos(Math.PI/(2*Math.round((fromTotal+1)/2))*( Math.round(lnum/2)*(lnum%2==1?1:-1)) ));
					var ly = center[1]-Math.round(r*Math.sin(Math.PI/(2*Math.round((fromTotal+1)/2))*( Math.round(lnum/2)*(lnum%2==1?1:-1)) ));
					lnum++;
					from.setLocation( lx, ly);
				}*/
				// 竖形展示 
				from.setLocation( center[0]-r, vertical+lnum*gap);
				lnum++;
			}
		});
	}
	
	//rootNode.layout = {type: 'circle', radius: 200};
	// 混合布局
	JTopo.layout.layoutNode(scene, rootNode, true);
	/*var cloudNode = map[data.rootid];
	if(cloudNode)
	{
		cloudNode.setLocation(cwidth/2, cheight/2);
		//cloudNode.layout = {type: 'circle', radius: 100};
		// 混合布局
		JTopo.layout.layoutNode(scene, cloudNode, true);
	}
	else
	{
		// 树形布局
	    //scene.doLayout(JTopo.layout.TreeLayout('down', 100, 200));
	}*/
}


