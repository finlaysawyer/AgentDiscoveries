import * as React from 'react';

import NavigationBar from './nav';

export default class Page extends React.Component {
    render() {
        return (
            <React.Fragment>
                <NavigationBar />
                    {this.props.children}
            </React.Fragment>
        );
    }
}
