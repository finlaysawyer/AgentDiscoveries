import * as React from 'react';
import { Container } from 'react-bootstrap/lib/tab';
import london from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\london.jpg'
import paris from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\paris.jpg'
import singapore from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\Singapore.jpg'

export default class Landing extends React.Component {

    render() {
        return (
            <body>
                <div>
                    <h1> Welcome to Agent Discoveries </h1>
                    <Container> 
                    <img src={london} width= '320px' height= '200px' alt="London" />
                    <img src={paris} width= '320px' height= '200px' alt="Paris" />
                    <img src={singapore} width= '320px' height= '200px' alt="Paris" />
                    </Container>
                   
                </div>
           </body>
        );
    }
}