async function fillCurrentUser() {
    let response = await fetch("http://localhost:8080/api/users/current");
    let commits = await response.json();
    let li = `<b>${commits.username}</b> with roles ${commits.roles.map(role => role.name).join(" ")}`;
    document.getElementById("currentUser").innerHTML = li;
}

async function addRolesOptions() {
    let response = await fetch("http://localhost:8080/api/users/roles");
    let commits = await response.json();
    let li = ``;
    commits.forEach(element => {
        li += `<option value='{"id": ${element.id}, "name": "${element.name}"}'>${element.name}</option>`
    });
    document.getElementById("addNewUserSelect").innerHTML = li;
    document.getElementById("deleteUserRole").innerHTML = li;
    document.getElementById("editUserRole").innerHTML = li;
}

async function fillUsersTable() {
    let response = await fetch("http://localhost:8080/api/users");
    let commits = await response.json();
    let li = ``;
    commits.forEach((user) => {
        li += `<tr>
        <td>${user.id}</td>
        <td>${user.firstName} </td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.username}</td>
        <td>${user.roles.map(role => role.name).join(" ")}</td>
        <td><button class="btn btn-info" type="button" data-toggle="modal" data-id="${user.id}"
        data-target="#userEdit">Edit</button></td>
        <td><button type="button" class="btn btn-danger" data-toggle="modal" data-id="${user.id}"
        data-target="#userDelete">Delete</button></td>
        </tr>`
    });
    document.getElementById("usersTable").innerHTML = li;
}

async function fillUpdateForm(id) {
    let response = await fetch(`http://localhost:8080/api/users/${id}`);
    let commits = await response.json();
    editForm.id.value = commits.id;
    editForm.firstName.value = commits.firstName;
    editForm.lastName.value = commits.lastName;
    editForm.age.value = commits.age;
    editForm.username.value = commits.username;
    editForm.password.value = commits.password;
    commits.roles.forEach(function (element) {
        editForm.roles.options[element.id - 1].selected = true;
    });
}

async function updateUser() {
    let user = {
        id: editForm.id.value,
        firstName: editForm.firstName.value,
        lastName: editForm.lastName.value,
        age: editForm.age.value,
        username: editForm.username.value,
        password: editForm.password.value,
        roles: Array.from(editForm.roles)
            .filter(option => option.selected)
            .map(option => JSON.parse(option.value))
    };
    let response = await fetch(`http://localhost:8080/api/users`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
    if (response.ok) {
        document.getElementById('closeEditModal').click()
        await fillUsersTable();
    }
}

async function fillDeleteForm(id) {
    let response = await fetch(`http://localhost:8080/api/users/${id}`);
    let commits = await response.json();
    deleteForm.id.value = commits.id;
    deleteForm.firstName.value = commits.firstName;
    deleteForm.lastName.value = commits.lastName;
    deleteForm.age.value = commits.age;
    deleteForm.username.value = commits.username;
    deleteForm.roles.value = commits.roles;
}

async function deleteUser() {
    let response = await fetch(`http://localhost:8080/api/users/${deleteForm.id.value}`, {
        method: 'DELETE'
    })
    if (response.ok) {
        document.getElementById('closeDeleteModal').click()
        await fillUsersTable();
    }
}

async function addFormButton() {
    let user = {
        firstName: addForm.firstName.value,
        lastName: addForm.lastName.value,
        age: parseInt(addForm.age.value),
        username: addForm.username.value,
        password: addForm.password.value,
        roles: Array.from(addForm.roles)
            .filter(option => option.selected)
            .map(option => JSON.parse(option.value))
    };
    let response = await fetch("http://localhost:8080/api/users", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
    let result = await response.json();
    if (response.ok) {
        $('.nav-tabs a:first').tab('show');
        document.getElementById('addForm').reset();
        await fillUsersTable();
    }
}

$(document).ready(() => {
    $('#userEdit').on('show.bs.modal', function (event) {
        let id = $(event.relatedTarget).attr("data-id")
        fillUpdateForm(id);
    })

    $('#userEdit').on('hidden.bs.modal', function () {
        document.getElementById('editForm').reset();
        $(this).removeData();
    })

    $('#userDelete').on('show.bs.modal', function (event) {
        let id = $(event.relatedTarget).attr("data-id")
        fillDeleteForm(id);
    })

    $('#userDelete').on('hidden.bs.modal', function () {
        document.getElementById('deleteForm').reset();
        $(this).removeData();
    })
})

window.onload = function() {
    fillUsersTable();
    fillCurrentUser();
    addRolesOptions();
};