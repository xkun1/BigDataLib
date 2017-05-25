var INITIALIZEDMENU = false;
var ALLMENUS;
var FIRSTMENUS;
var CURRENTSECONDMENUS;
var TIMESTAMP;
var CURRENTMENUID;

$(function () {
    var showql = CurrentParsedUrl.param("ql");
    if (!showql) showql = 0;

    CURRENTMENUID = CurrentParsedUrl.param("mid");
    if (!CURRENTMENUID) CURRENTMENUID = DEFAULTF10MENUID;

    if (showql == 1) {
        $("#quicklaunch").removeAttr("hidden");
        $("#main").css("margin-right", "25px");
    }

    if (CURRENTMENUID == -1) {
        $("#menu .about").removeClass("default");
        $("#menu .about").addClass("sd");
        $("#menu .about").attr("href", "default.htm?c={0}&mid=-2".format(PARAM_C));
        $("#menu .about").attr("target", "_self");
    }
    else if (CURRENTMENUID == DEFAULTSDMENUID) {
        $("#menu .about").removeClass("default");
        $("#menu .about").addClass("f10");
        //$("#menu .about").text("F10");
        $("#menu .about").attr("href", "default.htm?c={0}&mid=-1".format(PARAM_C));
        $("#menu .about").attr("target", "_self");
    }

    GetData(CurrentParsedUrl.param("ids"));

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
        if (stamp - TIMESTAMP <= 1000) return;
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
});

function GetData(id) {
    if (!id) id = "";
    Globals.Loading(true);
    DataService.GetData(GETDATAURL.format(PARAM_C, id, INITIALIZEDMENU ? 1 : 0, CURRENTMENUID), true, "GET", "", function (data) {
        Generate(data, id);
    }, "jsonp");
}

function Generate(data, id) {
    $("#main .module").not("#moduletemplate").remove();
    $("#quicklaunch a").remove();
    if (!INITIALIZEDMENU) {
        ALLMENUS = data.classes;
        //main menus
        FIRSTMENUS = $.grep(ALLMENUS, function (val, key) {
            if (val.pid == 0 && val.on_menu != "false")
                return true;
        }, false);

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

        SetToCenter($("#menulist .selected"));

        if (data.secu) {
            $("#header .t1").text("{0}({1})".format(data.secu.SECU_SHT, data.secu.TRD_CODE));
        } else if (PARAM_C == "STKSEL.sh") {
            $("#header .t1").text("智能选股");
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
                if (CurrentParsedUrl.param("ql") == 1) {
                    $("#quicklaunch").show();
                    $("#main").css("margin-right", "25px");
                }
            }

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

        var category = $(this).parent();
        if (detail.attr("loadtype") == "async") {
            var id = detail.attr("id");
            if (id != '-' && id != '') {
                Globals.Loading(true);
                DataService.GetData(GETDETAILINFOURL.format("BD_F10_ANN_TXT_1", parseInt(id), "CONT"), true, "GET", "", function(data) {
                    detail.find(".newscontent .mainbody").html(data.CONT);
                    Globals.Loading(false);
                    $("html,body").animate({ scrollTop: category.offset().top - 80 }, 10);

                }, "jsonp");
            }
        }

        $("html,body").animate({ scrollTop: category.offset().top - 80 }, 10);

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