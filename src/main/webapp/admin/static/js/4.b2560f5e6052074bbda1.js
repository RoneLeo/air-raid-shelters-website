webpackJsonp([4],{"0YFA":function(t,e){},wRV8:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i={name:"basetable",data:()=>({modelTitle:"工程案例详情",tableData:[],modelVisible:!1,addForm:{},file:{},size:10,page:1,totalElements:0,loading:!0,addLoading:!1,equipmentType:[]}),created(){this.getData()},computed:{},methods:{deleteTP(){this.addForm.tp="",this.file={}},closeClear(){this.$refs.addForm.resetFields()},editFile(t,e){this.addForm=Object.assign({},e),this.modelVisible=!0},delFile(t,e){this.$confirm("此操作将永久删除该案例, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{this.$axios.post("/projectCase/delete",this.$qs.stringify({id:e.id})).then(t=>{this.$message.success("已删除！"),this.getData()})}).catch(()=>{})},getFile(t){this.file=t.target.files[0],this.addForm.file=this.file},handleCurrentChange(t){this.page=t,this.getData()},getData(){this.loading=!0,this.$axios.post("/projectCase/findAllByGsidByPage",this.$qs.stringify({page:this.page,size:this.size})).then(t=>{200==t.resCode&&(this.loading=!1,this.tableData=t.data.content,this.totalElements=t.data.totalElements)})},add(){this.addForm={},this.modelVisible=!0},saveEdit(t){this.$refs.addForm.validate(t=>{if(t){this.addLoading=!0;let t="/projectCase/add";this.addForm.id&&(t="/projectCase/update");let a=new FormData;for(let t in this.addForm)a.append(t,this.addForm[t]);for(var e of a.values())console.log(e);let i={headers:{"Content-Type":"multipart/form-data"}};this.$axios.post(t,a,i).then(t=>{this.addLoading=!1,this.modelVisible=!1,"first"==this.activeName?this.getData(1,this.page,this.size):this.getData(2,this.page1,this.size1)})}})}}},l={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"table"},[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),t._v(" 工程案例")])],1)],1),t._v(" "),a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"container"},[a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:t.tableData}},[a("el-table-column",{attrs:{prop:"gcmc",label:"工程名称",width:"300","show-overflow-tooltip":!0}}),t._v(" "),a("el-table-column",{attrs:{prop:"aljs",label:"案例介绍","show-overflow-tooltip":!0}}),t._v(" "),a("el-table-column",{attrs:{prop:"cjsj",label:"创建时间",width:"160"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"160"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text",icon:"el-icon-lx-edit"},on:{click:function(a){t.editFile(e.$index,e.row)}}},[t._v("编辑\n                    ")]),t._v(" "),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){t.delFile(e.$index,e.row)}}},[t._v("删除")])]}}])})],1),t._v(" "),a("div",{staticStyle:{padding:"28px 8px"}},[a("el-button",{staticStyle:{float:"left"},attrs:{type:"primary"},on:{click:t.add}},[t._v("上传工程案例")]),t._v(" "),a("el-pagination",{staticStyle:{float:"right"},attrs:{"current-page":t.page,"page-size":t.size,layout:"prev, pager, next",total:t.totalElements},on:{"current-change":t.handleCurrentChange}})],1)],1),t._v(" "),a("el-dialog",{attrs:{title:t.modelTitle,visible:t.modelVisible,width:"60%","close-on-click-modal":!1},on:{"update:visible":function(e){t.modelVisible=e},closed:t.closeClear}},[a("el-form",{ref:"addForm",attrs:{model:t.addForm,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"工程名称",prop:"gcmc",rules:[{required:!0,message:"工程名称不能为空",trigger:"blur"}]}},[a("el-input",{model:{value:t.addForm.gcmc,callback:function(e){t.$set(t.addForm,"gcmc",e)},expression:"addForm.gcmc"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"案例介绍",prop:"aljs",rules:[{required:!0,message:"案例介绍不能为空",trigger:"blur"}]}},[a("el-input",{attrs:{type:"textarea",autosize:{minRows:5,maxRows:18}},model:{value:t.addForm.aljs,callback:function(e){t.$set(t.addForm,"aljs",e)},expression:"addForm.aljs"}})],1),t._v(" "),t.addForm.id&&t.addForm.tp?t._e():a("el-form-item",{attrs:{label:"展示图片"}},[a("input",{attrs:{type:"file"},on:{change:function(e){t.getFile(e)}}})]),t._v(" "),t.addForm.tp?a("el-form-item",{attrs:{label:"展示图片"}},[a("div",{staticStyle:{position:"relative",display:"inline-block",width:"auto",height:"auto"}},[a("img",{staticClass:"image",attrs:{src:"http://182.151.22.247:8081"+t.addForm.tp,alt:""}}),t._v(" "),t.addForm.tp?a("i",{staticClass:"el-icon-error image-icon",on:{click:t.deleteTP}}):t._e()])]):t._e()],1),t._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.modelVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary",loading:t.addLoading},on:{click:function(e){t.saveEdit(t.addForm)}}},[t._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var s=a("C7Lr")(i,l,!1,function(t){a("0YFA")},"data-v-58599069",null);e.default=s.exports}});