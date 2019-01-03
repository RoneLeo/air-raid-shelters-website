<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 产品管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" v-loading="loading">
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="cpmc" label="产品名称"></el-table-column>
                <el-table-column prop="wjmc" label="文件名称"></el-table-column>
                <el-table-column prop="cjsj" label="创建时间"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-lx-attention" @click="lookFile(scope.$index, scope.row)">点击查看
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="delFile(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 28px 8px">
                <el-button type="primary" @click="add" style="float: left">上传文件</el-button>
                <el-pagination
                        style="float: right;"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-size="size"
                        layout="prev, pager, next"
                        :total="totalElements">
                </el-pagination>
            </div>
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="40%"
                   :close-on-click-modal="false" @closed="modelClose(addForm)">
            <el-form :ref="addForm" :model="addForm" label-width="100px">
                <el-form-item label="文件名称"
                              prop="wjmc"
                              :rules="[{ required: true, message: '文件名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="addForm.wjmc"></el-input>
                </el-form-item>
                <el-form-item label="文件类型"
                              prop="wjlx"
                              :rules="[{ required: true, message: '文件类型不能为空', trigger: 'blur' }]">
                    <el-select v-model="addForm.wjlx" placeholder="请选择文件类型">
                        <el-option v-for="item in this.$dict.fileType" :key="item.id+Math.random()" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文件"
                              prop="file"
                              :rules="[{ required: true, message: '文件不能为空', trigger: 'blur' }]">
                    <input type="file" @change="getFile($event)" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="saveEdit(addForm)">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        name: 'basetable',
        data() {
            return {
                modelTitle: '添加文件',
                tableData: [],
                modelVisible: false,
                addForm: {},
                file: {},
                size: 10,
                page: 1,
                totalElements: 0,
                loading: true,
                addLoading: false
            }
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            delFile(index, row) {
                this.$axios.post('/api/file/delete', this.$qs.stringify({id: row.id})).then((res) => {
                    this.$message.success('已删除！');
                    this.getData();
                });
            },
            formatterWjlx(row) {
                return this.$common.dictParse(row.wjlx, this.$dict.fileType);
            },
            lookFile(index, row) {
                window.open('http://182.151.22.247:8081' + row.wjlj);
            },
            modelClose(addForm) {
                this.$refs[addForm].resetFields();
            },
            getFile(event) {
                this.file = event.target.files[0];
                this.addForm.file = this.file;
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.loading = true;
                this.$axios.post('/api/file/findAllByGsidByPage', this.$qs.stringify({wjlx: 1, page: this.page, size: this.size})).then((res) => {
                    if (res.resCode == 200) {
                        this.loading = false;
                        this.tableData = res.data.content;
                        this.totalElements = res.data.totalElements
                    }
                });
            },

            add(){
                this.addForm = {};
                this.modelVisible = true;
            },

            // 保存编辑
            saveEdit(addForm) {
                this.$refs[addForm].validate((valid) => {
                    if (valid) {
                        this.addLoading = true;
                        let formData = new FormData();
                        for(let key in this.addForm) {
                                formData.append(key, this.addForm[key]);
                        }
                        for (var value of formData.values()) {
                            console.log(value);
                        }
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        this.$axios.post( '/api/file/add', formData, config ).then( res=> {
                            this.addLoading = false
                            this.modelVisible = false;
                            this.getData();
                        })
                    }
                })
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

    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }

    .table {
        width: 100%;
        font-size: 14px;
    }

    .red {
        color: #ff0000;
    }
</style>
