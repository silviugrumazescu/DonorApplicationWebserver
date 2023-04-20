import axios from 'axios'

const API_URL = 'http://localhost:8080/donor/';

class AppointmentService {

    createAppointment(app) {
        return axios
            .post(API_URL + 'createAppointment', app)
    }

    deleteAppointment(appointmentId) {
        return axios
            .delete(API_URL + 'deleteAppointment/' + appointmentId);
    }

    
}

export default new AppointmentService();