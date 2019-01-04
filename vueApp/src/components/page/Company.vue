<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 公司信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="display: flex;justify-content: center;">
            <div class="company-box" style="min-width: 900px; max-width: 95%; height: auto;padding: 20px 0 50px;">
                <el-form ref="companyForm" :model="companyForm" label-width="120px" size="medium">
                    <el-form-item label="公司名称" style="display: block;width: 98%">
                        <el-input v-model="companyForm.gsmc" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="经纬度">
                        <el-input v-model="companyForm.jwd" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="联系地址">
                        <el-input v-model="companyForm.lxdz" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话">
                        <el-input v-model="companyForm.lxdh" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="联系传真">
                        <el-input v-model="companyForm.lxcz" :disabled="disabled"></el-input>
                    </el-form-item>

                    <el-form-item label="联系邮编">
                        <el-input v-model="companyForm.lxyb" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="联系邮箱">
                        <el-input v-model="companyForm.lxyx" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="公司网站">
                        <el-input v-model="companyForm.gswz" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="备案信息">
                        <el-input v-model="companyForm.baxx" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="公司简介" style="display: block;width: 98%">
                        <el-input v-model="companyForm.gsjj" type="textarea" :autosize="{ minRows: 8, maxRows: 15}" :disabled="disabled"s></el-input>
                    </el-form-item>
                </el-form>
                <div style="display: flex;justify-content: center;">
                    <el-button size="medium" type="primary" icon="el-icon-lx-edit" style="margin-right: 20px" :disabled="!disabled"
                               @click="edit">编辑
                    </el-button>
                    <el-button size="medium" type="success" icon="el-icon-check" @click="save" :disabled="disabled">保存</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                companyForm: {},
                disabled: true
            }
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {
            edit() {
                this.disabled = false;
            },
            save() {
                this.$axios.post('/api/contactUs/update', this.$qs.stringify(Object.assign({}, this.companyForm))).then((res) => {
                    this.disabled = true;
                });
            },
            modelClose() {
                this.form = {};
                this.$refs[form].resetFields();
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios.post('/api/contactUs/findByGsid').then((res) => {
                        this.companyForm = res.data;
                });
            },

            add(){
                this.form = {};
                this.modelVisible = true;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.form = Object.assign({}, row);
                this.modelVisible = true;
            },
            handleDelete(index, row) {
                this.$axios.post('/api/recruitment/delete', this.$qs.stringify({id: row.id})).then(res => {
                        this.getData();
                })

            },
            delAll() {
                const length = this.multipleSelection.length;
                let str = '';
                this.del_list = this.del_list.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].name + ' ';
                }
                this.$message.error('删除了' + str);
                this.multipleSelection = [];
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                let url = '/api/recruitment/add';
                if(this.form.id) {
                    url = '/api/recruitment/update'
                }
                this.$axios.post(url, this.$qs.stringify(Object.assign({},this.form))).then(res => {
//                    this.addLoading = false
                    this.modelVisible = false;
                    this.getData();
//                    if(this.activeName == 'first') {
//                        this.getData(1, this.page, this.size);
//                    }else {
//                        this.getData(2, this.page1, this.size1);
//                    }
                })
            },
            // 确定删除
            deleteRow(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
            }
        }
    }

</script>
<style>
    .company-box .el-form-item {
        width: 49%;
        display: inline-block;
    }
    .company-box .el-form-item--mini.el-form-item, .company-box .el-form-item--small.el-form-item {
        margin-bottom: 22px;
    }
    .company-box .el-input.is-disabled .el-input__inner {
        background-color: #fff;
        color: #303133;
    }
    .company-box .el-textarea.is-disabled .el-textarea__inner {
        background-color: #fff;
        color: #303133;
    }
</style>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
</style>
