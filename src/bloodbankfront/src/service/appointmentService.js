import axios from 'axios'
import authHeader from './authHeader'

const API_URL = 'http://localhost:8080/donor/';

class AppointmentService {

    createAppointment(app) {
        return axios
            .post(API_URL + 'createAppointment', app, authHeader())
    }

    deleteAppointment(appointmentId) {
        return axios
            .delete(API_URL + 'deleteAppointment/' + appointmentId, {
                headers: authHeader()
            });
    }

    getUnavailableDays(bloodbankId) {
        return axios
            .get(API_URL + 'getUnavailableDays/' + bloodbankId, {
                headers: authHeader()
            })
    }

    
}

export default new AppointmentService();