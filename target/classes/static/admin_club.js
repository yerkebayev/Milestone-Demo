// Get All Clubs
$('#getAllClubsBtn').click(function() {
    $.ajax({
        url: 'http://localhost:8080/admin/clubs',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var result = '';
            data.forEach(function(club) {
                result += 'Club ID: ' + club.id + '<br>';
                result += 'Name: ' + club.name + '<br>';
                result += 'Email: ' + club.email + '<br><br>';
            });
            $('#getAllClubsResult').html(result);
        }
    });
});

// Add Club
$('#addClubForm').submit(function(event) {
    event.preventDefault();
    var club = {
        name: $('#name').val(),
        email: $('#email').val()
    };
    $.ajax({
        url: 'http://localhost:8080/admin/clubs',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(club),
        success: function(data) {
            $('#addClubResult').html('Club added successfully. ID: ' + data.id);
        },
        error: function(xhr, textStatus, errorThrown) {
            $('#addClubResult').html('Error: ' + xhr.responseText);
        }
    });
});

// Update Club
$('#updateClubForm').submit(function(event) {
    event.preventDefault();
    var clubId = $('#updateClubId').val();
    var club = {
        name: $('#updateName').val(),
        email: $('#updateEmail').val()
    };
    $.ajax({
        url: 'http://localhost:8080/admin/clubs/' + clubId,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(club),
        success: function(data) {
            $('#updateClubResult').html('Club updated successfully. ID: ' + data.id);
        },
        error: function(xhr, textStatus, errorThrown) {
            $('#updateClubResult').html('Error: ' + xhr.responseText);
        }
    });
});

// Delete Club
$('#deleteClubForm').submit(function(event) {
    event.preventDefault();
    var clubId = $('#deleteClubId').val();
    $.ajax({
        url: 'http://localhost:8080/admin/clubs/' + clubId,
        type: 'DELETE',
        dataType: 'json',
        success: function(data) {
            $('#deleteClubResult').html(data);
        },
        error: function(xhr, textStatus, errorThrown) {
            $('#deleteClubResult').html('Error: ' + xhr.responseText);
        }
    });
});