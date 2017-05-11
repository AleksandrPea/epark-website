$(function () {
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

    $('.nav a').each(function(i,e){
        var href = window.location.href;
        if (href.split(/http:\/\/[^\/.]+/).pop() === $(this).attr('href')) {
            $(this).parent().addClass('active');
        }
    });
});