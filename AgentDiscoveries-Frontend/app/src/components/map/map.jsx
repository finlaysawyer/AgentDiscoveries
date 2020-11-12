import React, { Component } from 'react';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
//import markers from "./markers";
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';


const mapStyles = {
    width: '50%',
    height: '70%'
};



export class MapContainer extends Component {

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
            />
        );
    }
    render() {
        return (
            <div>
                <Map
                    google={this.props.google}
                    zoom={14}
                    style={mapStyles}
                    initialCenter={{
                        lat: 52.520008,
                        lng: 13.4049543
                    }}
                >
                    <Marker onClick={this.onMarkerClick} name={'Current location'} />
                    {this.state.entities.map(this.createMarker)}
                </Map>
            </div>
        );
    }

    loadEntities() {
        apiGet('locations')
            .then(results => this.setState({ entities: results }))
            .catch(errorLogAndRedirect);
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyDTG0djiQQ_PDvbbSNawquHi7PVYD8LxSE'
})(MapContainer);

