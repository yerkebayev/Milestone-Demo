$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/admin/clubs',
        method: 'GET',
        success: function(clubs) {
            const clubInTable = $("#clubInTable")
            console.log(clubs)
            for (let i = 0;i < clubs.length;i++) {
                console.log(clubs[i].head_id);
                console.log(clubs[i]);
                $.ajax({
                    url: 'http://localhost:8080/user/' + clubs[i].head_id,
                    method: 'GET',
                    success: function(head) {
                        const text = `<tr data-row-id="${clubs[i].id}">
              <td>${clubs[i].name}</td>
              <td>${clubs[i].email}</td>
              <td>${clubs[i].mission}</td>
              <td>${clubs[i].description}</td>
              <td>${head.name} ${head.surname}</td>
              <td>${clubs[i].contact}</td>
              <td>${clubs[i].image}</td>
              <td>
  <button class="edit editButton" data-row-id="${clubs[i].id}" data-toggle="modal" data-target="#editEmployeeModal">
    <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
  </button>
  <button class="delete deleteButton" data-row-id="${clubs[i].id}" data-toggle="modal" data-target="#deleteEmployeeModal">
    <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
  </button>
</td>

            </tr>`
                        clubInTable.append(text)
                    }
                });
            }
            const selectClubType = $("#select-club-type");
            $.ajax({
                url: "http://localhost:8080/clubTypesForAdmin",
                method: "GET",
                dataType: "json",
                success: function(response) {
                    const clubTypes = response.clubTypes;
                    for (let i = 0; i < clubTypes.length; i++) {
                        if (i === 0) {
                            const optionType = `<option value="${clubTypes[i].id}" selected>${clubTypes[i].name}</option>`
                            selectClubType.append(optionType)
                        } else {
                            const optionType = `<option value="${clubTypes[i].id}">${clubTypes[i].name}</option>`
                            selectClubType.append(optionType)
                        }

                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("Error:", errorThrown, textStatus, jqXHR);
                }
            });

            $("#clubInTable").on("click", ".deleteButton", function() {
                const rowId = $(this).data('row-id');
                const $row = $(this).closest('tr'); // Get the parent row element
                console.log(rowId);

                $.ajax({
                    url: '/admin/clubs/' + rowId,
                    method: 'DELETE',
                    success: function(response) {
                        console.log("Deletion successful");

                        // Remove the row from the HTML table
                        $row.remove();
                    },
                    error: function(xhr, status, error) {
                        console.error("Error occurred:", error);
                        // Handle error if AJAX request fails
                    }
                });
            });
            $("#clubInTable").on("click", ".editButton", function() {
                const rowId = $(this).data("row-id");
                // Fetch the data for the corresponding row using AJAX
                $.ajax({
                    url: 'http://localhost:8080/admin/clubs/' + rowId,
                    method: 'GET',
                    success: function(clubData) {
                        // Populate the modal with the data
                        clearModal();
                        populateEditModal(clubData);
                    },
                    error: function(xhr, status, error) {
                        console.error(error);
                    }
                });
            });
            function populateEditModal(clubData) {
                const modal = $("#club-form-modal");

                // Set the modal title
                modal.find(".modal-title").text("Edit Club");

                // Set the form input values with the corresponding data
                modal.find("input[name='name']").val(clubData.name);
                modal.find("input[name='email']").val(clubData.email);
                modal.find("select#select-club-type").val(clubData.clubType);
                modal.find("textarea[name='mission']").val(clubData.mission);
                modal.find("textarea[name='description']").val(clubData.description);
                modal.find("input[name='head']").val(clubData.headEmail);
                modal.find("input[name='contact']").val(clubData.contact);
                modal.find("input[name='image']").val(clubData.image);

                // Show the modal
                modal.modal("show");
            }
            function clearModal() {
                const modal = $("#club-form-modal");

                // Reset the form inputs
                modal.find("form")[0].reset();

                // Clear any validation error states if present
                modal.find(".is-invalid").removeClass("is-invalid");
                modal.find(".invalid-feedback").text("");

                // Optionally, you can also reset the modal title
                modal.find(".modal-title").text("Create Club");
            }



            $("#saveButton").click(function(event) {
                event.preventDefault();

                var clubForm = {
                    name: $('input[name="name"]').val(),
                    email: $('input[name="email"]').val(),
                    mission: $('textarea[name="mission"]').val(),
                    description: $('textarea[name="description"]').val(),
                    headEmail: $('input[name="head"]').val(),
                    contact: $('input[name="contact"]').val(),
                    clubType: $('#select-club-type').val()
                };
                console.log(clubForm);

                $.ajax({
                    contentType: 'application/json;charset=UTF-8',
                    url: "/admin/clubs",
                    method: "POST",
                    data: JSON.stringify(clubForm),
                    success: function(club) {
                        console.log(club);
                        $.ajax({
                            url: 'http://localhost:8080/user/' + club.head_id,
                            method: 'GET',
                            success: function(head) {
                                if (clubForm.headEmail.includes("@")) {
                                    const text = `<tr>
              <td>${club.name}</td>
              <td>${club.email}</td>
              <td>${club.mission}</td>
              <td>${club.description}</td>
              <td>${head.name} ${head.surname}</td>
              <td>${club.contact}</td>
              <td>${club.image}</td>
              <td>
                <a href="#editEmployeeModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                <a href="#deleteEmployeeModal" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
              </td>
            </tr>`
                                    clubInTable.append(text)
                                }
                                else {
                                    console.log("UPDATED CLUB")
                                    console.log(club);
                                    const rowId = club.id;
                                    const $row = $('tr[data-row-id="' + rowId + '"]');
                                    console.log($row);
                                    $row.find('td:eq(0)').text(club.name)
                                    $row.find('td:eq(1)').text(club.email);
                                    $row.find('td:eq(2)').text(club.mission);
                                    $row.find('td:eq(3)').text(club.description);
                                    $row.find('td:eq(4)').text(head.name + ' ' + head.surname);
                                    $row.find('td:eq(5)').text(club.contact);
                                    $row.find('td:eq(6)').text(club.image);
                                    console.log("UPDATED");
                                }

                            }
                        });
                        $('#club-form-modal').modal('hide');
                        return false;
                    },
                    error: function(xhr, status, error) {
                        console.log(error);
                        console.log(status);
                        console.log(xhr);
                    }
                });
            });


        },
        error: function (xhr, status, error) {
            console.log(xhr + " " + status + " " + error);
        }
    });

});

