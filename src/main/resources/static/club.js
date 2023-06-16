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
            const head = response.user;
            const ratings = response.ratings;
            const averageRating = response.averageRating;
            const recommendedClubs = response.recommendedClubs;

            console.log(club);
            console.log(head);
            console.log(ratings);
            // console.log(averageRating);
            console.log(recommendedClubs);
            const leftSideCLub = $("#left-side-club")
            const textForLeftSideClub = `<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3">${club.name}</h5>
                        <p class="text-muted mb-1">${club.mission}</p>
                        <p class="text-muted mb-1">Head</p>
                        <p class="text-muted mb-4">${head.name}</p>
                        <div class="d-flex justify-content-center mb-2">
                            <button type="button" class="btn btn-primary">Apply</button>
                        </div>`;
            leftSideCLub.append(textForLeftSideClub);
            console.log(leftSideCLub);

            const clubInfo = $("#club-info")
            const clubInfoText = `<div class="card-body">
                        <div class="col-sm-3">
                            <h2 class="mb-0">${club.name}</h2>
                        </div>
                        <hr>
                        <div class="col-sm-3">
                            <p class="mb-0">${club.description}</p>
                        </div>
                        <hr>
                    </div>`
            console.log(clubInfoText);
            clubInfo.append(clubInfoText)

        },
        error: function(jqXHR, textStatus, errorThrown) {
            // Handle the error case
            console.log("Error:", errorThrown);
        }
    });
});
