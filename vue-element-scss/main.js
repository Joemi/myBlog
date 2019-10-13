import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Index from './src/index.vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router';
import axios from 'axios';
import VueSimplemde from 'vue-simplemde';
import qs from 'qs';
import $ from 'jquery';
import routers from './src/routes/route.js';

Vue.use(Vuex);
Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(VueSimplemde);

Vue.prototype.$ajax = axios;
Vue.prototype.$qs = qs;


const router = new VueRouter({
  routes:routers
})

router.beforeEach((to,from,next) => {
	console.log(to.meta.requireAuth);
	console.log(sessionStorage.getItem("visitedId"));
	console.log(sessionStorage.getItem("nameId"));

    if(to.meta.requireAuth){
        if(!sessionStorage.getItem("visitedId") && !sessionStorage.getItem("nameId") && !sessionStorage.getItem("adminId")){
        	console.log(333);
        	next({path:'/'});
        }
        else{
        	console.log(222);
        	next();
        }
    }
    else{
    	console.log(111);
        next();
    }
})


new Vue({
	router,
	el: '#app',
	render: h => h(Index),
});
