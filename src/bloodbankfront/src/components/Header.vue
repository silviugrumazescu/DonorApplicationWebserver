<template>
  <nav class="navbar fixed-top navbar-expand-lg navbar-light mb-5">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Blood bank</a>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="#">Home</a>
          </li>
          <li class="nav-item">
            <!-- <a class="nav-link active" aria-current="page" href="#">Login</a> -->
            <router-link class="nav-link" to="/login" v-if="!getLoginStatus">Login</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/register" v-if="!getLoginStatus">Register</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/admin" v-if="isAdmin && getLoginStatus">Main</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/donorHome" v-if="isDonor && getLoginStatus">Appointments</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/editAccount" v-if="isDonor && getLoginStatus">Profile</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/doctorHome" v-if="isDoctor && getLoginStatus">Appointments</router-link>
          </li>

        </ul>
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <li class="nav-item" v-if="getLoginStatus">
            <router-link @click="logout" class="nav-link" to="/login">Log out</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Header',
  computed: {
    getLoginStatus() {
      console.log(this.$store.state.auth.status.loggedIn);
      return this.$store.state.auth.status.loggedIn;
    },
    isAdmin() {
      if (this.$store.state.auth.user != null)
        return this.$store.state.auth.user.role.localeCompare("Admin") == 0;
      else return false;
    },
    isDonor() {
      if (this.$store.state.auth.user != null)
        return this.$store.state.auth.user.role.localeCompare("Donor") == 0;
      else return false;
    },
    isDoctor() {
      if (this.$store.state.auth.user != null)
        return this.$store.state.auth.user.role.localeCompare("Doctor") == 0;
      else return false;  
    }

  },
  data() {
    return {
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
    }
  }
}
</script>