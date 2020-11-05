import * as React from 'react';
import london from '../../../images/london.jpg';
import paris from '../../../images/paris.jpg';
import singapore from '../../../images/Singapore.jpg';

export default class Landing extends React.Component {

    render() {
        return (
            <body>
                <div>
                    <h1> Welcome to Agent Discoveries </h1>
                    <container>
                        <img id="London" src={london} width= '320px' height= '200px' alt="London" />
                        <img id="Paris" src={paris} width= '320px' height= '200px' alt="Paris" />
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