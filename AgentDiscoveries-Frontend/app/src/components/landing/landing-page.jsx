import * as React from 'react';
import * as Bootstrap from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';
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
                <h1 className="text-center">{this.generateWelcome()}</h1>
                <Bootstrap.Row> 
                    {this.state.entities.map((entity, index) => (
                        <Bootstrap.Col key={index} md={12/this.state.entities.length} className="text-center">
                            <h1 key={index}>{entity.location}</h1>
                            <h2>{entity.siteName} | {new Date().toLocaleTimeString('en-GB', {timeZone: entity.timeZone})}</h2>
                            {this.generateMap(entity.latitude, entity.longitude)}
                        </Bootstrap.Col>
                    ))}
                </Bootstrap.Row>
            </Bootstrap.Grid>
        );
    }

    generateMap(latitude, longitude) {
        if (latitude != null && longitude != null) {
            return (
                <img src={"https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + ", " + longitude + "&size=250x250&zoom=16&key=AIzaSyDX49vDhcLkUrZV16O57h7WthEypDfR3-w"}></img>
            );
        }
    }

    generateWelcome() {
        let user = UserHelper.getUserName();
        let greeting = "";

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
        apiGet("locations")
            .then(results => this.setState({ entities: results }))
            .catch(errorLogAndRedirect);
    }
}