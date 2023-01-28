
fetch(url+"/api/auth/logout", {

    method: "POST",
    credentials: "include"
    }).then(response => {
        if (response.status === 200) {
            console.log("Logout successful");
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            //get if exist kickout parameter
            var url_string = window.location.href;
            var url = new URL(url_string);
            var kickout = url.searchParams.get("kickout");
            if(kickout === null) {
                window.location.href = "/";
            }
            else{
                window.location.href = "/?kickout";
            }
        }else{
            console.log("Logout failed");
            window.location.href = "/?kickout";
        }
    });

