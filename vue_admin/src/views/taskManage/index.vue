<template >
    <el-container> 
        <el-main>
            <el-row>
                <div style="margin-bottom: 15px;">
                    <el-input v-model="input" placeholder="新建任务名">
                        <el-button title="添加" slot="append" icon="el-icon-plus" v-on:click="addUserTask">
                        </el-button>
                    </el-input>
                    <el-button  title="刷新" icon="el-icon-refresh" @click="getUserTasks" >
                    </el-button>
                </div>
            </el-row>
            <el-row>
                <el-col :span="6" v-for="(task, index) in tasks" :key="task.name" :offset="index % 3 == 0 ? 0 : 1">
                    <el-card>
                        <div slot="header">
                            <span>{{task.taskname}}</span>
                        </div>
                        <div class="desc">
                            <h5>{{task.desc}}</h5>
                        </div>
                        <div class="info">
                            <time class="time">任务创建时间: {{task.createdTime}}</time>                                          
                        </div>
                        <div class="info">
                            <time class="time">运行起始时间: {{task.runTaskInfo.startTime}}</time>                     
                        </div>
                        <div class="info">
                            <time class="time">运行终止时间: {{task.runTaskInfo.endTime}}</time>
                        </div>
                        <div class="info">
                            <div class="state" :style="state_color(task.runTaskInfo.taskState)">状态: {{task.runTaskInfo.taskState}}</div>
                        </div>         
                        <div>
                            <el-progress :percentage="task.runResult.percentage" status="" color="rgba(142, 113, 199, 0.7)"></el-progress>

                        </div>               
                        <el-row class="bottom clearfix">
                            <el-button  type="text" icon="el-icon-info" @click="selectLog(index)">日志</el-button>
                            <el-button type="text" icon="el-icon-setting"  @click="edit(index)" :disabled="isRunning(index)">
                            编辑</el-button>
                            <el-button type="text"  v-if="! isRunning(index)" icon="el-icon-caret-right" @click="run(index)" >运行</el-button>
                            <el-button type="text"  v-else icon="el-icon-circle-close" @click="stop(index)" >终止</el-button>

                            <el-button type="text" icon="el-icon-delete" @click="deleteUserTask(index)" :disabled="isRunning(index)">删除</el-button>                            
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>
        </el-main>
        <el-dialog title="日志信息" :visible.sync="logVisible"> 
            <p>{{curLog.success == false ? '失败':'成功'}}</p>
            <el-row>
            <p>运行信息</p>
            <el-collapse v-model="activeName" :data="curLog" accordion>
                <el-collapse-item v-for="(log, index) in curLog.runLog" :title="curLog.total[index]" :name="index" :key="log">
                    <div class="divInfo" >{{log}}</div>
                </el-collapse-item>
            </el-collapse>
            </el-row>
            <el-row>
                <el-collapse v-model="activeName" :data="curLog" accordion >
                <el-collapse-item title="错误信息" name="3">
                    <div class="divInfo" >{{curLog.errorLog}}</div>
                </el-collapse-item>
                </el-collapse>
            </el-row>
        </el-dialog>
    </el-container>

</template>

<script>
import {getTasks, addTask, deleteTask, runTask, stopTask} from '@/api/userTask'


export default {
    data() {
        return {
            logVisible: false,
            curLog: {},
            tasks: [],
            activeName: '1',
            input: '',
            timer:0
        }
    },

    created() {
        this.getUserTasks()
    },
    mounted(){
        if(this.timer){      
                clearInterval(this.timer)    
        }else{      
            this.timer = setInterval(()=>{       
            // 调用相应的接口，渲染数据        
            this.getUserTasks()     
            console.log("get user task")
            },3000)    
        }  
    },

    destroyed(){    
        clearInterval(this.timer)  
    },    

    methods: {
        getUserTasks() {
            const params = {
                username: this.$store.getters.name,
            }
            getTasks(params).then(response => {
                const str_tasks = response.data
                this.tasks = []
                for(let i = 0; i < str_tasks.length; i++){
                    let task_json = JSON.parse(str_tasks[i])
                    task_json.runTaskInfo = JSON.parse(task_json.runTaskInfo)
                    task_json.runResult = JSON.parse(task_json.runResult)
                    const total = task_json.runResult.total.length
                    const done = task_json.runResult.runLog.length
                    if(total == 0){
                        task_json.runResult['percentage'] = 0
                    }
                    else{
                        task_json.runResult['percentage'] = Math.round((done / total)*100,3)
                    }                    
                    this.tasks.push(task_json)
                
                }
                console.log(this.tasks)
            })
                       
        },

        selectLog(index) {
            this.curLog = this.tasks[index].runResult
            this.logVisible = true
        },

        addUserTask() {
            if(this.input.length == ''){
                this.$message("任务名不能为空");
                return;
            }
            else{
                this.$message("创建任务" + this.input);
            }
            const params = {
                username: this.$store.getters.name,
                taskname: this.input
            }
            addTask(params).then(response => {
                this.$message("创建任务成功");
                this.getUserTasks()
            })
        },

        deleteUserTask(index) {
            const params = {
                username: this.$store.getters.name,
                taskname: this.tasks[index].taskname
            }
             this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                                }).then(() => {
                                deleteTask(params).then(response => {
                                    this.$message("删除任务成功");
                                    this.getUserTasks();
                                })
                                }).catch(() => {
                                this.$message({
                                    type: 'info',
                                    message: '已取消删除'
                                });          
            });    

        },

        edit(index) {
            this.$store.commit('SET_TASKNAME', this.tasks[index].taskname)
            this.$store.commit('SET_BEGINFILE', this.tasks[index].beginFile)
            this.$store.commit('SET_ENDFILE', this.tasks[index].endFile)
            console.log(this.$store.getters.taskname)
            this.$router.push({name: 'editor'})
        },

        run(index) {
            const params = {
                username: this.$store.getters.name,
                taskname: this.tasks[index].taskname
            }

            runTask(params).then(response => {
                this.$message("任务提交完毕");
                this.getUserTasks();
            })
        },

        stop(index){
                this.$confirm('终止任务?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {
                        const params = {
                            username: this.$store.getters.name,
                            taskname: this.tasks[index].taskname
                        }

                        stopTask(params).then(response => {
                            this.$message("任务终止");
                            this.getUserTasks();
                        })   

                    }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });          
                });                                 
        },

        isRunning(index) {
            const state = this.tasks[index].runTaskInfo.taskState
            const flag = (state == "RUNNING")
            return flag
        },      

        state_color(state){
            if(state=='SUCCESS'){
                return 'color:#67C23A'
            }
            else if(state == "RUNNING"){
                return 'color:#E6A23C'
            }
            else if(state == "FAILED"){
                return 'color:#F56C6C'
            }else{
                return 'color:#909399'

            }
        },
    }
}
</script>

<style>
    .time {
        font-size: 13px;
        color: #303133;
    }

    .state {
        font-size: 13px;
    }

    .info {
        margin-top: 13px;
        line-height: 12px;
    }

    .bottom {
    margin-top: 13px;
    line-height: 12px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .el-input {
        width: 300px;
    }

    .divInfo {
        white-space: pre-line;
    }

    .p {
        white-space: pre-line;

    }

 
</style>
