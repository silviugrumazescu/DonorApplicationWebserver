import axios from 'axios'

const API_URL = 'http://localhost:8080/auth/';

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'signin', {
                email: user.email,
                password: user.password
            })
            .then(response => {
                if(response.data) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                }
                return response.data;
            })
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        console.log("sent :" + user.bloodType);
        return axios.post(API_URL + 'signup', {
            email: user.email,
            name: user.name,
            cnp: user.cnp,
            password: user.password,
            district: user.district,
            bloodType: user.bloodType,
            phoneNumber: user.phoneNumber
        })
    }

}

export default new AuthService();
