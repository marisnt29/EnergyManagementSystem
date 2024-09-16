import axios from "axios"

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/",
    headers: {
        post: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "http://localhost:3000, http://localhost:3001",
            "Access-Control-Allow-Headers":
                "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
        }
    }
});

export default axiosInstance;
