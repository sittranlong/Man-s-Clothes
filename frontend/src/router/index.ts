import {createRouter, createWebHistory} from 'vue-router'
import store from "@/plugins/store";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: () => import('@/views/pages/home/HomePage.vue'),
        },
        {
            path: '/home',
            component: () => import('@/views/pages/home/HomePage.vue')
        },
        {
            path: '/index',
            component: () => import('@/views/pages/home/HomePage.vue')
        },
        {
            path: '/about',
            component: () => import('@/views/pages/about/AboutPage.vue')
        },
        {
            path: '/contact',
            component: () => import('@/views/pages/contact/ContactPage.vue')
        },
        {
            path: '/product',
            component: () => import('@/views/pages/product/ProductPage.vue')
        },
        {
            path: '/cart',
            component: () => import('@/views/pages/cart/CartPage.vue'),
        },
        {
            path: '/login',
            component: () => import('@/views/pages/login/LoginPage.vue'),
        },
        {
            path: '/register',
            component: () => import('@/views/pages/register/RegisterPage.vue'),
        },
        {
            path: '/logout',
            component: () => import('@/views/pages/logout/LogoutPage.vue'),
            meta: {
                requiredAuth: true
            }
        }
    ]
})
router.beforeEach((to, from, next) => {
    const isAuthenticated = store.getters.isAuthenticated;
    const isAdmin = store.getters.isAdmin;
    if (to.meta.requiresAuth && !isAuthenticated) {
        next({
            path: '/login',
            query: {redirect: to.fullPath}
        });
    } else if (to.meta.isAdmin && !isAdmin) {
        next('/home');
    } else {
        next();
    }
});
export default router
