function fillUserTable() {
    // fetch("https://localhost:8080/api/users")
    //     // Converting received data to JSON
    //     .then((response) => response.json())
    //     .then((json) => {

            // 2. Create a variable to store HTML table headers
            let li = `<td>1</td><td>Shamil</td><td>Zaynetdinov</td><td>24</td><td>admin@admin</td><td>ADMIN</td>`;

            // 3. Loop through each data and add a table row
            // json.forEach((user) => {
            //     li += `<tr>
            // <td>${user.id}</td>
            // <td>${user.firstName} </td>
            // <td>${user.lastName}</td>
            // <td>${user.age}</td>
            // <td>${user.username}</td>
            // <td>${user.roles.map(role => role.name)}</td>`;
            // });

            // 4. DOM Display result
            document.getElementById("users").innerHTML = li;
        // });
}

window.onload = () => {
    fillUserTable()
}