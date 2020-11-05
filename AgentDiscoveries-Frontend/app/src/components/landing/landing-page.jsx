import * as React from 'react';
import london from '../../../images/london.jpg';
import paris from '../../../images/paris.jpg';
import singapore from '../../../images/Singapore.jpg';

export default class Landing extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            api: 'http://worldtimeapi.org/api/timezone/'
        };
    }

    componentDidMount() {
        fetch(this.state.api + 'Europe/London')
            .then(res => res.json())
            .then(res => this.setState({ london: res.datetime.slice(11, -16)}));
        fetch(this.state.api + 'Europe/Paris')
            .then(res => res.json())
            .then(res => this.setState({ paris: res.datetime.slice(11, -16)}));
        fetch(this.state.api + 'Asia/Singapore')
            .then(res => res.json())
            .then(res => this.setState({ singapore: res.datetime.slice(11, -16)}));
    }

    render() {
        return (
            <div>
                <h1> Welcome to Agent Discoveries</h1>
                <container>
                    <div className="container">
                        <img src={london} width= '320px' height= '200px' className="imageLondon"  />
                        <div className="middleLondon">
                            <div className="text">London {this.state.london}</div>
                        </div>
                    </div>
                    <div className="container">
                        <img  src={paris} width= '320px' height= '200px' className="imageParis"  />
                        <div className="middleParis">
                            <div className="text">Paris {this.state.paris}</div>
                        </div>
                    </div>
                    <div className="container">
                        <img  src={singapore} width= '320px' height= '200px' className="imageSG"  />
                        <div className="middleSG">
                            <div className="text">Singapore {this.state.singapore}</div>
                        </div>
                    </div>
                    <img id="1" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                    <img id="2" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                    <img id="3" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                    <img id="4" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                    <img id="5" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                    <img id="6" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                </container>
            </div>
        );
    }    
}