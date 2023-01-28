class Customers extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            customers: [],
            showDetails: false,
            currentCustomer: null
        }
        this.showDetails = this.showDetails.bind(this);
        this.hideDetails = this.hideDetails.bind(this);
        this.researchResults = this.researchResults.bind(this);
    }

    componentDidMount() {

        fetch('http://127.0.0.1:7000/api/customers', {
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem('token')
            },
            method: 'GET',
            credentials: 'include'
        }).then(response => {
                if (response.status === 200) {
                    response.json().then(data => {
                            this.setState({customers: data});


                        }
                    );
                }else{
                    if(response.status !== 404) {
                        //set wait time to 2 seconds
                        setTimeout(() => {
                            this.componentDidMount();
                        }, 2000);
                    }
                }
            }
        );


    }

    showDetails = (customer_) => {
        this.setState({showDetails: true, currentCustomer: customer_});
        console.log("show details " + this.state.showDetails);
    }

    hideDetails = () => {
        this.setState({showDetails: false});
        console.log("hide details " + this.state.showDetails);
    }
    researchResults = (customers) => {
        this.setState({customers: customers});
    }

    render() {
        if (this.state.customers.length === 0
        ) {
            return (<div>
                    <Filter research={this.researchResults}/>
                    <div className="container">
                        <h3>No Customer</h3>
                    </div>
                </div>
            )

        } else {
            return (
                <div>

                    <Filter research={this.researchResults}/>
                    <div className="container">

                        {this.state.customers.map(customer =>

                            <div className="customer-box row " key={customer.customerid}>
                                <div className=" col-sm-12 col-md-3 col-lg-2"><b>Customer ID: </b>{customer.customerid}
                                </div>
                                <div className="col-sm-12 col-md-3  col-lg-4"><b>Name: </b>{customer.firstname}
                                    <b> Surname: </b>{customer.lastname}</div>
                                <div className="col-sm-12 col-md-3  col-lg-4"><b>Email: </b>{customer.email}</div>
                                <div className="col-sm-12 col-md-3  col-lg-2">
                                    <button className="btn btn-primary w-100"
                                            onClick={() => this.showDetails(customer)}> Show All
                                    </button>
                                    {this.state.showDetails &&
                                        <DetailsPanel customer={this.state.currentCustomer}
                                                      show={this.state.showDetails}
                                                      hide={this.hideDetails}/>}
                                </div>
                            </div>
                        )}

                    </div>
                </div>
            )
        }
    }
}

class Filter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            filter: 'company',
            targetTxt: '',
            data: null,
            setResearch: this.props.research
        }
        this.handleFilterChange = this.handleFilterChange.bind(this);
        this.handTargetTxt = this.handTargetTxt.bind(this);
        this.handleSearch = this.handleSearch.bind(this);

    }

    handleFilterChange(e) {
        this.setState({filter: e.target.value});

    }

    handTargetTxt(e) {
        this.setState({targetTxt: e.target.value});

    }

    handleSearch(e) {
        e.preventDefault();
        fetch('http://127.0.0.1:7000/api/customers/' + this.state.filter.toLocaleLowerCase() + '/' + this.state.targetTxt, {
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem('token')
            },
            method: 'GET',
            credentials: 'include'
        }).then(response => {
                if (response.status === 200) {
                    response.json().then(data => {
                        this.state.setResearch(data);
                    });

                } else {
                    this.state.setResearch([]);
                }
            }
        );

    }


    render() {
        return (
            <div>
                <div>
                    <label className="form-label">Search</label>
                    <input className="form-control" value={this.state.targetTxt} onChange={this.handTargetTxt}
                           type="text"/>
                </div>
                <div>
                    <label className="form-label">Filter</label>
                    <select className="form-control" value={this.state.filter} onChange={this.handleFilterChange}>
                        <option value="company">Company</option>
                        <option value="country">Country</option>
                        <option value="email">Email</option>
                        <option value="firstName">First Name</option>
                        <option value="lastName">Last Name</option>
                    </select>
                </div>
                <div>
                    <button className="btn btn-primary form-control" onClick={this.handleSearch} type="submit">Search
                    </button>
                </div>
            </div>
        )
    }
}

const rootElement = document.getElementById("customers_table_container");
const root = ReactDOM.createRoot(rootElement);

root.render(<Customers/>);