$(document).ready(function(){
    var urlParams = new URLSearchParams(window.location.search);
    if(urlParams.has('rows')){
        $('#success-alert').text($('#success-alert').text().replace('{0}',urlParams.get('rows')));
        $('#success-alert').show();
    }
});
