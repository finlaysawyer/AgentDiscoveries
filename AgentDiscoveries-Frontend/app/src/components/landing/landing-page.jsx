import * as React from 'react';
import * as Bootstrap from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';
import * as UserHelper from '../utilities/user-helper';
import {mapsApiKey} from '../utilities/config';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';

export class Landing extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            entities: []
        };
    }

    componentDidMount() {
        this.loadEntities();
    }

    createMarker(marker) {
        return (
            <Marker
                title={marker.siteName}
                name={marker.location}
                position={{lat:marker.latitude,lng:marker.longitude}}
                key={marker.locationId}
            />
        );
    }

    render() {
        return (
            <div>
                <Bootstrap.Grid>
                    <Bootstrap.Row>
                        <h1 className="text-center" id="landing-welcome">{this.generateWelcome()}</h1>
                        {this.state.entities.map((entity, index) => (
                            <Bootstrap.Col key={index} md={3} sm={6} className="text-center">
                                <h1 key={index}>{entity.location}</h1>
                                <h2>{entity.siteName} | {new Date().toLocaleTimeString('en-GB', {timeZone: entity.timeZone})}</h2>
                            </Bootstrap.Col>
                        ))}
                    </Bootstrap.Row>
                    <br></br>
                </Bootstrap.Grid>
                <Map google={this.props.google}
                    zoom={5}
                    style={{
                        height: '70%'
                    }}
                    initialCenter={{
                        lat: 52.520008,
                        lng: 13.4049543
                    }}
                >
                    {this.state.entities.map(this.createMarker)}
                </Map>
            </div>
        );
    }

    generateMap(latitude, longitude) {
        if (latitude != null && longitude != null) {
            return (
                <img src={'https://maps.googleapis.com/maps/api/staticmap?center=' + latitude + ', ' + longitude + '&size=250x250&zoom=16&key=' + mapsApiKey()}></img>
            );
        }
    }

    generateWelcome() {
        let user = UserHelper.getUserName();
        let greeting = '';

        const date = new Date();
        const currentTime = date.getHours();

        if (currentTime<12) {
            greeting = 'Good Morning, ';
        } else if (currentTime<18) {
            greeting = 'Good Afternoon, ';
        } else {
            greeting = 'Good Evening, ';
        }

        return greeting += user;
    }

    loadEntities() {
        apiGet('locations')
            .then(results => this.setState({ entities: results }))
            .catch(errorLogAndRedirect);
    }

    
}

export default GoogleApiWrapper({
    apiKey: mapsApiKey()
})(Landing);