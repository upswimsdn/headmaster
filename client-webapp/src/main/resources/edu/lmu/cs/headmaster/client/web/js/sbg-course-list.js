$(function () {
    // Variables for comparing a courses term and year
    var currentDate = new Date(),

        // Helper method to check that a courses listed year matches the current year.
        rowYearIsCurrent = function (row) {
            var yearTableCell = $(row).children().eq(1);
            return $(yearTableCell).text() == currentDate.getFullYear();
        },

        rowTermIsCurrent = function (row) {
            var termTableCell = $(row).children().eq(2);
            return $(termTableCell).text() === currentTerm;
        },

        getCurrentTerm = function () {
            // The month values are based on the UTC values for months, 0-11.
            var month = currentDate.getUTCMonth();
            if (month >= 7 && month <= 11) {
                return "FALL";
            } else if (month >= 0 && month <= 4) {
                return "SPRING";
            } else if (month >= 4 && month <= 7) {
                return "SUMMER";
            }
        },
        
        currentTerm = getCurrentTerm();


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
        
        // We don't need query data here.
        data=null,

        // Function to display check box that limits listing to courses
        // currently in session.
        function () {
            $("#only-show-current").toggle();
        }
    );
    
    $("#limit-view").bind("click", function () {
        var rows = $("#course-list tr");
        // Start at the second element, because the first row is always
        // the table head.
        for (var i = 1; i < rows.length; i++) {
            if(!(rowYearIsCurrent(rows[i]) && rowTermIsCurrent(rows[i]))) {
                $(rows[i]).toggle();
            }
        }
    });
})