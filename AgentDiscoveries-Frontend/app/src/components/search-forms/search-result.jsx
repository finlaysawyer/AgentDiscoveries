import * as React from 'react';
import {Button, Panel, Modal} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {
    constructor() {
        super();

        this.state = {
            show: false
        };
    }

    handleModal(reportId) {
        this.setState({[reportId]: !this.state[reportId]});
    }


    render() {
        return (
            <div className='results'>
                {this.getResultsHeader(this.props.results)}
                {this.renderResults(this.props.results)}
            </div>
        );
    }

    renderResults(results) {
        return results.map((result, index) => {
            return (
                <Panel id="resultsBox" key={index}>
                    <Panel.Heading>Result</Panel.Heading>
                    <Panel.Body>
                        {this.renderResultBody(result)}
                        <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>&nbsp;&nbsp;
                        <Button bsStyle="success" type="button" onClick={() => this.handleModal(result.reportId)}>View More</Button>
                        <Modal show={this.state[result.reportId]}>
                            <Modal.Header>{result.reportTitle ? result.reportTitle : 'Region ID: ' + result.regionId}</Modal.Header>
                            <Modal.Body>{result.reportBody}</Modal.Body>
                            <Modal.Footer>
                                <Button bsStyle="success" type="button" onClick={() => this.handleModal(result.reportId)}>
                                    Close modal
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    </Panel.Body>
                </Panel>
            );
        });
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
