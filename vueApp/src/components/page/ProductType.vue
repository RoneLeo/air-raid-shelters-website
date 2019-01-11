<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 产品类型</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">

        </div>
        <div class="container">
            <el-table :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="name" label="类型名"></el-table-column>
                <el-table-column label="操作"  align="left" width="200">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 20px 8px">
                <el-button type="primary" @click="add">增加产品类型</el-button>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                modelTitle: '添加信息',
                url: './static/vuetable.json',
                tableData: [],

            }
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {

            getData() {
                this.$axios.post('/api/equipmentType/findAllByGsid').then((res) => {
                    if(res.resCode == 200){
                        this.tableData = res.data;
                    }
                });
            },

            add(){
                this.$prompt('请输入新的产品类型名称', '增加产品类型', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({ value }) => {
                    if(value != '') {
                        this.$axios.post('/api/equipmentType/add', this.$qs.stringify({name: value})).then((res) => {
                            this.getData();
                            this.$message.success(res.resMsg);
                        });
                    }
                }).catch(() => {

                });
            },

            handleEdit(index, row) {
                this.$prompt('请输入新的产品类型名称', '修改产品类型 > ' + row.name, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({ value }) => {
                    if(value != '') {
                        this.$axios.post('/api/equipmentType/update', this.$qs.stringify({id: row.id, name: value, gsid: row.gsid})).then((res) => {
                            this.getData();
                            this.$message.success(res.resMsg);
                        });
                    }
                }).catch(() => {

                });
            },
            handleDelete(index, row) {
                this.$confirm('此操作将删除<' + row.name + '>类型及其所有的产品, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/api/equipmentType/delete', this.$qs.stringify({id: row.id})).then((res) => {
                        this.getData();
                        this.$message.success('已删除！');
                    });
                }).catch(() => {
                });
            },
        }
    }

</script>

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
