$(document).ready(function(){
    var urlParams = new URLSearchParams(window.location.search);
    if(urlParams.has("error")){
        var error=urlParams.get("error");
        var data=error.split(":");
        var field=data[0];
        var kind=data[1];

        $("#"+field).addClass("is-invalid");
        $("#"+field+"-"+kind).show();
    }
});
