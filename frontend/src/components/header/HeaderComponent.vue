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
                                    <router-link v-for="(item,index) in items"  :key="index" :to="item.uri" class="dropdown-item">{{item.title}}</router-link>
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
import {computed, defineComponent, onBeforeUnmount, onMounted, ref, watch} from "vue";
import {useStore} from "vuex";
import {useRoute} from "vue-router";

export default defineComponent({
    name: "HeaderComponent",
    props: {
        showHeader: {
            type: Boolean,
            default: true,
        },
    },
    setup(props) {
        const store = useStore();
        const route = useRoute();
        const isShow = ref(props.showHeader);
        const productUri = ['/product', '/products', '/product/:id'];
        const aboutUri = ['/about'];
        const contactUri = ['/contact'];
        const cartUri = ['/cart'];
        const menu = ref(false);
        const items = [
            {title: 'Tài khoản', uri: '/profile'},
            {title: 'Đơn hàng', uri: '/order'},
            {title: 'Đăng xuất', uri: '/logout'},
        ];

        const isAuthenticated = computed(() => store.getters.isAuthenticated);
        const isAdmin = computed(() => store.getters.isAdmin);
        if (isAdmin.value) {
            items.push({title: 'Quản lý', uri: '/admin'});
        }
        const activeMenu = (uri: string[]) => {
            let currentUri = route.path;
            return uri.includes(currentUri);
        };

        const scrollHandler = () => {
            let scroll = window.scrollY || document.documentElement.scrollTop;
            let header = document.querySelector('header')?.offsetHeight || 0;

            if (scroll >= header) {
                document.querySelector('header')?.classList.add('background-header');
            } else {
                document.querySelector('header')?.classList.remove('background-header');
            }
        };

        onMounted(() => {
            window.addEventListener('scroll', scrollHandler);
        });

        onBeforeUnmount(() => {
            window.removeEventListener('scroll', scrollHandler);
        });

        watch(isShow, (value) => {
            console.log('isShow changed:', value);
            if (value) {
                window.addEventListener('scroll', scrollHandler);
            } else {
                window.removeEventListener('scroll', scrollHandler);
            }
        });

        return {
            isShow,
            isAuthenticated,
            activeMenu,
            productUri,
            aboutUri,
            contactUri,
            cartUri,
            menu,
            items,
        };
    },
});
</script>

<style scoped>
@import "style.css";
</style>
