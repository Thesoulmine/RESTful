function fillCurrentUserTable(currentUser) {
    let li = `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.firstName}</td>
            <td>${currentUser.lastName}</td>
            <td>${currentUser.age}</td>
            <td>${currentUser.username}</td>
            <td>${currentUser.roles.map(role => role.name).join(" ")}</td>
            </tr>`;
    document.getElementById("currentUserTable").innerHTML = li;
}

async function fillCurrentUser() {
    let response = await fetch("http://localhost:8080/api/users/current");
    let commits = await response.json();
    let li = `<b>${commits.username}</b> with roles ${commits.roles.map(role => role.name).join(" ")}`;
    document.getElementById("currentUser").innerHTML = li;
    fillCurrentUserTable(commits);
}

window.onload = function() {
    fillCurrentUser();
};