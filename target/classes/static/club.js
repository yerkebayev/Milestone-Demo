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
            const averageRating = response.ratingAverage;
            const recommendedClubs = response.recommendedClubs;
            console.log(ratings);
            console.log(head);
            console.log(averageRating);
            const leftSideCLub = $("#left-side-club")
            const textForLeftSideClub = `<img src="${club.image}" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3">${club.name}</h5>
                        <p class="text-muted mb-1">${club.mission}</p>
                        <p class="my-3">Head</p>
                        <p class="text-muted mb-4">${head.name} ${head.surname}</p>
                        <div class="d-flex justify-content-center mb-2">
                            <button type="button" class="btn btn-primary">Apply</button>
                        </div>`;
            leftSideCLub.append(textForLeftSideClub);
            console.log(leftSideCLub);

            const clubInfo = $("#club-info")
            const clubInfoText = `<div class="card-body">
                        <div class="row col-12">
                            <div class="col-md-6">
                                <h2 class="mb-0">${club.name}</h2>
                            </div>
                            <div class="col-md-6">
                                <h3>${averageRating}</h3>
                            </div>
                        </div>
                        <hr>
                        <div class="col-12">
                            <p class="mb-0">${club.description}</p>
                        </div>
                        <hr>
                    </div>`
            console.log(clubInfoText);
            clubInfo.append(clubInfoText)

            const comments = $("#comments")
            for (let i = ratings.length - 1; i >= 0; i--) {
                console.log(ratings[i]);
                $.ajax({
                    url: '/user/' + ratings[i].user_id,
                    method: 'GET',
                    success: function (user) {
                        const commentItem = `<div class="card mb-4">
                            <div class="card-body">
                                <p>${ratings[i].comment}</p>

                                <div class="d-flex justify-content-between">
                                    <div class="d-flex flex-row align-items-center">
                                    <div class="row" width="25" height="25" />
                                        <i class="bi bi-person"></i>
                                        <p class="small mb-0 ms-2">${user.name} ${user.surname}</p>
                                    </div>
                                    <div class="d-flex flex-row align-items-center">
                                        <i class="far fa-thumbs-up mx-2 fa-xs text-black" style="margin-top: -0.16rem;"></i>
                                        <p class="small text-muted mb-0">${ratings[i].value}</p>
                                    </div>
                                </div>
                            </div>
                        </div>`;
                        comments.append(commentItem);
                    },
                    error: function (xhr, status, error) {
                        console.log("no user " + xhr + " " + status + " " + error);
                    }
                });
            }
            function fetchCommentAndAppend(ratingId) {
                $.ajax({
                    url: '/ratings/' + ratingId,
                    method: 'GET',
                    success: function (rating) {
                        $.ajax({
                            url: '/user/' + rating.user_id,
                            method: 'GET',
                            success: function (user) {
                                const commentItem = `<div class="card mb-4">
            <div class="card-body">
              <p>${rating.comment}</p>
              <div class="d-flex justify-content-between">
                <div class="d-flex flex-row align-items-center">
                  <img src="https://storage.cloudconvert.com/tasks/33836809-7715-4d57-96ac-7ff81939aa2c/149071.webp?AWSAccessKeyId=cloudconvert-production&Expires=1687036338&Signature=6lBoLU0zuWbG%2B3ACOjpPoVUxA%2Bg%3D&response-content-disposition=inline%3B%20filename%3D%22149071.webp%22&response-content-type=image%2Fwebp" alt="avatar" width="25" height="25" />
                  <p class="small mb-0 ms-2">${user.name} ${user.surname}</p>
                </div>
                <div class="d-flex flex-row align-items-center">
                  <i class="far fa-thumbs-up mx-2 fa-xs text-black" style="margin-top: -0.16rem;"></i>
                  <p class="small text-muted mb-0">${rating.value}</p>
                </div>
              </div>
            </div>
          </div>`;
                                $.ajax({
                                        url: '/clubs/' + id + '/ratings/avg',
                                        method: 'GET',
                                        success: function (avg) {
                                            const clubInfoTextNew = `<div class="card-body">
                        <div class="row col-12">
                            <div class="col-md-6">
                                <h2 class="mb-0">${club.name}</h2>
                            </div>
                            <div class="col-md-6">
                                <h3>${avg}</h3>
                            </div>
                        </div>
                        <hr>
                        <div class="col-12">
                            <p class="mb-0">${club.description}</p>
                        </div>
                        <hr>
                    </div>`
                                            console.log(clubInfoText);
                                            clubInfo.update(clubInfoTextNew)
                                            comments.prepend(commentItem)
                                        },
                                        error: function (xhr, status, error) {
                                            console.log("no user " + xhr + " " + status + " " + error);
                                        }
                                })

                            },
                            error: function (xhr, status, error) {
                                console.log("no user " + xhr + " " + status + " " + error);
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        console.log("failed to fetch comment " + xhr + " " + status + " " + error);

                    }
                });
            }

            $("#addCommentButton").click(function () {
                const comment = $("#addANote").val();
                const rate = $("#addARate").val();
                if (rate != null && rate >= 1 && rate <= 5 && comment.trim() !== '') {
                    const RatingData = {
                        comment: comment,
                        rating: rate,
                    };
                    $.ajax({
                        contentType: 'application/json;charset=UTF-8',
                        url: "/clubs/" + club.id + "/ratings",
                        method: "POST",
                        data: JSON.stringify(RatingData),
                        success: function (response) {
                            console.log(response.id);
                            fetchCommentAndAppend(response.id);
                        },
                        error: function (xhr, status, error) {
                            console.log(error);
                        }
                    });
                    $('#addANote').val('');
                    $('#addARate').val('');
                }
            })

            const recommendedClubsList = $("#recommendedClubs2");
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
        error: function(jqXHR, textStatus, errorThrown) {
            // Handle the error case
            console.log("Error:", errorThrown);
        }
    });
});
