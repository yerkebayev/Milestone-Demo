$(document).ready(function() {
    const url = "http://localhost:8080/clubTypes";
    $.ajax({
        url: url,
        method: "GET",
        dataType: "json",
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
                const selectedCheckboxes = [];
                $("input[type='checkbox']:checked").each(function () {
                    const checkboxId = $(this).attr("id");
                    selectedCheckboxes.push(checkboxId);
                });

                // Use the selectedCheckboxes array as needed
                $.ajax({
                    contentType: 'application/json;charset=UTF-8',
                    type: 'POST',
                    url: 'http://localhost:8080/clubTypes',
                    data: JSON.stringify(selectedCheckboxes),
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
                console.log(selectedCheckboxes);
            });

            console.log(selectClubTypes);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            // Handle the error case
            console.log("Error:", errorThrown);
        }
    });
});


