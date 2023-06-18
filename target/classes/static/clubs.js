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
            console.log("All clubs")
            console.log(allClubsList)

            allClubs.forEach(function(club) {
                console.log(club.id);

                const listItem = `<li class="list-group-item">
          <!-- Custom content-->
          <div class="card">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img src="${club.image}" class="card-img" alt="Item Photo">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <a class="card-title" href="club.html?id=${club.id}">${club.name}</a>
                <p class="card-text">${club.description}</p>
            </div>
        </div>
    </div>
</div>

        </li> `;
                console.log(listItem)
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
                listItem += "<div class=\"row card-wrapper container-sm d-flex justify-content-around\">\n";

                if (index + 2 < recommendedClubs.length) {
                    for (let i = 0; i < 3; i++) {
                        listItem += "<div class=\"card\" style=\"width: 18rem;\">\n" +
                            "  <img src=\"" + recommendedClubs[index + i].image + "\"  class=\"card-img-top\" alt=\"...\">\n" +
                            "  <div class=\"card-body\">\n" +
                            "    <a class=\"card-title\" href=\"club.html?id=" + recommendedClubs[index + i].id + "\">" + recommendedClubs[index + i].name + "</a>\n" +
                            "  </div>\n" +
                            "</div>";
                    }
                    index += 2;
                } else {
                    for (let i = index; i < recommendedClubs.length; i++) {
                        listItem += "<div class=\"card\" style=\"width: 18rem;\">\n" +
                            "  <img src=\"" + recommendedClubs[index + i].image + "\"  class=\"card-img-top\" alt=\"...\">\n" +
                            "  <div class=\"card-body\">\n" +
                            "    <a class=\"card-title\" href=\"club.html?id=" + recommendedClubs[i].id + ">" + recommendedClubs[i].name + "</a>\n" +
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

