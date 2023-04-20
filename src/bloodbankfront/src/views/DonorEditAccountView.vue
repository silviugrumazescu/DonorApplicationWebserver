<template>
    <div class="container" style="max-width: 40%;">
        <div class="card">
            <div class="card-body p-4">
                <h4 class="mb-4">My account</h4>
                <div class="card-center border rounded ps-5 pe-5 pb-3 pt-3">
                    <div class="row mb-3">
                        <label for="editedEmail" class="mb-2 mt-2">Email</label>
                        <InputText id="editedEmail" disabled v-model="currentUser.email" />
                    </div>
                    <div class="row mb-3">
                        <label for="editedName" class="mb-2 mt-2">Name</label>
                        <InputText id="editedName" v-model="currentUser.name" />
                    </div>
                    <div class="row mb-3">
                        <label for="editedCNP" class="mb-2 mt-2">CNP</label>
                        <InputText id="editedCNP" v-model="currentUser.CNP" />
                    </div>
                    <div class="row mb-3">
                        <label for="editedDistrict" class="mb-2 mt-2">District</label>
                        <Dropdown id="editedDistrict" v-model="currentUser.district" :options="districtList"
                            class="w-full md:w-14rem" />
                    </div>
                    <div class="row mb-3">
                        <label for="editedDistrict" class="mb-2 mt-2">Bloodtype</label>
                        <Dropdown id="editedDistrict" v-model="currentUser.bloodType" :options="bloodTypes"
                            class="w-full md:w-14rem" />
                    </div>

                </div>
                <div class="row mb-3 mt-3 ms-1 me-1">
                    <Button :onClick="updateAccount" label="Update account" />
                </div>
                <div class="row text-center">
                    <p>{{ errorMessage }}</p>
                </div>

            </div>
        </div>
    </div>
</template>

<script>

import Dropdown from 'primevue/dropdown'
import InputText from 'primevue/inputtext'
import Card from 'primevue/card'
import Button from 'primevue/button'

import Donor from '@/models/Donor'

import AdminService from '../service/adminService'
import DonorService from '../service/donorService'

export default {
    name: 'DonorEditAccountView',
    components: {
        Dropdown,
        InputText,
        Donor,
        Card,
        Button
    },
    data() {
        return {
            currentUser: {},
            districtList: [],
            bloodTypes: ['Aplus', 'Aminus', 'Bplus', 'Bminus', 'ABplus', 'ABminus', 'Oplus', 'Ominus'],
            errorMessage: ""
        }
    },
    methods: {
        updateInfo() {
            AdminService.getDistricts()
                .then(response => {
                    this.districtList = []
                    this.districtList = response.data;
                    this.currentUser = { district: 'SUCEAVA' }
                })
                .catch(err => {
                    console.log(err)
                })
            DonorService.getDonorInformation(this.storedEmail)
                .then(response => {
                    this.currentUser = response.data;
                })
        },
        updateAccount() {
            DonorService.updateDonorInfo(this.currentUser)
                .then(response => {
                    this.errorMessage = response.data;
                })
                .catch(err => {
                    this.errorMessage = err;
                });
        }
    },
    computed: {
        storedEmail() {
            return JSON.parse(localStorage.getItem('user')).email
        }
    },
    beforeMount() {
        this.updateInfo();
    },

}

</script>

<style></style>