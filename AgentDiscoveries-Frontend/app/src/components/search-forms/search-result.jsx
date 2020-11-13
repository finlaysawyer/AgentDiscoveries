import * as React from 'react';
import {Button, Panel, Modal} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            results: [],
            paginationItems: [],
            show: false
        };
    }

    sliceArray(x) {
        var maxResults = 10 * x;
        var minResults = maxResults - 10;

        var items = this.props.results.slice(minResults, maxResults);
        this.setState({results: items});
    }

    componentDidUpdate(prevProps) {
        let paginationItems = [];

        if (prevProps.results !== this.props.results) {
            this.sliceArray(1);

            for (let index = 10; index <= Math.ceil(this.props.results.length / 10) * 10; index = index+10) {
                paginationItems.push(<li key={index}><a id={`page${index/10}`} onClick={() => this.sliceArray(index/10)}>{index/10}</a></li>);
            }

            this.setState({paginationItems: paginationItems});
        }
    }

    pagination() {
        return ( 
            <div>
                <nav>
                    <ul className="pagination justify-content-left">
                        {this.state.paginationItems}
                    </ul>
                </nav>
            </div>
        );
    }

    handleModal(reportId) {
        this.setState({[reportId]: !this.state[reportId]});
    }

    render() {
        return(
            <div>
                {this.pagination()}
                {this.getResultsHeader(this.state.results)}
                {this.renderResults(this.state.results)}
            </div>
        );
    }

    renderResults(results) {
        return results.map((result, index) => {
            return (
                <Panel id="results-box" key={index}>
                    <Panel.Heading id="results">Result</Panel.Heading>
                    <Panel.Body id="body-id">
                        {this.renderResultBody(result)}
                        <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result.reportId)}>Export to PDF</Button>&nbsp;&nbsp;
                        <Button id="open-modal" bsStyle="success" type="button" onClick={() => this.handleModal(result.reportId)}>View More</Button>
                        <Modal show={this.state[result.reportId]}>
                            <Modal.Header id="modal-header">{result.reportTitle ? result.reportTitle : 'Region ID: ' + result.regionId}</Modal.Header>
                            <Modal.Body>{result.reportBody}</Modal.Body>
                            <Modal.Footer>
                                <Button id="close-modal" bsStyle="success" type="button" onClick={() => this.handleModal(result.reportId)}>
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

            let text = result[key];

            if (text.length > 100) {
                text = text.toString().substring(0, 135) + '...';
            }

            return <p key={key} id={key}>{`${key}: ${text}`}</p>;
        });
    }

    getResultsHeader(results) {
        return results.length > 0
            ? (results.length === 1
                ? <h3 id="results-length">{`${results.length} result`}</h3>
                : <h3 id="results-length">{`${results.length} results`}</h3>)
            : '';
    }
}
