$(document).ready(function() {
    $('#login-form').submit(function(event) {
        event.preventDefault();

        const email = $('#email').val();
        const password = $('#password').val();
        console.log(email);
        console.log(password);

        $.ajax({
            type: 'POST',
            url: '/login',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                if (response >= 0) {
                    // Redirect to another page
                    window.location.href = '/api/clubs'; // Replace with the desired URL
                } else {
                    $('.result').text('Invalid email or password');
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('.result').text('Error: ' + jqXHR.responseText);
            }
        });
    });
});
