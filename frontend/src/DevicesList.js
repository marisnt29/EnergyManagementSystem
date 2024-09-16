import React, { Component } from "react";
import axiosInstance from "axios";
import "./DevicesList.css";

class DevicesList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedDevice: null,
      description: "",
      location: "",
      maximumConsumption: 0,
    };
  }

  handleCreateDevice = () => {
    const { description, location, maximumConsumption } = this.state;
    const { selectedUser } = this.props;

    const newDevice = {
      description,
      location,
      maximumConsumption,
      accountID: selectedUser.id,
    };

    const token = localStorage.getItem('token'); 

   
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };
    
    axiosInstance
      .post("http://localhost:8083/device-management/devices/addDevice", newDevice,config)
      .then((res) => {
        console.log("Added new device");
        alert("SUCCESS IN CREATING NEW DEVICE!");
        this.props.fetchDevices();
      })
      .catch((error) => {
        console.error("Error in adding a new device", error);
        alert("Creating Device Error");
      });
  }

  handleDeleteDevice = () => {
    const { selectedDevice } = this.state;

    if (!selectedDevice) {
      alert("Please select a device to delete.");
      return;
    }

    const selectedDeviceID = selectedDevice.id;

    const token = localStorage.getItem('token'); 

   
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };


    axiosInstance
      .delete(`http://localhost:8083/device-management/devices/${selectedDeviceID}`,config)
      .then(() => {
        console.log("Deleted device");
        alert("Device deleted successfully!");
        this.props.fetchDevices();
        this.setState({ selectedDevice: null }); // Clear selected device
      })
      .catch((error) => {
        console.error("Error in deleting a device", error);
        alert("Device Deletion Error");
      });
  }

  handleUpdateDevice = () => {
    const { selectedDevice, description, location, maximumConsumption } = this.state;
    const updatedDevice = {
      id: selectedDevice.id,
      description,
      location,
      maximumConsumption,
    };

    const token = localStorage.getItem('token'); 

   
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };

    axiosInstance
      .put(`http://localhost:8083/device-management/devices/${selectedDevice.id}`, updatedDevice,config)
      .then(() => {
        console.log("Updated device");
        alert("Device updated successfully!");
        this.props.fetchDevices();
      })
      .catch((error) => {
        console.error("Error in updating a device", error);
        alert("Device Update Error");
      });
  }

  handleDeviceClick = (device) => {
  
    this.setState({ selectedDevice: device });
  }

  render() {
    const { devices } = this.props;
    const { selectedDevice, description, location, maximumConsumption } = this.state;

    return (
      <div className="devices-list">
        <h2>Devices List</h2>
        <ul className="device-list">
          {devices.map((device) => (
            <li className={`device-list-item ${selectedDevice === device ? 'selected' : ''}`} key={device.id} onClick={() => this.handleDeviceClick(device)}>
              <div className="device-item">
                <div className="device-info">
                  <h3>Device ID: {device.id}</h3>
                  <p>Maximum Consumption: {device.maximumConsumption}</p>
                  <p>Description: {device.description}</p>
                  <p>Location: {device.location}</p>
                  {selectedDevice === device && (
                    <>
                      <input
                        type="text"
                        placeholder="Description"
                        value={description}
                        onChange={(e) => this.setState({ description: e.target.value })}
                      />
                      <input
                        type="text"
                        placeholder="Location"
                        value={location}
                        onChange={(e) => this.setState({ location: e.target.value })}
                      />
                      <input
                        type="number"
                        placeholder="Maximum Consumption"
                        value={maximumConsumption}
                        onChange={(e) => this.setState({ maximumConsumption: e.target.value })}
                      />
                      <button onClick={this.handleUpdateDevice}>UPDATE</button>
                      <button onClick={this.handleDeleteDevice}>DELETE</button>
                    </>
                  )}
                </div>
              </div>
            </li>
          ))}
        </ul>
        <div>
          <input
            type="text"
            placeholder="Description"
            value={description}
            onChange={(e) => this.setState({ description: e.target.value })}
          />
          <input
            type="text"
            placeholder="Location"
            value={location}
            onChange={(e) => this.setState({ location: e.target.value })}
          />
          <input
            type="number"
            placeholder="Maximum Consumption"
            value={maximumConsumption}
            onChange={(e) => this.setState({ maximumConsumption: e.target.value })}
          />
          <button onClick={this.handleCreateDevice}>CREATE</button>
        </div>
      </div>
    );
  }
}

export default DevicesList;
