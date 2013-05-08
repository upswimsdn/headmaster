$(function () {

    var courses;

    $.ajax({
        type: "GET",
        url: Headmaster.serviceUri("users/self/courses"),
        contentType: "application/json",
        dataType: "json",

        success: function (data, textStatus, jqXHR) {
            courses = data;
            if (!courses.length) {
                $("#no-courses-msg").show();
            } else {
                // Do something...
            }
        }
    });
})