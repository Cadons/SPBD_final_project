class ProfileForm extends React.Component {
    constructor(props) {
        super(props);


        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleFirstNameChange = this.handleFirstNameChange.bind(this);
        this.handleLastNameChange = this.handleLastNameChange.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handleAddressChange = this.handleAddressChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handleStateChange = this.handleStateChange.bind(this);
        this.handleCountryChange = this.handleCountryChange.bind(this);
        this.handlePostalCodeChange = this.handlePostalCodeChange.bind(this);
        this.handlePhoneChange = this.handlePhoneChange.bind(this);
        this.handleFaxChange = this.handleFaxChange.bind(this);
        this.handleBirthDateChange = this.handleBirthDateChange.bind(this);
        this.handleJobChange = this.handleJobChange.bind(this);

        this.state = {
            firstName: '',
            lastName: '',
            email: ''
        }
        var profile = this.props.profileData;


        this.state.lastName = profile.lastname;
        this.state.firstName = profile.firstname;
        this.state.email = profile.email;
        this.state.address = profile.address;
        this.state.city = profile.city;
        this.state.state = profile.state;
        this.state.country = profile.country;
        this.state.postalCode = profile.postalcode;
        this.state.phone = profile.phone;
        this.state.fax = profile.fax;
        this.state.birthDate = profile.birthdate;
        this.state.hireDate = profile.hiredate;
        this.state.job = profile.title;


        //formate date
        var date = new Date(profile.birthdate);

        this.state.birthDate = date.toISOString().split('T')[0];
        date = new Date(profile.hiredate);
        this.state.hireDate = date.toISOString().split('T')[0];


    }
    componentDidMount() {
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
            } else {
                console.log("User is not logged in");
                window.location.href = "/logout";
            }
        });

    }

    handleSubmit(e) {
        e.preventDefault();

        var profile = {
            firstname: this.state.firstName,
            lastname: this.state.lastName,
            email: this.state.email,
            city: this.state.city,
            state: this.state.state,
            country: this.state.country,
            postalcode: this.state.postalCode,
            phone: this.state.phone,
            fax: this.state.fax,
            birthdate: this.state.birthDate,
            title: this.state.job,
            address: this.state.address
        }
        Swal.fire({
            title: 'Do you want to save the changes?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#102E44',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirm'
        }).then((result) => {
            if (result.isConfirmed) {
                if(profile.birthdate === null || profile.birthdate === undefined || profile.birthdate === ''||new Date(profile.birthdate)>new Date()||new Date(profile.birthdate)<new Date(1900,1,1)){
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Birthdate is invalid',
                        confirmButtonColor: '#102E44',
                    })
                }else{


                fetch('http://127.0.0.1:7000/api/profile', {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.getItem('token')
                    },
                    method: 'PUT',
                    body: JSON.stringify(profile),
                    type: 'json',
                    credentials: 'include'
                }).then(response => {
                    if (response.status === 200) {
                        Swal.fire({
                            icon: 'success',
                            title: 'Operation Successful',
                            text: 'Account updated successfully',
                            confirmButtonColor: '#102E44',


                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Something went wrong',
                            confirmButtonColor: '#102E44',


                        })
                    }
                });
                }
            }
        })


    }

    handleFirstNameChange(e) {
        this.setState({firstName: e.target.value});
    }

    handleLastNameChange(e) {
        this.setState({lastName: e.target.value});

    }

    handleEmailChange(e) {
        this.setState({email: e.target.value});
    }

    handleAddressChange(e) {
        this.setState({address: e.target.value});

    }

    handleCityChange(e) {
        this.setState({city: e.target.value});

    }

    handleStateChange(e) {
        this.setState({state: e.target.value});

    }

    handleCountryChange(e) {
        this.setState({country: e.target.value});


    }

    handlePostalCodeChange(e) {
        this.setState({postalCode: e.target.value});
    }

    handlePhoneChange(e) {
        this.setState({phone: e.target.value});
    }

    handleFaxChange(e) {
        this.setState({fax: e.target.value});
    }

    handleBirthDateChange(e) {
        this.setState({birthDate: e.target.value});
    }

    handleJobChange(e) {
        this.setState({job: e.target.value});
    }


    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>

                    <div className="form-group">
                        <label htmlFor="firstName">First Name</label>
                        <input type="text" className="form-control" id="firstName" placeholder="First Name"
                               value={this.state.firstName} onChange={this.handleFirstNameChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="lastName">Last Name</label>
                        <input type="text" className="form-control" id="lastName" placeholder="Last Name"
                               value={this.state.lastName} onChange={this.handleLastNameChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input type="email" className="form-control" id="email" placeholder="Email"
                               value={this.state.email} onChange={this.handleEmailChange}/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="job">Job Title</label>
                        <input type="text" className="form-control" id="job" value={this.state.job}
                               onChange={this.handleJobChange} placeholder="Job Title"/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="address">Address</label>
                        <input type="text" className="form-control" id="address" placeholder="Address"
                               value={this.state.address} onChange={this.handleAddressChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="city">City</label>
                        <input type="text" className="form-control" id="city" placeholder="City" value={this.state.city}
                               onChange={this.handleCityChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="state">State</label>
                        <input type="text" className="form-control" id="state" placeholder="State"
                               value={this.state.state} onChange={this.handleStateChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="country">Country</label>
                        <input type="text" className="form-control" id="country" placeholder="Country"
                               value={this.state.country} onChange={this.handleCountryChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="postalCode">Postal Code</label>
                        <input type="text" className="form-control" id="postalCode" placeholder="Postal Code"
                               value={this.state.postalCode} onChange={this.handlePostalCodeChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="phone">Phone</label>
                        <input type="text" className="form-control" id="phone" placeholder="Phone"
                               value={this.state.phone} onChange={this.handlePhoneChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="fax">Fax</label>
                        <input type="text" className="form-control" id="fax" placeholder="Fax" value={this.state.fax}
                               onChange={this.handleFaxChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="birthdate">Birthdate</label>
                        <input type="date" className="form-control" max={new Date().toISOString().split("T")[0]}
                               id="birthdate" placeholder="Birthdate"
                               value={this.state.birthDate} onChange={this.handleBirthDateChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="hiredate">Hiredate</label>
                        <input type="date" className="form-control" id="hiredate" placeholder="Hiredate"
                               value={this.state.hireDate} readOnly={true}/>
                    </div>


                    <button type="submit"  className="btn btn-primary col-12">Update</button>
                </form>
            </div>
        );
    }
}

var profile;
fetch('http://127.0.0.1:7000/api/profile', {
    headers: {
        "Content-Type": "application/json"
        , "Authorization": "Bearer " + localStorage.getItem('token')
    },
    method: 'GET',
    type: 'json',
    credentials: 'include'
}).then(response => {
    if (response.status === 200) {
        console.log("Profile request successful");
        response.json().then(data => {
            profile = data;

            const domContainer = document.querySelector('#pageContent');
            const root = ReactDOM.createRoot(domContainer);

            root.render(<ProfileForm profileData={profile}/>);
        });
    } else {
        console.log("Profile request failed");
        window.location.href = "/logout";
    }
});

