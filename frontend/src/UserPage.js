import React, { Component } from 'react';
import axios from 'axios';
import './UserPage.css';
import history from './history'; 
import {
  Navigate as Redirect
} from "react-router-dom";
import { Button ,Container} from '@mui/material';

class UserPage extends Component {
  state = {
    devices: [],
  };

  userId = localStorage.getItem('loggedAccountId');
  userRole = localStorage.getItem('ROLE');

  componentDidMount() {
    this.fetchUserDevices();
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.devices.length !== this.state.devices.length) {
      this.fetchUserDevices();
    }
  }

  fetchUserDevices = () => {
    const token = localStorage.getItem('token'); 


    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };

    axios.get(`http://localhost:8083/device-management/dev_accounts/${this.userId}/devices`,config)
      .then((response) => {
        this.setState({ devices: response.data });
      })
      .catch((error) => {
        console.error('Error fetching user devices:', error);
      });
  };

  handleChatNavigation = () => {
    history.push('/chat');
    window.location.reload();
  };

  render() {
    if (this.userRole === 'ADMIN') {
        return <Redirect to="/admin" />;
    } else if (this.userRole === 'USER') {
        return (
            <Container maxWidth="lg">
                <div className="user-page-container">
                    <h2>Your Devices</h2>
                    <div className="content-container">
                        <ul className="device-list">
                            {this.state.devices.map((device) => (
                                <li key={device.id} className="device-list-item">
                                    <strong>Maximum Consumption:</strong> {device.maximumConsumption}<br />
                                    <strong>Description:</strong> {device.description}<br />
                                    <strong>Location:</strong> {device.location}<br />
                                </li>
                            ))}
                        </ul>
                        <Button
                            variant="contained"
                            color="secondary"
                            className="chat-button"
                            onClick={this.handleChatNavigation}
                        >
                            CHAT
                        </Button>
                    </div>
                </div>
            </Container>
        );
    } else {
        return <Redirect to="/log-in" />;
    }
}
}

export default UserPage;
