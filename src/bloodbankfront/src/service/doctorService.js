import axios from 'axios'
import authHeader from './authHeader'

const API_URL = 'http://localhost:8080/doctor/';

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
            .post(API_URL + 'updateDonor', donor, authHeader())
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

    getDoctorAppointments(email) {
        console.log(authHeader())
        return axios
            .get(API_URL + 'getAppointments', {
                headers: authHeader(),
                params: {
                    email: email
                }
                
            })
    }

    getAppointmentsPage(email, page, size) {
        return axios
            .get(API_URL + 'getAppointmentsPage', {
                headers: authHeader(),
                params: {
                    email: email,
                    page: page,
                    size: size
                }
            })
    }

    getDoctorAppointmentsByDate(email, date) {
        return axios
            .get(API_URL + 'getAppointmentsByDate', {
                headers: authHeader(),
                params: {
                    date: date,
                    email: email
                }
            })
    }

    confirmAppointment(appointmentId) {
        return axios
            .post(API_URL + 'confirmAppointment', null, {
                params: {
                    appointmentId: appointmentId
                },
                headers: authHeader()
            })
            .then(response => {
                console.log(response.data)
            })
    }

}

export default new DonorService();