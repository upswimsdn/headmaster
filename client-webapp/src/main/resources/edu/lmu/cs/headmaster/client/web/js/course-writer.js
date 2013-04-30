$(function () {
    
    var DEFAULT_MONDAY = 18,
        DEFAULT_SUNDAY = 24,
        DEFAULT_MONTH = 2,
        DEFAULT_YEAR = 2013,
        
        getScheduleDateTimes = function () {
            var daySelectionButtons = $("#day-picker > .active"),
                timestamps = [];

            for (var i = 0; i < daySelectionButtons.length; i++) {
                var button = daySelectionButtons[i];
                timestamps.push(parseInputToDateTime($(button).attr("value")));
            }

            return timestamps;
        },
    
        parseInputToDateTime = function (day) {
           var time = $("#timeinput-" + day).val().split(":");
           return new Date(DEFAULT_YEAR, DEFAULT_MONTH, DEFAULT_MONDAY + parseInt(day), parseInt(time[0]), parseInt(time[1]), 0, 0);
        };

    $("#course-save").click(function (event) {
        var courseData = {
                title: $("#course-title").val(),
                discipline: $("#course-discipline").val(),
                instructor: $("#course-instructor").val(),
                description: $("#course-description").val(),
                classSize: $("#course-classsize").val(),
                credits: $("#course-credits").val(),
                room: $("#course-room").val(),
                classTimes: null // Will be filled in later
        }

        courseData.classTimes = getScheduleDateTimes();

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
    
    // Hide and initialize all bootstrap-timepickers
    $(".bootstrap-timepicker").hide();
    $(".bootstrap-timepicker > input").timepicker({showMeridian: false});
    
    $("#day-picker > .btn").click(function (event) {
        var day = this.value;
        $("#timepicker-" + day).toggle({duration: 100, easing: "easeInSine"});
    })
})