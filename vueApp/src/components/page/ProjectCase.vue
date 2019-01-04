<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 工程案例</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" v-loading="loading">
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="gcmc" label="工程名称"></el-table-column>
                <el-table-column label="展示图片">
                    <template slot-scope="scope">
                        <a target="_blank" :href='`http://182.151.22.247:8081${scope.row.tp}`'>{{scope.row.tp}}</a>
                    </template>
                </el-table-column>
                <el-table-column prop="cjsj" label="创建时间"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <!--<el-button type="text" icon="el-icon-lx-edit"-->
                                   <!--@click="editFile(scope.$index, scope.row)">编辑-->
                        <!--</el-button>-->
                        <el-button type="text" icon="el-icon-delete" class="red" @click="delFile(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 28px 8px">
                <el-button type="primary" @click="add" style="float: left">上传工程案例</el-button>
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
                <el-form-item label="工程名称"
                              prop="gcmc"
                              :rules="[{ required: true, message: '工程名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="addForm.gcmc"></el-input>
                </el-form-item>
                <el-form-item label="展示图片"
                              prop="file"
                              :rules="[{ required: true, message: '展示图片为空', trigger: 'blur' }]">
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
                modelTitle: '工程案例详情',
                tableData: [],
                modelVisible: false,
                addForm: {},
                file: {},
                size: 10,
                page: 1,
                totalElements: 0,
                loading: true,
                addLoading: false,
                equipmentType: []
            }
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            formatterSBLX(row) {
                this.$common.dictParse(row.sblx, this.equipmentType);
            },
            delFile(index, row) {
                this.$axios.post('/api/file/delete', this.$qs.stringify({id: row.id})).then((res) => {
                    this.$message.success('已删除！');
                    this.getData();
                });
            },
            formatterWjlx(row) {
                return this.$common.dictParse(row.wjlx, this.$dict.fileType);
            },
            editFile(index, row) {
                this.addForm = Object.assign({}, row);
                this.modelVisible = true;
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
                this.$axios.post('/api/projectCase/findAllByGsidByPage', this.$qs.stringify({ page: this.page, size: this.size})).then((res) => {
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
                        let url = '/api/projectCase/add';
                        if(this.addForm.id) {
                            url = '/api/projectCase/update'
                        }
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
                        this.$axios.post(url, formData, config).then(res => {
                            this.addLoading = false;
                            this.modelVisible = false;
                            if(this.activeName == 'first') {
                                this.getData(1, this.page, this.size);
                            }else {
                                this.getData(2, this.page1, this.size1);
                            }
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
