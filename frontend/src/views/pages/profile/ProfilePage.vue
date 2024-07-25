<template>
    <header-component/>
    <div class="container">
        <section class="py-5 my-5">
            <div class="container">
                <h1 class="mb-5">Thông tin cá nhân</h1>
                <div class="bg-white shadow rounded-lg d-block d-sm-flex">
                    <div class="profile-tab-nav border-right">
                        <div class="p-4">
                            <div class="img-circle text-center mb-3" @click.prevent="clickAvatar" title="Thay đổi ảnh đại diện">
                                <v-avatar size="100" :image="user.avatar !== null ? 'data:image/png;base64,' + user.avatar : '/src/assets/images/default.jpg'"></v-avatar>
                            </div>
                            <br>
                            <v-file-input v-show="false" id="file" v-model="image" label="Chọn ảnh" accept="image/*"></v-file-input>
                        </div>
                        <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist"
                             aria-orientation="vertical">
                            <a class="nav-link d-flex align-items-center" id="account-tab" data-bs-toggle="pill"
                               data-bs-target="#account"
                               role="tab" aria-controls="account" aria-selected="true">
                                <i class="bi bi-person-circle me-2"></i>
                                <span class="d-flex align-items-center">Tài khoản</span>
                            </a>
                            <a class="nav-link d-flex align-items-center" id="password-tab" data-bs-toggle="pill"
                               data-bs-target="#password"
                               role="tab" aria-controls="password" aria-selected="false">
                                <i class="bi bi-key-fill me-2"></i>
                                <span class="d-flex align-items-center">Mật khẩu</span>
                            </a>
                            <router-link to="/" class="nav-link d-flex align-items-center">
                                <i class="bi bi-house-door-fill me-2"></i>
                                Quay về trang chủ
                            </router-link>
                        </div>
                    </div>
                    <div class="tab-content p-4 p-md-5" id="v-pills-tabContent">
                        <div class="tab-pane fade show active" id="account" role="tabpanel"
                             aria-labelledby="account-tab">
                            <h3 class="mb-4">Tài khoản</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Họ tên</label>
                                        <input type="text" class="form-control" v-model="user.fullName">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Địa chỉ</label>
                                        <input type="text" class="form-control" v-model="user.address">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="text" class="form-control" :value="user.email" disabled>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>SDT</label>
                                        <input type="text" class="form-control" v-model="user.phone">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Ngày tham gia</label>
                                        <input type="text" class="form-control" :value="formatDate(user?.createDate)"
                                               disabled>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button class="btn btn-primary" @click.prevent="update">Cập nhật</button>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="password" role="tabpanel" aria-labelledby="password-tab">
                            <h3 class="mb-4">Đổi mật khẩu</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Mật khẩu cũ</label>
                                        <input type="password" class="form-control"
                                               v-model="changePasswordRequest.oldPassword">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Mật khẩu mới</label>
                                        <input type="password" class="form-control"
                                               v-model="changePasswordRequest.newPassword">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Nhập lại mật khẩu</label>
                                        <input type="password" class="form-control"
                                               v-model="changePasswordRequest.confirmPassword">
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button class="btn btn-primary" @click.prevent="changePasswordRequest">Cập nhật</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <footer-component/>
</template>

<script lang="ts">
import {User} from '@/base/model/user.model';
import {defineComponent, inject} from 'vue';
import {toast} from 'vue3-toastify';
import axios from 'axios';
import {UserService} from '@/base/service/user.service';
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";

export default defineComponent({
    name: 'ProfilePage',
    components: {FooterComponent, HeaderComponent},
    setup() {
        return {
            userService: inject('userService') as UserService
        }
    },
    data() {
        return {
            user: new User(),
            changePasswordRequest: {
                username: '',
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
            } as any,
            uploadFile: {} as any,
            image : [] as File[]
        }
    },
    methods: {
        formatDate(date: number) {
            return new Date(date).toLocaleDateString();
        },
        // async changePassword() {
        //     this.userService.changePassword(this.changePasswordRequest);
        // },
        async uploadFileData() {
            const file = document.getElementById('file') as HTMLInputElement;
            const formData = new FormData();
            if (file && file.files && file.files.length > 0) {
                formData.append('file', file?.files[0]);
                try {
                    const response = await axios.post('api/user/upload', formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                            'Authorization': localStorage.getItem('access_token')
                        }
                    });
                    if (response.status == 200) {
                        toast.success("Cập nhật ảnh đại diện thành công");
                        this.user.avatar = response.data;
                        localStorage.setItem('user', JSON.stringify(this.user));
                    }
                } catch (error: any) {
                    toast.error(error.response.data);
                }
            }
        },
        async update() {
            if (this.image.length > 0) {
                const reader = new FileReader();
                const readerPromise = new Promise((resolve, reject) => {
                    reader.onload = () => {
                        resolve(reader.result);
                    };
                    reader.onerror = (error) => {
                        reject(error);
                    };
                });
                reader.readAsDataURL(this.image[0]);
                try {
                    const imageData = await readerPromise;
                    this.user.avatarBase64 = imageData as string;
                } catch (error) {
                    toast.error('Error reading file');
                }
            }
            const payload = {
                fullName: this.user.fullName,
                address: this.user.address,
                email: this.user.email,
                phone : this.user.phone,
                avatarBase64: this.user.avatarBase64
            }
            this.userService.update(payload).then((res) => {
                this.user = res;
                toast.success('Cập nhật thông tin thành công');
            }, (error) => {
                toast.error(error.response.data);
            });
        },
        clickAvatar(){
            document.getElementById('file')?.click();
        },
        getUser() {
            this.userService.profile().then((data) => {
                this.user = data;
            });
        }
    },
    created() {
        this.getUser();
    }

})

</script>

<style scoped>

body {
    background: #f9f9f9;
    font-family: "Roboto", sans-serif;
}

.shadow {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1) !important;
}

.profile-tab-nav {
    min-width: 250px;
}

.tab-content {
    flex: 1;
}

.form-group {
    margin-bottom: 1.5rem;
}

.nav-pills a.nav-link {
    padding: 15px 20px;
    border-bottom: 1px solid #ddd;
    border-radius: 0;
    color: #333;
}

.nav-pills a.nav-link i {
    width: 20px;
}

.img-circle img {
    height: 100px;
    width: 100px;
    border-radius: 100%;
    border: 5px solid #fff;
}</style>