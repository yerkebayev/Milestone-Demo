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
                        const text = `<tr>
              <td>${clubs[i].name}</td>
              <td>${clubs[i].email}</td>
              <td>${clubs[i].mission}</td>
              <td>${clubs[i].description}</td>
              <td>${head.name} ${head.surname}</td>
              <td>${clubs[i].contact}</td>
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


            $('form.form').submit(function(event) {
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

                $.ajax({
                    contentType: 'application/json;charset=UTF-8',
                    url: "/admin/clubs",
                    method: "POST",
                    data: JSON.stringify(clubForm),
                    success: function(club) {
                        $.ajax({
                            url: 'http://localhost:8080/user/' + clubs[i].head_id,
                            method: 'GET',
                            success: function(head) {
                                console.log(head);
                                const text = `<tr>
              <td>${club.name}</td>
              <td>${club.email}</td>
              <td>${club.mission}</td>
              <td>${club.description}</td>
              <td>${head.name} ${head.surname}</td>
              <td>${club.contact}</td>
              <td>
                <a href="#editEmployeeModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                <a href="#deleteEmployeeModal" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
              </td>
            </tr>`
                                clubInTable.append(text)
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

