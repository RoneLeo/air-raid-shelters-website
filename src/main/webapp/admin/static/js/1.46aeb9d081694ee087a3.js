webpackJsonp([1],{"45aM":function(t,e){},"8+FI":function(t,e,s){"use strict";const n=new(s("+VlJ").default);e.a=n},MpTN:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=s("8+FI"),i={data:()=>({collapse:!1,fullscreen:!1,name:"linxin",message:2}),computed:{username(){let t=localStorage.getItem("ms_username");return t||this.name}},methods:{updateMM(){this.$prompt("请输入新密码","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(({value:t})=>{this.$axios.post("/api/user/updateMm",this.$qs.stringify({mm:t})).then(t=>{this.$message({type:"success",message:t.resMsg})})}).catch(()=>{})},handleCommand(t){"loginout"==t&&(localStorage.removeItem("ms_username"),this.$router.push("/login"))},collapseChage(){this.collapse=!this.collapse,n.a.$emit("collapse",this.collapse)},handleFullScreen(){let t=document.documentElement;this.fullscreen?document.exitFullscreen?document.exitFullscreen():document.webkitCancelFullScreen?document.webkitCancelFullScreen():document.mozCancelFullScreen?document.mozCancelFullScreen():document.msExitFullscreen&&document.msExitFullscreen():t.requestFullscreen?t.requestFullscreen():t.webkitRequestFullScreen?t.webkitRequestFullScreen():t.mozRequestFullScreen?t.mozRequestFullScreen():t.msRequestFullscreen&&t.msRequestFullscreen(),this.fullscreen=!this.fullscreen}},mounted(){document.body.clientWidth<1500&&this.collapseChage()}},l={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"header"},[s("div",{staticClass:"collapse-btn",on:{click:t.collapseChage}},[s("i",{staticClass:"el-icon-menu"})]),t._v(" "),s("div",{staticClass:"logo"},[t._v("人防门户网站管理系统 V1.0")]),t._v(" "),s("div",{staticClass:"header-right"},[s("div",{staticClass:"header-user-con"},[s("div",{staticClass:"btn-fullscreen",on:{click:t.handleFullScreen}},[s("el-tooltip",{attrs:{effect:"dark",content:t.fullscreen?"取消全屏":"全屏",placement:"bottom"}},[s("i",{staticClass:"el-icon-rank"})])],1),t._v(" "),t._m(0),t._v(" "),s("el-dropdown",{staticClass:"user-name",attrs:{trigger:"click"},on:{command:t.handleCommand}},[s("span",{staticClass:"el-dropdown-link"},[t._v("\n                    "+t._s(t.username)+" "),s("i",{staticClass:"el-icon-caret-bottom"})]),t._v(" "),s("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[s("el-dropdown-item",{nativeOn:{click:function(e){return t.updateMM(e)}}},[t._v("修改密码")]),t._v(" "),s("el-dropdown-item",{attrs:{divided:"",command:"loginout"}},[t._v("退出登录")])],1)],1)],1)])])},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"user-avator"},[e("img",{attrs:{src:"static/img/user-logo.png"}})])}]};var a=s("C7Lr")(i,l,!1,function(t){s("nZCe")},"data-v-4650ef3b",null).exports,o={data:()=>({collapse:!1,items:[{icon:"iconfont icon-gongsi",index:"company",title:"公司信息"},{icon:"iconfont icon-yonghu",index:"user",title:"用户信息"},{icon:"iconfont icon-wenjian",index:"files",title:"文件管理"},{icon:"iconfont icon-xinwen",index:"news",title:"新闻管理"},{icon:"iconfont icon-gongcheng",index:"projectCase",title:"工程案例"},{icon:"iconfont icon-chanpinC",index:"products",title:"产品管理"},{icon:"iconfont icon-daogouzhaopin",index:"recruitment",title:"招聘信息管理"},{icon:"iconfont icon-chanpinguanli",index:"productType",title:"产品类型管理"}]}),computed:{onRoutes(){return this.$route.path.replace("/","")}},created(){n.a.$on("collapse",t=>{this.collapse=t})}},c={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"sidebar"},[s("el-menu",{staticClass:"sidebar-el-menu",attrs:{"default-active":t.onRoutes,collapse:t.collapse,"background-color":"#324157","text-color":"#bfcbd9","active-text-color":"#20a0ff","unique-opened":"",router:""}},[t._l(t.items,function(e){return[e.subs?[s("el-submenu",{key:e.index,attrs:{index:e.index}},[s("template",{slot:"title"},[s("i",{class:e.icon}),s("span",{attrs:{slot:"title"},slot:"title"},[t._v(t._s(e.title))])]),t._v(" "),t._l(e.subs,function(e){return[e.subs?s("el-submenu",{key:e.index,attrs:{index:e.index}},[s("template",{slot:"title"},[t._v(t._s(e.title))]),t._v(" "),t._l(e.subs,function(e,n){return s("el-menu-item",{key:n,attrs:{index:e.index}},[t._v("\n                                "+t._s(e.title)+"\n                            ")])})],2):s("el-menu-item",{key:e.index,attrs:{index:e.index}},[t._v("\n                            "+t._s(e.title)+"\n                        ")])]})],2)]:[s("el-menu-item",{key:e.index,attrs:{index:e.index}},[s("i",{class:e.icon}),s("span",{attrs:{slot:"title"},slot:"title"},[t._v(t._s(e.title))])])]]})],2)],1)},staticRenderFns:[]};var r=s("C7Lr")(o,c,!1,function(t){s("45aM"),s("pkTL")},"data-v-e698f80c",null).exports,u={data:()=>({tagsList:[]}),methods:{isActive(t){return t===this.$route.path},closeTags(t){const e=this.tagsList.splice(t,1)[0],s=this.tagsList[t]?this.tagsList[t]:this.tagsList[t-1];s?e.path===this.$route.fullPath&&this.$router.push(s.path):this.$router.push("/")},closeAll(){this.tagsList=[],this.$router.push("/company")},closeOther(){const t=this.tagsList.filter(t=>t.path===this.$route.fullPath);this.tagsList=t},setTags(t){this.tagsList.some(e=>e.path===t.path)||(this.tagsList.length>=8&&this.tagsList.shift(),this.tagsList.push({title:t.meta.title,path:t.path,name:t.matched[1].components.default.name})),n.a.$emit("tags",this.tagsList)},handleTags(t){"other"===t?this.closeOther():this.closeAll()}},computed:{showTags(){return this.tagsList.length>0}},watch:{$route(t,e){this.setTags(t)}},created(){this.setTags(this.$route)}},d={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return t.showTags?s("div",{staticClass:"tags"},[s("ul",t._l(t.tagsList,function(e,n){return s("li",{key:n,staticClass:"tags-li",class:{active:t.isActive(e.path)}},[s("router-link",{staticClass:"tags-li-title",attrs:{to:e.path}},[t._v("\n                "+t._s(e.title)+"\n            ")]),t._v(" "),s("span",{staticClass:"tags-li-icon",on:{click:function(e){t.closeTags(n)}}},[s("i",{staticClass:"el-icon-close"})])],1)}),0),t._v(" "),s("div",{staticClass:"tags-close-box"},[s("el-dropdown",{on:{command:t.handleTags}},[s("el-button",{attrs:{size:"mini",type:"primary"}},[t._v("\n                标签选项"),s("i",{staticClass:"el-icon-arrow-down el-icon--right"})]),t._v(" "),s("el-dropdown-menu",{attrs:{slot:"dropdown",size:"small"},slot:"dropdown"},[s("el-dropdown-item",{attrs:{command:"other"}},[t._v("关闭其他")]),t._v(" "),s("el-dropdown-item",{attrs:{command:"all"}},[t._v("关闭所有")])],1)],1)],1)]):t._e()},staticRenderFns:[]};var m={data:()=>({tagsList:[],collapse:!1}),components:{vHead:a,vSidebar:r,vTags:s("C7Lr")(u,d,!1,function(t){s("q6D0")},null,null).exports},created(){n.a.$on("collapse",t=>{this.collapse=t}),n.a.$on("tags",t=>{let e=[];for(let s=0,n=t.length;s<n;s++)t[s].name&&e.push(t[s].name);this.tagsList=e})}},h={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"wrapper"},[s("v-head"),t._v(" "),s("v-sidebar"),t._v(" "),s("div",{staticClass:"content-box",class:{"content-collapse":t.collapse}},[s("v-tags"),t._v(" "),s("div",{staticClass:"content"},[s("keep-alive",{attrs:{exclude:t.tagsList}},[t.$route.meta.keepAlive?s("router-view"):t._e()],1),t._v(" "),t.$route.meta.keepAlive?t._e():s("router-view")],1)],1)],1)},staticRenderFns:[]},p=s("C7Lr")(m,h,!1,null,null,null);e.default=p.exports},nZCe:function(t,e){},pkTL:function(t,e){},q6D0:function(t,e){}});