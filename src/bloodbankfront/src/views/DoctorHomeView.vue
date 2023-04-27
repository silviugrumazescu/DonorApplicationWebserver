<template>
    <div class="container">

        <div class="row">
            <div class="col shadow p-5">
                <div class="row">
                    <h1>Today's appointments</h1>
                </div>
                <div class="row">
                    <DataTable paginator :rows="5" v-model:selection="selectedAppointment" :value="currentDayAppointments"
                        selectionMode="single" tableStyle="min-width: 50rem">
                        <Column field="formatedDate" header="Date"></Column>
                        <Column field="donorName" header="Donor"></Column>
                        <Column field="isConfirmed" header="Is Confirmed"></Column>
                    </DataTable>
                </div>
                <div class="row">
                    <h1>All appointments</h1>
                </div>
                <div class="row">
                    <DataTable lazy paginator :rows="5" :totalRecords="appointmentsTotalRecords" v-model:selection="selectedAppointment" :value="appointments"
                        selectionMode="single" tableStyle="min-width: 50rem" @page="onPage($event)">
                        <Column field="formatedDate" header="Date"></Column>
                        <Column field="donorName" header="Donor"></Column>
                        <Column field="isConfirmed" header="Is Confirmed"></Column>
                    </DataTable>
                </div>

            </div>
            <div class="col">
                <Card>
                    <template #title> Appointment </template>
                    <template #content>
                        <p>Date: {{ selectedAppointment.formatedDate }}</p>
                        <p>Donor: {{ selectedAppointment.donorName }}</p>
                        <p>Is Confirmed: {{ selectedAppointment.isConfirmed }}</p>
                        <Button :disabled="isSelectedConfirmed" label="Confirm" @click="confirmAppointment" />
                    </template>
                </Card>
            </div>
        </div>

    </div>
</template>

<script>

import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Card from 'primevue/card'
import Button from 'primevue/button'

import AppointmentService from '@/service/appointmentService';

import DoctorService from '../service/doctorService'
import doctorService from '../service/doctorService';


export default {
    name: 'DoctorHomeView',
    components: {
        DataTable,
        Column,
        Card,
        Button
    },
    data() {
        return {
            appointments: [],
            appointmentsTotalRecords: 0,
            appointmentsLoading: false,
            tableLazyParams: {
                nrRows: 5,
                page: 0
            },
            currentDayAppointments: [],
            selectedAppointment: {},
            currentDate: new Date(),

        }
    },
    methods: {
        getAllAppointments() {
            DoctorService.getDoctorAppointments(this.storedUser.email)
                .then(response => {
                    this.appointments = response.data;
                })
        },
        getCurrentDayAppointments() {
            DoctorService.getDoctorAppointmentsByDate(this.storedUser.email, new Date())
                .then(response => {
                    this.currentDayAppointments = response.data;
                })
        },
        loadLazyAppointments() {
            DoctorService.getAppointmentsPage(this.storedUser.email, this.tableLazyParams.page, this.tableLazyParams.nrRows)
            .then(response => {
                console.log(response);
                this.appointments = response.data.appointments;
                this.appointmentsTotalRecords = response.data.numberOfRecords;
            })
        },
        onPage(event){
            this.tableLazyParams.nrRows = event.rows;
            this.tableLazyParams.page = event.page;
            this.loadLazyAppointments();
        },
        confirmAppointment() {
            DoctorService.confirmAppointment(this.selectedAppointment.appointmentId)
            .then(response => {
                this.updateAppointments();
            });
            
        },
        updateAppointments() {
            this.getCurrentDayAppointments();
            this.loadLazyAppointments();
        }

    },
    computed: {
        storedUser() {
            return JSON.parse(localStorage.getItem('user'))
        },
        isSelectedConfirmed() {
            return this.selectedAppointment.isConfirmed
        }
    },
    beforeMount() {
        this.updateAppointments();
    }
}

</script>