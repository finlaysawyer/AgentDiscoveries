import * as React from 'react';
import {Button, ControlLabel, Form, FormControl, FormGroup} from 'react-bootstrap';
import {apiGet, apiPost, apiPut} from '../utilities/request-helper';
import Message from '../message';

export default class LocationForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            siteName: '',
            location: '',
            timeZone: '',
            regionId: '',
            longitude: '',
            latitude: '',

            message: {}
        };

        this.onSiteChange = this.onSiteChange.bind(this);
        this.onLocationChange = this.onLocationChange.bind(this);
        this.onTimeZoneChange = this.onTimeZoneChange.bind(this);
        this.onRegionIdChange = this.onRegionIdChange.bind(this);
        this.onLongitudeChange = this.onLongitudeChange.bind(this);
        this.onLatitudeChange = this.onLatitudeChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        // In edit mode, the ID of the location is passed in through props
        if (this.props.id) {
            this.loadLocation(this.props.id);
        }
    }

    render() {
        return (
            <div className='col-md-8 col-md-offset-2'>
                <Message message={this.state.message} />
                <div className='col-md-12'>
                    <Form onSubmit={this.onSubmit}>
                        <h3>{this.props.id ? 'Edit' : 'Create'} Location</h3>

                        <FormGroup>
                            <ControlLabel>Site Name</ControlLabel>
                            <FormControl type='text' required
                                placeholder='Enter site name'
                                value={this.state.siteName}
                                onChange={this.onSiteChange}
                                id="site-name"/>
                        </FormGroup>
                        <FormGroup>
                            <ControlLabel>Location Name</ControlLabel>
                            <FormControl type='text' required
                                placeholder='Enter location name'
                                value={this.state.location}
                                onChange={this.onLocationChange}
                                id="location-name"/>
                        </FormGroup>
                        <FormGroup>
                            <ControlLabel>Time Zone</ControlLabel>
                            <FormControl type='text' required
                                placeholder='Enter time zone (e.g. "Europe/London")'
                                value={this.state.timeZone}
                                onChange={this.onTimeZoneChange}
                                id="time-zone"/>
                        </FormGroup>
                        <FormGroup>
                            <ControlLabel>Longitude</ControlLabel>
                            <FormControl type='number'
                                placeholder='Enter longitude (e.g. -106.69142)'
                                value={this.state.longitude}
                                onChange={this.onLongitudeChange}
                                id="longitude"
                                step="any"
                                min="-180"
                                max="180"/>
                        </FormGroup>
                        <FormGroup>
                            <ControlLabel>Latitude</ControlLabel>
                            <FormControl type='number'
                                placeholder='Enter latitude (e.g. 19.48631)'
                                value={this.state.latitude}
                                onChange={this.onLatitudeChange}
                                id="latitude"
                                step="any"
                                min="-90"
                                max="90"/>
                        </FormGroup>
                        <FormGroup>
                            <ControlLabel>Region</ControlLabel>
                            <FormControl type='number'
                                placeholder='Enter region ID (optional)'
                                value={this.state.regionId}
                                onChange={this.onRegionIdChange}
                                id="region-id"/>
                        </FormGroup>
                        <Button type='submit' id='submit-location'>Submit</Button>
                    </Form>
                </div>
            </div>
        );
    }

    onSiteChange(event) {
        this.setState({ siteName: event.target.value });
    }

    onLocationChange(event) {
        this.setState({ location: event.target.value });
    }

    onTimeZoneChange(event) {
        this.setState({ timeZone: event.target.value });
    }

    onRegionIdChange(event) {
        this.setState({ regionId: parseInt(event.target.value) });
    }

    onLongitudeChange(event) {
        this.setState({ longitude: parseFloat(event.target.value) });
    }

    onLatitudeChange(event) {
        this.setState({ latitude: parseFloat(event.target.value) });
    }


    onSubmit(event) {
        event.preventDefault();

        const body = {
            siteName: this.state.siteName,
            location: this.state.location,
            timeZone: this.state.timeZone,
            longitude: this.state.longitude ? this.state.longitude : null,
            latitude: this.state.latitude ? this.state.latitude : null,
            regionId: this.state.regionId ? this.state.regionId : null
        };

        const request = this.props.id
            ? apiPut('locations', body, this.props.id)
            : apiPost('locations', body);

        request
            .then(() => window.location.hash = '#/admin/locations')
            .catch(error => this.setState({ message: { message: error.message, type: 'danger' } }));
    }

    loadLocation(id) {
        apiGet('locations', id)
            .then(result => this.setState(result))
            .catch(error => this.setState({ message: { message: error.message, type: 'danger' } }));
    }
}
