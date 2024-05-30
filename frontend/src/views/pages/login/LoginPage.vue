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
                        <h2 class="text-center">Đăng nhập</h2>
                    </div>
                    <v-form
                        @submit.prevent="onSubmit"
                    >
                        <v-text-field
                            :readonly="loading"
                            :rules="[validateUsername]"
                            class="mb-2"
                            v-model="form.username"
                            label="Tài khoản"
                        ></v-text-field>

                        <v-text-field
                            type="password"
                            :readonly="loading"
                            :rules="[required]"
                            v-model="form.password"
                            label="Mật khẩu"
                            placeholder="Mật khẩu"
                        ></v-text-field>
                        <v-checkbox
                            v-model="form.rememberMe"
                            label="Nhớ tôi"></v-checkbox>
                        <v-btn
                            :loading="loading"
                            :disabled="formSubmit"
                            color="success"
                            size="large"
                            type="submit"
                            variant="elevated"
                        >
                            Đăng nhập
                        </v-btn>
<!--                        chưa có tài khoản-->
                        <div>
                            Chưa có tài khoản?
                            <router-link to="/register">Đăng ký ngay</router-link>
                        </div>
                    </v-form>
                </div>
            </div>
        </div>
    </div>
    <footer-component/>
</template>

<script lang="ts">
import {defineComponent, inject} from 'vue'
import FooterComponent from "@/components/footer/FooterComponent.vue";
import {AuthService} from "@/base/service/auth.service";
import {toast} from "vue3-toastify";
export default defineComponent({
    name: 'LoginPage',
    components: {
        FooterComponent,
    },
    setup(){
        return {
            authService : inject('authService') as AuthService
        }
    }
    ,
    data: () => ({
        formSubmit: false,
        loading: false,
        form : {
            username: '',
            password: '',
            rememberMe: false
        },
    }),
    methods: {
        onSubmit() {
            this.formSubmit = true
            this.loading = true
            this.authService.login(this.form).then((response) => {
                toast.success('Đăng nhập thành công');
                this.$store.dispatch('login', response.data) // Gọi action login
                const redirect = this.$route.query.redirect as string;
                if (redirect) {
                    this.$router.push(redirect);
                } else {
                    this.$router.push('/');
                }
            }).catch((error) => {
                toast.error(error.response.data.message)
            }).finally(() => {
                this.loading = false
                this.formSubmit = false
            })
        },
        required(v : any) {
            return !!v || 'Không được để trống'
        },
        validateUsername(v : any) {
            return !!v || 'Không được để trống'
        },
    },
    created(){
        console.log('store',this.$store.state.user)
    }
})
</script>

<style scoped></style>