var INITIALIZEDMENU = false;
var ALLMENUS;
var FIRSTMENUS;
var CURRENTSECONDMENUS;
var TIMESTAMP;

$(function () {

	if(DEFAULTCOUNT > 0) SECONDLOADED = false;

    if (PARAM_QL == 1) {
        $("#quicklaunch").removeAttr("hidden");
        $("#main").css("margin-right", "25px");
    }

    if (PARAM_FID == FUNCTION_F10) {
        $("#menu .about").removeClass("default");
        $("#menu .about").addClass("sd");
        $("#menu .about").attr("href", "default.htm?c={0}&fid={1}&istophide={2}&cid={3}&sid={4}".format(PARAM_C, FUNCTION_SECU_DIAG,PARAM_HIDETOPBAR,PARAM_CID,PARAM_SID));
        $("#menu .about").attr("target", "_self");

		if(PARAM_SID.toUpperCase() == "GG004")
		{
			$("#menu .about").attr("href", "https://robot.gf.com.cn/#/diagnosis?exchange={0}&code={1}&from=f10".format(PARAM_TRADEEXCHANGE,PARAM_TRADECODE));
		}
    }
    else if (PARAM_FID == FUNCTION_SECU_DIAG) {
        $("#menu .about").removeClass("default");
        $("#menu .about").addClass("f10");

        $("#menu .about").attr("href", "default.htm?c={0}&cid={1}&fid={2}&istophide={3}&sid={4}".format(PARAM_C, PARAM_CID,FUNCTION_F10,PARAM_HIDETOPBAR,PARAM_SID));
        $("#menu .about").attr("target", "_self");
    }

    GetData(CurrentParsedUrl.param("ids"), DEFAULTCOUNT);

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
});


function GetData(id,count) {
    if (!id) id = "";
//    Globals.Loading(true);
	var url = GETDATAURL.format(PARAM_C, id, INITIALIZEDMENU ? 1 : 0, PARAM_CID, PARAM_FID);
	if(count && count > 0)
	{
		url = url + "&count={0}".format(count);
	}
    DataService.GetData(url, true, "GET", "", function (data) {
        Generate(data, id);
    }, "jsonp");
}

function Generate(data, id) {
	$("#main .summary").remove();
    $("#main .module").not("#moduletemplate").remove();
    $("#quicklaunch a").remove();
    if (!INITIALIZEDMENU) {
        ALLMENUS = data.classes;
        //main menus
        FIRSTMENUS = $.grep(ALLMENUS, function (val, key) {
            if (val.pid == 0 && val.on_menu != "false")
                return true;
        }, false);
        
        if(FIRSTMENUS.length == 1){
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
        if(menulistheight<=30){
            $("#menulist").animate({ padding: 5+"px"+" "+0});
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
		window.onscroll = function(){ 
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
        }else
		{
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

    $("#quicklaunch a").bind("click", function () {
        var category = $(".category[val={0}]".format($(this).attr("val")));
        $("html,body").animate({ scrollTop: category.offset().top - 70 }, 600, function () {
            var current = category.css("background-color");

            category.parent().animate({ backgroundColor: "#e8e8e8" }, 100).animate({ backgroundColor: current }, 1000);
        });
    });

    $(".list .item").bind("click", function () {
        var detail = $(this).next();
        detail.show();
        $(".list .item").hide();
        $(".list .sketch").hide();
        $(".list .footer").hide();

		$(".newstitlearea a").attr("href",ATTACHMENT.format(detail.attr("filename")));
		$(".newstitlearea a").text("打开附件文件");
		
		if(detail.attr("checkname") == "1")
		{
				$(".newstitlearea a").attr("href",ATTACHMENT_REPORT.format(detail.attr("filename")));
				
						if(detail.find(".newstitlearea").text().indexOf("江苏中民金服资产管理股份有限公司") == -1)
					  {
							$(".newstitlearea a").text("");
						}
		}

        var category = $(this).parent();
        if (detail.attr("loadtype") == "async") {
            var id = detail.attr("id");
            if (id != '-' && id != '') {
                Globals.Loading(true);
                DataService.GetData(GETDETAILINFOURL.format(DEFAULTDETAILDATABASE, parseInt(id), "CONT"), true, "GET", "", function (data) {
                    detail.find(".newscontent .mainbody").html(data.CONT);
                    Globals.Loading(false);
                    $("html,body").animate({ scrollTop: category.offset().top - 80 }, 10);

                }, "jsonp");
            }
        }

        $("html,body").animate({ scrollTop: category.offset().top - 80 }, 10);

    });
    $(".showorhide").bind("click",function(){
        var showorhide=$(".showorhide");
        var writing=showorhide.text();
        if(writing=="展示全部"){
            showorhide.text("收起");
            $(this).width("40px");
        }else {
            showorhide.text("展示全部");
            $("#izl_rmenu .btn-top").click();
            $(this).width("68px");
        }
        var comtable=showorhide.parent().parent().find(".commontable");
        comtable.each(function(){
            $(this).find(".signhide").toggleClass("hide");
        });
    });
    
    $(".eventstitle").bind("click", function () {
        $(this).parent().parent().next().show();
        $(".eventsitem").hide();
        $("html,body").animate({ scrollTop: $(this).parent().parent().parent().offset().top - 80 }, 10);
    });

    $(".detail .close").bind("click", function () {
        //$(this).parent().hide();
        $(".detail").hide();
        $(".list .item").show();
        $(".list .sketch").show();
        $(".list .footer").show();
        $(".eventsitem").show();
    });
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
    $("#menulist").animate({ left: val * -1 + "px" }, 1, function () {
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