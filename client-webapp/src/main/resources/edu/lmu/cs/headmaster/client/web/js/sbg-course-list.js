$(function () {

    $.ajax({
        type: "GET",
        url: Headmaster.serviceUri("users/self"),
        contentType: "application/json",
        dataType: "json",

        success: function (data, textStatus, jqXHR) {
            var courses = data.managedCourses;
            if (!courses) {
                $("#no-courses-msg").show();
            }
        }
    });
})