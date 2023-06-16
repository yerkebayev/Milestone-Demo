$(document).ready(function() {
    $('#register-form').submit(function(event) {
        event.preventDefault();

        const user = {
            name: $('#name').val(),
            surname: $('#surname').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/register',
            data: JSON.stringify(user),
            contentType: 'application/json',
            success: function(response) {
                // if (response >= 0) {
                    // Redirect to another page
                    window.location.href = 'clubs.html'; // Replace with the desired URL
                // } else {
                //     $('.result').text('Registration  unsuccessful! ' + response);
                // }
            },
            error: function(xhr, textStatus, errorThrown) {
                $('.result').text('Error: ' + xhr.responseText);
            },
        });
    });
});