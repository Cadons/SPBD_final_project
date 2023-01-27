
fetch("http://127.0.0.1:7000/api/auth/logout", {

    method: "POST",
    credentials: "include"
    }).then(response => {
        if (response.status === 200) {
            console.log("Logout successful");
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            window.location.href = "/";
        }else{
            console.log("Logout failed");
            window.location.href = "/";
        }
    });

