$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault(); // Prevent the default form submission

        var email = $('#email').val();
        var password = $('#password').val();

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/admin/login',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                if (response >= 0) {
                    window.location.href = 'admin_club.html';
                } else {
                    $('.result').text('Invalid email or password');
                }
                // Handle the success response here (e.g., show a success message)
            },
            error: function(xhr, status, error) {
                console.log(xhr.responseText);
                // Handle the error response here (e.g., show an error message)
            }
        });
    });
});