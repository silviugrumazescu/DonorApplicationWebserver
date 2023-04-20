import axios from 'axios'

const API_URL = 'http://localhost:8080/donor/';

class DonorService {

    getDonorInformation(email) {
        return axios
            .get(API_URL + 'getDonor', {
                params: {
                    email: email,
                }
            })
    }

    updateDonorInfo(donor) {
        return axios
            .post(API_URL + 'updateDonor', donor)
    }

    getDonorAppointments(email) {
        return axios
            .get(API_URL + 'getAppointments', {
                params: {
                    email: email
                }
            })
    }

}

export default new DonorService();