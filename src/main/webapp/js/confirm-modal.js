$(function () {
    $("a.confirm").click(function(e) {
        console.log("HEY!");
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
});