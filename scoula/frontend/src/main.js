import './assets/main.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'vue-awesome-paginate/dist/style.css';

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import VueAwesomePaginate from 'vue-awesome-paginate';

import App from './App.vue';
import router from './router';

import { useKakao } from 'vue3-kakao-maps/@utils';
const rest_api_key = '612ef664920dd4b3dfae593b6d3a3b5a'; // Javascript 키 값
useKakao(rest_api_key, ['services']);

const app = createApp(App);

app.use(VueAwesomePaginate);
app.use(createPinia());
app.use(router);

app.mount('#app');
