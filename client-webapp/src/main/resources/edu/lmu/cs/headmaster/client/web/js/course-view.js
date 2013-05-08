$(function () {
    var courseId = $("#course-id").text(),
        course,
        
        // Method to set the custom navigation bar section relevant to this course.
        setUpCurrentCourseNavBarSection = function () {
            $("#current-course-name").text(course.title);
            $("#current-course-nav").toggle();
        };

    $.ajax({
        type: "GET",
        url: Headmaster.serviceUri("courses/" + courseId),
        contentType: "application/json",
        dataType: "json",

        success: function (data, textStatus, jqXHR) {
            course = data;
            setUpCurrentCourseNavBarSection();
        }
    });

});