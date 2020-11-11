import * as React from 'react';
import {Button, Panel} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            viewElement: '',
            view: {},
            showView: false,
            locations: {},
            callSigns: {},
            regions: {},
        };
        this.onPageChange = this.onPageChange.bind(this);
    }

    componentWillMount() {
            apiGet('locations')
                .then(results => {
                    var eA = {};
                    results.map((result, index) => {
                        eA[result.locationId] = result.siteName;
                    });
                    this.setState({ locations: eA });
                })
                .catch(() => console.log('Error occurred'));
            apiGet('agents')
                .then(results => {
                    var eA = {};
                    results.map((result, index) => {
                        eA[result.agentId] = result.callSign;
                    });
                    this.setState({ callSigns: eA });
                })
                .catch(() => console.log('Error occurred'));
            apiGet('regions')
                .then(results => {
                    var eA = {};
                    results.map((result, index) => {
                        eA[result.regionId] = result.name;
                    });
                    this.setState({ regions: eA });
                })
                .catch(() => console.log('Error occurred'));
        }

        render() {
            return (
                <div className='results'>
                    {this.renderView(this.state.view)}
                    {this.getResultsHeader(this.props.results)}
                    <FormGroup>
                        <ControlLabel>Page</ControlLabel>
                        <FormControl type='number'
                            placeholder='Enter page'
                            value={this.state.page}
                            onChange={this.onPageChange}
                            id="input-page"/>
                    </FormGroup>
                    {this.renderResults(this.props.results)}
                </div>
            );
        }

    render() {
        return (
            <div className='results'>
                {this.renderView(this.props.results)}
                {this.getResultsHeader(this.props.results)}
                <FormGroup>
                    <ControlLabel>Page</ControlLabel>
                    <FormControl type='number'
                        placeholder='Enter page'
                        value={this.state.page}
                        onChange={this.onPageChange}
                        id="input-page"/>
                </FormGroup>
                {this.renderResults(this.props.results)}
            </div>
        );
    }

    onPageChange(event) {
        this.setState({ page: parseInt(event.target.value) });
    }

    onViewChange(value) {
        this.setState({ view: value });
    }

    renderResults(results) {
        return results.map((result, index) => {
            if (index >= (this.state.page - 1) * 10 && index < this.state.page * 10) {
                if (result.reportTitle == undefined) {      // REGION REPORT
                    return (
                        <Panel key={index}>
                            <Panel.Heading>Result</Panel.Heading>
                            <Panel.Body>
                                {this.renderResultBody(result)}
                                <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>
                                <Button bsStyle="success" className="buttonSubmit" id='submit-view' onClick={() => this.onViewChange(result)} type='submit'>View</Button>
                            </Panel.Body>
                        </Panel>
                    );
                } else {                                    // LOCATION REPORT
                    return (
                        <Panel key={index}>
                            <Panel.Heading id="reportId">{`ID: ${result.reportId}`}</Panel.Heading>
                            <Panel.Body>
                                {this.renderResultBody(result)}
                                <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>
                                <Button bsStyle="success" className="buttonSubmit" id='submit-view' onClick={() => this.onViewChange(result)} type='submit'>View</Button>
                            </Panel.Body>
                        </Panel>
                    );
                }
            }
        });
    }

    renderView(result) {
        if (result.reportTitle == undefined) {      // REGION REPORT VIEW
            return (
                <div id='resultView' ref={(ref) => this.viewElement = ref}>
                    <Panel>
                        <Panel.Heading>View Report</Panel.Heading>
                        <Panel.Body>
                            {this.renderResultBody(result)}
                            <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>
                            <Button bsStyle="success" className="buttonSubmit" id='submit-view' onClick={() => this.onViewChange(result)} type='submit'>View</Button>
                        </Panel.Body>
                    </Panel>
                </div>
            );
        } else {                                    // LOCATION REPORT VIEW
            return (
                <div id='resultView' ref={(ref) => this.viewElement = ref}>
                    <Panel>
                        <Panel.Heading>View Report</Panel.Heading>
                        <Panel.Body>
                            {this.renderResultBody(result)}
                            <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>
                            <Button bsStyle="success" className="buttonSubmit" id='submit-view' onClick={() => this.onViewChange(result)} type='submit'>View</Button>
                        </Panel.Body>
                    </Panel>
                </div>
            );
        }
    }

    generatePdf(id) {
        apiGet('reports/locationstatuses/pdf', id)
            .then(results => console.log(results))
            .catch(errorLogAndRedirect);
    }

    renderResultBody(result) {
        return Object.keys(result).map(key => {
            return <p key={key} id={key}>{`${key}: ${result[key]}`}</p>;
        });
    }

    getResultsHeader(results) {
        return results.length > 0
            ? (results.length === 1
                ? <h3>{`${results.length} result`}</h3>
                : <h3>{`${results.length} results`}</h3>)
            : '';
    }
}
