var CurrentParsedUrl = $.url();
var BASEURL = CurrentParsedUrl.attr("base");
var CURRENTFILE = CurrentParsedUrl.attr("file");
var CURRENTDIR = CurrentParsedUrl.attr("directory");
var GETDATAURL = CURRENTDIR + "php/get_f10.php?c={0}&ids={1}&nomenu={2}&mid={3}";
//var GETDATAURL = "http://f10.chinabigdata.com/php/get_f10.php?c={0}&ids={1}&nomenu={2}&mid={3}";
var GETSECUINFOURL = CURRENTDIR + "php/get_secu.php?c={0}";
//var GETSECUINFOURL = "http://f10.chinabigdata.com/php/get_secu.php?c={0}";
var GETDETAILINFOURL = CURRENTDIR + "php/get_rec.php?c={0}&id={1}&p={2}";
//var GETDETAILINFOURL = "http://f10.chinabigdata.com/php/get_rec.php?c={0}&id={1}&p={2}";
var GETBDCODEURL = CURRENTDIR + "php/get_secu_by_code.php?c={0}";
var DEFAULTF10MENUID = 0;
var DEFAULTSDMENUID = -2;
var PARAM_C = CurrentParsedUrl.param("c");
var PARAM_MID = CurrentParsedUrl.param("mid");
var PARAM_IDS = CurrentParsedUrl.param("ids") == null ? "" : CurrentParsedUrl.param("ids");
var PARAM_QL = CurrentParsedUrl.param("ql") == null ? "" : CurrentParsedUrl.param("ql");

if (PARAM_C.toLowerCase() == "stksel.sh") {
    PARAM_C = "STKSEL.sh";
}

$(function () {
    var htmlfile = CurrentParsedUrl.attr("file").toLowerCase();
    var mid = CurrentParsedUrl.param("mid");
    if (htmlfile == "default.htm" && (mid == -1)) {
        $("#header .logo").bind("click", function () {
            location.href = "home-1.htm?c={0}&mid={1}".format(PARAM_C, PARAM_MID);
        });
    }
    else if (htmlfile == "default.htm" && mid == -2) {
        $("#header .logo").bind("click", function () {
            location.href = "home-2.htm?c={0}&mid={1}".format(PARAM_C, PARAM_MID);
        });
    }
    else if (htmlfile == "default.htm" && mid == -3) {
        $("#header .logo").hide();
        $("#header").css("text-align", "center");
    }
    else {
        $("#header .logo").bind("click", function () {
            location.href = "home-0.htm?c={0}&mid={1}".format(PARAM_C, PARAM_MID);
        });
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
                location.href = "{4}?c={0}&mid={1}&ids={2}&ql={3}".format($(".resultlist ul li:first").attr("val"), PARAM_MID, PARAM_IDS, PARAM_QL, CURRENTFILE);
            }
        }
    });

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

    $(".bdinput input").bind("input propertychange", function() {
        
        //Globals.Loading(true);
        DataService.GetData(GETBDCODEURL.format($(this).val()), true, "GET", "", function (data) {
            //Globals.Loading(false);
            var template = " <li val=\"{0}\"><span class=\"trdcode\">{1}</span><span class=\"secusht\">{2}</span><span class=\"icon-arrow-right3\"></span></li>";
            $(".resultlist ul").html("");
            $.each(data,function (i, obj) {
                $(".resultlist ul").append(template.format(obj.BD_CODE.toLowerCase(), obj.TRD_CODE, obj.SECU_SHT));
            });

            $(".resultlist ul li").bind("click", function() {
                location.href = "{4}?c={0}&mid={1}&ids={2}&ql={3}".format($(this).attr("val"), PARAM_MID, PARAM_IDS, PARAM_QL, CURRENTFILE);
            });

        }, "jsonp");

    });
    
});

var Globals = new function () {
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
        $(".tablesplit tr td").each(function (i, obj) {
            if ($(obj).text() == "合计") {
                $(obj).parent().css("font-weight", "bold");
                $(obj).parent().css("background-color", "#e6e6e6");
            }
        });

        $(".list .item .recommend").each(function (i, obj) {
            if ($(obj).attr("val") == "10.00") {
                $(obj).css("color", "red");
            }
            if ($(obj).attr("val") == "20.00") {
                $(obj).css("color", "#FFC000");
            }
            if ($(obj).attr("val") == "30.00") {
                $(obj).css("color", "#0070c0");
            }
            if ($(obj).attr("val") == "40.00") {
                $(obj).css("color", "#4f6228");
            }
            if ($(obj).attr("val") == "50.00") {
                $(obj).css("color", "#00b050");
            }
        });

        $(".module .category[val='10']").hide();
    };

    this.TryBuildChart = function () {
        var chartobjs = $(".gchart");
        chartobjs.each(function (i, obj) {
            var charttype = $(obj).attr("charttype").toLowerCase();
            var chartdata = $(obj).attr("chartdata");
            var charttitle = $(obj).attr("charttitle");
            var data = eval('(' + chartdata + ')');

            if (charttitle && charttitle != "") {
                $(obj).before("<div class='charttitle'>{0}</div>".format(charttitle));
            }

            if (charttype == "pie") {


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
                            allowPointSelect: false,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false,
                                //format: '{point.percentage}'

                            },
                            showInLegend: true,
                            animation: false
                        }
                    },
                    series: [{
                        type: 'pie',
                        data: eval(chartdata)
                    }]
                });
            }
            else if (charttype == "column") {
                var color1 = "#4b88d5";
                var color2 = "#feb847";
                if ($(obj).attr("colorstyle") == "1") {
                    color1 = "#f26c6c";
                    color2 = "#00FF2A";
                }
                var list = [];

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


                $(obj).highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: ''
                    },
                    xAxis: {
                        categories: data.categories,
                        labels: {
                            enabled: true,
                            staggerLines: 1,
                            step: data.categories.length <= 5? 1 : parseInt(data.categories.length / 2.3)
                        }
                    },
                    yAxis: {
                        title: {
                            enabled: false
                        }
                    },
                    tooltip: { enabled: false
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.1,
                            borderWidth: 0,
                            animation: false
                        }
                    },
                    series: [{
                        name: '',
                        data: list//data.datas
                    }],
                    legend: {
                        enabled: false
                    }
                });
            }
            else if (charttype == "line") {
                var step = parseInt(data.categories.length / 4);

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
                    series: [{
                        name: '',
                        marker: {
                            symbol: 'square'
                        },
                        data: data.datas
                    }],
                    legend: {
                        enabled: false
                    }
                });
            }
            else if (charttype == "stock") {

                $(obj).highcharts("StockChart", {
                    rangeSelector: {
                        enabled: false
                    },
                    chart: {
                        height: 250
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
            }else if (charttype == "bar") {
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

        });

        $(".ratebar").each(function (i, obj) {
            $(obj).find("span").each(function (m, o) {
                $(o).css("background", "url(/php/images.php?rc=" + $(o).attr("val") + "&num=0) left -14px no-repeat");
            });
        });
    };

};

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