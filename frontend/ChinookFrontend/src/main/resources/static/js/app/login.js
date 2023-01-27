class Login extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.state = {
            username: '',
            password: ''
        }
    }

    handleSubmit(e) {

        e.preventDefault();

        var credentials = {
            username: this.state.username,
            password: this.state.password
        }


        fetch('http://127.0.0.1:7000/api/auth/login', {
            headers: {
                "Content-Type": "application/json"
            },
            method: 'POST',
            body: JSON.stringify(credentials),
            type: 'json',
            credentials: 'include'
        }).then(response => {
            if (response.status === 200) {
                console.log("Login successful");
                response.json().then(data => {


                    localStorage.setItem('token', data.token);
                    localStorage.setItem('username', data.username);

                });

                window.location.href = "/customers";
            } else {

                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Check your credentials and try again!',
                    confirmButtonColor: '#102E44',


                })
            }
        });
    }

    handleUsernameChange(e) {
        this.setState({username: e.target.value});

    }

    handlePasswordChange(e) {
        this.setState({password: e.target.value});
    }

    render() {
        return (


            <div>
                <form onSubmit={this.handleSubmit}>
                    <div className="d-flex align-items-center mb-3 pb-1">
                        <i className="fas fa-cubes fa-2x me-3" style={{color: '#ff6219'}}></i>
                        <span className="h1 fw-bold mb-0">Chinook</span>
                    </div>

                    <h5 className="fw-normal mb-3 pb-3" style={{letterSpacing: '1px'}}>Login into your account</h5>

                    <div className="form-outline mb-4">
                        <label className="form-label" htmlFor="form2Example17">Username</label>
                        <input type="text" value={this.state.username} onChange={this.handleUsernameChange}
                               id="form2Example17" className="form-control form-control-lg"/>

                    </div>

                    <div className="form-outline mb-4">

                        <label className="form-label" htmlFor="form2Example27">Password</label>
                        <input type="password" id="form2Example27" onChange={this.handlePasswordChange}
                               value={this.state.password} className="form-control form-control-lg"/>
                    </div>

                    <div className="pt-1 mb-4">
                        <button className="btn btn-dark btn-lg btn-block" id="loginBtn" type="submit">Login</button>
                    </div>
                </form>
            </div>

        );
    }
}

const domContainer = document.querySelector('#login_container');
const root = ReactDOM.createRoot(domContainer);
root.render(<Login/>);