import React, { Component } from "react";
import axiosInstance from "axios";

class UserList extends Component {
  constructor() {
    super();
    this.state = {
      selectedUserId: null,
    };
  }

  handleUserSelect = (selectedUser) => {
    this.setState({ selectedUserId: selectedUser.id });
    console.log(this.state.selectedUserId);
    this.props.onUserSelect(selectedUser);
  };

  handleDeleteUser = () => {
    const { selectedUserId } = this.state;

    if (!selectedUserId) {
      alert("Please select a user to delete.");
      return;
    }

    const token = localStorage.getItem('token'); 

   
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };
  

    axiosInstance
      .delete(`http://localhost:8082/user-management/accounts/${selectedUserId}`,config)
      .then(() => {
        console.log("Deleted User");
        alert("User deleted successfully!");
        this.props.fetchUsers();
        this.setState({ selectedUser: null }); 
      })
      .catch((error) => {
        console.error("Error in deleting a user", error);
        alert("User Deletion Error");
      });
  };

  render() {
    const { users, loggedAccountId } = this.props;
    const { selectedUserId } = this.state;

    return (
      <div className="user-list">
        <h2>User List</h2>
        <ul className="user-list">
          {users
            .filter((user) => !user.isAdmin) // Filter out users with role "ADMIN"
            .map((user) => {
              const isSelected = selectedUserId === user.id;

              const userItemStyle = {
                cursor: "pointer",
                padding: "10px",
                border: "1px solid #d1d1d1",
                marginBottom: "10px",
                borderRadius: "5px",
                backgroundColor: isSelected ? "#e0e0e0" : "transparent",
              };

              return (
                <li
                  style={userItemStyle}
                  key={user.id}
                  onClick={() => this.handleUserSelect(user)}
                >
                  <div className="user-item">
                    <div className="user-info">
                      <h3>Username: {user.name}</h3>
                      <p>Role: {user.isAdmin ? "ADMIN" : "USER"}</p>
                      {isSelected && (
                        <button onClick={() => this.handleDeleteUser(user)}>DELETE</button>
                      )}
                    </div>
                  </div>
                </li>
              );
            })}
        </ul>
      </div>
    );
  }
}

export default UserList;
