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
    
    Headmaster.loadJsonArrayIntoTable(
            Headmaster.serviceUri("users/self/courses"),
            "course-list-progress",
            "course-list",
            "course-list-empty",
    
        function (course) {
            return $("<tr></tr>")
                .append($("<td></td>").text(course.title))
                .append($("<td></td>").text(course.year))
                .append($("<td></td>").append(course.term))
                .click(function () {
                    location = course.id;
            });
        },
        
        null,
        null
    );
})