<template>
    <el-container> 
        <el-header> 
            <h3  > 系统资源监控 </h3>
        </el-header>
        <el-main>
            <el-row>
                <el-col :span="8">
                      <h2>CPU</h2>
                      <ve-line :data="cpuChartData" :settings="chartSettings"></ve-line>
                </el-col >
                <el-col  :span="8">
                    <h2>内存</h2>
                      <ve-line :data="memChartData" :settings="chartSettings"></ve-line>
                </el-col>
                <el-col :span="8">
                     <h2>磁盘</h2>
                      <ve-line :data="diskChartData" :settings="chartSettings"></ve-line>
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
            xAxisType: 'category'
        }
        return {
            systemInfo : {},
            cpuChartData: {
                label:'CPU',
                columns: ['时间','百分比' ],
                rows: [

                ]
            },
            memChartData: {
                label:'CPU',
                columns: ['时间','百分比' ],
                rows: [
                ]
            },
            diskChartData: {
                label:'CPU',
                columns: ['时间','百分比' ],
                rows: [

                ]
            }            
        }
    },
    created() {
        this.getSystemInfo()
    },    

    mounted(){
        if(this.timer){      
                clearInterval(this.timer)    
        }else{      
            this.timer = setInterval(()=>{       
            // 调用相应的接口，渲染数据        
            this.getSystemInfo()     
            console.log("get user task")
            },3000)    
        }  
    },

    destroyed(){    
        clearInterval(this.timer)  
    }, 


    methods:{
        
        getSystemInfo(){
            const params = {
            }
            getSystemInfo(params).then(response =>{
                var d = new Date()
                var systemInfo = response.data
                this.systemInfo = JSON.parse(systemInfo.trim())
                var cpu_item = {}
                cpu_item['时间'] = d.toLocaleTimeString();
                cpu_item['百分比'] = this.systemInfo['cpu']
                this.cpuChartData['rows'].push(cpu_item)

                var mem_item = {}
                mem_item['时间'] = d.toLocaleTimeString();
                mem_item['百分比'] = this.systemInfo['mem_percent']
                this.memChartData['rows'].push(mem_item)

                var disk_item = {}
                disk_item['时间'] = d.toLocaleTimeString();
                disk_item['百分比'] = this.systemInfo['disk_percent']
                this.diskChartData['rows'].push(disk_item)
                console.log(disk_item)
                
            })
        }
    }
}
</script>
