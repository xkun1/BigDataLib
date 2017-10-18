var INITIALIZEDMENU = false;
var ALLMENUS = null;
var FIRSTMENUS;
var CURRENTSECONDMENUS;
var TIMESTAMP;
var getnumbers = 0; //接口请求次数
var storageTimes = 1000 * 60 * 20; //缓存生存时间

function defaultOnload() {
    if (DEFAULTCOUNT > 0) SECONDLOADED = false;

    if (PARAM_QL == 1) {
        $("#quicklaunch").removeAttr("hidden");
        $("#main").css("margin-right", "25px");
    }

    if (PARAM_FID == FUNCTION_F10) {
        $("#menu .about").removeClass("default");
        $("#menu .about").addClass("sd");
        $("#menu .about").attr("href", "default.htm?c={0}&fid={1}&istophide={2}&cid={3}&sid={4}".format(PARAM_C, FUNCTION_SECU_DIAG, PARAM_HIDETOPBAR, PARAM_CID, PARAM_SID));
        $("#menu .about").attr("target", "_self");

        if (PARAM_SID.toUpperCase() == "GG004") {
            $("#menu .about").attr("href", "https://robot.gf.com.cn/#/diagnosis?exchange={0}&code={1}&from=f10".format(PARAM_TRADEEXCHANGE, PARAM_TRADECODE));
        }
    } else if (PARAM_FID == FUNCTION_SECU_DIAG) {
        $("#menu .about").removeClass("default");
        $("#menu .about").addClass("f10");

        $("#menu .about").attr("href", "default.htm?c={0}&cid={1}&fid={2}&istophide={3}&sid={4}".format(PARAM_C, PARAM_CID, FUNCTION_F10, PARAM_HIDETOPBAR, PARAM_SID));
        $("#menu .about").attr("target", "_self");
    }

    GetData(PARAM_IDS, DEFAULTCOUNT);


    /*
     $("#menu").on("drag", function (e) {
     var stamp = new Date().getTime();
     if (stamp - TIMESTAMP <= 1000) return;
     if (e.orientation == "horizontal") {
     if (e.direction == 1) {
     $("#menu .leftbutton").click();
     }
     else if (e.direction == -1) {
     $("#menu .rightbutton").click();
     }

     e.preventDefault();
     TIMESTAMP = new Date().getTime();
     }
     });

     $("#main").on("drag", function (e) {
     var stamp = new Date().getTime();
     if (stamp - TIMESTAMP <= 2000) return;
     //        $("#main").animate({ opacity: 0.1 }, "normal", function () {
     //            $("#main").animate({ opacity: 1 });
     //        });
     if (e.orientation == "horizontal") {
     if (e.direction == 1) {

     var prevs = $("#menu .selected").prev().not(".about");
     if (prevs.length > 0) {
     prevs.click();
     } else {
     //history.go(-1);
     }
     }
     else if (e.direction == -1) {
     var nexts = $("#menu .selected").next().not(".about");
     if (nexts.length > 0) {
     nexts.click();
     } else {
     //history.go(1);
     }
     }

     e.preventDefault();
     TIMESTAMP = new Date().getTime();
     }
     });


     $("#menu .leftbutton").bind("click", function () {
     if ($("#menu").width() - $("#menulist").width() >= 70) {
     return;
     }

     var left = parseInt($("#menulist").css("left").replace("px"));
     var tick = 200;
     var val = 0;
     if (left <= tick * -1) {
     val = left + tick;
     }
     else {
     val = 0;
     $("#menu .leftbutton").hide();
     }
     $("#menulist").animate({ left: val + "px" });

     if ($("#menu").width() - $("#menulist").width() < 50) {
     $("#menu .rightbutton").show();
     }
     });

     $("#menu .rightbutton").bind("click", function () {
     if ($("#menu").width() - $("#menulist").width() >= 70) {
     return;
     }

     $("#menu .leftbutton").show();
     var left = parseInt($("#menulist").css("left").replace("px"));
     var tick = 200;
     var width = $("#menulist").width();
     var right = left + width;
     var parentright = $("#menu").width() - 50;
     var offset = right - parentright;
     var val = 0;

     if (offset >= tick) {
     val = left - tick;
     }
     else {
     val = left - offset;
     $("#menu .rightbutton").hide();
     }
     $("#menulist").animate({ left: val + "px" });

     //$("#menulist").css("left", val + "px");
     });
     */
}
$(function () {

});

function GetData(id, count) {
    var cont = null;
    if (!id) {
        var currentMenu = $.cookie('currentMenu');
        if (currentMenu) {
            id = currentMenu;
        } else {
            id = "";
        }
    }
    //获取缓存
    if (typeof(Storage) !== "undefined") {
        cont = JSON.parse(localStorage.getItem(PARAM_C + PARAM_CID + PARAM_FID + id));
    }
    if (cont != null && cont != "" && new Date().getTime() - cont.time < storageTimes) {
        getnumbers = 0;
        Generate(cont.data, id);
    } else {
        Globals.Loading(true);
        var url = GETDATAURL.format(PARAM_C, id, INITIALIZEDMENU ? 1 : 0, PARAM_CID, PARAM_FID);
        if (count && count > 0) {
            url = url + "&count={0}".format(count);
        }
        if (params != null && params != "") {
            url = url + "&appVer={0}".format(params.appVer) + "&appType={0}".format(params.appType) + "&deviceID={0}".format(params.deviceID)+ "&phoneNum={0}".format(params.phoneNum);
        }
        DataService.GetData(url, true, "GET", "", function (data) {
            if (data.hasOwnProperty("error")) {
                if (getnumbers < 10) {
                    urlEach();//地址重定义
                    GetData(id, count);
                    getnumbers++;
                } else {
                    $("body").append(data.error);
                    Globals.Loading(false);
                }
            } else {
                getnumbers = 0;
                Generate(data, id);
                //生成本地缓存
                if (typeof(Storage) !== "undefined") {
                    var curTime = new Date().getTime();
                    localStorage.setItem(PARAM_C + PARAM_CID + PARAM_FID + id, JSON.stringify({
                        data: data,
                        time: curTime
                    }));
                    if (data.hasOwnProperty("classes")) {
                        localStorage.setItem(PARAM_C + PARAM_CID + PARAM_FID + "classes", JSON.stringify(data.classes));
                    }
                }
            }

        }, "jsonp");
    }
}

function Generate(data, id) {
    $("#main .summary").remove();
    $("#main .module").not("#moduletemplate").remove();
    $("#quicklaunch a").remove();
    if (typeof(Storage) !== "undefined") {
        ALLMENUS = JSON.parse(localStorage.getItem(PARAM_C + PARAM_CID + PARAM_FID + "classes"));
    }
    if (!INITIALIZEDMENU) {
        if (ALLMENUS == null) {
            ALLMENUS = data.classes;
        }
        //main menus
        FIRSTMENUS = $.grep(ALLMENUS, function (val, key) {
            if (val.pid == 0 && val.on_menu != "false")
                return true;
        }, false);

        if (FIRSTMENUS.length == 1) {
            $("#menu #menulist").hide();
        }
        //MainMenu
        $.each(FIRSTMENUS, function (i, obj) {
            if (id != "") {
                if (obj.id == id) {
                    $("#menulist").append("<a href=\"#\" val=\"{1}\" ind=\"{2}\" class=\"selected\">{0}</a>".format(obj.caption, obj.id, i));
                } else {
                    $("#menulist").append("<a href=\"#\" val=\"{1}\" ind=\"{2}\">{0}</a>".format(obj.caption, obj.id, i));
                }
            } else {
                if (i == 0) {
                    $("#menulist").append("<a href=\"#\" val=\"{1}\" ind=\"{2}\" class=\"selected\">{0}</a>".format(obj.caption, obj.id, i));
                } else {
                    $("#menulist").append("<a href=\"#\" val=\"{1}\" ind=\"{2}\">{0}</a>".format(obj.caption, obj.id, i));
                }
            }
        });
        //诊股、F10-单行时调整样式
        var menulistheight = $("#menulist").height();
        if (menulistheight <= 30) {
            $("#menulist").animate({padding: 5 + "px" + " " + 0});
        }

        SetToCenter($("#menulist .selected"));

        if (data.secu) {
            $("#header .t1").html("{0}<span>{1}</span>".format(data.secu.SECU_SHT, data.secu.TRD_CODE));
        } else if (PARAM_C.toUpperCase() == "STKSEL.OT") {
            $("#header .t1").text("智能选股");
        } else if (PARAM_C.toUpperCase() == "SJTSZX.OT") {
            $("#header .t1").text("特色资讯");
        }
        //bind menu event
        $("#menulist a").not(".about").bind("click", function () {
            $("#menulist a").removeClass("selected");
            $(this).addClass("selected");
            //$(this).focus();
            //$(this).blur();
            GetData($(this).attr("val"));
            SetToCenter(this);
            $.cookie('currentMenu', $(this).attr("val"));

        });

        if ($("#menu").width() - $("#menulist").width() < 35) {
            $("#menu .rightbutton").show();
        }

        INITIALIZEDMENU = true;
    }

    CURRENTSECONDMENUS = $.grep(ALLMENUS, function (val, key) {
        if ((id == "" && val.pid == FIRSTMENUS[0].id) || val.pid == id)
            return true;
    }, false);

    if (CURRENTSECONDMENUS.length > 0) {
        window.onscroll = function () {
            //var t = document.documentElement.scrollTop || document.body.scrollTop;
            //if(!SECONDLOADED && t > 120)
            //{
            //SECONDLOADED = true;
            //GetData(CurrentParsedUrl.param("ids"));
            //}
        }

        $.each(CURRENTSECONDMENUS, function (i, obj) {
            var template = $("#moduletemplate").clone();
            var content = "<div class='summary'>近期无披露数据！</div>";
            template.removeAttr("id");
            template.find(".category").text(obj.caption);
            template.find(".category").attr("val", obj.id);
            if (i == CURRENTSECONDMENUS.length - 1) {
                template.css("background-image", "none");
            }
            if (obj.isShowTitle) {
                template.find(".category").hide();
            }

            if (data.contents) {
                for (var m = 0; m < data.contents.length; m++) {
                    var cobj = data.contents[m];

                    if (cobj.classid == obj.id) {
                        if (cobj.content && cobj.content.length > 3) {
                            content = cobj.content;
                        }
                        break;
                    }
                }
            }

            if (CURRENTSECONDMENUS.length == 1) {
                $("#quicklaunch").hide();
                $("#main").css("margin-right", "0px");

                template.find(".category").hide();
            } else {
                if (PARAM_QL == 1) {
                    $("#quicklaunch").show();
                    $("#main").css("margin-right", "25px");
                }
            }
            //alert(content);
            template.find(".content").html(content);
            $("#main").append(template);

            if (obj.on_menu != "false") {
                $("#quicklaunch").append("<a val=\"{1}\">{0}</a>".format(obj.caption, obj.id));
            }
        });
    } else {
        var currentCategory = $.grep(FIRSTMENUS, function (val, key) {
            if ((id == "" && val.id == FIRSTMENUS[0].id) || val.id == id)
                return true;
        }, false);

        if (currentCategory.length > 0) {
            var obj = currentCategory[0];
            var template = $("#moduletemplate").clone();
            var content = "<div class='summary'>近期无披露数据！</div>";
            template.removeAttr("id");
            template.find(".category").text(obj.caption);
            template.find(".category").attr("val", obj.id);
            template.css("background-image", "none");
            if (obj.isShowTitle) {
                template.find(".category").hide();
            }

            if (data.contents) {
                for (var m = 0; m < data.contents.length; m++) {
                    var cobj = data.contents[m];

                    if (cobj.classid == obj.id) {
                        if (cobj.content && cobj.content.length > 10) {
                            content = cobj.content;
                        }
                        break;
                    }
                }
            }
            template.find(".category").hide();
            template.find(".content").html(content);
            $("#main").append(template);
        } else {
            $("#main").append("<div class='summary'>近期没有该代码披露数据！</div>");
        }
    }

    try {
        Globals.TryBuildChart();
    } catch (e) {

    }

    try {
        Globals.TryParseResult();
    } catch (e) {

    }

    $(".list .item").bind("click", function () {
        var id = $(this).attr("id");
        var type = $(this).attr("type");
        if (id != '-' && id != '') {
            if (params != "" && params != null && params.hasOwnProperty("deviceID")) {
                var shareUrl = "/detail.html?id=" + id;
                if (type != null && type != undefined && type != "") {
                    shareUrl += "&type=" + type;
                }
                openThirdPartyWebInSubPage(GETDETAILINFOURL.format(DEFAULTDETAILDATABASE, parseInt(Math.round(id)), "CONT,TIT,INFO_SOUR,FILENAME,ID,PUB_DT,DETAIL_ID"), shareUrl, type)
            } else {
                $("#newpage").show();
                $("#newpage").siblings().hide();
                Globals.Loading(true);
                DataService.GetData(GETDETAILINFOURL.format(DEFAULTDETAILDATABASE, parseInt(Math.round(id)), "CONT,TIT,INFO_SOUR,FILENAME,ID,PUB_DT,DETAIL_ID"), true, "GET", "", function (data) {
                    if (data.hasOwnProperty("error")) {
                        if (getnumbers < 10) {
                            getnumbers++;
                            urlEach();//地址重定义
                            $(this).click();
                        } else {
                            $("body").append(data.error);
                            Globals.Loading(false);
                        }
                    } else {
                        getnumbers = 0;

                        if (data.TIT != null && data.TIT != "") {
                            $("#newpage h1").html(data.TIT);
                        }
                        if (data.PUB_DT != null) {
                            var html_text = "";
                            if (data.PUB_DT.sec != null && data.PUB_DT.sec != "") {
                                var unixTimestamp = new Date(data.PUB_DT.sec * 1000);
                                html_text += unixTimestamp.toLocaleString();
                            }
                            if (data.INFO_SOUR != null && data.INFO_SOUR != "") {
                                html_text += "　" + data.INFO_SOUR;
                            }
                            $("#newpage .time").html(html_text);
                        }
                        if (data.CONT != null && data.CONT != "") {
                            var cont = data.CONT.replace(/(\r\n)|(\n)/g, "<br>").replace(/\s/g, "&nbsp;");
                            $("#newpage .article").html(cont);
                        }
                        if (data.FILENAME != null && data.FILENAME != "") {
                            if (type == "blt") {
                                $("#newpage .time").append("　 <a href='" + ATTACHMENT.format(data.FILENAME) + "'>打开附件文件</a>");
                            } else if (type == "rpt") {
                                $("#newpage .time").append("　 <a href='" + ATTACHMENT_REPORT.format(data.FILENAME) + "'>打开附件文件</a>");
                            }
                        }
                    }
                    Globals.Loading(false);
                }, "jsonp");
            }

        }

    });
    if (params != "" && params != null && params.hasOwnProperty("appType")) {
        //Android/iOS客户端类型判断
        if (params.appType == "android") {
            KDS_Native.setCurrentWebPageHeight($("body").height());
        } else if (params.appType == "ios") {
            location.href = "KDS_Native://setCurrentWebPageHeight:" + $("body").height();
        }

    }
    Globals.Loading(false);

}

function SetToCenter(obj) {
    if ($("#menu").width() - $("#menulist").width() >= 70) {
        return;
    }
    var left = parseInt($("#menulist").css("left").replace("px"));
    var currentind = $(obj).attr("ind");
    var objleft = ($(obj).width() + 10) / 2;
    var val = 0;
    $("#menulist a").each(function (i, obj2) {
        if (i < currentind) {
            objleft += $(obj2).width() + 13;
        }
    });

    val = objleft - ($("#menu").width() / 2);

    //$("#menulist").css("left", val * -1 + "px");
    $("#menulist").animate({left: val * -1 + "px"}, 1, function () {
        left = parseInt($("#menulist").css("left").replace("px"));
        if (left >= 0) {
            // $("#menulist").animate({ left: 0 + "px" });
            $("#menulist").css("left", 0 + "px");
            $("#menu .leftbutton").hide();
        } else {
            $("#menu .leftbutton").show();
        }

        var right = parseInt($("#menulist").css("left").replace("px")) + $("#menulist").width();
        if (right > $("#menu").width() - 50) {
            $("#menu .rightbutton").show();
        } else {
            //$("#menulist").animate({ left: ($("#menu").width() - 50) - $("#menulist").width() + "px" });
            $("#menulist").css("left", ($("#menu").width() - 50) - $("#menulist").width() + "px");
            $("#menu .rightbutton").hide();
        }
    });

    //$("#bottomarea").text("right:{0}   menuwidth:{1}".format(right, $("#menu").width()));
}