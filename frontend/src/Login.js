import React from "react";
import axiosInstance from 'axios';
import history from './history'; 
import { Container, Grid, TextField, Radio, RadioGroup, FormControlLabel, Button } from '@mui/material';

class Login extends React.Component {
  constructor() {
    super();
    this.state = {
      name: "",
      password: "",
      isAdmin: false, // Default value for isAdmin
    };
  }

  handleInput = (event) => {
    const { name, value } = event.target;
    this.setState({
      [name]: value,
    });
  };

  handleRadioChange = (event) => {
    this.setState({
      isAdmin: event.target.value === "true",
    });
  };

  handleLogin = (event) => {
    event.preventDefault();
    const credentials = {
      name: this.state.name,
      password: this.state.password,
    };

    axiosInstance
      .post("http://localhost:8082/user-management/accounts/login", credentials)
      .then((response) => {
        const account = response.data;
        if (account.id !== 0) {
          console.log(account.id);
          alert("Logged in!");
          localStorage.setItem("loggedAccountId", account.id);
          localStorage.setItem("name",account.name);
          localStorage.setItem("ROLE", account.isAdmin ? "ADMIN" : "USER");
          localStorage.setItem("token",account.token);
          console.log("Token: " + account.token);
          console.log("logged in account from login.js " + account.id);
          account.isAdmin? history.push("/admin"): history.push("/user");
          window.location.reload();
        }
      })
      .catch((error) => {
        alert("The username or password is incorrect!");
        console.error(error);
      });
  };

  handleRegister = (event) => {
    event.preventDefault();
    const credentials = {
      name: this.state.name,
      password: this.state.password,
      isAdmin: this.state.isAdmin, 
    };

    axiosInstance
      .post("http://localhost:8082/user-management/accounts/register", credentials)
      .then((res) => {
        console.log("Registration success");
        alert("Registered successfully!");
      })
      .catch((error) => {
        
        console.error("Registration error:", error);
        alert("Registration Error");
      });
  };

  render() {
    return (
      <Container maxWidth="sm">
        <div>
          <Grid>
            <form onSubmit={this.handleLogin}>
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="name"
                label="Username"
                name="name"
                autoComplete="string"
                value={this.state.name}
                onChange={this.handleInput}
                autoFocus
              />
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                value={this.state.password}
                onChange={this.handleInput}
                autoComplete="current-password"
              />

              
              <RadioGroup
                row
                name="isAdmin"
                value={this.state.isAdmin.toString()}
                onChange={this.handleRadioChange}
              >
                <FormControlLabel
                  value="true"
                  control={<Radio />}
                  label="Admin"
                />
                <FormControlLabel
                  value="false"
                  control={<Radio />}
                  label="User"
                />
              </RadioGroup>

              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                sx="padding:2"
              >
                Sign In
              </Button>
            </form>
            <Button
              fullWidth
              variant="contained"
              color="secondary"
              onClick={this.handleRegister}
            >
              Register
            </Button>
          </Grid>
        </div>
      </Container>
    );
  }
}

export default Login;
