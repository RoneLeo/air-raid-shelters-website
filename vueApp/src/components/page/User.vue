<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 用户信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">

        </div>
        <div class="container">
            <el-table :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="zh" label="账号"></el-table-column>
                <el-table-column prop="xm" label="姓名"></el-table-column>
                <!--<el-table-column prop="mm" label="密码"></el-table-column>-->
                <el-table-column prop="gsid" label="公司ID" :formatter="formatterGS"></el-table-column>
                <el-table-column prop="cjsj" label="创建时间"></el-table-column>
                <el-table-column label="操作"  align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 20px 8px">
                <el-button type="primary" @click="add">添加用户</el-button>
            </div>
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="35%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="账号"
                              prop="zh"
                              :rules="[{ required: true, message: '登录账号不能为空', trigger: 'blur' }]">
                    <el-input v-model="form.zh"></el-input>
                </el-form-item>
                <el-form-item label="密码"
                              v-if="!form.uuid"
                              prop="mm"
                              :rules="[{ required: true, message: '密码不能为空', trigger: 'blur' }]">
                    <el-input v-model="form.mm"></el-input>
                </el-form-item>
                <el-form-item label="姓名"
                              prop="xm"
                              :rules="[{ required: true, message: '姓名不能为空', trigger: 'blur' }]">
                    <el-input v-model="form.xm"></el-input>
                </el-form-item>
                <el-form-item label="公司ID"
                              v-if="!form.uuid"
                              prop="gsid"
                              :rules="[{ required: true, message: '所属公司不能为空', trigger: 'blur' }]">
                    <el-select v-model="form.gsid" placeholder="请选择">
                        <el-option v-for="item in this.$dict.company" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
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
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                modelVisible: false,
                delVisible: false,
                form: {},
                idx: -1,
                dict: this.$dict
            }
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {
            formatterGS(row) {
                return this.$common.dictParse(row.gsid, this.dict.company);
            },
            closeClear() {
                this.$refs.form.resetFields()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios.post('/api/user/findAllByGsid').then((res) => {
                    if(res.resCode == 200){
                        this.tableData = res.data;
                    }
                });
            },
            search() {
                this.is_search = true;
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
                this.$axios.post('/api/user/delete', this.$qs.stringify({id: row.id})).then((res) => {
                    this.$message.success('已删除！');
                    this.getData();
                });
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
                let url = '/api/user/add';
                if(this.form.uuid) {
                    url = '/api/user/update';
                }
                this.$axios.post(url ,this.$qs.stringify(Object.assign({}, this.form))).then((res) => {
                    this.modelVisible = false;
                    this.getData();
                });
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
