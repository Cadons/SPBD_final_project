class ChangePassword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            oldPassword: '',
            newPassword: '',
            confirmPassword: ''
        }
        this.handleOldPasswordChange = this.handleOldPasswordChange.bind(this);
        this.handleNewPasswordChange = this.handleNewPasswordChange.bind(this);
        this.handleConfirmPasswordChange = this.handleConfirmPasswordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }



    handleOldPasswordChange(e) {
        this.setState({oldPassword: e.target.value});
    }

    handleNewPasswordChange(e) {
        this.setState({newPassword: e.target.value});
    }

    handleConfirmPasswordChange(e) {
        this.setState({confirmPassword: e.target.value});
    }

    handleSubmit(e) {

        e.preventDefault();
        checkLogin();
        //check if password is almoost 6 characters long minimum and 14 characters maximum, and if it contains at least one number and one letter lower and upper and one special character, password must have almost 3 of the 4 conditions
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{6,14})/;

        if (regex.test(this.state.newPassword)) {
            if (this.state.newPassword !== this.state.confirmPassword) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Passwords do not match!',
                    confirmButtonColor: '#102E44',
                })
            }else if(this.state.newPassword !== this.state.confirmPassword){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Passwords do not match!',
                    confirmButtonColor: '#102E44',
                })
            }
            else if (this.state.newPassword === this.state.oldPassword) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'New password must be different from old password!',
                    confirmButtonColor: '#102E44',
                })
            }
            else if (this.state.newPassword === '') {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'New password must not be empty!',
                    confirmButtonColor: '#102E44',
                })
            }
            else {


                var data = {
                    oldPassword: this.state.oldPassword,
                    newPassword: this.state.newPassword
                }
                fetch(url+'/api/profile/password', {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.getItem('token')
                    },
                    method: 'PUT',
                    body: JSON.stringify(data),
                    credentials: 'include'
                }).then(response => {
                        if (response.status === 200) {
                            Swal.fire({
                                icon: 'success',
                                title: 'Password changed successfully\nYou will be logged out',
                                confirmButtonColor: '#102E44',
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    window.location.href = "/logout";
                                }
                            })
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: 'New password must be different from old password!',
                                confirmButtonColor: '#102E44',
                            })
                        }
                    }
                ).catch(error => {
                    swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Application error!, please contact the administrator',
                        confirmButtonColor: '#102E44',

                    })
                })
            }

        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: "Passwords is not strong enough!",
                confirmButtonColor: '#102E44',
            })
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-5 offset-md-3 offset-sm-0 col-sm-12">
                        <div className="card">
                            <div className="card-header">
                                <h4>Change Password</h4>
                            </div>

                            <div className="card-body">

                                <ul>
                                    <li>Password must be between 6 and 14 characters long</li>
                                    <li>Password must contain at least one number</li>
                                    <li>Password must contain at least one lower case letter</li>
                                    <li>Password must contain at least one upper case letter</li>
                                    <li>Password must contain at least one special character</li>
                                </ul>
                            </div>


                            <div className="card-body">
                                <form onSubmit={this.handleSubmit}>
                                    <div className="form-group">
                                        <label htmlFor="oldPassword">Old Password</label>
                                        <input type="password" className="form-control" id="oldPassword"
                                               onChange={this.handleOldPasswordChange}/>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="newPassword">New Password</label>
                                        <input type="password" className="form-control" id="newPassword"
                                               onChange={this.handleNewPasswordChange}/>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="confirmPassword">Confirm Password</label>
                                        <input type="password" className="form-control" id="confirmPassword"
                                               onChange={this.handleConfirmPasswordChange}/>
                                    </div>
                                    <button type="submit" className="btn btn-primary col-12">Change Password</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        )

    }
}

const domContainer = document.querySelector('#changePassword_form_container');
var root=ReactDOM.createRoot(domContainer);
root.render(<ChangePassword/>);