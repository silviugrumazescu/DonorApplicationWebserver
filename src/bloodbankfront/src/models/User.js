export default class User {
    constructor(email, password, name, cnp, district, role, bloodType, phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.cnp = cnp;
        this.district = district;
        this.role = role;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
    }
}