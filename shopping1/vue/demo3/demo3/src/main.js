import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import './css/global.css'
import VueCookies from 'vue-cookies'


import router from './router'

Vue.use(ElementUI);
Vue.use(VueCookies);


new Vue({
  router,
  render:h=>h(App)
}).$mount('#app')