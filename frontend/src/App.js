import './App.css';

import {
    BrowserRouter as Router,
    Route,
    Routes as Switch,
    Navigate as Redirect
} from "react-router-dom";

import Login from './Login';
import UserPage from './UserPage'
import AdminPage from './AdminPage'
import ChatApp from './ChatApp';


function App() {
    const defaultRoute = window.location.pathname === "/" ? <Redirect to="/log-in"/> : undefined;
 


    return (

        <Router>
            <Switch>
                <Route exact path="/log-in" element={<Login/>}/>
                <Route exact path="/user" element={<UserPage/>}/>
                <Route exact path="/admin" element={<AdminPage/>}/>
                <Route exact path="/chat" element={<ChatApp/>}/>
            </Switch>
            {defaultRoute}
        </Router>
    );
}

export default App;
