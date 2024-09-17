import React, { Fragment, useState } from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import store from './store';
import App from './containers/App';
import { lightTheme, darkTheme } from './utils/theme';
import GlobalStyle from './utils/globals';

const rootElement = document.querySelector('#root');
const root = ReactDOM.createRoot(rootElement);

const AppWrapper = () => {
    const [isDarkTheme, setIsDarkTheme] = useState(false);

    const theme = isDarkTheme ? darkTheme : lightTheme;

    return (
        <Provider store={store}>
            <ThemeProvider theme={theme}>
                <Fragment>
                    <GlobalStyle />
                    <App toggleTheme={() => setIsDarkTheme(!isDarkTheme)} />
                </Fragment>
            </ThemeProvider>
        </Provider>
    );
};

root.render(<AppWrapper />);
