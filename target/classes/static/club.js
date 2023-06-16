$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    console.log(urlParams);
    const id = urlParams.get('id');
    console.log(id);
    const url = "http://localhost:8080/clubs/" + id;

    $.ajax({
        url: url,
        method: "GET",
        dataType: "json",
        success: function(response) {
            // Handle the response data
            const club = response.club;
            const user = response.user;
            const ratings = response.ratings;
            const averageRating = response.averageRating;
            const recommendedClubs = response.recommendedClubs;

            console.log(club);
            console.log(user);
            console.log(ratings);
            console.log(averageRating);
            console.log(recommendedClubs);

            // Generate HTML content based on the response
            var html = "<h1>" + club.name + "</h1>";
            html += "<p>Club Type: " + club.clubType + "</p>";
            // Add more content as needed

            // Update the club-page div with the generated HTML
            $("#club-page").html(html);

            // Process ratings and recommended clubs as needed
            // ...

        },
        error: function(jqXHR, textStatus, errorThrown) {
            // Handle the error case
            console.log("Error:", errorThrown);
        }
    });
});
