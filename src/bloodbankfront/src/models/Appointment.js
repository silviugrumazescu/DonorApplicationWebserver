export default class Appointment {
    constructor(donorEmail, bloodbankId, date, notifyType) {
        this.donorEmail = donorEmail;
        this.bloodbankId = bloodbankId;
        this.date = date;
        this.notifyType = notifyType;
    }
}