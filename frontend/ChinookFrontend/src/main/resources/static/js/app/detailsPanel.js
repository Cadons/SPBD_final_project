class DetailsPanel extends React.Component {


    constructor(props) {
        super(props);

        this.state = {
            customer: this.props.customer,


        }

    }

    render() {

        if (this.state.show === false) {
            return null;
        } else {
            return (<div className="details-panel detailsPopup">


                    <div className="details-panel-body container">
                        <div className="details-panel-header">

                            <h3>Details</h3>
                        </div>
                        {Object.keys(this.state.customer).map(key => {
                            return (
                                <div className="row" key={key}>
                                    <div className="col-6 title">{key.toLocaleUpperCase()}</div>
                                    <div className="col-6">{this.state.customer[key]}</div>
                                </div>
                            )

                        })}
                        <button className="btn btn-danger col-12" onClick={this.props.hide}>Close</button>
                    </div>

                </div>
            )


        }


    }

}