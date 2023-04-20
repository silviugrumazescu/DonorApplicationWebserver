<template>
    <div class="row">
        <div class="col ms-5 p-5 border">
            <div class="row mb-3">
                <h2>View/Edit accounts</h2>
            </div>
            <div class="row">
                <Dropdown v-model="selectedDistrict" :options="districtList"
                @change="updateDoctorsList" placeholder="Select a district" class="w-full md:w-14rem" />
            </div>
            <div class="row mt-2">
                <Dropdown v-model="selectedDoctor" :options="doctors" optionLabel="name"
                @change="updateFieldData" placeholder="Select a doctor"/>
            </div>
            <div class="row">
                <label for="editedEmail" class="mb-2 mt-2 text-center">Email</label>
                <InputText id="editedEmail" v-model="editedDoctor.email" disabled/>
                <label for="editedName" class="mb-2 mt-2 text-center">Name</label>
                <InputText id="editedName" v-model="editedDoctor.name" />
                <label for="editedCNP" class="mb-2 mt-2 text-center">CNP</label>
                <InputText id="editedCNP" v-model="editedDoctor.CNP" />
                <label for="editedDistrict" class="mb-2 mt-2 text-center">District</label>
                <Dropdown id="editedDistrict" v-model="editedDoctor.district" :options="districtList"
                @change="updateBloodbanksList" class="w-full md:w-14rem" />
                 <label for="editedBloodbank" class="mb-2 mt-2 text-center">Bloodbank</label>
                <Dropdown id="editedBloodbank" v-model="editedDoctor.bloodbankId" :options="bloodbanks" optionLabel="name" optionValue="id"
                 class="w-full md:w-14rem" />
            </div>
            <div class="row mt-4 mb-4">
                <Button :onClick="updateAccount" label="Update account"/>
            </div>
            <div class="row mt-4 mb-4">
                <Button :onClick="deleteAccount" label="Delete account"/>
            </div>
            <div class="row text-center">
                <p>{{ editErrorMessage }}</p>
            </div>
        </div>
        <div class="col ms-5 p-5 border">
            <div class="row mb-3">
                <h2>Create doctor account</h2>
            </div>
            <div class="row">
                <label for="createEmail" class="mb-2 mt-2 text-center">Email</label>
                <InputText id="createEmail" v-model="createDoctor.email" />
                <label for="createName" class="mb-2 mt-2 text-center">Name</label>
                <InputText id="createName" v-model="createDoctor.name" />
                <label for="createPassword" class="mb-2 mt-2 text-center">Password</label>
                <InputText id="createPassword" v-model="createDoctor.password" />
                <label for="createCNP" class="mb-2 mt-2 text-center">CNP</label>
                <InputText id="createCNP" v-model="createDoctor.CNP" />
                <label for="createDistrict" class="mb-2 mt-2 text-center">District</label>
                <Dropdown id="createDistrict" v-model="createDoctor.district" :options="districtList"
                @change="updateBloodbanksListCreate" class="w-full md:w-14rem" />
                 <label for="createBloodbank" class="mb-2 mt-2 text-center">Bloodbank</label>
                <Dropdown id="createBloodbank" v-model="createDoctor.bloodbankId" :options="bloodbanksCreate" optionLabel="name" optionValue="id"
                 class="w-full md:w-14rem" />
            </div>
            <div class="row mt-4 mb-4">
                <Button :onClick="createDoctorAccount" label="Create account"/>
            </div>
            <div class="row text-center">
                <p>{{ errorMessageCreate }}</p>
            </div>
            
        </div>
    </div>
</template>

<script>

import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';

import Doctor from '@/models/Doctor';
import BloodbankPreview from '@/models/BloodbankPreview';
import AdminService from '../service/adminService'

export default {

    name: 'AdminBoardView',
    components: {
        Dropdown,
        InputText,
        Button
    },
    data() {
        return {
            districtList: [],

            // Edit
            selectedDistrict: "",
            doctors: [],
            selectedDoctor: {},
            editErrorMessage: "",
            editedDoctor: new Doctor("", "", "", "", "", ""),
            bloodbanks: [],
            selectedBloodbank: {},

            // Create
            doctorsCreate: [],
            selectedDoctorCreate: {},
            createErrorMessage: "",
            bloodbanksCreate: [],
            errorMessageCreate: "",
            selectedBloodbankCreate: {},

            createDoctor: new Doctor("", "", "", "", "", "")

        }
    },
    methods: {
        updateDoctorsList() {
            AdminService.getDoctorsByDistrict(this.selectedDistrict)
            .then(response => {
                this.doctors = response.data;
            })
            .catch(err => {
                console.log(err);
            })
        },
        updateFieldData() {
            AdminService.getDoctorData(this.selectedDoctor.email)
            .then(response => {
                this.editedDoctor = response.data;
                this.updateBloodbanksList()
            })
            .catch(err => {
                console.log(err);
            })
        },
        updateBloodbanksList() {
            AdminService.getBloodbanksByDistrict(this.editedDoctor.district)
            .then(response => {
                this.bloodbanks = response.data;
            })
        },
        updateBloodbanksListCreate() {
            AdminService.getBloodbanksByDistrict(this.createDoctor.district)
            .then(response => {
                this.bloodbanksCreate = response.data;
            })
        },
        updateAccount() {
            AdminService.updateDoctorAccount(this.editedDoctor)
            .then(response => {
                this.editErrorMessage = response.data;
            })
        },
        deleteAccount() {
            AdminService.deleteDoctorAccount(this.selectedDoctor.email).then(response => {
                this.updateDoctorsList();
            })
        },
        createDoctorAccount() {
            AdminService.createDoctorAccount(this.createDoctor) 
            .then(response => {
                this.errorMessageCreate = response.data;
            })
            .catch(err => {
                this.errorMessageCreate = err;
            })
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