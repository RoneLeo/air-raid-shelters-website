<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 用户信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-form :inline="true" :model="form" class="demo-form-inline"  label-position="right">
                <el-form-item label-width="80px" label="产品类型">
                    <el-input v-model="form.sblx" placeholder="产品类型"></el-input>
                </el-form-item>
                <el-form-item label-width="150px" label="产品名称">
                    <el-select v-model="form.cpmc" placeholder="活动区域">
                        <el-option label="区域一" value="shanghai"></el-option>
                        <el-option label="区域二" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label-width="150px" label="产品图片">
                    <input type="file">
                </el-form-item>
                <el-form-item >
                    <el-button @click="prelook" type="primary">预览</el-button>
                </el-form-item>
            </el-form>
            <div v-show="!editorShow">
                <div v-for="item in contents" :key="item.nr">
                    {{item.bt}}
                    <div v-html="item.nr"></div>
                    <!--{{item.nr}}-->
                </div>
            </div>
            <div v-show="editorShow">
                <el-form :inline="true" :model="content" class="demo-form-inline"  label-position="right">
                    <el-form-item label-width="80px" label="小标题">
                        <el-input v-model="content.bt" placeholder="请输入介绍标题"></el-input>
                    </el-form-item>
                    <el-form-item label-width="150px">
                        <el-button @click="finish" type="primary">此部分已完成</el-button>
                    </el-form-item>
                </el-form>
                <UEditor :config=config ref="ueditor"></UEditor>
            </div>

            <button size="primary" type="info" icon="plus" @click="getContent">获取内容</button>

        </div>

    </div>
</template>

<script>
//    import 'UEditor/UE/ueditor.all.js'
//    import 'static/UE/ueditor.config.js'
//
//    import 'static/UE/ueditor.parse.js'
//    import 'static/UE/lang/zh-cn/zh-cn.js'\
import UEditor from '@/components/common/ueditor.vue'

    export default {
        name: 'basetable',
        components: {UEditor},
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
                dict: this.$dict,

                config: {
                    //可以在此处定义工具栏的内容
                     toolbars: [
                         ['fullscreen', 'source', '|', 'undo', 'redo', '|',
                         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                         'directionalityltr', 'directionalityrtl', 'indent', '|',
                         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                         'simpleupload', 'emotion', 'scrawl', 'map', 'pagebreak', '|',
                         'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                         'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                         'print', 'preview', 'searchreplace', 'drafts', 'help']
                     ],
                    autoHeightEnabled: false,
                    autoFloatEnabled: true,
                    initialContent:'请输入内容',   //初始化编辑器的内容,也可以通过textarea/script给值，看官网例子
                    autoClearinitialContent:true, //是否自动清除编辑器初始内容，注意：如果focus属性设置为true,这个也为真，那么编辑器一上来就会触发导致初始化的内容看不到了
                    initialFrameWidth: null,
                    initialFrameHeight: 450,
//                    BaseUrl: '',
                    UEDITOR_HOME_URL: '../../../static/ueditor/'
                },
                addFormVisible: false,
                contents: [],
                content: {},
                editorShow: true
            }
        },
        mounted () {
            this.editor = UE.getEditor("editor");
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {
            finish() {
                this.content.nr = this.$refs.ueditor.getUEContent();
                this.contents.push(this.content);
                this.editorShow = false;
                console.log(this.content.bt, this.content.nr);
            },
            prelook() {
                console.log(this.form, this.content);
            },
            getContent: function(){
                let content = this.$refs.ueditor.getUEContent();
                console.log(content);

                alert(content);
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
                    console.log(111,res);
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
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    name: item.name,
                    date: item.date,
                    address: item.address
                }
                this.modelVisible = true;
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
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
                console.log(this.form)
                    this.$set(this.tableData, this.idx, this.form);
                    this.$message.success(`修改第 ${this.idx+1} 行成功`);

                this.modelVisible = false;
                this.$axios.post('/api/user/add',this.form).then((res) => {
                    console.log(111,res);
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
