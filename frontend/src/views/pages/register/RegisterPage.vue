<script lang="ts">
import {defineComponent, inject} from "vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import {AuthService} from "@/base/service/auth.service";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import {toast} from "vue3-toastify";
export default defineComponent({
    name: "RegisterPage",
    components: {LoadingComponent, FooterComponent},
    setup() {
        return {
            authService: inject('authService') as AuthService
        }
    },
    data: () => ({
        form: {
            email: '',
            password: '',
            confirmPassword: '',
            username: '',
        },
        otpRequest : {
            email: '',
            otp: ''
        },
        formSubmit: false,
        loading: false,
        isVerifyStep: false
    }),
    methods: {
        register() {
            this.formSubmit = true
            this.loading = true
            this.authService.register(this.form).then((response) => {
                toast.success(response.data.message)
                this.isVerifyStep = true
            }).catch((error) => {
                toast.error(error.response.data.message)
            }).finally(() => {
                this.loading = false
                this.formSubmit = false
            })
        },
        verify() {
            this.formSubmit = true
            this.loading = true
            this.otpRequest.email = this.form.email
            this.authService.verifyEmail(this.otpRequest).then((response) => {
                toast.success(response.data.message)
            }).catch((error) => {
                toast.error(error.response.data.message)
            }).finally(() => {
                this.loading = false
                this.formSubmit = false
            })
        }
    }
})
</script>

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
                        <h2 class="text-center">Đăng ký</h2>
                    </div>
                    <form @submit.prevent="register" v-if="!isVerifyStep">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" v-model="form.email"
                                   placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label for="password">Mật khẩu</label>
                            <input type="password" class="form-control" id="password" v-model="form.password"
                                   placeholder="Password">
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Xác nhận mật khẩu</label>
                            <input type="password" class="form-control" id="confirmPassword"
                                   v-model="form.confirmPassword" placeholder="Confirm Password">
                        </div>
                        <div class="form-group">
                            <label for="username">Tên đăng nhập</label>
                            <input type="text" class="form-control" id="username" v-model="form.username"
                                   placeholder="Username">
                        </div>
                        <button type="submit" class="btn btn-primary">Đăng ký</button>
                    </form>
                    <div v-else>
                        <h4 class="text-center">Nhập mã OTP</h4>
                        <v-otp-input v-model="otpRequest.otp"/>
                        <div class="d-flex justify-content-center align-items-center mt-3">
                            <button @click.prevent="verify" class="btn btn-primary">Xác nhận</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer-component/>
    <loading-component :loading="loading"/>
</template>

<style>
.form-group {
    margin-bottom: 1rem;
}
</style>