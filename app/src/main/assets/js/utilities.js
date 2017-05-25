var CurrentParsedUrl = $.url();
var DEFAULTCOUNT = 0;
var SECONDLOADED = true;
var BASEURL = CurrentParsedUrl.attr("base");
var CURRENTFILE = CurrentParsedUrl.attr("file");
var CURRENTDIR = CurrentParsedUrl.attr("directory");
var BASEHOST = "http://f10.chinabigdata.com";


var GETDATAURL = "http://f10.chinabigdata.com/php/get_f10.php?c={0}&ids={1}&nomenu={2}&cid={3}&fid={4}";
var GETSECUINFOURL = "http://f10.chinabigdata.com/php/get_secu.php?c={0}";
var GETDETAILINFOURL = "http://f10.chinabigdata.com/php/get_rec.php?c={0}&id={1}&p={2}";
var GETBDCODEURL = "http://f10.chinabigdata.com/php/get_secu_by_code.php?c={0}";

//var GETDATAURL = BASEHOST + "/php/get_f10.php?c={0}&ids={1}&nomenu={2}&cid={3}&fid={4}";
//var GETSECUINFOURL = BASEHOST + "/php/get_secu.php?c={0}";
//var GETDETAILINFOURL = BASEHOST + "/php/get_rec.php?c={0}&id={1}&p={2}";
//var GETBDCODEURL = BASEHOST + "/php/get_secu_by_code.php?c={0}";

//var GETDATAURL = CURRENTDIR + "php/get_f10.php?c={0}&ids={1}&nomenu={2}&cid={3}&fid={4}";
//var GETSECUINFOURL = CURRENTDIR + "php/get_secu.php?c={0}";
//var GETDETAILINFOURL = CURRENTDIR + "php/get_rec.php?c={0}&id={1}&p={2}";
//var GETBDCODEURL = CURRENTDIR + "php/get_secu_by_code.php?c={0}";

var DEFAULTDETAILDATABASE = "BD_F10_ANN_TXT_1";
var ATTACHMENT = "http://d1.chinabigdata.com/annex/blt/{0}"
var ATTACHMENT_REPORT = "http://d1.chinabigdata.com/annex/rpt/{0}"
var DEFAULT_CID = -2;
var PARAM_C = CurrentParsedUrl.param("c");
var PARAM_CID = CurrentParsedUrl.param("cid") == null ? DEFAULT_CID : CurrentParsedUrl.param("cid");
var PARAM_SID = CurrentParsedUrl.param("sid") == null ? "" : CurrentParsedUrl.param("sid");
var PARAM_FID = CurrentParsedUrl.param("fid") == null ? "F10" : CurrentParsedUrl.param("fid").toUpperCase();
var PARAM_IDS = CurrentParsedUrl.param("ids") == null ? "" : CurrentParsedUrl.param("ids");
var PARAM_QL = 0;//CurrentParsedUrl.param("ql") == null ? "1" : CurrentParsedUrl.param("ql");
var PARAM_HIDETOPBAR = CurrentParsedUrl.param("istophide") == null ? (PARAM_CID == "-2" ? "1" : "0") : CurrentParsedUrl.param("istophide");
var PARAM_TRADECODE = "";
var PARAM_TRADEEXCHANGE = "";

var tmp_code_arr = PARAM_C.split('.');
if(tmp_code_arr.length == 2)
{
	PARAM_TRADECODE = tmp_code_arr[0];
	PARAM_TRADEEXCHANGE = tmp_code_arr[1];
}

var FUNCTION_F10 = "F10";
var FUNCTION_SECU_DIAG = "SECU_DIAG";
var FUNCTION_SECU_SCRN = "SECU_SCRN";
var FUNCTION_BD_INFO = "BD_INFO";

if (PARAM_C.toLowerCase() == "stksel.ot") {
    PARAM_C = "STKSEL.ot";
}

if (PARAM_C.toLowerCase() == "sjtszx.ot") {
    PARAM_C = "SJTSZX.ot";
}


String.prototype.format = function (args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if (args[key] != undefined) {
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}

String.prototype.substr2 = function (length) {
    var result = this;
		if(length < this.length)
		{
			result = result.substr(0, length);
		}
    return result;
}

String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
}

$(function () {
	var code = PARAM_C.toUpperCase();
	var firstCode = code.substr(0,1);
	var exchCode = code.substr(code.length - 3, 3);

	if(PARAM_HIDETOPBAR == "1")
	{
		$("#header").hide();
		$("#main").css("margin-top","0px");
	}

	if(PARAM_SID.toLowerCase() == "hj020")
	{
		if(PARAM_FID == FUNCTION_F10 && ((exchCode == ".SH" && firstCode == "6") || (exchCode == ".SZ" && (firstCode == "0" || firstCode == "3"))))
		{
			PARAM_CID = -3;
		}
		else
		{
			PARAM_CID = -2;
		}
	}
	
	//判断是否有个股诊断按钮，以及根据客户定制适配CID
	if(PARAM_FID == FUNCTION_F10)
	{
		if((exchCode == ".SH" && firstCode == "6") || (exchCode == ".SZ" && (firstCode == "0" || firstCode == "3")))
		{
			$("#menu .zd_f10").show();
			$("#menulist").css("margin-right","50px");
		}
		else
		{
			$("#menu .zd_f10").hide();
			$("#menulist").css("margin-right","0px");
		}
	}

    var htmlfile = CurrentParsedUrl.attr("file").toLowerCase();
    if (htmlfile == "default.htm" && (PARAM_CID == -1 && PARAM_FID == FUNCTION_F10)) {
        $("#header .logo").bind("click", function () {
            location.href = "home-1.htm?c={0}&cid={1}&fid={2}&istophide={3}&sid={4}".format(PARAM_C, PARAM_CID, PARAM_FID,PARAM_HIDETOPBAR,PARAM_SID);
        });

        Globals.BodyKeyevent();
    }
    else if (htmlfile == "default.htm" && PARAM_FID == FUNCTION_SECU_DIAG) {
        //$("#header .logo").bind("click", function () {
        //   location.href = "home-2.htm?c={0}&cid={1}&fid={2}&istophide={3}".format(PARAM_C, PARAM_CID, PARAM_FID,PARAM_HIDETOPBAR);
        //});
        //Globals.BodyKeyevent();
        
        $("#header .logo").hide();
        $("#header").css("text-align", "center");
        
    }
    else if (htmlfile == "default.htm" && (PARAM_FID == FUNCTION_SECU_SCRN || PARAM_FID == FUNCTION_BD_INFO)) {
        $("#header .logo").hide();
        $("#header .bdcode").hide();
        $("#header").css("text-align", "center");
        $("#header .t1").css("line-height", "40px");
    }
    else if (htmlfile == "default.htm" && PARAM_FID == FUNCTION_F10) {
        $("#header .logo").hide();
        $("#header").css("text-align", "center");
    }

    else {
        $("#header .logo").bind("click", function () {
            location.href = "home-0.htm?c={0}&cid={1}&fid={2}&istophide={3}&sid={4}".format(PARAM_C, PARAM_CID, PARAM_FID,PARAM_HIDETOPBAR,PARAM_SID);
        });
        Globals.BodyKeyevent();

    }

    var title = CurrentParsedUrl.param("title");
    if (title && title != "") {
        $("title").text(title);
    }

    $.ajaxSetup({
        timeout: 60000,
        error: function (xhr, status, e) {
            Globals.Loading(false);
        }
    });

    $(".bdcode").bind("click", function () {
        //$(".bdcode").css("background-color", "#ffffff");
        $(".bdinput").fadeToggle();
        $(".resultlist").fadeToggle();
        $(".bdinput input").focus();
    });

    $("#main").bind("click", function () {
        $(".bdinput").fadeOut();
        $(".resultlist").fadeOut();
    });

    $(".bdinput input").keydown(function (e) {
        if (e.keyCode == 13) {
            if ($(".resultlist ul li").length > 0) {
//                var fix = $(".resultlist ul li:first").attr("val");
//                var typecodei =$(".resultlist ul li:first").attr("exch");
                location.href = "{0}?c={1}&cid={2}&ids={3}&ql={4}&fid={5}&istophide={6}".format(CURRENTFILE, $(".resultlist ul li:first").attr("val"), PARAM_CID, "", PARAM_QL,PARAM_FID,PARAM_HIDETOPBAR);//PARAM_IDS
            }
        }
    });

    $(".bdinput input").bind("input propertychange", function() {
        
        //Globals.Loading(true);
        DataService.GetData(GETBDCODEURL.format($(this).val()), true, "GET", "", function (data) {
            //Globals.Loading(false);
            var template = " <li val=\"{0}\" exch=\"{1}\"><span class=\"trdcode\">{2}</span><span class=\"secusht\">{3}</span><span class=\"icon-arrow-right3\"></span></li>";
            $(".resultlist ul").html("");
            $.each(data,function (i, obj) {
                $(".resultlist ul").append(template.format(obj.BD_CODE.toLowerCase(),obj.TYP_CODEI, obj.TRD_CODE, obj.SECU_SHT));
            });

            $(".resultlist ul li").bind("click", function() {
                
                    location.href = "{0}?c={1}&cid={2}&ids={3}&ql={4}&fid={5}&istophide={6}".format(CURRENTFILE, $(this).attr("val"), PARAM_CID, "", PARAM_QL, PARAM_FID,PARAM_HIDETOPBAR);//PARAM_IDS
            });

        }, "jsonp");

    });

    /*$("#menu").bind("click", function () {
        var alist = $("#menulist").find("a");
        alist.each(function(i,obj){
            var val = $(obj).attr("val");
            if(val == 10){
                var percent = $(".summary").find("span");
                 alert(percent);
            }
        });
    });*/

    
});

var Globals = new function () {
    this.BodyKeyevent = function() {
        $("body").keydown(function (e) {
        //if (e.keyCode >= 48 && e.keyCode <= 57) {
        $(".bdinput").fadeIn();
        $(".resultlist").fadeIn();
        $(".bdinput input").focus();

        //}

        if (e.keyCode == 27) {
            $(".bdinput input").val("");
            $(".bdinput").fadeOut();
            $(".resultlist").fadeOut();
        }
    });
    };
    this.Loading = function (isBusy) {
        var template = "<div id=\"loading\"><img src=\"images/loading.gif\"/></div>";
        if (isBusy) {
            if ($("#loading").length == 0) {
                $("body").append(template);
            } else {
                $("#loading").show();
                $('html, body').animate({
                        scrollTop: 0
                    },
                    1);
            }
        } else {
            if ($("#loading").length > 0) {
                $("#loading").hide();
            }
        }
    };

    this.TryParseResult = function () {
    	    var list = [];
            var max;
            var index;
    		$(".tableshift").each(function(i,o){
                var table = $(o).find("table");
                var a = $(o).find("a");
                var laterly;
                a.each(function(i,obj){
                    laterly =  $(obj).attr("dates");
                    if(laterly != undefined && laterly != '' && laterly != null){
                        list.push(laterly)
                    }
                });
                for (var i=0; i<list.length; i++) {
                    if (0 == i) {
                        max = list[i];
                        index = i;
                    } else {
                        if (list[i] > max) {
                            max = list[i];
                            index = i;
                        }
                    }
                }
                table.hide();
                $(table.get(index)).show();
                $(a.get(index)).addClass("red");
                a.each(function(i, obj){
                        $(obj).bind("click", function(){
                                table.hide();
                                $(table.get(i)).show();
                                a.removeClass("red");
                                $(a.get(i)).addClass("red");
                            });

                });
            });



    	  
        $(".tablesplit tr td").each(function (i, obj) {
            if ($(obj).text().indexOf("合计")!=-1) {
                $(obj).parent().css("font-weight", "bold");
                $(obj).parent().css("background-color", "#e6e6e6");
            }
        });

        //$(".list .footer .recommend").each(function (i, obj) {
        $(".list .recommend").each(function (i, obj) {
            if ($(obj).attr("val") == "10.00") {
                $(obj).css("color", "#E44122");
            }
            if ($(obj).attr("val") == "20.00") {
                $(obj).css("color", "#FE8260");
            }
            if ($(obj).attr("val") == "30.00") {
                $(obj).css("color", "#326EBD");
            }
            if ($(obj).attr("val") == "40.00") {
                $(obj).css("color", "#00A14E");
            }
            if ($(obj).attr("val") == "50.00") {
                $(obj).css("color", "#64D39A");
            }
        });
        //注释掉子栏目名称。
        $(".module .category[val='10'],.module .category[val='122'],.module .category[val='211'],.module .category[val='123'],.module .category[val='1011']").hide();
    };

    this.TryBuildChart = function () {
        var chartobjs = $(".gchart");
        chartobjs.each(function (i, obj) {
            var charttype = $(obj).attr("charttype").toLowerCase();
            var chartdata = $(obj).attr("chartdata").replaceAll("url(/php/images.php?","url({0}/php/images.php?".format(BASEHOST));
            var charttitle = $(obj).attr("charttitle");
            var data = eval("("+ chartdata+")" );
            if (charttitle && charttitle != "") {
                $(obj).before("<div class='charttitle'>{0}</div>".format(charttitle));
            }
            if (charttype == "pie") {
                var pielist = [];
                var size='70%';
                var innerSize='0%';
                if($(obj).attr("colorstyle")=="mnyStyle"){
                    size='80%';
                    innerSize='40%';
                    for (var m = 0; m < data.length; m++){
                        if(data[m][0]=='散户流入'){
                            var myObj =
                            {
                                'name':data[m][0],
                                'y': data[m][1],
                                'color': "#FE8260"
                            };
                            pielist.push(myObj);
                        }else if (data[m][0]=='主力流入'){
                            var myObj =
                            {
                                'name':data[m][0],
                                'y': data[m][1],
                                'color': "#E44122"
                            };
                            pielist.push(myObj);
                        }else if (data[m][0]=='散户流出'){
                            var myObj =
                            {
                                'name':data[m][0],
                                'y': data[m][1],
                                'color': "#64D39A"
                            };
                            pielist.push(myObj);
                        }else if(data[m][0]=='主力流出'){
                            var myObj =
                            {
                                'name':data[m][0],
                                'y': data[m][1],
                                'color': "#00A14E"
                            };
                            pielist.push(myObj);
                        }
                    }
                }else {
                    pielist=data;
                }

                $(obj).highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: ''
                    },
                    tooltip: {
                        enabled: false
                    },
                    plotOptions: {
                        pie: {
                            size:size,
                            innerSize:innerSize,
                            allowPointSelect: false,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                //format: '{point.name}: {point.percentage:.2f}%'
                                format: '{point.percentage:.2f}%'

                            },
                            showInLegend: true,
                            animation: false,
                            point : {
                                events : {
                                    legendItemClick: function() {
                                        return false;
                                    }
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        //data: eval(chartdata)
                        data: pielist
                    }]
                });
            }
            else if (charttype == "column") {
                var color1 = "#4b88d5";
                var color2 = "#feb847";
                var format= '{point.y:.2f}';
                var fontSize='14px';
                var categories=data.categories;
                if ($(obj).attr("colorstyle") == "1") {
                    color1 = "#E9471F";
                    color2 = "#09AB40";
                }
                if ($(obj).attr("colorstyle") == "format0"){
                    format='{point.y}';
                }
                if ($(obj).attr("fontstyle") == "10"){
                    fontSize='10px';
                }
                var list = [];
                //var flowin = 0;
                //var flowout = 0;
                var flow=0;
                if ($(obj).attr("colorstyle") == "2") {
                    format='{point.y}';
                    for (var m = 0; m < data.categories.length; m++) {
                        //买入、增持、中性、减持、卖出
                        if(data.categories[m] == "买入"){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#E44122"
                            };
                            list.push(myObj);
                        }else if(data.categories[m] == "增持"){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#FE8260"
                            };
                            list.push(myObj);
                        }else if(data.categories[m] == "中性"){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#326EBD"
                            };
                            list.push(myObj);
                        }else if(data.categories[m] == "减持"){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#00A14E"
                            };
                            list.push(myObj);
                        }else if(data.categories[m] == "卖出"){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#64D39A"
                            };
                            list.push(myObj);
                        }else {
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': data.datas[m] > 0 ? color1 : color2
                            };
                            list.push(myObj);
                        }
                    }
                }else if($(obj).attr("colorstyle") == "flow"){
                    categories=['大单','中单','小单','大单','中单','小单'];
                    for (var m = 0; m < data.datas.length; m++) {
                        flow  = flow + data.datas[m];
                        if (data.categories[m]=='大单流入'){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#E44122"
                            }
                            list.push(myObj);
                        }else if (data.categories[m]=='中单流入'){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#FD5330"
                            }
                            list.push(myObj);
                        }else if (data.categories[m]=='小单流入'){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#FE8260"
                            }
                            list.push(myObj);
                        }else if (data.categories[m]=='大单流出'){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#00A14E"
                            }
                            list.push(myObj);
                        }else if (data.categories[m]=='中单流出'){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#00C664"
                            }
                            list.push(myObj);
                        }else if (data.categories[m]=='小单流出'){
                            var myObj =
                            {
                                'y': data.datas[m],
                                'color': "#64D39A"
                            }
                            list.push(myObj);
                        }

                    }
                    flow = flow.toFixed(2);
                }else{
                    for (var m = 0; m < data.datas.length; m++) {
                        if (data.datas[m] == "-") {
                            data.datas[m] = 0;
                        }
                        var myObj =
                        {
                            'y': data.datas[m],
                            'color': data.datas[m] > 0 ? color1 : color2
                        };

                        list.push(myObj);
                    }
                }

                $(obj).highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: ''
                    },
                    xAxis: {
                        //categories: data.categories,
                        categories: categories,
                        tickLength: 0,
                        gridLineWidth:0,
                        labels: {
                            enabled: true,
                            //rotation: -50,
                            staggerLines: 1,
                            step: data.categories.length <= 10? 1 : parseInt(data.categories.length / 5),
                            y: 18, // 10 pixels down from the top
                            //x:45,//调节x偏移
                            rotation:0,//调节倾斜角度偏移
                            style: {
                                //fontSize: '11px',
                                fontSize: fontSize,
                                fontFamily: 'Verdana, sans-serif'
                            }
                        }
                    },
                    yAxis: {
                        gridLineWidth:0,
                        title: {
                            enabled: false
                        },
                        labels:{
                            enabled:false
                        }
                    },
                    tooltip: {
                        enabled: false
                        // headerFormat: '<span style="font-size:11px"></span><br>',
                        //pointFormat: '<span style="color:{point.color}"></span>: <b>{point.y:.2f}</b><br/>'
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.1,
                            borderWidth: 0,
                            animation: false
                        },
                        series: {
                            pointPadding: -0.2,//数据点之间的距离值
                            borderWidth: 0,
                            colorByPoint: true,
                            dataLabels: {
                                enabled: true,
                                //rotation: -90,
                                //color: '#FFFFFF',
                                align: 'center',
                                //format: '{point.y:.2f}', // one decimal
                                format:format, // one decimal
                                //y: 10, // 10 pixels down from the top
                                style: {
                                    fontSize: '13px',
                                    fontFamily: 'Verdana, sans-serif'
                                }
                            }
                        }
                    },
                    series: [{
                        name: '',
                        colorByPoint: false,
                        data: list//data.datas
                    }],
                    legend: {
                        enabled: false,
                        //margin:2
                    }
                    /*dataLabels: {
                     enabled: true,
                     rotation: -90,
                     color: '#FFFFFF',
                     align: 'right',
                     format: '{point.y:.1f}', // one decimal
                     y: 10, // 10 pixels down from the top
                     style: {
                     fontSize: '13px',
                     fontFamily: 'Verdana, sans-serif'
                     }
                     }*/
                });
            }
            else if (charttype == "line") {
                var step = parseInt(data.categories.length / 4);
				var series = [];

				if(data.datas[0].name){
					for(var i = 0; i < data.datas.length; i++)
					{
						var dataobj = data.datas[i];
						series.push({"name":dataobj.name,"data":dataobj.data, "marker":{"symbol":'square'}});
					}
				}
				else{
						series.push({"name":'',"data":data.datas, "marker":{"symbol":'square'}});
				}
                $(obj).highcharts({
                    chart: {
                        type: 'spline',
                        animation: false
                    },
                    title: {
                        text: ''
},
                    xAxis: {
                        categories: data.categories,
                        labels: {
                            enabled: true,
                            staggerLines: 1,
                            step: step
                        }
                    },
                    yAxis: {
                        title: {
                            enabled: false
                        }
                        //                        ,
                        //                        labels: {
                        //                            enabled: false
                        //                        }
                    },
                    tooltip: { enabled: false
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                radius: 0,
                                lineColor: '#666666',
                                lineWidth: 1
                            },
                            animation: false
                        }
                    },
                    series: series,
                    colors:['#012257'],
                    legend: {
                        enabled: data.datas[0].name
                    }
                });
            }
            else if (charttype == "stock") {

                $(obj).highcharts("StockChart", {
                    rangeSelector: {
                        enabled: false
                    },
                    chart: {
                        height: 250,
                        panning: false, //禁用放大
                        pinchType: '', //禁用手势操作
                    },
                    title: {
                        text: ''
                    },
                    navigator: { enabled: false
                    },
                    scrollbar: { enabled: false },
                    tooltip: { enabled: false },
                    plotOptions: {
                        column: {
                            animation: false
                        }
                    },
                    yAxis: [{
                        labels: {
                            align: 'left',
                            x: 3
                        },
                        height: '60%',
                        lineWidth: 0
                    }, {
                        labels: {
                            align: 'left',
                            x: 3
                        },
                        title: {
                            enabled: false
                        },
                        top: '61%',
                        height: '35%',
                        offset: 0,
                        lineWidth: 0
                    }],
                    xAxis: {
                        type: 'datetime',
                        labels: {
                            enabled: false,
                            staggerLines: 1//,
                            //step: parseInt(data.volume.length / 10)
                        }
                    },
                    series: [{
                        type: 'candlestick',
                        name: '',
                        data: data.candlestick
                    }
                        , {
                            type: 'column',
                            name: '',
                            data: data.volume,
                            yAxis: 1
                        }
                    ]
                });
                var maxdate = data.candlestick[0][0];
                var middate = data.candlestick[parseInt(data.candlestick.length / 2)][0];
                var middate0 = data.candlestick[parseInt(data.candlestick.length / 4)][0];
                var middate1 = data.candlestick[parseInt(data.candlestick.length / 4) + parseInt(data.candlestick.length / 2)][0];
                var mindate = data.candlestick[data.candlestick.length - 1][0];
                $(obj).append("<table class='datelabel'><tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td></tr></table><div style='clear:both;'></div>"
                    .format(maxdate,middate0, middate,middate1, mindate));
            }
            else if (charttype == "bar") {
                $(obj).highcharts({
                   chart: {
                type: 'bar',height:200
            },
            title: {
                text: ''
            },
            xAxis: {
                categories: data.categories
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                        labels: {
                            enabled: true,
                            staggerLines: 1,
                            step: parseInt(data.categories.length / 3)
                        }
            },
            legend: {
                reversed: true
            },
            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },
                series: data.datas
                });
            }
            else if (charttype == "bar2") {
                var color = $(obj).attr("colorstyle");
                var list = [];

                for (var m = 0; m < data.datas.length; m++) {
                    if (data.datas[m] == "-") {
                        data.datas[m] = 0;
                    }
                    var myObj =
                    {
                        'y': data.datas[m],
                        'color': color
                    };

                    list.push(myObj);
                }

               $(obj).highcharts({
                   chart: {
                       type: 'bar'
                   },
                   title: {
                       text: '',
                   },
                   xAxis: {
                       categories: data.categories,title: {
                           text: ''
                       }
                   },
                   yAxis: {
                       min: 0,
                       title: {
                           text: ''
                       },
                       gridLineWidth: 0,//刻度线
                       labels: {
                           enabled: false,
                           staggerLines: 1,
                           step: parseInt(data.categories.length / 3)
                       }
                   },
                   tooltip: {
                       enabled: false
                   },
                   plotOptions: {
                       bar: {
                           dataLabels: {
                               enabled: true
                           }
                       },
                       series: {
                           pointPadding: -0.1,//数据点之间的距离值
                           //stacking: 'normal'
                       }
                   }, legend: {
                       verticalAlign: 'top',
                       y: -100,
                       floating: false,
                       borderWidth: 0
                   },
                   series:[{
                       name: ' ',
                       data:list//data.datas
                   }]
               });
            }
            else if (charttype == "bar3") {
        $(obj).highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: [{
            categories: data.categories,
                        labels: {
                            enabled: true,
                            staggerLines: 1,
                            step: data.categories.length <= 8? 1 : parseInt(data.categories.length / 3)
                        }
        }],
        yAxis: [{ // Primary yAxis
            title: {
                text: '',
            },
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '',
            },
            labels: {
                format: '{value%}',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            opposite: true
        }],
        tooltip: {
            enabled:false
        },
                    plotOptions: {
                        column: {
                            pointPadding: 0.1,
                            borderWidth: 0,
                            animation: false
                        },spline: {
                            marker: {
                                radius: 0,
                                lineColor: '#666666',
                                lineWidth: 1
                            },
                            animation: false
                        }
                    },
        series: [{
            name: '沽空金额',
            type: 'column',
            yAxis: 1,
            data: data.datas,
            tooltip: {
                enabled:false
            }

        }, {
            name: '沽空占股票成交额比',
            type: 'spline',
            data: data.datas_1,
            tooltip: {
                enabled:false
            }
        }]
    });
            }
        });

        $(".ratebar").each(function (i, obj) {
            $(obj).find("span").each(function (m, o) {
                $(o).css("background", "url(/php/images.php?rc=" + $(o).attr("val") + "&num=0) left -14px no-repeat");
            });
        });
    };

};
