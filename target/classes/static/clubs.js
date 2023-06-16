$(document).ready(function() {
    $.ajax({
        // dataType: "JSON",
        url: "http://localhost:8080/clubs",
        // contentType: "application/json; charset=utf-8",
        method: "GET",
        success: function(data) {
            console.log(data);
            const allClubs = data.allClubs;
            const recommendedClubs = data.preferredClubs;
            console.log(allClubs);
            console.log(recommendedClubs);

            const allClubsList = $("#allClubs");

            allClubs.forEach(function(club) {
                console.log(club.id);

                const listItem = `<li class="list-group-item">
          <!-- Custom content-->
          <div class="card">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" class="card-img" alt="Item Photo">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <h5 class="card-title">${club.name}</h5>
                <p class="card-text">${club.description}</p>
            </div>
        </div>
    </div>
</div>

        </li> `;

                allClubsList.append(listItem);
            });


            const recommendedClubsList = $("#recommendedClubs");
            for (let index = 0; index < recommendedClubs.length; index++) {
                let listItem = "";
                if (index === 0) {
                    listItem += "<div class=\"carousel-item active\">\n";
                } else {
                    listItem += "<div class=\"carousel-item\">\n";
                }
                listItem += "<div class=\"card-wrapper container-sm d-flex justify-content-around\">\n";

                if (index + 2 < recommendedClubs.length) {
                    for (let i = 0; i < 3; i++) {
                        listItem += "<div class=\"card\" style=\"width: 18rem;\">\n" +
                            "  <img src=\"https://source.unsplash.com/collection/190727/1600x900\" class=\"card-img-top\" alt=\"...\">\n" +
                            "  <div class=\"card-body\">\n" +
                            "    <h5 class=\"card-title\">" + recommendedClubs[index + i].name + "</h5>\n" +
                            "  </div>\n" +
                            "</div>";
                    }
                    index += 2;
                } else {
                    for (let i = index; i < recommendedClubs.length; i++) {
                        listItem += "<div class=\"card\" style=\"width: 18rem;\">\n" +
                            "  <img src=\"https://source.unsplash.com/collection/190727/1600x900\" class=\"card-img-top\" alt=\"...\">\n" +
                            "  <div class=\"card-body\">\n" +
                            "    <h5 class=\"card-title\">" + recommendedClubs[i].name + "</h5>\n" +
                            "  </div>\n" +
                            "</div>";
                    }
                    index = recommendedClubs.length;
                }
                listItem += "</div>\n" +
                    "</div>";
                console.log(listItem);
                recommendedClubsList.append(listItem);
            }


        },
        error: function(xhr, textStatus, errorThrown) {
            console.log("Error: " + errorThrown + " " + xhr + " " + textStatus);
        }
    });
});

