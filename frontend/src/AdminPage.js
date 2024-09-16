import React, { Component } from 'react';
import axiosInstance from 'axios';
import UserList from './UserList';
import DevicesList from './DevicesList';
import './AdminPage.css';
import { Box, Button, Container } from '@mui/material';
import history from './history'; 
import {
    Navigate as Redirect
  } from "react-router-dom";

class AdminPage extends Component {
  constructor() {
    super();
    this.state = {
      selectedUser: null,
      users: [],
      devices: [],
    };
  }

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers = () => {
    const token = localStorage.getItem('token'); 


    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };

    axiosInstance.get('http://localhost:8082/user-management/accounts/',config)
      .then((response) => {
        this.setState({ users: response.data });
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  }

  fetchDevices = () => {
    const token = localStorage.getItem('token'); 


    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };
    
    const { selectedUser } = this.state;
    if (selectedUser) {
      axiosInstance.get(`http://localhost:8083/device-management/dev_accounts/${selectedUser.id}/devices`,config)
        .then((response) => {
          this.setState({ devices: response.data });
        })
        .catch((error) => {
          console.error('Error fetching devices:', error);
        });
    }
  }

  handleUserSelect = (user) => {
    this.setState({ selectedUser: user });
    this.fetchDevices();
  }

  handleChatNavigation = () => {
    history.push('/chat');
    window.location.reload();
  };

  render() {
    const { selectedUser, devices, users } = this.state;
    const loggedAccountId = localStorage.getItem('loggedAccountId');
    const userRole = localStorage.getItem('ROLE');


    if (userRole !== 'ADMIN') {
        return <Redirect to="/user" />;
    }
    return (
      <Container maxWidth="xl">
        <Box display="flex" flexDirection="column" height="100vh">
          <Box display="flex" justifyContent="space-between" alignItems="center">
            <h1>ADMIN PAGE</h1>
            <Button
              variant="contained"
              color="secondary"
              onClick={this.handleChatNavigation}
            >
              CHAT
            </Button>
          </Box>
          <Box display="flex" flexGrow={1} py={2}>
            <Box width="48%" mr={1}>
              <UserList
                loggedAccountId={loggedAccountId}
                users={users}
                onUserSelect={this.handleUserSelect}
                fetchUsers={this.fetchUsers}
              />
            </Box>
            <Box width="48%" ml={1}>
              <DevicesList
                devices={devices}
                selectedUser={selectedUser}
                fetchDevices={this.fetchDevices}
              />
            </Box>
          </Box>
        </Box>
      </Container>
    );
  }
}

export default AdminPage;
