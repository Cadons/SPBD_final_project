
async function refreshToken() {
    if (localStorage.getItem('role') !== "ROLE_MANAGER") {
        fetch(url+'/api/auth/refresh', {
            headers: {
                "Content-Type": "application/json",
            },
            method: 'POST',
            type: 'json',
            credentials: 'include'
        }).then(response => {
            if (response.status === 200) {
                console.log("Refresh successful");

                response.json().then(data => {

                    localStorage.setItem('token', data.token);
                    return true;
                });
            } else {
                console.log("Refresh failed");
                return false;

            }
        });
    }
    else {
        window.location.href = "/logout";
    }

}
//check if user is logged in
var toggle = false;
function checkLogin() {

    fetch(url+'/api/', {
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem('token')
        },
        method: 'GET',
        type: 'json',
        credentials: 'include'

    }).then(async response => {
        if (response.status === 200) {
            console.log("User is logged in");
           response.text().then(data => {
                document.getElementById("greet").innerText = data;
            });
        } else if(response.status === 403){
            if (await refreshToken() === false) {
                console.log("User is not logged in");

             window.location.href = "/logout";
            }else
            {
                if(toggle === false){
                    toggle = true;
                    checkLogin();
                }

            }


        }else{
            console.log("User is not logged in");
          window.location.href = "/logout?kickout";
        }
    }).catch(error => {
        swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Application error!, please contact the administrator',
            confirmButtonColor: '#102E44',

        }) ;
    });
}
checkLogin();