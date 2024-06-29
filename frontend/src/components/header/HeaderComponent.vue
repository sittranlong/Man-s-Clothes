<template>
    <div>
        <header v-if="isShow">
            <nav class="navbar navbar-expand-lg">
                <div class="container">
                    <a class="navbar-brand" href="index.html">
                        <h2>Men <em>Shop</em></h2>
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarResponsive">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item" role="button" title="product">
                                <router-link :to="'/product'"
                                             :class="{'nav-link': true, 'active': activeMenu(productUri)}"
                                             id="product">Sản phẩm
                                </router-link>
                            </li>
                            <li class="nav-item" role="button" title="about">
                                <router-link :to="'/about'"
                                             :class="{'nav-link': true, 'active': activeMenu(aboutUri)}"
                                             id="about">Về chúng tôi
                                </router-link>
                            </li>
                            <li class="nav-item" role="button" title="contact">
                                <router-link :to="'/contact'"
                                             :class="{'nav-link': true, 'active': activeMenu(contactUri)}"
                                             id="contact">Liên hệ
                                </router-link>
                            </li>
                            <li class="nav-item" role="button" title="cart" v-if="isAuthenticated">
                                <router-link :to="'/cart'"
                                             :class="{'nav-link': true, 'active': activeMenu(cartUri)}"
                                             id="cart">
                                    <i class="bi bi-cart p-1"></i>
                                    Giỏ hàng
                                </router-link>
                            </li>
                            <li class="nav-item" role="button" title="login" v-if="!isAuthenticated">
                                <router-link :to="'/login'"
                                             :class="{'nav-link': true, 'active': activeMenu(cartUri)}"
                                             id="login">
                                    Đăng nhập
                                </router-link>
                            </li>
                            <!--                           tạo ra 1 dropdown để action khi người dùng đã đăng nhập-->
                            <li class="nav-item dropdown" v-if="isAuthenticated">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                   data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="bi bi-person p-1"></i>
                                    Tài khoản
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <router-link v-for="(item,index) in items" :key="index" :to="item.uri" class="dropdown-item">{{item.title}}</router-link>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
    </div>
</template>

<script lang="ts">
import {defineComponent, inject, ref} from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { WishlistService } from "@/base/service/wishlist.service";

export default defineComponent({
    name: "HeaderComponent",
    props: {
        showHeader: {
            type: Boolean,
            default: true,
        },
    },
    setup() {
        return {
            route: useRoute(),
            wishListService: inject('wishlistService') as WishlistService,
        };
    },
    data() {
        return {
            isShow: this.showHeader,
            productUri: ['/product', '/products', '/product/:id'],
            aboutUri: ['/about'],
            contactUri: ['/contact'],
            cartUri: ['/cart'],
            menu: false,
            items: [
                { title: 'Tài khoản', uri: '/profile' ,order : 1},
                { title: 'Đơn hàng', uri: '/order', order : 2 },
                { title: 'Đăng xuất', uri: '/logout', order : 4 },
            ],
            isAuthenticated: false,
            isAdmin: false,
        };
    },
    methods: {
        activeMenu(uri: string[]) {
            const currentUri = this.route.path;
            return uri.includes(currentUri);
        },
        scrollHandler() {
            let scroll = window.scrollY || document.documentElement.scrollTop;
            let header = document.querySelector('header')?.offsetHeight || 0;

            if (scroll >= header) {
                document.querySelector('header')?.classList.add('background-header');
            } else {
                document.querySelector('header')?.classList.remove('background-header');
            }
        },
        countWishlist() {
            this.wishListService.countAll().then((response) => {
                this.items.push({ title: 'Yêu thích (' + response + ')', uri: '/wishlist' , order : 3});
            });
            // sort menu

        },
    },
    created() {
        const store = useStore();
        this.isAuthenticated = store.getters.isAuthenticated;
        this.isAdmin = store.getters.isAdmin;

        if (this.isAdmin) {
            this.items.push({ title: 'Quản lý', uri: '/admin' , order : 5});
        }

        window.addEventListener('scroll', this.scrollHandler);
        this.countWishlist();
        this.items.sort((a, b) => a.order - b.order);
    },
    beforeUnmount() {
        window.removeEventListener('scroll', this.scrollHandler);
    },
});
</script>

<style scoped>
@import "style.css";
/* CSS scoped để áp dụng cho component */
</style>
