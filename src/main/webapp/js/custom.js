$(function () {
    // making confirm modal
    $("a.confirm").click(function(e) {
        e.preventDefault();
        var location = $(this).attr('href');
        bootbox.confirm({
            message: "Are you sure?",
            size: "small",
            callback: function (confirmed) {
                if(confirmed) {
                    window.location.replace(location);
                }
            }
        });
    });

    //setting language
    $("a.language-picker").click(function(e) {
        e.preventDefault();
        var language = $(this).attr('href');
        var href = window.location.href.split(/[&]?language=[\w]{5}/)[0];
        var symbol = href.indexOf("?") > 0 ? "&" : "?";
        window.location.replace(href+symbol+"language="+language);
    });

    // for activating top buttons on navigation
    $(".nav a").each(function(i,e){
        var href = window.location.href;
        if (href.split(/http:\/\/[^\/.]+/).pop() === $(this).attr('href')) {
            $(this).parent().addClass('active');
        }
    });
});