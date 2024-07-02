import { createRouter, createWebHistory } from 'vue-router';

import IndexPage from '../views/IndexPage.vue';
import RecordsPage from '../views/RecordsPage.vue';
import LoginPage from '../views/LoginPage.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/index',
            name: 'index',
            component: IndexPage,
            meta: { requiresAuth: true } // 需要登录才能访问
        },
        {
            path: '/records',
            name: 'records',
            component: RecordsPage,
            meta: { requiresAuth: true } // 需要登录才能访问
        },
        {
            path: '/login',
            name: 'login',
            component: LoginPage
        }
    ]
});

router.beforeEach((to, from, next) => {
    const loggedIn = localStorage.getItem('loggedIn');
    if (to.matched.some(record => record.meta.requiresAuth) && !loggedIn) {
        // 需要登录且未登录，重定向到登录页面
        next('/login');
    } else {
        next();
    }
});

export default router;
