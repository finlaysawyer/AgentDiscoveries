import * as React from 'react';
import * as Bootstrap from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

import london from '../../../images/london.jpg';
import paris from '../../../images/paris.jpg';
import singapore from '../../../images/Singapore.jpg';
import berlin from '../../../images/berlin.jpg';
import madrid from '../../../images/madrid.jpg';
import stockholm from '../../../images/Stockholm.jpg';
import washingtonDC from '../../../images/WashingtonDC.jpg';
import moscow from '../../../images/Moscow.jpg';
import shanghai from '../../../images/Shanghai.jpg';
import * as UserHelper from '../utilities/user-helper';

export default class Landing extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            entities: []
        };
    }

    componentDidMount() {
        this.loadEntities();
    }

    render() {
        return (
            <Bootstrap.Grid>
                <Bootstrap.Row> 
                    {this.state.entities.map((entity, index) => (
                        <Bootstrap.Col key={index} md={12/this.state.entities.length} className="text-center">
                            <h1 key={index}>{entity.location}</h1>
                            <h2>{entity.siteName} | {new Date().toLocaleTimeString('en-GB', {timeZone: entity.timeZone})}</h2>
                            <img src={"https://maps.googleapis.com/maps/api/staticmap?center=" + entity.latitude + ", " + entity.longitude + "&size=250x250&zoom=16&key="}></img>
                        </Bootstrap.Col>
                    ))}
                </Bootstrap.Row>
            </Bootstrap.Grid>
        );
    }

    loadEntities() {
        apiGet("locations")
            .then(results => this.setState({ entities: results }))
            .catch(errorLogAndRedirect);
    }

    render_old() {
        function getUser(){
            return UserHelper.getUserName();
        }
        let greeting;
        const date = new Date();
        const currentTime = date.getHours();
        if (currentTime<12){
            greeting='Good Morning,';
        }else if(currentTime<18){
            greeting='Good Afternoon,';
        }else{
            greeting='Good Night,';
        }
        return (

            <div>
                <h1>{greeting} <span>{getUser()} — welcome to Agent Discoveries.</span></h1>
                <container>
                    <div className="container1">
                        <img src={london} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleLondontext location-show-text">
                            <div className="text">London</div>
                        </div>
                        <div className="middleLondontime location-show-text">
                            <div className="time">{this.state.london}</div>
                        </div>

                    </div>
                    <div className="container1">
                        <img  src={paris} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleParistext location-show-text">
                            <div className="text">Paris</div>
                        </div>
                        <div className="middleParistime location-show-text">
                            <div className="time">{this.state.paris}</div>
                        </div>

                    </div>
                    <div className="container1">
                        <img  src={singapore} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleSGtext location-show-text">
                            <div className="text">Singapore</div>
                        </div>
                        <div className="middleSGtime location-show-text">
                            <div className="time">{this.state.singapore}</div>
                        </div>

                    </div>
                    <div className="container1">
                        <img  src={berlin} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleBerlintext location-show-text">
                            <div className="text">Berlin</div>
                        </div>
                        <div className="middleBerlintime location-show-text">
                            <div className="time">{this.state.berlin}</div>
                        </div>
                    </div>
                    <div className="container1">
                        <img  src={madrid} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleMadridtext location-show-text">
                            <div className="text">Madrid</div>
                        </div>
                        <div className="middleMadridtime location-show-text">
                            <div className="time">{this.state.madrid}</div>
                        </div>
                    </div>
                    <div className="container1">
                        <img  src={stockholm} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleStockholmtext location-show-text">
                            <div className="text">Stockholm</div>
                        </div>
                        <div className="middleStockholmtime location-show-text">
                            <div className="time">{this.state.stockholm}</div>
                        </div>
                    </div>
                    <div className="container1">
                        <img  src={moscow} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleMoscowtext location-show-text">
                            <div className="text">Moscow</div>
                        </div>
                        <div className="middleMoscowtime location-show-text">
                            <div className="time">{this.state.moscow}</div>
                        </div>
                    </div>
                    <div className="container1">
                        <img  src={washingtonDC} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleWashingtonDCtext location-show-text">
                            <div className="text">Washington DC</div>
                        </div>
                        <div className="middleWashingtonDCtime location-show-text">
                            <div className="time">{this.state.washingtonDC}
                            </div>
                        </div>
                    </div>
                    <div className="container1">
                        <img  src={shanghai} width= '320px' height= '200px' className="location-image"  />
                        <div className="middleShanghaitext location-show-text">
                            <div className="text">Shanghai</div>
                        </div>
                        <div className="middleShanghaitime location-show-text">
                            <div className="time">{this.state.shanghai}</div>
                        </div>
                    </div>
                </container>
            </div>
        );
    }
}