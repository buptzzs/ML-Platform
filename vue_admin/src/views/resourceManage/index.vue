<template>
    <el-container> 
        <el-header> 
            <h3 v-if="type == 'data'" > 数据文件管理 </h3>
            <h3 v-else-if="type == 'model'"> 模型文件管理</h3>
            <h3 v-else>评估结果文件</h3>
        </el-header>

        <el-main>
            <el-row style="margin-bottom: 10px">
            <el-col :span="5">
                <el-button type="primary" @click="dialogUpload = true">上传文件<i class="el-icon-upload2"></i></el-button>
            </el-col>
            <el-col :span="5" :offset="4">
                <el-input 
                    placeholder="请输入文件名进行搜索"
                    suffix-icon="el-icon-search"
                    v-model="filters[0].value">
                </el-input>
            </el-col>
            </el-row>            
            <data-tables :data="data" 
                :pagination-props="{ pageSizes: [5, 10, 15] }" 
                :layout="layout" 
                :action-col="actionCol"
                :table-props="tableProps"
                :filters="filters"
                >
                <el-table-column v-for="title in titles" :prop="title.prop" :label="title.label" :key="title.label"   sortable="custom">
                </el-table-column>
            </data-tables>
        </el-main>

        <el-dialog title="文件上传" :visible.sync="dialogUpload">
            <el-upload
                class="upload"
                drag
                :http-request="upload_file"
                :before-upload="beforeUpload"
                action=""
                :auto-upload="true"
                :show-file-list="true"
                multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传json/text/csv文件</div>
            </el-upload>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogUpload = false">退出</el-button>
            </div>
        </el-dialog>        

        <el-dialog title="预览信息" :visible.sync="previewVisible"> 
            <el-row>
                <ve-line v-if="preview_type=='json'"  :data="preview_chart" :settings="chartSettings"></ve-line>
                <div v-else class="divInfo" >{{preview_txt}}</div>            
            </el-row>
        </el-dialog>

    </el-container>

</template>

<script>
import {getFileInfos, download, delete_file, upload, preview} from '@/api/file'

export default {
    props: ['type'],

    data() {
        this.chartSettings = {
            xAxisType: 'value',
            area: true
        }        
        const titles = [{
                prop: 'name',
                label: '文件名'
            }, {
                prop: 'size',
                label: '文件大小/bytes'
            },{
                prop:'time',
                label: '上次修改时间'
            }
            ];

        return {
            data: [],
            titles: titles,
            layout: 'table, pagination',
            tableProps: {
                defaultSort: {
                    prop: 'flow_type',
                    order: 'descending'
                }
            },
            filters: [
                {
                prop: 'name',
                value: ''
                }
            ],
            actionCol: {
                label: '操作',
                props: {
                    align: 'center',
                },
                buttons: [
                    {
                    props: {
                        //type: 'primary',
                        icon: 'el-icon-view'
                    },
                    handler: row => {
                        this.preview(row.name)
                    },
                    label: '预览' 
                },                    
                    {
                    props: {
                        //type: 'primary',
                        icon: 'el-icon-download'
                    },
                    handler: row => {
                        this.download_file(row.name)
                    },
                    label: '下载' 
                }, {
                    props: {
                        //type: 'primary',
                        icon: 'el-icon-delete'
                    },
                    handler: row  => {
                        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                                }).then(() => {
                                this.delete(row.name)
                                this.$message({
                                    type: 'success',
                                    message: '删除成功!'
                                });
                                }).catch(() => {
                                this.$message({
                                    type: 'info',
                                    message: '已取消删除'
                                });          
                                });                        
                    },
                    label: '删除'
                }
                ]
            },
            dialogUpload: false,

            previewVisible: false,         
            preview_type: "error",
            preview_txt: '',
            preview_chart: {}

        }
    },

    created() {
        this.fetchData()
    },    

    watch: {
        'type': function() {
            this.fetchData()
        }
    },

    methods: {
        beforeUpload(file) {
            this.$message(file.name)
            const filename = file.name
            const index = filename.lastIndexOf(".")
            const ext = filename.substr(index+1)
            const isSupportType = ext == 'json' || ext == 'txt' || ext == 'csv'
            /** 
            if (!isSupportType) {
                this.$message.error('上传文件只能是json、txt、csv格式')
            }
            
            return isSupportType
            */
            return true;
            
        },

        fetchData() {
            const params = {
                username: this.$store.getters.name,
                type: this.type
            }
            getFileInfos(params).then(response =>{
                this.data = response.data
            })
        },

        download_file(filename) {
            const params = {
                username: this.$store.getters.name,
                type: this.type,
                filename: filename
            }

            download(params).then(response =>{
                console.log(response)
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', filename); //or any other extension
                document.body.appendChild(link);
                link.click(); 
            })
        },

        delete(filename) {
            const params = {
                username: this.$store.getters.name,
                type: this.type,
                filename: filename
            }

            delete_file(params).then(response =>{
                this.$message.info(response.message)
                this.fetchData()
            })
        },
        preview(filename){
            this.$message(filename)
            const params = {
                username: this.$store.getters.name,
                type: this.type,
                filename: filename
            }
            preview(params).then(response =>{
                const preview_data = JSON.parse(response.data)
                this.preview_type = preview_data.type
                if(this.preview_type=='json'){
                    let chart_data =  JSON.parse(preview_data.data)
                    this.preview_chart.columns = chart_data.columns
                    this.preview_chart.rows = chart_data.rows
                }
                else if(this.preview_type=='txt'){
                    this.preview_txt = preview_data.data
                }
                else{
                    this.preview_txt = "Not support"
                }
                this.previewVisible = true                
            })
            
        },        

        upload_file(param) {
            let formData = new FormData()
            formData.append('file', param.file)
            formData.append('username', this.$store.getters.name)
            formData.append('type', this.type)
            console.log(param)
            upload(formData).then(response =>{
                this.$message.info(response.message)
                this.fetchData()
            })
        }
    }

}
</script>
<style>
    .divInfo {
        white-space: pre-line;
    }

</style>