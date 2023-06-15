$(document).ready(function() {
    // Make GET request to fetch clubs
    $.ajax({
        type: 'GET',
        url: '/api/clubs',
        dataType: 'json',
        success: function(response) {
            displayClubs(response);
        },
        error: function(xhr, textStatus, errorThrown) {
            console.log('Error:', xhr.responseText);
        }
    });

    function displayClubs(response) {
        // Clear the clubs container
        $('#clubs-container').empty();

        if (response.clubs.length === 0 && response.recommendedClubs.length === 0) {
            $('#clubs-container').text('No clubs available.');
        } else {
            // Display all clubs
            if (response.clubs.length > 0) {
                const allClubsHtml = generateClubTable(response.clubs, 'All Clubs');
                $('#clubs-container').append(allClubsHtml);
            }

            // Display recommended clubs
            if (response.recommendedClubs.length > 0) {
                const recommendedClubsHtml = generateClubTable(response.recommendedClubs, 'Recommended Clubs');
                $('#clubs-container').append(recommendedClubsHtml);
            }
        }
    }

    function generateClubTable(clubs, heading) {
        let tableHtml = '<h2>' + heading + '</h2>';
        tableHtml += '<table>';
        tableHtml += '<tr><th>Name</th><th>Description</th></tr>';

        for (const club of clubs) {
            tableHtml += '<tr>';
            tableHtml += '<td>' + club.name + '</td>';
            tableHtml += '<td>' + club.description + '</td>';
            tableHtml += '</tr>';
        }

        tableHtml += '</table>';
        return tableHtml;
    }
});
