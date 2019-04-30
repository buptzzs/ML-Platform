<template>
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
                <el-col :span="5" v-for="(task, index) in tasks" :key="task.name" :offset="index % 4 == 0 ? 0 : 1">
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
                            <div class="state" style="color:#F56C6C">状态: {{task.runTaskInfo.taskState}}</div>
                        </div>                        
                        <el-row class="bottom clearfix">
                            <el-button  type="text" icon="el-icon-info" @click="selectLog(index)">日志</el-button>
                            <el-button type="text" icon="el-icon-setting"  @click="edit(index)" :disabled="isDisable(index)">
                            配置</el-button>
                            <el-button type="text" icon="el-icon-caret-right" @click="run(index)" :disabled="isDisable(index)">运行</el-button>
                            <el-button type="text" icon="el-icon-delete" @click="deleteUserTask(index)" :disabled="isDisable(index)"></el-button>                            
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>
        </el-main>
        <el-dialog title="日志信息" :visible.sync="logVisible"> 
            <el-collapse v-model="activeName" :data="curLog" accordion>
                <el-collapse-item title="运行结果" name="1">
                    <div class="divInfo">{{curLog.success == false ? '失败':'成功'}}</div>
                </el-collapse-item>
                <el-collapse-item title="运行信息" name="2">
                    <div class="divInfo">{{curLog.runLog}}</div>
                </el-collapse-item>
                <el-collapse-item title="错误信息" name="3">
                    <div class="divInfo">{{curLog.errorLog}}</div>
                </el-collapse-item>
            </el-collapse>
        </el-dialog>
    </el-container>

</template>

<script>
import {getTasks, addTask, deleteTask, runTask} from '@/api/userTask'


export default {
    data() {
        return {
            logVisible: false,
            curLog: {},
            tasks: [],
            activeName: '1',
            input: '',
        }
    },

    created() {
        this.getUserTasks()
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

            deleteTask(params).then(response => {
                this.$message("删除任务成功");
                this.getUserTasks();
            })

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

        isDisable(index) {
            const state = this.tasks[index].runTaskInfo.taskState
            const flag = (state == "RUNNING")
            return flag
        }
    }
}
</script>

<style>
    .time {
        font-size: 13px;
        color: #999;
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
</style>
