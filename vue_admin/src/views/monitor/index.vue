<template>
    <el-container> 
        <el-header> 
            <h3  > 系统资源监控 </h3>
        </el-header>
        <el-main>
            <el-row>
                <el-col :span="8">
                      <h2>CPU</h2>
                      <ve-pie :data="cpuChartData" :settings="chartSettings"></ve-pie>
                </el-col >
                <el-col  :span="8">
                    <h2>内存</h2>
                      <ve-pie :data="memChartData" :settings="chartSettings"></ve-pie>
                </el-col>
                <el-col :span="8">
                     <h2>磁盘</h2>
                      <ve-pie :data="diskChartData" :settings="chartSettings"></ve-pie>
                </el-col>                                
            </el-row>
            
        </el-main>
    </el-container>
</template>

<script>
import G2 from '@antv/g2';
import {getSystemInfo} from '@/api/monitor'

export default {

    data() {
        this.chartSettings = {
            dataType: 'normal'
        }
        return {
            systemInfo : {},
            cpuChartData: {
                label:'CPU',
                columns: ['状态', '百分比' ],
                rows: [
                    { '状态': '占用', '百分比': 0 },
                    { '状态': '空闲', '百分比': 1 },
                ]
            },
            memChartData: {
                label:'CPU',
                columns: ['状态', '百分比' ],
                rows: [
                    { '状态': '占用', '百分比':  0},
                    { '状态': '空闲', '百分比': 1 },
                ]
            },
            diskChartData: {
                label:'CPU',
                columns: ['状态', '百分比' ],
                rows: [
                    { '状态': '占用', '百分比': 0 },
                    { '状态': '空闲', '百分比': 1 },
                ]
            }            
        }
    },
    created() {
        this.getSystemInfo()
    },    
    methods:{
        
        getSystemInfo(){
            const params = {
            }
            getSystemInfo(params).then(response =>{
                var systemInfo = response.data
                this.systemInfo = JSON.parse(systemInfo.trim())
                this.cpuChartData['rows'][0]['百分比'] = this.systemInfo['cpu']
                this.cpuChartData['rows'][1]['百分比'] = 100 - this.systemInfo['cpu']
                var mem_total = this.systemInfo['mem_total']
                this.memChartData['rows'][0]['百分比'] = mem_total*(this.systemInfo['mem_percent']) / 100
                this.memChartData['rows'][1]['百分比'] = mem_total*(100 - this.systemInfo['mem_percent']) / 100
                
                var disk_total = this.systemInfo['disk_total']
                this.diskChartData['rows'][0]['百分比'] = disk_total * this.systemInfo['disk_percent'] / 100
                this.diskChartData['rows'][1]['百分比'] = disk_total*(100 - this.systemInfo['disk_percent']) / 100
            })
        }
    }
}
</script>
