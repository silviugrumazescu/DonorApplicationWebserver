import axios from 'axios'
import authHeader from './authHeader'

const API_URL = 'http://localhost:8080/admin/';

class AdminService {

    getDistricts() {
        return axios
            .get(API_URL + 'getDistricts', {
                headers: authHeader()
            })       
    }

    getDoctorsByDistrict(dist) {
        return axios
            .get(API_URL + 'getDoctorsByDistrict', { 
                params: {
                    district: dist,
                },
                headers: authHeader()
            })
    }

    getDoctorData(email) {
        return axios
            .get(API_URL + 'getDoctor', {
                params: {
                    email: email,
                }, 
                headers: authHeader()
            })
    }

    getBloodbanksByDistrict(district) {
        return axios
            .get(API_URL + 'getBloodbanksByDistrict', {
                params: {
                    district: district,
                },
                headers: authHeader()
            })
    }

    updateDoctorAccount(doctor) {
        return axios
            .post(API_URL + 'updateDoctorAccount', doctor, authHeader())
    }

    createDoctorAccount(doctor) {
        return axios
            .post(API_URL + 'createDoctorAccount', doctor, authHeader())
    }

    deleteDoctorAccount(doctorEmail) {
        return axios
            .delete(API_URL + 'deleteDoctorAccount/' + doctorEmail, {
                headers: authHeader()
            });
    }
}

export default new AdminService();