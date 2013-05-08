$(function () {

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
        
        data=null,

        callback=null
    );
})