$(function () {
    Globals.Loading(true);
    var c = CurrentParsedUrl.param("c");
    location.href = "home-2.htm?c={0}&fid=secu_diag".format(c);
});

function Generate(data, id) {
    var content = "";

    var secondmenus = $.grep(data.classes, function (val, key) {
        if (val.pid == id)
            return true;
    }, false);

    if (data.contents) {
        if (secondmenus.length > 0) {
            for (var m = 0; m < data.contents.length; m++) {
                var cobj = data.contents[m];

                if (cobj.classid == secondmenus[0].id) {
                    content = cobj.content;
                    break;
                }
            }
        } else {
            for (var m = 0; m < data.contents.length; m++) {
                var cobj = data.contents[m];

                if (cobj.classid == id) {
                    content = cobj.content;
                    break;
                }
            }
        }
    }

    if (content != "") {
        $("#subcontent").html(content);
    }

    try {
        Globals.TryBuildChart();
    } catch (e) {

    }
    
    Globals.Loading(false);
}