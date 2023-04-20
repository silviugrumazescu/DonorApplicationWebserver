import axios from 'axios'

const API_URL = 'http://localhost:8080/admin/';

class AdminService {

    getDistricts() {
        return axios
            .get(API_URL + 'getDistricts')       
    }

    getDoctorsByDistrict(dist) {
        return axios
            .get(API_URL + 'getDoctorsByDistrict', { 
                params: {
                    district: dist,
                }
            })
    }

    getDoctorData(email) {
        return axios
            .get(API_URL + 'getDoctor', {
                params: {
                    email: email,
                }
            })
    }

    getBloodbanksByDistrict(district) {
        return axios
            .get(API_URL + 'getBloodbanksByDistrict', {
                params: {
                    district: district,
                }
            })
    }

    updateDoctorAccount(doctor) {
        return axios
            .post(API_URL + 'updateDoctorAccount', doctor)
    }

    createDoctorAccount(doctor) {
        return axios
            .post(API_URL + 'createDoctorAccount', doctor)
    }

    deleteDoctorAccount(doctorEmail) {
        return axios
            .delete(API_URL + 'deleteDoctorAccount/' + doctorEmail);
    }
}

export default new AdminService();