<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 用户信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-row style="border-bottom: 1px dashed #0095FF;margin-bottom: 10px;padding: 8px 0;">
                <el-col :span="22" style="height: 100%;">
                    <el-form :inline="true" :model="form" class="demo-form-inline"  label-position="right">
                        <el-form-item label-width="80px" label="产品类型">
                            <el-select v-model="form.sblx" placeholder="活动区域">
                                <el-option v-for="item in this.equipmentType" :key="item.id*2.0987" :label="item.name"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label-width="150px" label="产品名称">
                            <el-input v-model="form.cpmc" placeholder="产品类型"></el-input>
                        </el-form-item>
                        <el-form-item label-width="150px" label="产品图片">
                            <input type="file" @change="getFile($event)"/>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="2" style="text-align: right;">
                    <el-button icon="el-icon-lx-attention" @click="prelook" type="primary">保存</el-button>
                </el-col>
            </el-row>

            <div  style="margin-top: 20px;">
                <div v-for="item,index in contents" :key="index*1.00107" style="margin-bottom: 20px;">
                    <el-row style="margin-bottom: 5px;">
                        <el-col :span="22">
                            <h3 style="padding: 0 10px;">{{item.bt}}</h3>
                        </el-col>
                        <el-col :span="2" style="text-align: right">
                            <el-button icon="el-icon-edit" @click="editItem(item, index)" type="primary">编辑</el-button>
                        </el-col>
                    </el-row>

                    <div v-html="item.nr" style="padding:0 10px;"></div>
                </div>
            </div>
            <div style="margin-top: 20px;">
                <el-row>
                    <el-col :span="18">
                        <el-form :inline="true" :model="content" class="demo-form-inline"  label-position="right">
                            <el-form-item label-width="80px" label="小标题">
                                <el-input v-model="content.bt" placeholder="请输入介绍标题"></el-input>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="6" style="text-align: right;">
                        <el-button icon="el-icon-check" @click="finishItem" type="primary">完成</el-button>
                    </el-col>
                </el-row>
                <UEditor :config=config ref="ueditor"></UEditor>
            </div>
            <button size="primary" type="info" icon="plus" @click="getContent">获取内容</button>
        </div>

    </div>
</template>

<script>
import UEditor from '@/components/common/ueditor.vue'
    export default {
        name: 'basetable',
        components: {UEditor},
        data() {
            return {
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
                    initialFrameWidth: 1100,
                    initialFrameHeight: 450,
//                    BaseUrl: '',
                    UEDITOR_HOME_URL: '../../../static/ueditor/'
                },
                form: {},
                contents: [],
                content: {},
                equipmentType: []
            }
        },
        mounted () {
            this.editor = UE.getEditor("editor");
            this.$axios.post('/api/equipmentType/findAll').then((res) => {
                let data = res.data;
                this.equipmentType = data;
            });
        },

        created() {
        },
        computed: {

        },
        methods: {
            getFile(event) {
                this.file = event.target.files[0];
                this.form.file = this.file;
            },
            editItem(item, index) {
                console.log(index)
                console.log()
                this.content.bt = item.bt;
                this.content.nr = '';
                this.content.index = index;
                console.log(this.content);
                this.editor.setContent(this.contents[index].nr);
            },
            finishItem() {
                console.log(this.content.index, this.contents);
                if(this.content.index >= 0) {
                    let item = {
                        bt: this.content.bt,
                        nr: this.$refs.ueditor.getUEContent()
                    }
                    let index = this.content.index;
                    console.log(item)
                    this.contents[index] = item;
                    console.log(this.contents);
                }else {
                    this.content.nr = this.$refs.ueditor.getUEContent();
                    this.contents.push(this.content);
                }
                window.setTimeout(() => {
                    this.content = {};
                    this.editor.setContent('');
                }, 300)
            },
            prelook() {
                console.log(this.form, this.contents);
            },
            getContent: function(){
                let content = this.$refs.ueditor.getUEContent();
                console.log(content);

                alert(content);
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
