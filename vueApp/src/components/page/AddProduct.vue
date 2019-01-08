<template>
    <div class="table">
        <div class="container">
            <div >
                <el-row style="border-bottom: 1px dashed #0095FF;margin-bottom: 10px;padding: 8px 0;">
                    <el-col  :span="22" style="height: 100%;">
                        <el-form :inline="true" :model="form" class="demo-form-inline"  label-position="right">
                            <el-form-item label-width="80px" label="产品类型">
                                <el-select v-model="form.sblx" placeholder="请选择产品类型">
                                    <el-option v-for="item in this.equipmentType" :key="item.id*2.0987" :label="item.name"
                                               :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label-width="150px" label="产品名称">
                                <el-input v-model="form.cpmc" placeholder="产品名称"></el-input>
                            </el-form-item>

                            <el-form-item v-if="!form.id || !form.cptp" label-width="150px" label="产品图片">
                                <input type="file" @change="getFile($event)"/>
                            </el-form-item>
                            <el-form-item v-else="" label-width="150px" label="产品图片">
                                <img :src="`http://182.151.22.247:8081${form.cptp}`" alt="" style="width: 100px;height: 100px;position: relative;">
                                <i v-if="form.cptp" class="el-icon-error" @click="deleteTP" style="font-size: 18px;font-weight: 600;position: absolute;top: -9px;right: -9px;color: #0095FF;"></i>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <!--<el-col v-else :span="22" style="height: 100%;">-->
                        <!--<span>{{this.$common.dictParse(form.sblx, this.equipmentType)}} > {{form.cpmc}}</span>-->
                        <!--<img :src="`http://182.151.22.247:8081${form.cptp}`" alt="" style="width: 100px;height: 100px;">-->
                    <!--</el-col>-->
                    <el-col :span="2" style="text-align: right;">
                        <el-button icon="el-icon-success" @click="prelook" type="primary">保存</el-button>
                    </el-col>
                </el-row>
            </div>

            <div  style="margin-top: 20px;">
                <!--<h2 style="line-height: 40px;padding-bottom: 10px;color: #606266;font-size: 20px">产品介绍资料</h2>-->
                    <div class="contents" v-for="item,index in contents" :key="index*1.00107" :style="{borderBottom: index == contents.length-1 ? '1px dashed #dcdfe6' : '0', marginBottom: index == contents.length-1 ? '30px': '20px'}">
                        <el-row style="margin-bottom: 5px;">
                            <el-col :span="18">
                                <h3 style="padding: 0 10px;color: #0095FF">{{item.bt}}</h3>
                            </el-col>
                            <el-col :span="6" style="text-align: right">
                                <el-button type="warning" icon="el-icon-delete" @click="deleteItem(item, index)">删除</el-button>

                                <el-button icon="el-icon-edit" @click="editItem(item, index)" type="primary">编辑</el-button>
                            </el-col>
                        </el-row>
                        <div v-html="item.xxnr" style="padding:3px 10px;"></div>
                    </div>
            </div>
            <div style="margin-top: 30px;">
                <el-row>
                    <el-col :span="18">
                        <el-form :inline="true" :model="content" class="demo-form-inline"  label-position="right">
                            <el-form-item label-width="70px" label="小标题">
                                <el-input v-model="content.bt" placeholder="请输入介绍标题"></el-input>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="6" style="text-align: right;">
                        <el-button @click="finishItem" type="primary">完成</el-button>
                    </el-col>
                </el-row>
                <UEditor :config=config ref="ueditor"></UEditor>
            </div>
            <!--<button size="primary" type="info" icon="plus" @click="getContent">获取内容</button>-->
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
                equipmentType: [],
                pId: null
            }
        },
        mounted () {
            this.$nextTick(()=>{
                this.editor = UE.getEditor("editor");
            })

            this.$axios.post('/api/equipmentType/findAll').then((res) => {
                let data = res.data;
                this.equipmentType = data;
            });
        },
        created() {
            console.log(this.$route.params.pId);
            this.pId = this.$route.params.pId;
            if(this.$route.params.pId) {
                this.$axios.post('/api/equipment/findById?id=' + this.$route.params.pId).then((res) => {
                    console.log(res)
                    let data = res.data[0];
                    this.form = {
                        id: data.id,
                        gsid: data.gsid,
                        cpmc : data.cpmc,
                        cptp: data.cptp,
                        sblx: data.sblx
                    };
                    this.contents = data.cpxq;
                    console.log(this.form, this.contents)
                });
            }
        },
        computed: {

        },
        methods: {
            deleteTP() {
                this.form.cptp = '';
                this.file = {};
            },
            deleteItem(item, index) {
                this.contents.splice(index, 1);
                console.log(this.contents)
            },
            getFile(event) {
                this.file = event.target.files[0];
                this.form.file = this.file;
            },
            editItem(item, index) {
                console.log(index)
                console.log()
                this.content.bt = item.bt;
                this.content.xxnr = '';
                this.content.index = index;
                console.log(this.content.bt);
                window.setTimeout(() => {
                    this.editor.setContent(this.contents[index].xxnr);
                }, 50)
            },
            finishItem() {
                console.log(this.content.index, this.contents);
                if(this.content.index >= 0) {
                    let item = Object.assign({}, this.content);
                    item.xxnr = this.$refs.ueditor.getUEContent();
                    let index = this.content.index;
                    this.contents[index] = item;
                }else {
                    if(this.pId) {
                        this.content.id = null;
                        this.content.cpid = null;
                    }
                    this.content.xxnr = this.$refs.ueditor.getUEContent();
                    this.contents.push(this.content);
                }
                window.setTimeout(() => {
                    this.content = {};
                    this.editor.setContent('');
                }, 300)
            },
            prelook() {
                let url = '/api/equipment/add';
                if(this.pId) {
                    url = '/api/equipment/update'
                }
                let formData = new FormData();
                for (let key in this.form) {
                    formData.append(key, this.form[key]);
                }
//                for(let i = 0; i < this.contents.length; i++) {
//                    this.contents[i].title = this.contents[i].bt;
//                    this.contents[i].content = this.contents[i].xxnr;
//                }
                formData.append('contents', JSON.stringify(this.contents));
//                for (var value of formData.values()) {
//                    console.log(value);
//                }
//                this.form.contents = this.contents;
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                };
                this.$axios.post(url, formData, config).then(res => {
                    this.$router.push('/products');
                    console.log(res);
                })
            },
            getContent: function(){
                let content = this.$refs.ueditor.getUEContent();
            },


        }
    }

</script>

<style scoped>
    .contents {
        margin-bottom: 20px;
        padding: 5px 0 15px;
    }
</style>
