import {createApp, provide} from 'vue';
import App from './App.vue';
import router from './router';
import 'vue3-toastify/dist/index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './assets/css/fontawesome.css';
import 'owl.carousel/dist/assets/owl.carousel.css';
import './assets/css/flex-slider.css';
import './assets/css/templatemo-sixteen.css';
import './assets/css/main.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import {DependencyInjection} from "@/dj";
import axios from 'axios'
import VueAxios from 'vue-axios'
import vuetify from '@/plugins/vuetify';
import Vue3Toast, { toast } from 'vue3-toastify';
import {ProductService} from "@/base/service/product.service";
const options = {
    position: 'bottom-right',
    timeout: 3000,
    closeOnClick: true,
    pauseOnFocusLoss: true,
    pauseOnHover: true,
    draggable: true,
    draggablePercent: 0.6,
    showCloseButtonOnHover: false,
    hideProgressBar: false,
    closeButton: 'button',
    icon: true,
    rtl: false,
};
const app = createApp(App)
axios.defaults.baseURL = 'http://localhost:3000/api';
app.use(router)
    .use(vuetify)
    .use(Vue3Toast, options)
    .use(DependencyInjection)
    .use(VueAxios, axios)
    .mount('#app');
app.config.globalProperties.$toast = toast;
