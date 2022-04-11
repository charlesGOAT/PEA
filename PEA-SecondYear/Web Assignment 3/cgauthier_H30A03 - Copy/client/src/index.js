import React from 'react';
import ReactDOM from 'react-dom';
import App from './Components/App';
import {MovieProvider} from "./Components/ContextApi";



ReactDOM.render(
  <React.StrictMode>
    <MovieProvider>
   <App/>
   </MovieProvider>
  </React.StrictMode>,
  document.getElementById('root')
);


