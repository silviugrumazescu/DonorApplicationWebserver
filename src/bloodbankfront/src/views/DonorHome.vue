<template>
    <div class="container-fluid w-75">
        <div class="row mb-3">
            <h1 class="text-center">Donor home</h1>
        </div>
        <div class="row mb-3">
            <h2>Pending appointments</h2>
        </div>
        <div class="row mb-5">
            <div class="col border p-4">
                <div class="row">
                    <h5 class="text-center">Create a new appointment</h5>
                </div>
                <div class="row">
                    <label for="selectBloodbank" class="mb-2 mt-2 text-center">Location</label>
                    <Dropdown id="selectBloodbank" v-model="selectedBloodbankId" :options="bloodbanks" optionLabel="name"
                        optionValue="id" class="w-full md:w-14rem" @change="getUnavailableDays"/>
                </div>
                <div class="row">
                    <label for="selectBloodbank" class="mb-2 mt-2 text-center">Date</label>
                    <Calendar v-model="selectedDate" @date-select="dateSelected" dateFormat="yy/mm/dd"
                        :minDate="createAppointmentMinDate" :disabledDates="calendarDisabledDays"/>
                </div>
                <div class="row">
                    <p class="pt-3">Select reminder type</p>
                </div>
                <div class="row">
                    <div class="flex align-items-center">
                        <RadioButton v-model="notifyType" inputId="notify1" name="Email" value="email" />
                        <label for="notify1" class="ml-2">Email</label>
                    </div>
                    <div class="flex align-items-center">
                        <RadioButton v-model="notifyType" inputId="notify2" name="SMS" value="sms" />
                        <label for="notify2" class="ml-2">SMS</label>
                    </div>
                    
                </div>
                <div class="row mt-4">
                    <Button :onClick="createAppointment" label="Create" />
                </div>
                <div class="row mt-4">
                    <p>{{ createErrorMessage }}</p>
                </div>

            </div>
            <div class="col-8 border">
                <DataTable paginator :rows="5" v-model:selection="selectedAppointment" :value="appointments"
                    tableStyle="min-width: 50rem" selectionMode="single">
                    <Column field="date" header="Date"></Column>
                    <Column field="locationName" header="Location"></Column>
                    <Column field="address" header="Address"></Column>
                    <Column field="doctorName" header="Doctor"></Column>
                </DataTable>
            </div>
            <div class="col border p-4">
                <div class="row">

                </div>
                <div class="row ">
                    <h1 class="text-center">Appointment</h1>
                </div>
                <div class="row mt-2">
                    <p>Location: {{ selectedAppointment.locationName }}</p>
                </div>
                <div class="row">
                    <p>Address: {{ selectedAppointment.address }}</p>
                </div>
                <div class="row">
                    <p>Date: {{ selectedAppointment.date }}</p>
                </div>
                <div class="row">
                    <p>Doctor: {{ selectedAppointment.doctorName }}</p>
                </div>
                <div class="row mt-4">
                    <Button @click="deleteAppointment">Cancel appointment</Button>
                </div>
                <div class="row mt-4">
                    <p>{{ cancelErrorMessage }}</p>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <h2>Confirmed appointments</h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-8 border">
                <DataTable paginator :rows="5" v-model:selection="selectedAppointment" :value="confirmedAppointments"
                    tableStyle="min-width: 50rem">
                    <Column field="date" header="Date"></Column>
                    <Column field="locationName" header="Location"></Column>
                    <Column field="address" header="Address"></Column>
                    <Column field="doctorName" header="Doctor"></Column>
                </DataTable>
            </div>
        </div>

    </div>
</template>

<script>
import Appointment from '../models/Appointment'

import AdminService from '../service/adminService'
import AppointmentService from '../service/appointmentService'
import DonorService from '@/service/donorService'

import Dropdown from 'primevue/dropdown'
import Calendar from 'primevue/calendar'
import Button from 'primevue/button'
import RadioButton from 'primevue/radiobutton'

import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import appointmentService from '../service/appointmentService'



export default {
    name: 'DonorHome',
    components: {
        Dropdown,
        Calendar,
        Button,
        Column,
        DataTable,
        RadioButton
    },
    data() {
        return {
            bloodbanks: [],
            selectedBloodbankId: "",
            selectedDate: "",
            appointments: [],
            notifyType: "",
            confirmedAppointments: [],
            selectedAppointment: {},
            createErrorMessage: "",
            cancelErrorMessage: "",

            calendarDisabledDays: [],

            createAppointmentMinDate: null,
            createAppointmentMaxDate: null
        }
    },
    beforeMount() {
        AdminService.getBloodbanksByDistrict(this.storedUser.district)
            .then(response => {
                this.bloodbanks = response.data;
            })
            .catch(err => {
                console.log(err);
            })

        this.updateAppointments();

    },
    created() {
        let today = new Date();

        this.createAppointmentMinDate = new Date();
        this.createAppointmentMaxDate = new Date(new Date().setDate(today.getDate() + 30));

        console.log(this.createAppointmentMinDate);
        console.log(this.createAppointmentMaxDate);
    },
    methods: {
        dateSelected() {
            console.log(this.selectedDate.getFullYear() + '/' + (this.selectedDate.getMonth() + 1) + '/' + (this.selectedDate.getDate()))
        },
        createAppointment() {
            var formatedDate = this.selectedDate.getFullYear() + '/' + (this.selectedDate.getMonth() + 1) + '/' + (this.selectedDate.getDate());
            const app = new Appointment(this.storedUser.email, this.selectedBloodbankId, formatedDate, this.notifyType);
            AppointmentService.createAppointment(app)
                .then(response => {
                    this.createErrorMessage = "";
                    this.updateAppointments();
                })
                .catch(err => {
                    console.log(err);
                    this.createErrorMessage = err.response.data;
                });

        },
        updateAppointments() {
            DonorService.getDonorAppointments(this.storedUser.email)
                .then(response => {
                    this.appointments = [];
                    this.confirmedAppointments = [];

                    const appoint = response.data;
                    for (let i = 0; i < appoint.length; i++) {
                        if (appoint[i].isConfirmed == true) {
                            this.confirmedAppointments.push(appoint[i]);
                        } else {
                            this.appointments.push(appoint[i]);
                        }
                    }
                })
                .catch(err => {
                    console.log(err)
                })
        },
        deleteAppointment() {
            AppointmentService.deleteAppointment(this.selectedAppointment.appointmentId)
                .then(response => {
                    this.cancelErrorMessage = "";
                    this.updateAppointments()
                    console.log(response.data);
                })
                .catch(err => {
                    console.log(err);
                    this.cancelErrorMessage = err.response.data;
                })
        },

        getUnavailableDays() {
            AppointmentService.getUnavailableDays(this.selectedBloodbankId)
                    .then(response => {
                        console.log(response.data);

                        for(let i = 0; i < response.data.length; i++) {
                            this.calendarDisabledDays[i] = new Date(response.data[i]);
                        }
                    })
        }
    },
    computed: {
        storedUser() {
            return JSON.parse(localStorage.getItem('user'))
        },
    },
}

</script>