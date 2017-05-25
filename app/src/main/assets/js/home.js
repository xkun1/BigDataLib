$(function () {
    var htmlfile = CurrentParsedUrl.attr("file").toLowerCase();
    if (htmlfile == "home-1.htm") {
        var url = "default.htm?c={0}&fid=f10&cid={1}&ids={2}&istophide={3}".format(PARAM_C, PARAM_CID, "{0}",PARAM_HIDETOPBAR);
        $("#main .navigation a").each(function (i, obj) {
            if ($(obj).attr("val")) {
                $(obj).attr("href", url.format($(obj).attr("val")));
            }
        });
    }
    else if (htmlfile == "home-0.htm") {
        $("#main .navigation_2 a").each(function (i, obj) {
            $(obj).attr("href", $(obj).attr("href").format(PARAM_C));
        });
    }
    else if (htmlfile == "home-2.htm") {
        $("#main .navigation_2 a").each(function (i, obj) {
            $(obj).attr("href", $(obj).attr("href").format(PARAM_C, PARAM_CID));
        });
    }

    if ($("#header .auto").length > 0) {
        DataService.GetData(GETSECUINFOURL.format(PARAM_C), true, "GET", "", function (data) {
            Generate(data);
        }, "jsonp");
    }

});

function Generate(data) {
    $("#header .t1").html("{0}<br /><span>{1}</span>".format(data.secu.SECU_SHT, data.secu.TRD_CODE));
}