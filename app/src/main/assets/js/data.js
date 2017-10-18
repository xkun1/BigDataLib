$(function() {
    $.ajaxSetup({
        timeout: 10000,
        dataType: 'json',
        success: function(data) {
        },
        error: function(e) {
        }
    });
});


DataService = new function () {
    this.GetData = function () {
        var url, isasync, submittype, data, succeeded, datatype;
        url = "";
        isasync = false;
        submittype = "get";
        data = "{}";
        succeeded = null;
        datatype = "text";

		try
		{
			_hmt.push(['_trackEvent', 'DataService', 'GetData', url]);
		}
		catch (e)
		{
		}

        if (arguments.length == 0) {
            alert("Arguments error!");
        }
        else {
            url = arguments[0];
            if (arguments.length >= 2) {
                isasync = arguments[1];
            }
            
            if (arguments.length >= 3) {
                submittype = arguments[2];
            }

            if (arguments.length >= 4) {
                data = arguments[3];
                if (data == '') data = "{}";
            }

            if (arguments.length >= 5) {
                succeeded = arguments[4];
            }

            if (arguments.length >= 6) {
                datatype = arguments[5];
            }
        }
        //alert(url);
        if (isasync) {
            $.ajax({
                type: submittype,
                url: url,
                data: data,
                dataType: datatype,
                async: isasync,
                success: succeeded,
                error: function (request, status, error) {
                    if(getnumbers<10){
                        urlEach();//地址重定义
                        GetData("", DEFAULTCOUNT);
                        getnumbers++;
                    }else {
                        $("body").append(error);
                        Globals.Loading(false);
                    }
                }
            });
        } else {
            var result = $.ajax({
                type: submittype,
                url: url,
                data: data,
                dataType: datatype,
                async: isasync
            }).responseText;

            return result;
        }
    };
};


