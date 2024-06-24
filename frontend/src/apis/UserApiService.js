import Axios from "axios";

const API_URL = "http://localhost:7777/api/v1/auth"; // Spring Boot backend URL

class UserApiService {
  joinUser(user) {
    return Axios.post(API_URL + "/register", user);
  }

  loginOk(user) {
    return Axios.post(API_URL + "/login", user)
      .then(response => {
        // Assuming your JWT token is returned in the response
        const token = response.data.accessToken;
        // Save the token in local storage
        localStorage.setItem("jwtToken", token);

        const userId = response.data.userId;
        localStorage.setItem("userId", userId);
        return response;
      });
  }

  logoutOK() {
    // Clear the JWT token from local storage upon logout
    localStorage.removeItem("jwtToken");
    return Axios.post(API_URL + "/logout");
  }
}

export default new UserApiService();
