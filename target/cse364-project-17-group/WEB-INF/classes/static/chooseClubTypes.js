$(document).ready(function() {
    const url = "http://localhost:8080/clubTypes";
    $.ajax({
        url: url,
        method: "GET",
        success: function(response) {
            const clubTypes = response.clubTypes;
            const preferredClubTypes = response.preferredClubTypes;
            console.log(clubTypes);
            console.log(preferredClubTypes);
            console.log(response);
            const selectClubTypes = $("#selectClubTypes")
            for (let i = 0; i < clubTypes.length; i++) {
                if (preferredClubTypes.includes(clubTypes[i].id)) {
                    const item = `<div class="mb-2">
<input type="checkbox" class="form-check-input" id="${clubTypes[i].id}" checked>
<label class="form-check-label" for="${clubTypes[i].id}">${clubTypes[i].name}</label> 
</div>`
                    console.log(item);
                    selectClubTypes.append(item)
                } else {
                    const item = `<div class="mb-2">
<input type="checkbox" class="form-check-input" id="${clubTypes[i].id}">
<label class="form-check-label" for="${clubTypes[i].id}">${clubTypes[i].name}</label> 
</div>`
                    console.log(item);
                    selectClubTypes.append(item)
                }
            }

            $("#chooseButton").click(function () {
                console.log("BUTTONED")
                let clubTypesSelected = "";
                $("input[type='checkbox']:checked").each(function () {
                    const checkboxId = $(this).attr("id");
                    clubTypesSelected += (checkboxId) + "_";
                });
                console.log(clubTypesSelected)


                // Use the selectedCheckboxes array as needed
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/clubTypes/add?clubTypes=' + clubTypesSelected,
                    contentType: 'application/x-www-form-urlencoded',
                    success: function(response) {
                        if (response >= 0) {
                            window.location.reload();
                            window.location.href = 'clubs.html';
                        } else {
                            console.log("ERROR" + response);
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("ERROR" + errorThrown);
                    }
                });
            });

            console.log(selectClubTypes);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            // Handle the error case
            console.log("Error:", errorThrown);
        }
    });
});


