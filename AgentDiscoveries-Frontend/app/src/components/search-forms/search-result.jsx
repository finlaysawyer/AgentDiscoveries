import * as React from 'react';
import {Button, Panel} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {

    render() {
        return (
            <div className='results'>
                {this.renderView(this.props.results)}
                {this.getResultsHeader(this.props.results)}
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
