$(function () {
    var searchField = $("#search-field");

    // Set up search field event handling.
    Headmaster.setupSearchField(
        searchField, $("#search-progress"), $("#search-empty"), $("#search-results"),
        Headmaster.serviceUri("courses"), "title",
        function (student) {
            return $("<tr></tr>")
                .append($("<td></td>").text(course.title))
                .click(function () {
                    console.log("click")
                });
        }
    );

    // If there is already a value in the search field, trigger a search.
    if (searchField.val()) {
        searchField.trigger("input");
    }
});
