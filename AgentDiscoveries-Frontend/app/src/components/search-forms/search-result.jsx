import * as React from 'react';
import {Button, Panel} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {
constructor(props) {
        super(props);
        this.state = {
            results: [],
            paginationItems: []
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
                paginationItems.push(<li key={index}><a onClick={() => this.sliceArray(index/10)}>{index/10}</a></li>)
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

            let text = result[key];

            if (text.length > 100) {
                text = text.toString().substring(0, 135) + "..."
            }

            return <p key={key} id={key}>{`${key}: ${text}`}</p>;
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
