webpackJsonp([10],{NRjg:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={name:"basetable",data:()=>({modelTitle:"添加信息",url:"./static/vuetable.json",tableData:[]}),created(){this.getData()},computed:{},methods:{getData(){this.$axios.post("/equipmentType/findAllByGsid").then(t=>{200==t.resCode&&(this.tableData=t.data)})},add(){this.$prompt("请输入新的产品类型名称","增加产品类型",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(({value:t})=>{""!=t&&this.$axios.post("/equipmentType/add",this.$qs.stringify({name:t})).then(t=>{this.getData(),this.$message.success(t.resMsg)})}).catch(()=>{})},handleEdit(t,e){this.$prompt("请输入新的产品类型名称","修改产品类型 > "+e.name,{confirmButtonText:"确定",cancelButtonText:"取消"}).then(({value:t})=>{""!=t&&this.$axios.post("/equipmentType/update",this.$qs.stringify({id:e.id,name:t,gsid:e.gsid})).then(t=>{this.getData(),this.$message.success(t.resMsg)})}).catch(()=>{})},handleDelete(t,e){this.$confirm("此操作将删除<"+e.name+">类型及其所有的产品, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{this.$axios.post("/equipmentType/delete",this.$qs.stringify({id:e.id})).then(t=>{this.getData(),this.$message.success("已删除！")})}).catch(()=>{})}}},i={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"table"},[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),t._v(" 产品类型")])],1)],1),t._v(" "),a("div",{staticClass:"handle-box"}),t._v(" "),a("div",{staticClass:"container"},[a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:t.tableData}},[a("el-table-column",{attrs:{prop:"name",label:"类型名"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"left",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(a){t.handleEdit(e.$index,e.row)}}},[t._v("编辑")]),t._v(" "),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){t.handleDelete(e.$index,e.row)}}},[t._v("删除")])]}}])})],1),t._v(" "),a("div",{staticStyle:{padding:"20px 8px"}},[a("el-button",{attrs:{type:"primary"},on:{click:t.add}},[t._v("增加产品类型")])],1)],1)])},staticRenderFns:[]};var n=a("C7Lr")(s,i,!1,function(t){a("hHO3")},"data-v-29104eac",null);e.default=n.exports},hHO3:function(t,e){}});