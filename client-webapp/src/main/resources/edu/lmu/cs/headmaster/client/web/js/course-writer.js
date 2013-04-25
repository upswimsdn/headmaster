$(function () {
    
    $("#course-save").click(function (event) {
        var courseData = {
                title: $("#course-title").val(),
                instructor: $("course-instructor").val(),
                description: $("course-description").val()
        }

        $.ajax({
            type: "POST",
            url: Headmaster.serviceUri("courses"),
            data: JSON.stringify(courseData),
            contentType: "application/json",
            dataType: "json",

            success: function (data, textStatus, jqXHR) {SS
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