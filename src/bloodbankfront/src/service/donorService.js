import axios from 'axios'
import authHeader from './authHeader'


const API_URL = 'http://localhost:8080/donor/';

class DonorService {

    getDonorInformation(email) {
        return axios
            .get(API_URL + 'getDonor', {
                params: {
                    email: email,
                }, 
                headers: authHeader()
            })
    }

    updateDonorInfo(donor) {
        return axios
            .post(API_URL + 'updateDonor', donor , authHeader())
    }

    getDonorAppointments(email) {
        return axios
            .get(API_URL + 'getAppointments', {
                params: {
                    email: email
                },
                headers: authHeader()
            })
    }

}

export default new DonorService();