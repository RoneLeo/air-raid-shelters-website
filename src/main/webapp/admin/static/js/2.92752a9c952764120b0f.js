webpackJsonp([2],{"0k5K":function(t,e){},NVSH:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={name:"basetable",data:()=>({companyForm:{},subcompanyForm:[],disabled:!1,map:{},positionPicker:{},marker:{},map2:{},positionPicker2:{},marker2:{},gsmc:"",newCompanyBoxShow:!1,newCompanyData:{},disabled2:!1}),mounted(){this.$nextTick(()=>{this.initMap(),window.setTimeout(()=>{this.getData()},300)})},created(){this.$axios.get("/company/findGsmcByGsid").then(t=>{this.gsmc=t.data})},computed:{},destoryed(){this.map&&this.map.remove(this.marker),this.map&&this.map.destory()},methods:{goBack(){this.newCompanyBoxShow=!1,this.newCompanyData={}},deleteNewCompany(t){this.$axios.get("/contactUs/delete?id="+t.id).then(e=>{for(let e=0;e<this.subcompanyForm.length;e++)this.subcompanyForm[e].id===t.id&&this.subcompanyForm.splice(e,1);this.$message({message:e.resMsg,type:"success"})})},editNewCompany(t){this.newCompanyBoxShow=!0,this.newCompanyData=t;let e=t.jwd&&t.jwd.split(",");this.positionPicker2.start(new AMap.LngLat(e[1],e[0]))},saveNewCompany(){this.newCompanyData=Object.assign({},this.newCompanyData),this.save(this.newCompanyData,2)},addNewCompany(){this.newCompanyData={jwd:"",lxdz:""},this.newCompanyBoxShow=!0,this.positionPicker2.start(this.map2.getCenter())},initMap(){this.map=new AMap.Map("AMap_container",{zoom:16}),this.map2=new AMap.Map("map2",{zoom:16});let t=!0;"undefined"==typeof AMapUI&&(t=!1,this.$axios.get("http://webapi.amap.com/ui/1.0/main.js?v=1.0.11").then(e=>{"undefined"!=typeof AMapUI?t=!0:this.$message.error("无法加载地图")})),t&&AMapUI.loadUI(["misc/PositionPicker"],t=>{this.positionPicker=new t({mode:"dragMap",map:this.map}),this.positionPicker.on("success",t=>{this.companyForm.jwd=t.position.lat+","+t.position.lng,this.companyForm.lxdz=t.address}),this.positionPicker2=new t({mode:"dragMap",map:this.map2}),this.positionPicker2.on("success",t=>{this.newCompanyData.jwd=t.position.lat+","+t.position.lng,this.newCompanyData.lxdz=t.address})})},setMarker(){let t=this.companyForm.jwd.split(","),e=new AMap.LngLat(t[1],t[0]);this.map&&(this.map.setCenter(e),this.marker=new AMap.Marker({icon:"//vdata.amap.com/icons/b18/1/2.png",position:e,offset:new AMap.Pixel(-12,-12)}),this.marker.setMap(this.map))},edit(){this.disabled=!1,this.map.remove(this.marker);let t=this.companyForm.jwd.split(","),e=new AMap.LngLat(t[1],t[0]);this.positionPicker.start(e)},save(t,e){let a="/contactUs/add";t.id&&(a="/contactUs/update"),this.$axios.post(a,this.$qs.stringify(Object.assign({},t,{gslx:e}))).then(a=>{200===a.resCode?(1===e&&(this.disabled=!0,this.positionPicker.stop(),this.setMarker()),2===e&&(this.newCompanyBoxShow=!1,this.positionPicker2.stop(),this.subcompanyForm.push(t)),this.$message({message:a.resMsg,type:"success"})):this.$message({message:a.resMsg,type:"error"})})},getData(){this.subcompanyForm=[],this.$axios.post("/contactUs/findByGsid").then(t=>{let e=t.data;for(let t=0;t<e.length;t++)1===e[t].gslx?this.companyForm=e[t]:this.subcompanyForm.push(e[t]);null!==this.companyForm?(this.disabled=!0,this.setMarker()):(this.companyForm={gsmc:this.gsmc,lxdh:"",lxdz:"",lxcz:"",gswz:"",lxyb:"",lxyx:""},this.positionPicker.start(this.map.getCenter()))})}}},o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"table"},[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),t._v(" 公司信息")])],1)],1),t._v(" "),a("div",{staticClass:"container"},[a("div",{staticStyle:{"text-align":"right","max-width":"95%","margin-bottom":"10px"}},[a("el-tooltip",{directives:[{name:"show",rawName:"v-show",value:!t.newCompanyBoxShow,expression:"!newCompanyBoxShow"}],staticClass:"item",attrs:{effect:"light",content:"添加分公司",placement:"bottom"}},[a("el-button",{staticStyle:{"box-shadow":"1px 2px 2px 1px #ccc"},attrs:{type:"primary",icon:"el-icon-plus",circle:""},on:{click:t.addNewCompany}})],1),t._v(" "),a("el-tooltip",{directives:[{name:"show",rawName:"v-show",value:t.newCompanyBoxShow,expression:"newCompanyBoxShow"}],staticClass:"item",attrs:{effect:"light",content:"返回主页面",placement:"bottom"}},[a("el-button",{staticStyle:{"box-shadow":"1px 2px 2px 1px #ccc","margin-right":"5px"},attrs:{type:"primary",icon:"el-icon-back",circle:""},on:{click:t.goBack}})],1),t._v(" "),a("el-tooltip",{directives:[{name:"show",rawName:"v-show",value:t.newCompanyBoxShow,expression:"newCompanyBoxShow"}],staticClass:"item",attrs:{effect:"light",content:"保存分公司信息",placement:"bottom"}},[a("el-button",{staticStyle:{"box-shadow":"1px 2px 2px 1px #ccc"},attrs:{type:"success",icon:"el-icon-check",circle:""},on:{click:t.saveNewCompany}})],1)],1),t._v(" "),a("div",{staticStyle:{display:"flex","justify-content":"center"}},[a("div",{directives:[{name:"show",rawName:"v-show",value:!t.newCompanyBoxShow,expression:"!newCompanyBoxShow"}],staticClass:"company-box",staticStyle:{"min-width":"900px","max-width":"95%",height:"auto",padding:"20px 0 50px"}},[a("el-form",{ref:"companyForm",attrs:{model:t.companyForm,"label-width":"120px",size:"medium"}},[a("el-form-item",{staticStyle:{display:"block",width:"98%"},attrs:{label:"公司名称"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.gsmc,callback:function(e){t.$set(t.companyForm,"gsmc",e)},expression:"companyForm.gsmc"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"经纬度"}},[a("el-input",{attrs:{disabled:!0},model:{value:t.companyForm.jwd,callback:function(e){t.$set(t.companyForm,"jwd",e)},expression:"companyForm.jwd"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系地址"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.lxdz,callback:function(e){t.$set(t.companyForm,"lxdz",e)},expression:"companyForm.lxdz"}})],1),t._v(" "),a("el-form-item",{staticStyle:{display:"block",width:"98%"},attrs:{label:""}},[a("div",{staticStyle:{width:"100%",height:"400px"},attrs:{id:"AMap_container"}})]),t._v(" "),a("el-form-item",{attrs:{label:"联系电话"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.lxdh,callback:function(e){t.$set(t.companyForm,"lxdh",e)},expression:"companyForm.lxdh"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系传真"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.lxcz,callback:function(e){t.$set(t.companyForm,"lxcz",e)},expression:"companyForm.lxcz"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系邮编"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.lxyb,callback:function(e){t.$set(t.companyForm,"lxyb",e)},expression:"companyForm.lxyb"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系邮箱"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.lxyx,callback:function(e){t.$set(t.companyForm,"lxyx",e)},expression:"companyForm.lxyx"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"公司网站"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.gswz,callback:function(e){t.$set(t.companyForm,"gswz",e)},expression:"companyForm.gswz"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"备案信息"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.baxx,callback:function(e){t.$set(t.companyForm,"baxx",e)},expression:"companyForm.baxx"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"公司描述"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.gsms,callback:function(e){t.$set(t.companyForm,"gsms",e)},expression:"companyForm.gsms"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"搜索关键字"}},[a("el-input",{attrs:{disabled:t.disabled},model:{value:t.companyForm.gjz,callback:function(e){t.$set(t.companyForm,"gjz",e)},expression:"companyForm.gjz"}})],1),t._v(" "),a("el-form-item",{staticStyle:{display:"block",width:"98%"},attrs:{label:"公司简介"}},[a("el-input",{attrs:{type:"textarea",autosize:{minRows:15,maxRows:30},disabled:t.disabled,s:""},model:{value:t.companyForm.gsjj,callback:function(e){t.$set(t.companyForm,"gsjj",e)},expression:"companyForm.gsjj"}})],1)],1),t._v(" "),a("div",{staticStyle:{display:"flex","justify-content":"center"}},[t.companyForm.id?[a("el-button",{staticStyle:{"margin-right":"20px"},attrs:{size:"medium",type:"primary",icon:"el-icon-lx-edit",disabled:!t.disabled},on:{click:t.edit}},[t._v("编辑\n                        ")]),t._v(" "),a("el-button",{attrs:{size:"medium",type:"success",icon:"el-icon-check",disabled:t.disabled},on:{click:function(e){t.save(t.companyForm,1)}}},[t._v("\n                            保存\n                        ")])]:[a("el-button",{attrs:{size:"medium",type:"success",icon:"el-icon-check",disabled:t.disabled},on:{click:function(e){t.save(t.companyForm,1)}}},[t._v("\n                            保存\n                        ")])]],2),t._v(" "),a("div",{staticClass:"company-list",staticStyle:{"margin-top":"80px"}},t._l(t.subcompanyForm,function(e){return a("div",{key:.989870023*e.id,staticClass:"company-list-item"},[a("div",{staticStyle:{position:"absolute",top:"8px",right:"14px"}},[a("el-button-group",[a("el-tooltip",{staticClass:"item",attrs:{effect:"light",content:"编辑分公司信息",placement:"bottom"}},[a("el-button",{staticStyle:{"box-shadow":"1px 2px 2px 1px #ccc"},attrs:{type:"warning",icon:"el-icon-edit",round:""},on:{click:function(a){t.editNewCompany(e)}}})],1),t._v(" "),a("el-tooltip",{staticClass:"item",attrs:{effect:"light",content:"删除分公司",placement:"bottom"}},[a("el-button",{staticStyle:{"box-shadow":"1px 2px 2px 1px #ccc"},attrs:{type:"danger",icon:"el-icon-delete",round:""},on:{click:function(a){t.deleteNewCompany(e)}}})],1)],1)],1),t._v(" "),a("div",{staticClass:"subtitle"},[t._v(t._s(e.gsmc))]),t._v(" "),a("el-form",{attrs:{model:e,"label-width":"120px",size:"medium"}},[a("el-form-item",{attrs:{label:"联系电话"}},[t._v("\n                                    "+t._s(e.lxdh)+"\n                                ")]),t._v(" "),a("el-form-item",{attrs:{label:"联系邮箱"}},[t._v("\n                                    "+t._s(e.lxyx)+"\n                                ")]),t._v(" "),a("el-form-item",{staticStyle:{width:"98%"},attrs:{label:"联系地址"}},[t._v("\n                                    "+t._s(e.lxdz)+"\n                                ")])],1)],1)}),0)],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:t.newCompanyBoxShow,expression:"newCompanyBoxShow"}],staticClass:"subcompany-box",staticStyle:{"min-width":"900px","max-width":"90%",height:"auto",padding:"20px 0 50px"}},[a("el-form",{ref:"newCompanyForm",attrs:{model:t.newCompanyData,"label-width":"120px",size:"medium"}},[a("el-form-item",{staticStyle:{display:"block",width:"98%"},attrs:{label:"公司名称"}},[a("el-input",{attrs:{disabled:t.disabled2},model:{value:t.newCompanyData.gsmc,callback:function(e){t.$set(t.newCompanyData,"gsmc",e)},expression:"newCompanyData.gsmc"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系电话"}},[a("el-input",{attrs:{disabled:t.disabled2},model:{value:t.newCompanyData.lxdh,callback:function(e){t.$set(t.newCompanyData,"lxdh",e)},expression:"newCompanyData.lxdh"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系传真"}},[a("el-input",{attrs:{disabled:t.disabled2},model:{value:t.newCompanyData.lxcz,callback:function(e){t.$set(t.newCompanyData,"lxcz",e)},expression:"newCompanyData.lxcz"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系邮编"}},[a("el-input",{attrs:{disabled:t.disabled2},model:{value:t.newCompanyData.lxyb,callback:function(e){t.$set(t.newCompanyData,"lxyb",e)},expression:"newCompanyData.lxyb"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系邮箱"}},[a("el-input",{attrs:{disabled:t.disabled2},model:{value:t.newCompanyData.lxyx,callback:function(e){t.$set(t.newCompanyData,"lxyx",e)},expression:"newCompanyData.lxyx"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"经纬度"}},[a("el-input",{attrs:{disabled:!0},model:{value:t.newCompanyData.jwd,callback:function(e){t.$set(t.newCompanyData,"jwd",e)},expression:"newCompanyData.jwd"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"联系地址"}},[a("el-input",{attrs:{disabled:t.disabled2},model:{value:t.newCompanyData.lxdz,callback:function(e){t.$set(t.newCompanyData,"lxdz",e)},expression:"newCompanyData.lxdz"}})],1),t._v(" "),a("el-form-item",{staticStyle:{display:"block",width:"98%"},attrs:{label:""}},[a("div",{staticStyle:{width:"100%",height:"400px"},attrs:{id:"map2"}})])],1)],1)])])])},staticRenderFns:[]};var i=a("C7Lr")(s,o,!1,function(t){a("0k5K"),a("eghF")},"data-v-79424342",null);e.default=i.exports},eghF:function(t,e){}});