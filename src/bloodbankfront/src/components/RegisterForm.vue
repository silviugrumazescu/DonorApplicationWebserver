<template>
    <section class="vh-100 gradient-custom">
        <div class="container py-5 h-100">
            <div class="row justify-content-center align-items-center">
                <div class="col-6">
                    <div class="card bg-dark text-white" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">
                            <div class="row d-flex justify-content-center align-items-center h-100">
                                <h2 class="fw-bold mb-2 text-uppercase">Register</h2>
                                <p class="text-white-50 mb-5">Please enter your register information</p>
                            </div>
                            <div class="row d-flex justify-content-center align-items-center h-100">
                                <div class="col">
                                    <div class="form-outline form-white mb-4">
                                        <input v-model="user.email" type="email" id="emailInput"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="emailInput">Email</label>
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <input v-model="user.name" type="name" id="nameInput"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="nameInput">Full name</label>
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <input v-model="user.cnp" type="cnp" id="cnpInput"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="cnpInput">CNP</label>
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <input v-model="user.phoneNumber" type="phoneNumber" id="phoneInput"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="phoneInput">Phone Number</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-outline form-white mb-4">
                                        <input v-model="user.password" type="password" id="passwordInput"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="passwordInput">Password</label>
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <input v-model="repeatedPassword" type="password" id="repeatPasswordInput"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="repeatPasswordInput">Repeat password</label>
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <select v-model="user.district" class="form-select"
                                            aria-label="Default select example" id="districtSelect">
                                            <option v-for="district in districtList"
                                                v-bind:value="district">
                                                {{ district }}
                                            </option>
                                        </select>
                                        <label class="form-label" for="districtInput">District</label>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="row d-flex justify-content-center align-items-center h-100 mb-5">
                                    <div class="col">
                                        <select v-model="selectedBloodType" class="form-select"
                                            aria-label="Default select example" id="bloodTypeSelect">
                                            <option v-for="bloodType in bloodTypes" v-bind:value="bloodType">
                                                {{ bloodType }}
                                            </option>
                                        </select>
                                        <label class="form-label" for="bloodTypeSelect">Blood Type</label>
                                    </div>
                                    <div class="col">
                                        <select v-model="selectedRhFactor" class="form-select"
                                            aria-label="Default select example" id="bloodTypeSelect">
                                            <option v-for="rhFactor in rhFactors"
                                                v-bind:value="{ name: rhFactor.name, value: rhFactor.value }">
                                                {{ rhFactor.name }}
                                            </option>
                                        </select>
                                        <label class="form-label" for="rhFactorSelect">Rh factor</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row d-flex justify-content-center align-items-center h-100">
                                <button @click="handleRegister"
                                    class="btn btn-outline-light btn-lg px-5 mb-5 limit-button w-50"
                                    type="submit">Register</button>
                                <p>{{ errorMessage }}</p>
                                <div class="mb-3">
                                    <p class="mb-0">Already have an account?</p>
                                    <router-link to="/login">Login</router-link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import { useRoute } from 'vue-router'
import User from '../models/User'
import AdminService from '../service/adminService'

export default {
    name: "RegisterForm",
    data() {
        return {
            user: new User("", "", "", "", "", "", "", ""),
            errorMessage: "",
            bloodTypes: ['A', 'B', 'AB', 'O'],
            rhFactors: [
                { name: 'Positive', value: 'plus' },
                { name: 'Negative', value: 'minus' },
            ],
            selectedBloodType: '',
            selectedRhFactor: '',
            repeatedPassword: '',
            districtList: []
        }
    },
    methods: {
        handleRegister() {
            this.user.bloodType = this.selectedBloodType + this.selectedRhFactor.value;

            if (this.repeatedPassword.localeCompare(this.user.password) != 0) {
                this.errorMessage = "Passwords do not match";
            }
            else {
                console.log(this.user);
                this.$store.dispatch('register', this.user).then(
                    data => {
                        console.log("SUCCESS")
                        this.errorMessage = data;
                        this.user = new User("", "", "", "", "", "", "");
                        this.repeatedPassword = ""
                    },
                    error => {
                        console.log(error);
                        this.errorMessage = error.response.data;
                    }
                )
            }


        }
    },
    beforeCreate() {
        AdminService.getDistricts()
            .then(response => {
                this.districtList = []
                this.districtList = response.data;
            })
            .catch(err => {
                console.log(err)
            })
    }
}


</script>

<style>
.limit-button {
    max-width: 50%;
}</style>