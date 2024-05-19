<template>
    <div class="container my-7">
        <br/><br/>
        <div class="row">
            <div class="col-md-6 col-sm-6 col-lg-6">
                <div>
                    <img src="@/assets/images/login.webp" alt="login image">
                </div>
            </div>
            <div class="col-md-6 col-sm-6 col-lg-6">
                <div class="contact-form">
                    <div class="section-heading">
                        <h2 class="text-center">Login</h2>
                    </div>
                    <v-form
                        v-model="form"
                        @submit.prevent="onSubmit"
                    >
                        <v-text-field
                            :readonly="loading"
                            :rules="[validateEmail]"
                            class="mb-2"
                            clearable="true"
                            label="Email"
                        ></v-text-field>

                        <v-text-field
                            type="password"
                            :readonly="loading"
                            :rules="[required]"
                            clearable="true"
                            label="Password"
                            placeholder="Enter your password"
                        ></v-text-field>

                        <br>

                        <v-btn
                            :loading="loading"
                            :disabled="formSubmit"
                            block="true"
                            color="success"
                            size="large"
                            type="submit"
                            variant="elevated"
                        >
                            Sign In
                        </v-btn>
                    </v-form>
                </div>
            </div>
        </div>
    </div>
    <footer-component/>
</template>

<script>
import {defineComponent} from 'vue'
import FooterComponent from "@/components/footer/FooterComponent.vue";

export default defineComponent({
    name: 'LoginPage',
    components: {
        FooterComponent,
    },
    data: () => ({
        otp: '111111',
        formSubmit: false,
        loading: false,
        form : {
            email: '',
            password: '',
        },
    }),
    methods: {
        onSubmit() {
            this.formSubmit = true
            this.loading = true
            setTimeout(() => {
                this.loading = false
                this.formSubmit = false
            }, 2000)
        },
        required(v) {
            return !!v || 'Không được để trống'
        },
        validateEmail(v) {
            return /.+@.+/.test(v) || 'Email không hợp lệ' || this.required(v)
        },
    },
})
</script>

<style scoped></style>