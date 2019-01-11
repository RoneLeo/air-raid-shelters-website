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
                        <!--<div>{{}}</div>-->
                        <el-input v-model="companyForm.jwd" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="联系地址">
                        <el-input v-model="companyForm.lxdz" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="" style="display: block;width: 98%">
                        <!--<div>{{}}</div>-->
                        <div id="AMap_container" style="width: 100%; height: 400px;"></div>
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
                    <el-form-item label="公司描述">
                        <el-input v-model="companyForm.gsms" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="搜索关键字">
                        <el-input v-model="companyForm.gjz" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="公司简介" style="display: block;width: 98%">
                        <el-input v-model="companyForm.gsjj" type="textarea" :autosize="{ minRows: 15, maxRows: 30}"
                                  :disabled="disabled" s></el-input>
                    </el-form-item>
                </el-form>
                <div style="display: flex;justify-content: center;">
                    <el-button size="medium" type="primary" icon="el-icon-lx-edit" style="margin-right: 20px"
                               :disabled="!disabled"
                               @click="edit">编辑
                    </el-button>
                    <el-button size="medium" type="success" icon="el-icon-check" @click="save" :disabled="disabled">保存
                    </el-button>
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
                disabled: true,
                map: {},
                positionPicker: {},
                marker: {}
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.initMap();
                window.setTimeout(() => {
                    this.getData();
                },300)
            })


//            this.map = new AMap.Map('AMap_container', {
////                center: [103.956189, 30.555156],
//                resizeEnable: true,
//                zoom: 10,
//            });

//            this.map.plugin('AMap.Geolocation',  () => {
//                let geolocation = new AMap.Geolocation({
//                    enableHighAccuracy: true,//是否使用高精度定位，默认:true
//                    timeout: 10000,          //超过10秒后停止定位，默认：无穷大
//                    maximumAge: 0,           //定位结果缓存0毫秒，默认：0
//                    convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
//                    showButton: true,        //显示定位按钮，默认：true
//                    buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
//                    buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
//                    showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
//                    showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
//                    panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
//                    zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
//                });
//                this.map.addControl(geolocation);
////                console.log(geolocation.getCurrentPosition());
//                geolocation.getCurrentPosition((status,result) => {
//                    if(status == 'complete') {
//                        console.log(result.position);
//                        this.companyForm.jwd = result.position.lat + ',' + result.position.lng;
////                        this.onComplete();
//                    }else if(status == 'error') {
//                        this.onError()
//                    }
//                });
//                AMap.event.addListener(geolocation, 'complete', this.onComplete);//返回定位信息
//                AMap.event.addListener(geolocation, 'error', this.onError);      //返回定位出错信息
//            });
        },
        created() {


        },
        computed: {},
        destoryed() {
            this.map && this.map.remove(this.marker);
            this.map && this.map.destory();
        },
        methods: {
            initMap() {
                this.map = new AMap.Map('AMap_container', {
                    zoom: 16,
                })
                let UIloaded = true;
                if(typeof AMapUI == 'undefined') {
                    UIloaded = false;
                    this.$axios.get('http://webapi.amap.com/ui/1.0/main.js?v=1.0.11').then(res => {
                        if(typeof AMapUI != 'undefined') {
                            UIloaded = true;
                        }else {
                            this.$message.error('无法加载地图');
                        }
                    })
                }
                if(UIloaded) {
                    AMapUI.loadUI(['misc/PositionPicker'], (PositionPicker) => {
                        this.positionPicker = new PositionPicker({
                            mode: 'dragMap',//设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
                            map: this.map//依赖地图对象
                        });
                        this.positionPicker.on('success', (positionResult) => {
                            this.companyForm.jwd = positionResult.position.lat + ',' + positionResult.position.lng;
                            this.companyForm.lxdz = positionResult.address;
                        });
                    });
                }
            },
            setMarker() {
                let positions = this.companyForm.jwd.split(',');
                let position = new AMap.LngLat(positions[1], positions[0]);
                if(this.map) {
                    this.map.setCenter(position);
                    this.marker = new AMap.Marker({
                        icon: "//vdata.amap.com/icons/b18/1/2.png",
                        position: position,
                        offset: new AMap.Pixel(-12, -12)
                    });
                    this.marker.setMap(this.map);
                }

            },

            edit() {
                this.disabled = false;
                this.map.remove(this.marker);
                let positions = this.companyForm.jwd.split(',');
                let position = new AMap.LngLat(positions[1], positions[0]);
//                this.positionPicker.start(this.map.getBounds().getSouthWest())
                this.positionPicker.start(position)
            },
            save() {
                this.$axios.post('/api/contactUs/update', this.$qs.stringify(Object.assign({}, this.companyForm))).then((res) => {
                    this.disabled = true;
                    this.positionPicker.stop();
                    this.setMarker();
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
                    this.setMarker();
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
                if (this.form.id) {
                    url = '/api/recruitment/update'
                }
                this.$axios.post(url, this.$qs.stringify(Object.assign({}, this.form))).then(res => {
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
    .amap-icon img, .amap-marker-content img {
        width: 24px;
        height: 24px;
    }
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
