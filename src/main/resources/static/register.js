$(document).ready(function() {
    $('#registerButton').click(function(event) {
        event.preventDefault();

        const user = {
            name: $('#name').val(),
            surname: $('#surname').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };
        console.log($.param(user))
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/register?' + $.param(user),
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