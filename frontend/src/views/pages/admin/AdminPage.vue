<template>
    <div class="container-fluid">
        <div class="row flex-nowrap">
            <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-dark">
                <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
                    <a href="/"
                       class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                        <span class="fs-5 d-none d-sm-inline text-uppercase">Quản lý dữ liệu</span>
                    </a>
                    <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start"
                        id="menu">
                        <li class="nav-item">
                            <a href="#" class="nav-link align-middle px-0" @click.prevent="$router.push('/chart')">
                                <i class="bi bi-bar-chart-line-fill"></i> <span
                                class="ms-1 d-none d-sm-inline">Thống kê</span>
                            </a>
                        </li>
                        <li>
                            <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                                <i class="bi bi-database-fill"></i> <span class="ms-1 d-none d-sm-inline">Quản lý dữ liệu bảng</span>
                            </a>
                            <ul class="collapse show nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
                                <li class="w-100" v-for="(item,index) in component" :key="index">
                                    <a class="nav-link px-0 d-flex m-3" @click.prevent="$router.push(item.routerLink)"
                                       role="button">
                                        <span class="d-none d-sm-inline" :title="item.label">{{ item.label }}</span>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <hr>
                    <div class="dropdown pb-4 position-fixed bottom-0 start-0">
                        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
                           id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                           <v-avatar size="50" :image="'data:image/png;base64,' + userCurrent?.avatar"></v-avatar>
                            <span class="d-none d-sm-inline mx-1">{{userCurrent?.username}}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
                            <li><a class="dropdown-item" @click.prevent="logout()">Đăng xuất</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col py-3">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import  {User} from "@/base/model/user.model";
import {useRouter} from "vue-router";
import {useStore} from "vuex";

export default defineComponent({
    name: "AdminPage",
    components: {},
    data() {
        return {
            component: [
                {name: 'user', show: false, label: 'Quản lý người dùng', routerLink: '/admin/user'},
                {name: 'category', show: false, label: 'Quản lý danh mục', routerLink: '/admin/category'},
                {name: 'brand', show: false, label: 'Quản lý thương hiệu', routerLink: '/admin/brand'},
                {name: 'color', show: false, label: 'Quản lý màu', routerLink: '/admin/color'},
                {name: 'size', show: false, label: 'Quản lý kích cỡ', routerLink: '/admin/size'},
                {name: 'product', show: false, label: 'Quản lý sản phẩm', routerLink: '/admin/product'},
                {name: 'post', show: false, label: 'Quản lý bài viết', routerLink: '/admin/post'},
                {name: 'voucher', show: false, label: 'Quản lý voucher', routerLink: '/admin/voucher'},
                {name: 'order', show: false, label: 'Quản lý đơn hàng', routerLink: '/admin/order'},
            ],
            userCurrent : this.store.state.user as User
        }
    },
    methods: {
        logout() {
            this.router.push('/logout');
        }
    },
    created() {
    }
    ,
    setup(){
        return {
            router : useRouter(),
            store : useStore()
        }
    }

})
</script>

<style scoped>

a {
    color: #fff;
}
</style>