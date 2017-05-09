$(function () {
    $("a.confirmDelete").click(function(e) {
        console.log("HEY!");
        e.preventDefault();
        var location = $(this).attr('href');
        bootbox.confirm({
            message: "Confirm delete?",
            size: "small",
            callback: function (confirmed) {
                if(confirmed) {
                    window.location.replace(location);
                }
            }
        });
    });
});