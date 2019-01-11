<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 公司信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="display: flex;justify-content: center;">
            <!--<div v-if="companyForm"-->
                 <!--style="min-width: 95%;min-height: 500px;display: flex;justify-content: center;align-items: center;flex-direction: column;">-->
                <!--<div @click="showAddBox" style="width: 300px;height: 200px;border: 2px dashed #0095FF;border-radius: 3px;display: flex;flex-direction: column;justify-content: center;">-->
                    <!--<i class="el-icon-plus" style="color: #0095FF;font-size: 78px;font-weight: 500;margin: 0 auto;"></i>-->
                <!--</div>-->
                <!--<div style="color: #666;font-size: 20px;font-weight: 500;width: 100%;text-align: center;margin: 10px 0;">-->
                    <!--点击添加公司详细信息-->
                <!--</div>-->

            <!--</div>-->
            <div class="company-box"
                 style="min-width: 900px; max-width: 95%; height: auto;padding: 20px 0 50px;">
                <el-form ref="companyForm" :model="companyForm" label-width="120px" size="medium">
                    <el-form-item label="公司名称" style="display: block;width: 98%">
                        <el-input v-model="companyForm.gsmc" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="经纬度">
                        <el-input v-model="companyForm.jwd" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="联系地址">
                        <el-input v-model="companyForm.lxdz" :disabled="disabled"></el-input>
                    </el-form-item>
                    <el-form-item label="" style="display: block;width: 98%">
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
                    <template v-if="companyForm.id">
                        <el-button size="medium" type="primary" icon="el-icon-lx-edit" style="margin-right: 20px"
                                   :disabled="!disabled"
                                   @click="edit">编辑
                        </el-button>
                        <el-button size="medium" type="success" icon="el-icon-check" @click="save" :disabled="disabled">
                            保存
                        </el-button>
                    </template>
                    <template v-else="">
                        <el-button size="medium" type="success" icon="el-icon-check" @click="save" :disabled="disabled">
                            保存
                        </el-button>
                    </template>
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
                disabled: false,
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
                }, 300)
            })
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
                if (typeof AMapUI == 'undefined') {
                    UIloaded = false;
                    this.$axios.get('http://webapi.amap.com/ui/1.0/main.js?v=1.0.11').then(res => {
                        if (typeof AMapUI != 'undefined') {
                            UIloaded = true;
                        } else {
                            this.$message.error('无法加载地图');
                        }
                    })
                }
                if (UIloaded) {
                    console.log(this.map);
                    AMapUI.loadUI(['misc/PositionPicker'], (PositionPicker) => {
                        this.positionPicker = new PositionPicker({
                            mode: 'dragMap',//设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
                            map: this.map//依赖地图对象
                        });
                        this.positionPicker.start(this.map.getCenter());
                        console.log(this.positionPicker);
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
                if (this.map) {
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
                let url = '/api/contactUs/add';
                if(this.companyForm.id) {
                    url = '/api/contactUs/update'
                }
                this.$axios.post(url , this.$qs.stringify(Object.assign({}, this.companyForm))).then((res) => {
                    this.disabled = true;
                    this.positionPicker.stop();
                    this.setMarker();
                });
            },
            showAddBox() {
//                this.companyForm = {
//                    gsmc: '',
//                    lxdh: '',
//                    lxdz:'',
//                    lxcz: '',
//                    gswz: '',
//                    lxyb: '',
//                    lxyx: '',
//                }
            },
            getData() {
                this.$axios.post('/api/contactUs/findByGsid').then((res) => {
                    this.companyForm = res.data;
                    console.log(this.companyForm)
                    if (this.companyForm !== null) {
//                        console.log(111)
                        this.disabled = true
                        this.setMarker();
                    }else {
                        this.companyForm = {
                            gsmc: '',
                            lxdh: '',
                            lxdz:'',
                            lxcz: '',
                            gswz: '',
                            lxyb: '',
                            lxyx: '',
                        }
                    }

                });
            },
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
