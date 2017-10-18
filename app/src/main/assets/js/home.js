$(function () {
    var htmlfile = CurrentParsedUrl.attr("file").toLowerCase();
    if (htmlfile == "home-2.htm") {
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