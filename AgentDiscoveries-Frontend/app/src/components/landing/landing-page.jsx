import * as React from 'react';
import london from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\london.jpg'
import paris from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\paris.jpg'
import singapore from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\Singapore.jpg'
import * as Bootstrap from 'react-bootstrap'
export default class Landing extends React.Component {

    render() {
        return (
            <body>
                <div>
                    <h1> Welcome to Agent Discoveries </h1>
                    <img src={london} width= '320px' height= '200px' alt="London" />
                    <img src={paris} width= '320px' height= '200px' alt="Paris" />
                    <img src={singapore} width= '320px' height= '200px' alt="Paris" />
                    <Bootstrap.Container>
                    <Row>
                     <Col>1 of 1</Col>
                    </Row>
                    </Bootstrap.Container>
                </div>
           </body>
        );
    }
}