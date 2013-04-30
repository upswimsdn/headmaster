$(function () {
    
    $("#course-save").click(function (event) {
        var courseData = {
                title: $("#course-title").val(),
                discipline: $("#course-discipline").val(),
                instructor: $("#course-instructor").val(),
                description: $("#course-description").val(),
                classSize: $("#course-classsize").val(),
                credits: $("#course-credits").val(),
                room: $("#course-room").val()
        }

        $.ajax({
            type: "POST",
            url: Headmaster.serviceUri("courses"),
            data: JSON.stringify(courseData),
            contentType: "application/json",
            dataType: "json",

            success: function (data, textStatus, jqXHR) {
                var resultId = jqXHR.getResponseHeader("Location").split("/").pop();

                // Provide visible UI feedback.
                $("#course-success").fadeIn();

                // Dismiss the alert after a fixed delay (not needed for edits).
                setTimeout(function () {
                    $("#course-success").fadeOut();
                }, 5000);
            }
        });
    });

})