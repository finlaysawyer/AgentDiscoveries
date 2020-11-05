import * as React from 'react';
import london from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\london.jpg';
import paris from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\paris.jpg';
import singapore from 'D:\\BP\\CodeBootCamp\\RepoStore\\AgentDiscoveries\\AgentDiscoveries-Frontend\\app\\images\\Singapore.jpg';

export default class Landing extends React.Component {

    render() {
        return (
            <body>
                <div>
                    <h1> Welcome to Agent Discoveries </h1>
                    <container>
                    <div class="container">
                            <img src={london} width= '320px' height= '200px' class="imageLondon"  />
                            <div class="middleLondon">
                            <div class="text">London</div>
                                    </div>
                                    </div>
                    <div class="container">
                            <img  src={paris} width= '320px' height= '200px' class="imageParis"  />
                            <div class="middleParis">
                            <div class="text">Paris</div>
                                    </div>
                                    </div>
                                    <div class="container">
                            <img  src={singapore} width= '320px' height= '200px' class="imageSG"  />
                            <div class="middleSG">
                            <div class="text">Singapore</div>
                                    </div>
                                    </div>
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