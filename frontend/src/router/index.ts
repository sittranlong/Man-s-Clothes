import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: () => import('@/views/pages/home/HomePage.vue')
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
        }
    ]
})

export default router
