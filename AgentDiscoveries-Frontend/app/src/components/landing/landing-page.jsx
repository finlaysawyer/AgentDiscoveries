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
        fetch(this.state.api + "Europe/London")
            .then(res => res.json())
            .then(res => this.setState({ london: res.datetime}))
        fetch(this.state.api + "Europe/Paris")
            .then(res => res.json())
            .then(res => this.setState({ paris: res.datetime}))
        fetch(this.state.api + "Asia/Singapore")
            .then(res => res.json())
            .then(res => this.setState({ london: res.datetime}))
    }

    render() {
        return (
            <body>
                <div>
                    <h1> Welcome to Agent Discoveries</h1>
                    <container>
                        <img id="London" src={london} width= '320px' height= '200px' alt="London" />
                        <p>{this.state.london}</p>
                        <img id="Paris" src={paris} width= '320px' height= '200px' alt="Paris" />
                        <p>{this.state.paris}</p>
                        <img id="Singapore" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                        <img id="1" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                        <img id="2" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                        <img id="3" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                        <img id="4" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                        <img id="5" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                        <img id="6" src={singapore} width= '320px' height= '200px' alt="Singapore" />
                    </container>
                </div>
            </body>
        );
    }    
}