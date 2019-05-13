<template>
<el-container>
  <el-header>
    <h2>概况</h2>
  </el-header>
<el-main>
<el-tabs tab-position="left" style="height: 300px;">
    <el-tab-pane label="个人信息">
      <div>
      <h5>用户姓名：{{name}}</h5>
      <h5 v-if="roles[0] == 'ROLE_ADMIN'" > 用户权限: 管理员 </h5>
      <h5 v-else> 用户权限:普通用户 </h5>  
      </div>    
    </el-tab-pane>
    <el-tab-pane label="文件信息">
        <el-table :data="all_files"
          max-height="500"
        :default-sort = "{prop: 'name', order: 'descending'}"
        >
          <el-table-column prop="name" label="文件名" align="center" sortable></el-table-column>  
          <el-table-column prop="type" label="文件类型"  align="center" sortable></el-table-column>  
          <el-table-column prop="time" label="上次修改时间" align="center" sortable></el-table-column>  
        </el-table>        
    </el-tab-pane>
    <el-tab-pane label="任务信息">
        <el-table :data="tasks"
        :default-sort = "{prop: 'taskname', order: 'descending'}"
          max-height="400"
        :row-class-name="tableRowClassName" 
        >
          <el-table-column prop="taskname" label="任务名" align="center" sortable></el-table-column>  
          <el-table-column prop="taskID" label="任务ID" align="center" sortable></el-table-column>  
          <el-table-column prop="startTime" label="任务开始运行时间" align="center" sortable></el-table-column>  
          <el-table-column prop="endTime" label="任务结束运行时间" align="center" sortable></el-table-column>  
          <el-table-column prop="state" label="任务状态" align="center" sortable>
            <template slot-scope="scope">
              <el-tag
                :type="show_state(scope)"
                disable-transitions>{{scope.row.state}}
              </el-tag>
            </template>
          </el-table-column>  
        </el-table>
    </el-tab-pane>
  </el-tabs>  
  </el-main>

</el-container>  
</template>

<script>
import { mapGetters } from 'vuex'
import {getFileInfos} from '@/api/file'
import {getTasks} from '@/api/userTask'

export default {
  name: 'Dashboard',

  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },

  data() {
    return {
        tasks:[],
        all_files:[]
    }
  },

  created() {
    this.fetchData('data')
    this.fetchModel('model')
    this.getUserTasks()
  },
  
  methods: {
        fetchData() {
            const params = {
                username: this.$store.getters.name,
                type: 'data'
            }
            getFileInfos(params).then(response =>{
              const data_files = response.data
              for(let i = 0; i< data_files.length;i++){
                let file_info = {}
                file_info['name'] = data_files[i].name
                file_info['time'] = data_files[i].time
                file_info['type'] = '数据文件'
                this.all_files.push(file_info)
              }              
            })
        },
        fetchModel() {
            const params = {
                username: this.$store.getters.name,
                type: 'model'
            }
            getFileInfos(params).then(response =>{
              const models = response.data
              for(let i = 0; i< models.length;i++){
                let file_info = {}
                file_info['name'] = models[i].name
                file_info['time'] = models[i].time
                file_info['type'] = '模型文件'
                this.all_files.push(file_info)  
              }
            })
        },
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
                    let task = {}
                    task['taskname'] = task_json.taskname
                    task['state'] = task_json.runTaskInfo.taskState
                    task['startTime'] = task_json.runTaskInfo.startTime
                    task['endTime'] = task_json.runTaskInfo.endTime
                    task['taskID'] = task_json.runTaskInfo.taskId
                    this.tasks.push(task)
                }
                console.log(this.tasks)
            })
                       
        },

        show_state(scope){
          if(scope.row.state == 'SUCCESS'){
             return 'success'
          }
          else if(scope.row.state == 'FAILED'){
            return 'danger'
          }
          return 'info'

        },

  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 100px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

  .el-header, .el-footer {
    //background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
    .el-aside {
    //background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 20px;
  }
  
</style>
