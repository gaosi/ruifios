function _configAddElementsData(type, data) {

	if (type == "node") {

		//alert("爱你一万年---节点"+data.x+"----"+data.y+"---"+data.topoType);

		//切记: 请在这里一定要添加节点,不然无效
		var node = {
			weight : 0.10,
			shape : "PARALLELOGRAM",
			label : "A05",
			topoType : data.topoType
		};
		var node1 = vis.addNode(data.x, data.y, node, true);

	} else if (type == "edge") {

		//alert("爱你一万年---连线"+data.id+"--"+data.source+"--"+data.target);

		//切记: 请在这里一定要添加连线,不然无效

		var edge = {
			id : data.id,
			source : data.source,
			target : data.target,
			directed : false,
			network : 15,
			width : 2,
			label : "a08 (15) a08",
			lineStyle : "SOLID"
		};

		var edge = vis.addEdge(edge, true);

	}
	return null; //无需更改
}

/**
 * 配置控制台参数---注意:本方法名不可以修改
 *　本函数独立区分于ctyoscapeweb的配置方式,降低耦合,本方法由控制台(自定义组件)直接调用.
 */
function _initConsoleBoxParam() {
	//参数对象----注意:所有字段不可以修改,不支持自定义字段
	var setParameters = {
		//控制跟随的背景图片,有缺陷,节点需要x,y偏移才能使用.
		backgroundFollowImg : {
			//xOffset:-20,		//程序定位大概的坐标后,在通过此变量精确调整x坐标
			//yOffset:15,		//程序定位大概的坐标后,在通过此变量精确调整y坐标
			isShow : true, //只有配置了才显示,默认为false,
			timer : 1000//, //用于背景图片的延时加载,单位毫秒,默认1000毫秒(1秒),
			//followNode:"A01",	//Management这里用节点的Label或者Id来进行控制,(这个节点选用左上角节点作为背景图的对应点)
			//url : ".././flash/top_bg.jpg"
		}
	};
	return setParameters;
}

var vvis;

function saveAsPngImage () {
	var strData = 'data:image/octet-stream;base64,' + vvis.png();;
	document.location.href = strData;
}

function saveAsPdfImage (potions) {
	var data = vvis.pdf(potions);
	var strData = 'data:application/pdf;base64,' + data;
	window.open(strData, "_blank");
}

function saveTopo()
{
//	alert(vvis);
//	var element = vvis.networkModel();
//
//	var size = element.data.nodes.length;
//	var contents = "[";
//	var need = false;
//	for ( var i = 0; i < size; i++) {
//
//		var node = element.data.nodes[i];
//		var id = node.id;
//
//		var n = vvis.node(id);
//
//		contents += "{";
//		contents += ("\"id\":\"" + node.id + "\",");
//		contents += ("\"name\":\"" + node.label + "\",");
//		contents += ("\"ip\":\"" + node.ip + "\",");
//		contents += ("\"type\":\"" + node.type + "\",");
//		contents += ("\"x\":\"" + Math.floor(n.x) + "\",");
//		contents += ("\"y\":\"" + Math.floor(n.y) + "\",");
//		contents += ("\"imgtype\":\"" + node.imgtype + "\"");
//
//		contents += "}";
//		contents += ",";
//
//		need = true;
//	}
//
//	if (need) {
//		contents = contents.substring(0, contents.length - 1);
//	}
//
//	contents += "]";
//	
//	alert(contents);
	
}



function drawTopoAutoLayout(json)
{
    var autoLayout = true;

	var vis; //拓扑图对象
	var skinBackgroundColor = "#ffffff";//"#252525"; //动态修改拓扑图样式示例
	var skinCompoundColor = "#252525";
	var skinEdgeColor = "#c1c1c1";
	var skinNodeGlowColor = "#666666";
	var skinLabelColor = "#000000";
	var line_mode = false; //连线模式
	var line_mode_obj = { //连线配置对象
		weight : 1,
		arrow : "",
		color : ""
	};

	var visual_style = {
		global : {
			backgroundColor : skinBackgroundColor, //背景颜色,默认#ffffff白色,不知道为什么不好使????????
			tooltipDelay : 400, //光标在node和dege的tooltip的反应延时,默认800
			selectionFillColor : "#8888ff", //选区矩形的颜色,默认#8888ff
			selectionLineColor : "#8888ff", //选区矩形边框的颜色,默认#8888ff
			selectionFillOpacity : 0.1, //选区矩形的透明度,默认0.1
			selectionLineOpacity : 0.8, //选区矩形边框的透明度,默认0.8
			selectionLineWidth : 1
		//选区矩形边框的宽度,默认1
		},
		nodes : {
			shape : {
				passthroughMapper : {
					attrName : "shape"
				}
			},//"ELLIPSE",	//节点形状,椭圆ELLIPSE/矩形RECTANGLE/三角形TRIANGLE/钻石DIAMOND/六角HEXAGON/八角OCTAGON/平行四边形PARALLELOGRAM/绘制的ROUNDRECT/v字形VEE
			size : {
				passthroughMapper : {
					attrName : "weight"
				}
			}, //节点大小,可以自动设置,默认24
			//color:{ passthroughMapper: { attrName: "color" } },  //节点颜色,默认#f5f5f5
			opacity : 1, //节点的透明度,默认0.8
			//width://节点的宽度,以像素为单位
			//height://节点的高度,以像素为单位
			//image:"${base}/images/aaaa.gif", //图像的URL作为节点的背景
			image : {
				passthroughMapper : {
					attrName : "imgtype"
				}
			},//{ discreteMapper:{attrName: "imgtype",entries: nodeImages } },
			borderColor : {
				passthroughMapper : {
					attrName : "color"
				}
			},//节点边框颜色,默认值#666666
			borderWidth : 1,//节点边框的宽度,默认值1
			selectionColor : {
				passthroughMapper : {
					attrName : "color"
				}
			},//"#f5f5f5",//节点选中的颜色,默认等于color
			selectionBorderColor : skinNodeGlowColor,//节点选中的边框颜色,默认等于bordercolor 666666
			selectionOpacity : 1, //节点选中的透明度,默认等于opacity
			selectionBorderWidth : 0, //节点选中的边框宽度,默认等于borderWidth
			selectionGlowColor : skinNodeGlowColor,//"#ffff33",//节点选中的发光颜色,默认#ffff33
			selectionGlowOpacity : 0.9, //节点选中的发光透明度,默认0.6
			selectionGlowBlur : 8, //节点选中的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			selectionGlowStrength : 3, //节点选中的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			hoverOpacity : 1, //节点悬停的透明度,默认等于opacity
			hoverBorderColor : skinNodeGlowColor,//节点悬停的边框颜色,默认等于bordercolor 666666
			hoverBorderWidth : 0,//节点悬停的边框宽度,默认等于borderWidth
			hoverGlowColor : skinNodeGlowColor,//{ passthroughMapper: { attrName: "color" } },//"#aae6ff",//节点悬停的发光颜色,默认“#aae6ff”
			hoverGlowOpacity : 0.9, //节点悬停的透明度,默认0
			hoverGlowBlur : 8, //节点悬停的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			hoverGlowStrength : 3,//节点悬停的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			label : {
				passthroughMapper : {
					attrName : "label"
				}
			},//节点显示文字,可以映射节点数据的字段,自动默认映射label字段
			labelfontname : "Arial",//节点显示标签的字体,默认Arial
			labelfontsize : 12,//节点显示标签的字体大小,默认11
			labelFontColor : skinLabelColor,//节点显示标签的字体颜色,默认#000000黑色
			labelFontWeight : "normal",//节点显示标签的字体粗细,默认normal,支持bold(加粗)
			labelFontStyle : "normal",//节点显示标签的字体样式,默认normal,支持italic(斜体)
			labelHorizontalAnchor : "center",//节点显示标签的水平显示位置,默认center,支持left,right
			labelVerticalAnchor : "bottom", //节点显示标签的垂直的显示位置,默认middle,支持top,bottom
			labelXOffset : 0, //节点显示标签的X轴的偏移量,类似于padding,配合labelHorizontalAnchor使用,可以用负整数
			labelYOffset : 0, //节点显示标签的Y轴的偏移量,类似于padding,配合labelVerticalAnchor使用,可以用负整数
			labelGlowColor : "#ffffff", //节点显示标签的发光颜色,默认#ffffff
			labelGlowOpacity : 0, //节点显示标签的发光透明度,默认0
			labelGlowBlur : 8, //节点显示标签的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			labelGlowStrength : 20,//节点显示标签的发光对比度,有效值0-255,默认值20,对滤镜和渐变理解不深的不建议修改
			tooltipText : {
				passthroughMapper : {
					attrName : "tooltip"
				}
			},//"<b>${label}</b>: ${weight}",//节点的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			tooltipFont : "Arial", //节点提示文字的字体,默认Arial
			tooltipFontSize : 11,//节点提示文字的字体大小,默认11
			tooltipFontColor : "#ffffff",//线的提示文字的文字颜色,默认#000000
			tooltipBackgroundColor : "#505050",//线的提示文字的背景颜色,默认#f5f5cc
			tooltipBorderColor : "#505050",//线的提示文字的边框颜色,默认#000000
			//
			//节点组(复合节点)的样式,参考上面节点的样式,是一样的,只不过字段前面加了compound而已
			//
			compoundShape : "RECTANGLE",//节点组形状,只接受椭圆ELLIPSE/矩形RECTANGLE/绘制的ROUNDRECT
			//compoundSize:"auto",//auto(默认),ignoreLabels,//节点组的尺寸,一般自动,不需要设置.
			compoundColor : skinCompoundColor,//"#252525", //节点组颜色,默认#f5f5f5
			compoundOpacity : 0.8, //节点组的透明度,默认0.8
			//compoundWidth:400,//节点组的宽度,以像素为单位,不支持
			//compoundHeight:150,//节点组的高度,以像素为单位,不支持
			//compoundImage://图像的URL作为节点组的背景
			compoundBorderColor : {
				passthroughMapper : {
					attrName : "color"
				}
			},//"#20b6e4",//节点组边框颜色,默认值#666666
			compoundBorderWidth : 1,//节点组边框的宽度,默认值1
			compoundSelectionBorderColor : "#20b6e4",//节点组选中的边框颜色,默认等于bordercolor
			compoundSelectionBorderWidth : 0, //节点组选中的边框宽度,默认等于borderWidth
			compoundSelectionColor : {
				passthroughMapper : {
					attrName : "compoundColor"
				}
			}, //"#f5f5f5",//节点组选中的颜色,默认等于color
			compoundSelectionOpacity : 0.8, //节点组选中的透明度,默认等于opacity
			compoundSelectionGlowColor : {
				passthroughMapper : {
					attrName : "compoundColor"
				}
			}, //"#ffff33",	//节点组选中的发光颜色,默认#ffff33
			compoundSelectionGlowOpacity : 0.5, //节点组选中的发光透明度,默认0.6
			compoundSelectionGlowBlur : 8, //节点组选中的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			compoundSelectionGlowStrength : 3, //节点组选中的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			compoundHoverBorderColor : "#20b6e4",//节点组悬停的边框颜色,默认等于bordercolor
			compoundHoverBorderWidth : 0,//节点组悬停的边框宽度,默认等于borderWidth
			compoundHoverColor : "#20b6e4",
			compoundHoverOpacity : 0.8, //节点组悬停的透明度,默认等于opacity
			compoundHoverGlowColor : {
				passthroughMapper : {
					attrName : "compoundColor"
				}
			}, //节点组悬停的发光颜色,默认“#aae6ff”
			compoundHoverGlowOpacity : 0.5, //节点组悬停的透明度,默认0
			compoundHoverGlowBlur : 8, //节点组悬停的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			compoundHoverGlowStrength : 3,//节点组悬停的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			//compoundLabel: { passthroughMapper: { attrName: "label" } } ,//节点组显示文字,可以映射节点数据的字段,自动默认映射label字段
			compoundLabelfontname : "Arial",//节点组显示标签的字体,默认Arial
			compoundLabelfontsize : 11,//节点组显示标签的字体大小,默认11
			compoundLabelFontColor : skinLabelColor,//节点组显示标签的字体颜色,默认#000000黑色
			compoundLabelFontWeight : "normal",//节点组显示标签的字体粗细,默认normal,支持bold(加粗)
			compoundLabelFontStyle : "normal",//节点组显示标签的字体样式,默认normal,支持italic(斜体)
			compoundLabelHorizontalAnchor : "center",//节点组显示标签的水平显示位置,默认center,支持left,right
			compoundLabelVerticalAnchor : "bottom", //节点组显示标签的垂直的显示位置,默认middle,支持top,bottom
			compoundLabelXOffset : 0, //节点组显示标签的X轴的偏移量,类似于padding,配合labelHorizontalAnchor使用,可以用负整数
			compoundLabelYOffset : 0, //节点组显示标签的Y轴的偏移量,类似于padding,配合labelVerticalAnchor使用,可以用负整数
			compoundLabelGlowColor : skinLabelColor, //节点组显示标签的发光颜色,默认#ffffff
			compoundLabelGlowOpacity : 0, //节点组显示标签的发光透明度,默认0
			compoundLabelGlowBlur : 2, //节点组显示标签的发光模糊效果,默认2,有效值0-255,最好8的倍数,例如2、4、8、16、32
			compoundLabelGlowStrength : 20,//节点组显示标签的发光对比度,有效值0-255,默认值20,对滤镜和渐变理解不深的不建议修改
			//compoundTooltipText:{ passthroughMapper: { attrName: "tooltip" } },//"<b>${label}</b>: ${weight}",//节点组的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			compoundTtooltipFont : "Arial", //节点组提示文字的字体,默认Arial
			compoundTooltipFontSize : 11,//节点组提示文字的字体大小,默认11
			compoundTooltipFontColor : "#ffffff",//节点组提示文字的字体颜色,默认#000000黑色
			compoundTooltipBackgroundColor : "#505050",//节点组提示文字的背景颜色,默认#f5f5cc
			compoundTooltipBorderColor : "#505050"//节点组提示文字的边框,默认#000000黑色
		},
		edges : {
			style : "SOLID", //线的样式,"SOLID(实心)/DOT(点)/LONG_DASH长破折号/EQUAL_DASH短破折号. 默认"SOLID"
			color : {
				passthroughMapper : {
					attrName : "color"
				}
			}, //线的颜色,默认#999999
			width : {
				passthroughMapper : {
					attrName : "weight"
				}
			}, //线的宽度,默认1px
			opacity : 0.5, //线的透明度,默认0.8
			//???????????????????????????????????????????????????/
			// mergeColor:"#666666",	//合并线的颜色,默认#999999
			// mergeWidth: 1,		//合并线的宽度,默认1px
			// mergeOpacity: 0.8, 	//合并线的透明度,默认0.8
			// mergeStyle:"SOLID",	//合并线的样式,"SOLID(实心)/DOT(点)/LONG_DASH长破折号/EQUAL_DASH短破折号. 默认"SOLID"
			// mergeTooltipText:"<b>${label}</b>",//合并线的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			//???????????????????????????????????????????????????/
			selectionColor : {
				passthroughMapper : {
					attrName : "color"
				}
			}, //"#999999",//线选中的颜色,默认color,或者mergeColor
			selectionOpacity : 1, //线选中的透明度,默认opacity,或者mergeOpacity
			selectionGlowColor : {
				passthroughMapper : {
					attrName : "color"
				}
			}, //"#ffff33",//线选中发光的颜色,默认#ffff33
			selectionGlowOpacity : 0.5,//线选中发光的透明度,默认0.6
			selectionGlowBlur : 4,//线选中发光的模糊效果,默认4,有效值0-255,最好8的倍数,例如2、4、8、16、32
			selectionGlowStrength : 3,//线选中发光的对比度,有效值0-255,默认值10,对滤镜和渐变理解不深的不建议修改
			hoverOpacity : 1,//悬停的透明度
			curvature : 18, //线的曲率,线的弧形弯曲角度,默认18
			sourceArrowShape : {
				passthroughMapper : {
					attrName : "sourceArrowShape"
				}
			}, //线起点的样式,NONE没有/DELTA三角洲/ARROW箭头/DIAMOND钻石/CIRCLE圆/T(T型)
			targetArrowShape : {
				passthroughMapper : {
					attrName : "targetArrowShape"
				}
			},//"delta",//线终点的样式,线数据没有配置,默认none,线数据设置directed,默认delta
			//sourceArrowColor:"#999999", //线起点颜色,默认color
			//targetArrowColor:"#179fdc", //"#999999", //线终点颜色,默认color
			label : {
				passthroughMapper : {
					attrName : "label"
				}
			}, //线显示文字,可以映射节点数据的字段,自动默认映射label字段
			labelHorizontalAnchor : "center", //线显示标签的水平显示位置,默认center,支持left,right
			labelVerticalAnchor : "middle", //线显示标签的垂直的显示位置,默认middle,支持top,bottom
			labelXOffset : 0, //线显示标签的X轴的偏移量,类似于padding,配合labelHorizontalAnchor使用,可以用负整数
			labelYOffset : 0, //线显示标签的Y轴的偏移量,类似于padding,配合labelVerticalAnchor使用,可以用负整数
			labelFontName : "Arial", //线显示标签的字体,默认Arial
			labelFontSize : 11, //线显示标签的字体大小,默认11
			labelFontColor : skinLabelColor, //线显示标签的字体颜色,默认#000000黑色
			labelFontWeight : "normal", //线显示标签的字体粗细,默认normal,支持bold(加粗)
			labelFontStyle : "normal", //线显示标签的字体样式,默认normal,支持italic(斜体)
			labelGlowColor : skinLabelColor, //线显示标签的发光颜色,默认#fffff白色
			labelGlowOpacity : 0,//线显示标签的发光透明度,默认#fffff白色
			labelGlowBlur : 2, //线显示标签的发光模糊效果,默认2,有效值0-255,最好8的倍数,例如2、4、8、16、32
			labelGlowStrength : 20,//线显示标签的发光对比度,有效值0-255,默认值10,对滤镜和渐变理解不深的不建议修改
			tooltipText : {
				passthroughMapper : {
					attrName : "tooltip"
				}
			},//线的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			tooltipFont : "Arial",//线的提示文字的文字字体,默认Arial
			tooltipFontSize : 11,//线的提示文字的文字大小,默认11px
			tooltipFontColor : "#000000",//线的提示文字的文字颜色,默认#000000
			tooltipBackgroundColor : "#505050",//线的提示文字的背景颜色,默认#f5f5cc
			tooltipBorderColor : "#505050"//线的提示文字的边框颜色,默认#000000
		}
	};

	//---------------------------------初始化flex图形---------------------------------------------//
	var div_id = "cytoscapeweb"; //显示拓扑图的<div>id,
	var options = {
		swfPath : ".././flash/CytoscapeWeb", //主程序.swf文件
		flashInstallerPath : ".././flash/playerProductInstall",//本地的flash安装路径.swf文件
	//flashAlternateContent:"",//flash没有打开的空白文字
	//resourceBundleUrl:"",	//国际化文字.properties文件
	//idToken:"home" 			//一个页面多个topo图时,自定义id,通常不需要配置
	//mouseDownToDragDelay:400 //鼠标选中节点,按住400毫秒,即可选中相关联的所有节点,设置1即不激活.默认400毫秒
	};

	vis = new org.cytoscapeweb.Visualization(div_id, options); //取得了拓扑图对象
	vvis = vis;
	//---------------------------------配置绘画---------------------------------------------//
	var draw_options = null;

	if (!autoLayout) {
		var dataObj = eval('(' + topoCoordinatesJson + ')');
		draw_options = {
			//network: network_xml,	//显示数据
			nodeLabelsVisible : true, //是否显示线的标签
			edgeLabelsVisible : true, //是否显示点的标签
			layout : { //"CompoundSpringEmbedder",//layout: "Tree",Circle,ForceDirected,Radial,CompoundSpringEmbedder
				name : "Preset",
				options : {
					fitToScreen : false, //false左上角为0,0 true为节点居中,自动适应屏幕,0,0不是中心点
					points : null
				//pointData
				}
			},
			//layout:"ForceDirected",
			visualStyle : visual_style, //设置样式
			panZoomControlVisible : true, //是否显示控制台
			nodeTooltipsEnabled : true, //是否显示点的tooltip
			edgeTooltipsEnabled : true
		//是否显示线的tooltip
		//preloadImages: true,
		//useProxy: false
		};

	} else {
		draw_options = {
			//network: network_xml,	//显示数据
			nodeLabelsVisible : true, //是否显示线的标签
			edgeLabelsVisible : true, //是否显示点的标签
			//layout: { //"CompoundSpringEmbedder",//layout: "Tree",Circle,ForceDirected,Radial,CompoundSpringEmbedder
			// name:"Preset",
			// options:{
			//	fitToScreen :false, //false左上角为0,0 true为节点居中,自动适应屏幕,0,0不是中心点
			//	points: null//pointData
			// }
			// },  
			layout : "ForceDirected",
			visualStyle : visual_style, //设置样式
			panZoomControlVisible : true, //是否显示控制台
			nodeTooltipsEnabled : true, //是否显示点的tooltip
			edgeTooltipsEnabled : true
		//是否显示线的tooltip
		//preloadImages: true,
		//useProxy: false
		};
	}

	//---------------------------------开始绘画---------------------------------------------//
	//第一次绘制加载样式和配置
	vis.draw(draw_options);

	//---------------------------------初始化拓扑图的数据---------------------------------------------//
	//参考示例ajax加载xml数据

	
	var dt = json;
	if (typeof dt !== "string") {
		if (window.ActiveXObject) {
			dt = obj.xml; //ie
		} else {
			dt = obj.children[0].outerHTML; //火狐
		}
	}
	draw_options.network = dt;
	//第二次绘制加载数据
	vis.draw(draw_options);

	function writeObj(obj) {
		var description = "";
		for ( var i in obj) {
			var property = obj[i];
			description += i + " = " + property + "\n";
		}
		//alert(description);
	}

	//alert("aaa");
	//---------------------------------当vis绘制加载完成执行ready---------------------------------------------//
	vis.ready(

	function() {

		if (true) {
			//alert("bbb");

			//					//加载数据,转换坐标
			//					var networkModel = vis.networkModel();
			//					//alert("bbb--cc");
			//					var array = new Array();
			//
			//					var nodes = networkModel.data.nodes, edges = networkModel.data.edges;
			//					//alert("bbb--dd");
			//					for ( var i = 0; i < nodes.length; i++) {
			//						var obj = nodes[i];
			//						//writeObj(obj);
			//						obj.flicker = false; //设置闪烁的, true为闪烁,false为不闪烁
			//						var o = {
			//							group : "nodes",
			//							x : obj.x,
			//							y : obj.y,
			//							data : obj
			//						};
			//						//writeObj(o);
			//						array.push(o);
			//					}
			//					for ( var i = 0; i < edges.length; i++) {
			//						var obj = edges[i];
			//						
			//						array.push({
			//							group : "edges",
			//							data : obj
			//						});
			//					}
			//
			//					//alert("bbb2");
			//
			//					vis.removeElements();
			//alert(array);
			//vis.addElements(array, false); //配合Preset布局使用,只有这样才能第一次将x,y坐标传入
			//alert("bbb4");
		}

		vis.addListener("click", "nodes", function(event) {
			if (line_mode) {
				return;
			}//连线模式,不弹框

			var id = event.target.data.id;
			var name = event.target.data.label;
			var ip = event.target.data.ip;
			//							alert("aaa");
			//$wnd.executeJs(id);
			//							alert("aaa---2");	
			//alert(id+" ip:"+ip);
			//查看节点详情
			nodeDetail(id, name, ip);
			//@com.ruifios.client.home.topo.TopoPlugInPanel::nodeDetail(Ljava/lang/String;Ljava/lang/String;)(id,ip);
		});

		vis.addListener("click", "edges", function(event) {
			if (line_mode) {
				return;
			}//连线模式,不弹框

			var id = event.target.data.id;
			//						alert("aaa");
			//$wnd.executeJs(id);
			//							alert("aaa---2");		
			//@com.ruifios.client.home.topo.TopoPlugInPanel::linkDetail(Ljava/lang/String;)(id);
			//alert(id);
			//查看互联信息
			linkDetail(id);
		});
	}); // end vis.ready
}

/**
 * 自定义布局
 * @param json
 */
var _srcId;
function drawTopoSelfLayout(data)
{
	var autoLayout = data.isAutoLayout;//是否自定义布局
	var json = data.topoDataXml;//拓扑数据 xml
	var topoCoordinatesJson = data.topoCoordinatesJson;
	var deviceListJson = data.deviceListJson;

	var vis; //拓扑图对象
	var skinBackgroundColor = "#ffffff";//"#252525"; //动态修改拓扑图样式示例
	var skinCompoundColor = "#252525";
	var skinEdgeColor = "#c1c1c1";
	var skinNodeGlowColor = "#666666";
	var skinLabelColor = "#000000";
	var line_mode = false; //连线模式
	var line_mode_obj = { //连线配置对象
		weight : 1,
		arrow : "",
		color : ""
	};
	
	var visual_style = {
		global : {
			backgroundColor : skinBackgroundColor, //背景颜色,默认#ffffff白色,不知道为什么不好使????????
			tooltipDelay : 400, //光标在node和dege的tooltip的反应延时,默认800
			selectionFillColor : "#8888ff", //选区矩形的颜色,默认#8888ff
			selectionLineColor : "#8888ff", //选区矩形边框的颜色,默认#8888ff
			selectionFillOpacity : 0.1, //选区矩形的透明度,默认0.1
			selectionLineOpacity : 0.8, //选区矩形边框的透明度,默认0.8
			selectionLineWidth : 1 //选区矩形边框的宽度,默认1
		},
		nodes : {
			shape : {
				passthroughMapper : {
					attrName : "shape"
				}
			},//"ELLIPSE",	//节点形状,椭圆ELLIPSE/矩形RECTANGLE/三角形TRIANGLE/钻石DIAMOND/六角HEXAGON/八角OCTAGON/平行四边形PARALLELOGRAM/绘制的ROUNDRECT/v字形VEE
			size : {
				passthroughMapper : {
					attrName : "weight"
				}
			}, //节点大小,可以自动设置,默认24
			//color:{ passthroughMapper: { attrName: "color" } },  //节点颜色,默认#f5f5f5
			opacity : 1, //节点的透明度,默认0.8
			//width://节点的宽度,以像素为单位
			//height://节点的高度,以像素为单位
			//image:"${base}/images/aaaa.gif", //图像的URL作为节点的背景
			image : {
				passthroughMapper : {
					attrName : "imgtype"
				}
			},//{ discreteMapper:{attrName: "imgtype",entries: nodeImages } },
			borderColor : {
				passthroughMapper : {
					attrName : "color"
				}
			},//节点边框颜色,默认值#666666
			borderWidth : 1,//节点边框的宽度,默认值1
			selectionColor : {
				passthroughMapper : {
					attrName : "color"
				}
			},//"#f5f5f5",//节点选中的颜色,默认等于color
			selectionBorderColor : skinNodeGlowColor,//节点选中的边框颜色,默认等于bordercolor 666666
			selectionOpacity : 1, //节点选中的透明度,默认等于opacity
			selectionBorderWidth : 0, //节点选中的边框宽度,默认等于borderWidth
			selectionGlowColor : skinNodeGlowColor,//"#ffff33",//节点选中的发光颜色,默认#ffff33
			selectionGlowOpacity : 0.9, //节点选中的发光透明度,默认0.6
			selectionGlowBlur : 8, //节点选中的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			selectionGlowStrength : 3, //节点选中的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			hoverOpacity : 1, //节点悬停的透明度,默认等于opacity
			hoverBorderColor : skinNodeGlowColor,//节点悬停的边框颜色,默认等于bordercolor 666666
			hoverBorderWidth : 0,//节点悬停的边框宽度,默认等于borderWidth
			hoverGlowColor : skinNodeGlowColor,//{ passthroughMapper: { attrName: "color" } },//"#aae6ff",//节点悬停的发光颜色,默认“#aae6ff”
			hoverGlowOpacity : 0.9, //节点悬停的透明度,默认0
			hoverGlowBlur : 8, //节点悬停的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			hoverGlowStrength : 3,//节点悬停的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			label : {
				passthroughMapper : {
					attrName : "label"
				}
			},//节点显示文字,可以映射节点数据的字段,自动默认映射label字段
			labelfontname : "Arial",//节点显示标签的字体,默认Arial
			labelfontsize : 12,//节点显示标签的字体大小,默认11
			labelFontColor : skinLabelColor,//节点显示标签的字体颜色,默认#000000黑色
			labelFontWeight : "normal",//节点显示标签的字体粗细,默认normal,支持bold(加粗)
			labelFontStyle : "normal",//节点显示标签的字体样式,默认normal,支持italic(斜体)
			labelHorizontalAnchor : "center",//节点显示标签的水平显示位置,默认center,支持left,right
			labelVerticalAnchor : "bottom", //节点显示标签的垂直的显示位置,默认middle,支持top,bottom
			labelXOffset : 0, //节点显示标签的X轴的偏移量,类似于padding,配合labelHorizontalAnchor使用,可以用负整数
			labelYOffset : 0, //节点显示标签的Y轴的偏移量,类似于padding,配合labelVerticalAnchor使用,可以用负整数
			labelGlowColor : "#ffffff", //节点显示标签的发光颜色,默认#ffffff
			labelGlowOpacity : 0, //节点显示标签的发光透明度,默认0
			labelGlowBlur : 8, //节点显示标签的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			labelGlowStrength : 20,//节点显示标签的发光对比度,有效值0-255,默认值20,对滤镜和渐变理解不深的不建议修改
			tooltipText : {
				passthroughMapper : {
					attrName : "tooltip"
				}
			},//"<b>${label}</b>: ${weight}",//节点的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			tooltipFont : "Arial", //节点提示文字的字体,默认Arial
			tooltipFontSize : 11,//节点提示文字的字体大小,默认11
			tooltipFontColor : "#ffffff",//线的提示文字的文字颜色,默认#000000
			tooltipBackgroundColor : "#505050",//线的提示文字的背景颜色,默认#f5f5cc
			tooltipBorderColor : "#505050",//线的提示文字的边框颜色,默认#000000
			//
			//节点组(复合节点)的样式,参考上面节点的样式,是一样的,只不过字段前面加了compound而已
			//
			compoundShape : "RECTANGLE",//节点组形状,只接受椭圆ELLIPSE/矩形RECTANGLE/绘制的ROUNDRECT
			//compoundSize:"auto",//auto(默认),ignoreLabels,//节点组的尺寸,一般自动,不需要设置.
			compoundColor : skinCompoundColor,//"#252525", //节点组颜色,默认#f5f5f5
			compoundOpacity : 0.8, //节点组的透明度,默认0.8
			//compoundWidth:400,//节点组的宽度,以像素为单位,不支持
			//compoundHeight:150,//节点组的高度,以像素为单位,不支持
			//compoundImage://图像的URL作为节点组的背景
			compoundBorderColor : {
				passthroughMapper : {
					attrName : "color"
				}
			},//"#20b6e4",//节点组边框颜色,默认值#666666
			compoundBorderWidth : 1,//节点组边框的宽度,默认值1
			compoundSelectionBorderColor : "#20b6e4",//节点组选中的边框颜色,默认等于bordercolor
			compoundSelectionBorderWidth : 0, //节点组选中的边框宽度,默认等于borderWidth
			compoundSelectionColor : {
				passthroughMapper : {
					attrName : "compoundColor"
				}
			}, //"#f5f5f5",//节点组选中的颜色,默认等于color
			compoundSelectionOpacity : 0.8, //节点组选中的透明度,默认等于opacity
			compoundSelectionGlowColor : {
				passthroughMapper : {
					attrName : "compoundColor"
				}
			}, //"#ffff33",	//节点组选中的发光颜色,默认#ffff33
			compoundSelectionGlowOpacity : 0.5, //节点组选中的发光透明度,默认0.6
			compoundSelectionGlowBlur : 8, //节点组选中的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			compoundSelectionGlowStrength : 3, //节点组选中的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			compoundHoverBorderColor : "#20b6e4",//节点组悬停的边框颜色,默认等于bordercolor
			compoundHoverBorderWidth : 0,//节点组悬停的边框宽度,默认等于borderWidth
			compoundHoverColor : "#20b6e4",
			compoundHoverOpacity : 0.8, //节点组悬停的透明度,默认等于opacity
			compoundHoverGlowColor : {
				passthroughMapper : {
					attrName : "compoundColor"
				}
			}, //节点组悬停的发光颜色,默认“#aae6ff”
			compoundHoverGlowOpacity : 0.5, //节点组悬停的透明度,默认0
			compoundHoverGlowBlur : 8, //节点组悬停的发光模糊效果,默认8,有效值0-255,最好8的倍数,例如2、4、8、16、32
			compoundHoverGlowStrength : 3,//节点组悬停的发光对比度,有效值0-255,默认值6,对滤镜和渐变理解不深的不建议修改
			//compoundLabel: { passthroughMapper: { attrName: "label" } } ,//节点组显示文字,可以映射节点数据的字段,自动默认映射label字段
			compoundLabelfontname : "Arial",//节点组显示标签的字体,默认Arial
			compoundLabelfontsize : 11,//节点组显示标签的字体大小,默认11
			compoundLabelFontColor : skinLabelColor,//节点组显示标签的字体颜色,默认#000000黑色
			compoundLabelFontWeight : "normal",//节点组显示标签的字体粗细,默认normal,支持bold(加粗)
			compoundLabelFontStyle : "normal",//节点组显示标签的字体样式,默认normal,支持italic(斜体)
			compoundLabelHorizontalAnchor : "center",//节点组显示标签的水平显示位置,默认center,支持left,right
			compoundLabelVerticalAnchor : "bottom", //节点组显示标签的垂直的显示位置,默认middle,支持top,bottom
			compoundLabelXOffset : 0, //节点组显示标签的X轴的偏移量,类似于padding,配合labelHorizontalAnchor使用,可以用负整数
			compoundLabelYOffset : 0, //节点组显示标签的Y轴的偏移量,类似于padding,配合labelVerticalAnchor使用,可以用负整数
			compoundLabelGlowColor : skinLabelColor, //节点组显示标签的发光颜色,默认#ffffff
			compoundLabelGlowOpacity : 0, //节点组显示标签的发光透明度,默认0
			compoundLabelGlowBlur : 2, //节点组显示标签的发光模糊效果,默认2,有效值0-255,最好8的倍数,例如2、4、8、16、32
			compoundLabelGlowStrength : 20,//节点组显示标签的发光对比度,有效值0-255,默认值20,对滤镜和渐变理解不深的不建议修改
			//compoundTooltipText:{ passthroughMapper: { attrName: "tooltip" } },//"<b>${label}</b>: ${weight}",//节点组的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			compoundTtooltipFont : "Arial", //节点组提示文字的字体,默认Arial
			compoundTooltipFontSize : 11,//节点组提示文字的字体大小,默认11
			compoundTooltipFontColor : "#ffffff",//节点组提示文字的字体颜色,默认#000000黑色
			compoundTooltipBackgroundColor : "#505050",//节点组提示文字的背景颜色,默认#f5f5cc
			compoundTooltipBorderColor : "#505050"//节点组提示文字的边框,默认#000000黑色
		},
		edges : {
			style : {
				passthroughMapper : {
					attrName : "style"
				}
			},//"SOLID", //线的样式,"SOLID(实心)/DOT(点)/LONG_DASH长破折号/EQUAL_DASH短破折号. 默认"SOLID"
			color : {
				passthroughMapper : {
					attrName : "color"
				}
			}, //线的颜色,默认#999999
			width : {
				passthroughMapper : {
					attrName : "weight"
				}
			}, //线的宽度,默认1px
			opacity : 0.5, //线的透明度,默认0.8
			//???????????????????????????????????????????????????/
			// mergeColor:"#666666",	//合并线的颜色,默认#999999
			// mergeWidth: 1,		//合并线的宽度,默认1px
			// mergeOpacity: 0.8, 	//合并线的透明度,默认0.8
			// mergeStyle:"SOLID",	//合并线的样式,"SOLID(实心)/DOT(点)/LONG_DASH长破折号/EQUAL_DASH短破折号. 默认"SOLID"
			// mergeTooltipText:"<b>${label}</b>",//合并线的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			//???????????????????????????????????????????????????/
			selectionColor : {
				passthroughMapper : {
					attrName : "color"
				}
			}, //"#999999",//线选中的颜色,默认color,或者mergeColor
			selectionOpacity : 1, //线选中的透明度,默认opacity,或者mergeOpacity
			selectionGlowColor : {
				passthroughMapper : {
					attrName : "color"
				}
			}, //"#ffff33",//线选中发光的颜色,默认#ffff33
			selectionGlowOpacity : 0.5,//线选中发光的透明度,默认0.6
			selectionGlowBlur : 4,//线选中发光的模糊效果,默认4,有效值0-255,最好8的倍数,例如2、4、8、16、32
			selectionGlowStrength : 3,//线选中发光的对比度,有效值0-255,默认值10,对滤镜和渐变理解不深的不建议修改
			hoverOpacity : 1,//悬停的透明度
			curvature : 18, //线的曲率,线的弧形弯曲角度,默认18
			sourceArrowShape : {
				passthroughMapper : {
					attrName : "sourceArrowShape"
				}
			}, //线起点的样式,NONE没有/DELTA三角洲/ARROW箭头/DIAMOND钻石/CIRCLE圆/T(T型)
			targetArrowShape : {
				passthroughMapper : {
					attrName : "targetArrowShape"
				}
			},//"delta",//线终点的样式,线数据没有配置,默认none,线数据设置directed,默认delta
			//sourceArrowColor:"#999999", //线起点颜色,默认color
			//targetArrowColor:"#179fdc", //"#999999", //线终点颜色,默认color
			label : {
				passthroughMapper : {
					attrName : "label"
				}
			}, //线显示文字,可以映射节点数据的字段,自动默认映射label字段
			labelHorizontalAnchor : "center", //线显示标签的水平显示位置,默认center,支持left,right
			labelVerticalAnchor : "middle", //线显示标签的垂直的显示位置,默认middle,支持top,bottom
			labelXOffset : 0, //线显示标签的X轴的偏移量,类似于padding,配合labelHorizontalAnchor使用,可以用负整数
			labelYOffset : 0, //线显示标签的Y轴的偏移量,类似于padding,配合labelVerticalAnchor使用,可以用负整数
			labelFontName : "Arial", //线显示标签的字体,默认Arial
			labelFontSize : 11, //线显示标签的字体大小,默认11
			labelFontColor : skinLabelColor, //线显示标签的字体颜色,默认#000000黑色
			labelFontWeight : "normal", //线显示标签的字体粗细,默认normal,支持bold(加粗)
			labelFontStyle : "normal", //线显示标签的字体样式,默认normal,支持italic(斜体)
			labelGlowColor : skinLabelColor, //线显示标签的发光颜色,默认#fffff白色
			labelGlowOpacity : 0,//线显示标签的发光透明度,默认#fffff白色
			labelGlowBlur : 2, //线显示标签的发光模糊效果,默认2,有效值0-255,最好8的倍数,例如2、4、8、16、32
			labelGlowStrength : 20,//线显示标签的发光对比度,有效值0-255,默认值10,对滤镜和渐变理解不深的不建议修改
			tooltipText : {
				passthroughMapper : {
					attrName : "tooltip"
				}
			},//线的提示文字,默认显示,绑定节点数据的字段,可以是html代码
			tooltipFont : "Arial",//线的提示文字的文字字体,默认Arial
			tooltipFontSize : 11,//线的提示文字的文字大小,默认11px
			tooltipFontColor : "#000000",//线的提示文字的文字颜色,默认#000000
			tooltipBackgroundColor : "#505050",//线的提示文字的背景颜色,默认#f5f5cc
			tooltipBorderColor : "#505050"//线的提示文字的边框颜色,默认#000000
		}
	};
		
	//---------------------------------初始化flex图形---------------------------------------------//
	var div_id = "cytoscapeweb"; //显示拓扑图的<div>id,
	var options = {
		swfPath : ".././flash/CytoscapeWeb", //主程序.swf文件
		flashInstallerPath : ".././flash/playerProductInstall",//本地的flash安装路径.swf文件
	//flashAlternateContent:"",//flash没有打开的空白文字
	//resourceBundleUrl:"",	//国际化文字.properties文件
	//idToken:"home" 			//一个页面多个topo图时,自定义id,通常不需要配置
	//mouseDownToDragDelay:400 //鼠标选中节点,按住400毫秒,即可选中相关联的所有节点,设置1即不激活.默认400毫秒
	};
	vis = new org.cytoscapeweb.Visualization(div_id, options); //取得了拓扑图对象
	vvis = vis;
	
	//---------------------------------配置绘画---------------------------------------------//
	var draw_options = null;

	if (!autoLayout) {
		var dataObj = eval('(' + topoCoordinatesJson + ')');

		draw_options = {
			//network: network_xml,	//显示数据
			nodeLabelsVisible : true, //是否显示线的标签
			edgeLabelsVisible : true, //是否显示点的标签
			layout : { //"CompoundSpringEmbedder",//layout: "Tree",Circle,ForceDirected,Radial,CompoundSpringEmbedder
				name : "Preset",
				options : {
					fitToScreen : false, //false左上角为0,0 true为节点居中,自动适应屏幕,0,0不是中心点
					points : null
				//pointData
				}
			},
			//layout:"ForceDirected",
			visualStyle : visual_style, //设置样式
			panZoomControlVisible : true, //是否显示控制台
			nodeTooltipsEnabled : true, //是否显示点的tooltip
			edgeTooltipsEnabled : true
		//是否显示线的tooltip
		//preloadImages: true,
		//useProxy: false
		};

	} else {
		draw_options = {
			//network: network_xml,	//显示数据
			nodeLabelsVisible : true, //是否显示线的标签
			edgeLabelsVisible : true, //是否显示点的标签
			//layout: { //"CompoundSpringEmbedder",//layout: "Tree",Circle,ForceDirected,Radial,CompoundSpringEmbedder
			// name:"Preset",
			// options:{
			//	fitToScreen :false, //false左上角为0,0 true为节点居中,自动适应屏幕,0,0不是中心点
			//	points: null//pointData
			// }
			// },  
			layout : "ForceDirected",
			visualStyle : visual_style, //设置样式
			panZoomControlVisible : true, //是否显示控制台
			nodeTooltipsEnabled : true, //是否显示点的tooltip
			edgeTooltipsEnabled : true
		//是否显示线的tooltip
		//preloadImages: true,
		//useProxy: false
		};
	}

	//---------------------------------开始绘画---------------------------------------------//
	//第一次绘制加载样式和配置
	vis.draw(draw_options);

	//---------------------------------初始化拓扑图的数据---------------------------------------------//
	//参考示例ajax加载xml数据

	var dt = json;

	if (typeof dt !== "string") {
		if (window.ActiveXObject) {
			dt = obj.xml; //ie
		} else {
			dt = obj.children[0].outerHTML; //火狐
		}
	}
	draw_options.network = dt;
	//第二次绘制加载数据
	vis.draw(draw_options);

	function writeObj(obj) {
		var description = "";
		for ( var i in obj) {
			var property = obj[i];
			description += i + " = " + property + "\n";
		}
	}

	vis.ready(function() {

		if (true) {
			//加载数据,转换坐标
			var networkModel = vis.networkModel();
			var nodes = networkModel.data.nodes;
			var edges = networkModel.data.edges;

			vis.removeElements();
			
			for ( var i = 0; i < nodes.length; i++) {
				var obj = nodes[i];
				//writeObj(obj);
				obj.flicker = false; //设置闪烁的, true为闪烁,false为不闪烁
				var o = {
					group : "nodes",
					x : obj.x,
					y : obj.y,
					data : obj
				};
				//writeObj(o);
				//array.push(o);
				if (i == nodes.length - 1) {
					vis.addNode(obj.x, obj.y, obj, true);
				} else {
					vis.addNode(obj.x, obj.y, obj, false);
				}
			}
			
			for ( var i = 0; i < edges.length; i++) {
				var obj = edges[i];
				if (i == nodes.length - 1) {
					vis.addEdge(obj, true);
				} else {
					vis.addEdge(obj, false);
				}
				//array.push({group : "edges",data : obj});
			}
		}
		
		vis.addListener("dblclick", "nodes", function(event) {
			if (line_mode) {
				return;
			}//连线模式,不弹框

			var id = event.target.data.id;
			var name = event.target.data.label;
			var ip = event.target.data.ip;
			//查看节点详情@com.ruifios.client.home.topo.TopoPlugInPanel::nodeDetail(Ljava/lang/String;Ljava/lang/String;)(id,ip);
			nodeDetail(id, name, ip);
		});

		vis.addListener("click", "edges", function(event) {
			if (line_mode) {
				return;
			}//连线模式,不弹框

			var id = event.target.data.id;
			//查看互联信息@com.ruifios.client.home.topo.TopoPlugInPanel::linkDetail(Ljava/lang/String;)(id);
			linkDetail(id);
		});

		vis.addContextMenuItem("添加业务访问", "none", function(evt) {
			addBusiness();
        });
		if(data.type == 2)
		{
			vis.addContextMenuItem("添加连线", "nodes", function(evt) {
		    	_srcId = evt.target.data.id;
	            vis.removeListener("click", "nodes", clickNodeToAddEdge);
	            vis.addListener("click", "nodes", clickNodeToAddEdge);
	        })
	        .addContextMenuItem("删除连线", "edges", function(evt) {
	        	var _dstId = evt.target.data.id;
	        	delEdge(_dstId);
	        });
		}
		
	}); // end vis.ready
}

function getTopodata() 
{
	var contents = "[";
	var nodes = vvis.selected();
	if (nodes.length > 0) {
		var size = nodes.length;
		var need = false;
		for ( var i = 0; i < size; i++) {
			if (nodes[i].group == "nodes") {
				contents += "{";
				contents += ("\"id\":\"" + nodes[i].data.id + "\",");
				contents += ("\"name\":\"" + nodes[i].data.label + "\",");
				contents += ("\"ip\":\"" + nodes[i].data.ip + "\",");
				contents += ("\"type\":\"" + nodes[i].data.type + "\",");
				contents += ("\"x\":\"" + Math.floor(nodes[i].x) + "\",");
				contents += ("\"y\":\"" + Math.floor(nodes[i].y) + "\",");
				contents += ("\"imgtype\":\"" + nodes[i].data.imgtype + "\"");

				contents += "}";
				contents += ",";
				need = true;
			}
		}
		if (need) {
			contents = contents.substring(0, contents.length - 1);
		}
	}
	contents += "]";
	return contents;
}

/**
 * 添加连线
 * @param evt
 */
function clickNodeToAddEdge(evt) {
    if (_srcId != null) 
    {
    	var srcip = vvis.node(_srcId).data.ip;
    	var _dstId = evt.target.data.id;
    	var dstip = evt.target.data.ip;
    	var id = dstip+":"+srcip;
    	if(_srcId != _dstId && vvis.edge(id) == null)//  && vvis.edge(srcip+":"+dstip) == null
    	{
    		vvis.removeListener("click", "nodes", clickNodeToAddEdge);
    		vvis.addEdge({id: id, source: _srcId, target:  _dstId,  label: "", style: 'LONG_DASH', color: '#24B14C', sourceArrowShape: 'none' ,targetArrowShape: 'ARROW'}, true);
        	//添加连线到数据库
        	addBEdge(_srcId, _dstId);
        	_srcId = null;	
    	}
    }
}
