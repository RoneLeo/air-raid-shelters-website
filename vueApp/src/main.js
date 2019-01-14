import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import dict from './components/common/dict';
import common from './components/common/commonFn';
import qs from 'qs';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';    // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import '../static/css/icon.css';
import '../static/ueditor/ueditor.config.js'
import '../static/ueditor/ueditor.all.min.js'
import '../static/ueditor/lang/zh-cn/zh-cn.js'
import "babel-polyfill";

console.log(process.env.NODE_ENV)
// if(process.env.NODE_ENV === 'development') {
//     console.log(111);
// }
axios.defaults.baseURL= process.env.NODE_ENV === 'development' ? '/api' : 'http://182.151.22.247:8081';
// http request 拦截器（所有发送的请求都要从这儿过一次）
axios.interceptors.request.use(
    config => {
        const uuid = localStorage.getItem("uuid"); //获取存储在本地的token
        // config.data = qs.stringify(config.data);
        // console.log(config.data)
        // config.headers = {
        //     'Content-Type': 'application/x-www-form-urlencoded', //参数格式设置
        // };
        if (uuid) {
            config.headers.Authorization = "Token"; //携带权限参数
            config.headers.uuid = uuid; //用户id
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
// http response 拦截器（所有接收到的请求都要从这儿过一次）
axios.interceptors.response.use(
    response => {
        let status = response.status;
        let statusText = response.statusText;
        if(status == 200){
            return response.data;
        }else{
            alert(status + ':' + statusText);
        }
    },
    error => {
        let errData = error.response.data;
        let status = errData.status;
        let path = errData.path;
        alert('code: '+ status + ', path: ' + path);
        return Promise.reject(error.response.data)
    }
);
Vue.use(ElementUI, { size: 'small' });
Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.prototype.$dict = dict;
Vue.prototype.$common = common;

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    const role = localStorage.getItem('ms_username');
    if(!role && to.path !== '/login'){
        next('/login');
    }else if(to.meta.permission){
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin' ? next() : next('/403');
    }else{
        // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
        if(navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor'){
            Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        }else{
            next();
        }
    }
})

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');