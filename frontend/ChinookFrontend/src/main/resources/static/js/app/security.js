//check if user is logged in
fetch('http://127.0.0.1:7000/api/', {
    headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem('token')
    },
    method: 'GET',
    type: 'json',
    credentials: 'include'

}).then(response => {
    if (response.status === 200) {
        console.log("User is logged in");
        var text=response.text().then(data => {
            document.getElementById("greet").innerText =data;
        });
    } else {
        console.log("User is not logged in");
        window.location.href = "/logout";
    }
});