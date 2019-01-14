import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/company'
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            children:[
                {
                    path: '/user',
                    component: resolve => require(['../components/page/User.vue'], resolve),
                    meta: { title: '用户信息', keepAlive: true }
                },
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '系统首页', keepAlive: true }
                },
                {
                    path: '/files',
                    component: resolve => require(['../components/page/Files.vue'], resolve),
                    meta: { title: '文件管理', keepAlive: true }
                },
                {
                    path: '/products',
                    name: 'products',
                    component: resolve => require(['../components/page/Products.vue'], resolve),
                    meta: { title: '产品管理', keepAlive: false }
                },
                {
                    path: '/addProduct',
                    name: 'addProduct',
                    component: resolve => require(['../components/page/AddProduct.vue'], resolve),
                    meta: { title: '产品详情', keepAlive: false }
                },
                {
                    path: '/productType',
                    name: 'productType',
                    component: resolve => require(['../components/page/ProductType.vue'], resolve),
                    meta: { title: '产品类型', keepAlive: true}
                },
                {
                    path: '/news',
                    component: resolve => require(['../components/page/News.vue'], resolve),
                    meta: { title: '新闻管理', keepAlive: true }
                },
                {
                    path: '/projectCase',
                    component: resolve => require(['../components/page/ProjectCase.vue'], resolve),
                    meta: { title: '工程案例', keepAlive: true }
                },
                {
                    path: '/recruitment',
                    component: resolve => require(['../components/page/Recruitment.vue'], resolve),
                    meta: { title: '招聘信息管理', keepAlive: true }
                },
                {
                    path: '/services',
                    component: resolve => require(['../components/page/Services.vue'], resolve),
                    meta: { title: '网上订购列表', keepAlive: true }
                },
                {
                    path: '/company',
                    component: resolve => require(['../components/page/Company.vue'], resolve),
                    meta: { title: '公司信息', keepAlive: true }
                },
                // {
                //     path: '/icon',
                //     component: resolve => require(['../components/page/Icon.vue'], resolve),
                //     meta: { title: '自定义图标' }
                // },
                // {
                //     path: '/table',
                //     component: resolve => require(['../components/page/BaseTable.vue'], resolve),
                //     meta: { title: '基础表格' }
                // },
                // {
                //     path: '/tabs',
                //     component: resolve => require(['../components/page/Tabs.vue'], resolve),
                //     meta: { title: 'tab选项卡' }
                // },
                // {
                //     path: '/form',
                //     component: resolve => require(['../components/page/BaseForm.vue'], resolve),
                //     meta: { title: '基本表单' }
                // },
                // {
                //     // 富文本编辑器组件
                //     path: '/editor',
                //     component: resolve => require(['../components/page/VueEditor.vue'], resolve),
                //     meta: { title: '富文本编辑器' }
                // },
                // {
                //     // markdown组件
                //     path: '/markdown',
                //     component: resolve => require(['../components/page/Markdown.vue'], resolve),
                //     meta: { title: 'markdown编辑器' }
                // },
                // {
                //     // 图片上传组件
                //     path: '/upload',
                //     component: resolve => require(['../components/page/Upload.vue'], resolve),
                //     meta: { title: '文件上传' }
                // },
                // {
                //     // vue-schart组件
                //     path: '/charts',
                //     component: resolve => require(['../components/page/BaseCharts.vue'], resolve),
                //     meta: { title: 'schart图表' }
                // },
                // {
                //     // 拖拽列表组件
                //     path: '/drag',
                //     component: resolve => require(['../components/page/DragList.vue'], resolve),
                //     meta: { title: '拖拽列表' }
                // },
                // {
                //     // 权限页面
                //     path: '/permission',
                //     component: resolve => require(['../components/page/Permission.vue'], resolve),
                //     meta: { title: '权限测试', permission: true }
                // },
                // {
                //     path: '/404',
                //     component: resolve => require(['../components/page/404.vue'], resolve),
                //     meta: { title: '404' }
                // },
                // {
                //     path: '/403',
                //     component: resolve => require(['../components/page/403.vue'], resolve),
                //     meta: { title: '403' }
                // }
            ]
        }
    ]
})
