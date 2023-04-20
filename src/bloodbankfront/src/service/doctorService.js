import axios from 'axios'

const API_URL = 'http://localhost:8080/doctor/';

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

    getDoctorAppointments(email) {
        return axios
            .get(API_URL + 'getAppointments', {
                params: {
                    email: email
                }
            })
    }

    getDoctorAppointmentsByDate(email, date) {
        return axios
            .get(API_URL + 'getAppointmentsByDate', {
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
                }
            })
            .then(response => {
                console.log(response.data)
            })
    }

}

export default new DonorService();