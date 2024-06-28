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
        },
        {
            path: '/admin',
            component: () => import('@/views/pages/admin/AdminPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/user',
            component: () => import('@/views/pages/admin/user/UserPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/user/:id',
            component: () => import('@/views/pages/admin/user/UserDetailPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/category',
            component: () => import('@/views/pages/admin/category/CategoryPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/category/:id',
            component: () => import('@/views/pages/admin/category/CategoryDetailPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/brand',
            component: () => import('@/views/pages/admin/brand/BrandPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/brand/:id',
            component: () => import('@/views/pages/admin/brand/BrandDetailPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/product',
            component: () => import('@/views/pages/admin/product/ProductPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/product/:id',
            component: () => import('@/views/pages/admin/product/ProductDetailPage.vue'),
            meta: {
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/order',
            component: () => import('@/views/pages/admin/order/OrderPage.vue'),
        },
        {
            path: '/admin/order/:id',
            component: () => import('@/views/pages/admin/order/OrderDetailPage.vue'),
        },
        {
            path: '/admin/post',
            component: () => import('@/views/pages/admin/post/PostPage.vue'),
        },
        {
            path: '/admin/post/:id',
            component: () => import('@/views/pages/admin/post/PostDetailPage.vue'),
        },
        {
            path: '/admin/chart',
            component: () => import('@/views/pages/admin/chart/ChartPage.vue'),
        },
        {
            path: '/admin/voucher',
            component: () => import('@/views/pages/admin/voucher/VoucherPage.vue'),
        },

        {
            path: '/admin/voucher/:id',
            component: () => import('@/views/pages/admin/voucher/VoucherDetailPage.vue'),
        },
        {
            path: '/:pathMatch(.*)*',
            component: () => import('@/components/NotFoundComponent.vue')
        },

    ]
})
router.beforeEach((to, from, next) => {
    const isAuthenticated = store.getters.isAuthenticated;
    const isAdmin = store.getters.isAdmin;
    if (to.meta?.requiresAuth && !isAuthenticated) {
        next({
            path: '/login',
            query: {redirect: to.fullPath}
        });
    } else if (to.meta?.isAdmin && !isAdmin) {
        next('/home');
    } else {
        next();
    }
});
export default router
