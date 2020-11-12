import * as React from 'react';
import {Button, Panel} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {
constructor(props) {
        super(props);
        this.state = {
            results: []
        };
    }

    sliceArray(x) {
        var maxResults = 10 * x;
        var minResults = maxResults - 10;

        var items = this.props.results.slice(minResults, maxResults);
        this.setState({results: items});
    }

    componentDidUpdate(prevProps) {
        if (prevProps.results !== this.props.results) {
            this.sliceArray(1)
        }
    }

    pagination() {
        return ( 
            <div>
                <nav>
                    <ul className="pagination justify-content-left">
                        <li><a onClick={() => this.sliceArray(1)}>Previous</a></li>
                        <li><a onClick={() => this.sliceArray(1)}>1</a></li>
                        <li><a onClick={() => this.sliceArray(2)}>2</a></li>
                        <li><a onClick={() => this.sliceArray(3)}>3</a></li>
                        <li><a onClick={() => this.sliceArray(1)}>Next</a></li>
                    </ul>
                </nav>
            </div>
        );
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
                <Panel id="resultsBox" key={index}>
                    <Panel.Heading>Result</Panel.Heading>
                    <Panel.Body>
                        {this.renderResultBody(result)}
                        <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>
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
