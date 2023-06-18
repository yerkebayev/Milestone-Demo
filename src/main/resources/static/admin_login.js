$(document).ready(function() {
    $('#login-form').submit(function(event) {
        event.preventDefault();

        const email = $('#email').val();
        const password = $('#password').val();
        console.log(email);
        console.log(password);

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/admin/login',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                console.log("Osyndamn");
                if (response >= 0) {
                    error = false;
                    window.location.href = 'admin_club.html';
                } else {
                    error = true;
                    console.log("ERROR" + response);
                    alert("Oi error");
                    // $('.result').text('Invalid email or password');
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("Oi error 2");
            }
        });
    });
});

var error = false;

// Check if `error` is true and display the error message
if (error) {
    document.getElementById("error-message").style.display = "block";
}
