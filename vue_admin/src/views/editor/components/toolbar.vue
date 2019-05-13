<template>
    <div id="toolbar">
        <i title="撤销">
            <svg-icon data-command="undo" class="command" icon-class="undo"/>
        </i>    
        <i title="重做">
            <svg-icon data-command="redo" class="command" icon-class="redo"/>
        </i>
        <span class="separator"/>
        <i  title="复制">
            <svg-icon data-command="copy" class="command" icon-class="file-copy"/>
        </i>
        <i title="粘贴">
            <svg-icon data-command="paste" class="command" icon-class="paste"/>
        </i>           
        <i title="删除">
            <svg-icon data-command="delete" class="command" icon-class="delete"/>
        </i>    
        <span class="separator"/>
        <i title="放大">
            <svg-icon data-command="zoomIn" class="command" icon-class="zoom in"/>
        </i>    
        <i title="缩小">
            <svg-icon data-command="zoomOut" class="command" icon-class="zoom out"/>
        </i>     
        <i title="适应画布">
            <svg-icon data-command="autoZoom" class="command" icon-class="fit"/>
        </i>    
        <i title="实际尺寸">
            <svg-icon data-command="resetZoom" class="command" icon-class="reset"/>
        </i>
        <span class="separator"/>
        <el-radio-group
            v-model="lineType"
            size="mini" >
            <el-radio-button label="line" title="虚线">
                <svg-icon icon-class="dotted_line" />
            </el-radio-button>
            <el-radio-button label="flow-smooth" title="曲线">
                <svg-icon icon-class="curve"/>
            </el-radio-button>
            <el-radio-button label="flow-polyline-round" title="折线">
                <svg-icon icon-class="polyline"/>
            </el-radio-button>
        </el-radio-group>
        <span class="separator"/>
        任务名:{{taskname.length == 0 ? "当前任务为空": taskname}} |
        <el-select size="small" v-model="curBeginFile" placeholder="输入文件" > 
            <el-option
                v-for="file in files"
                :key="file.name"
                :label="file.name"
                :value="file.name"
            >
            </el-option>
        </el-select>
        <el-input size="small" placeholder="输出文件名" v-model="curEndFile" class="input-with-select" value="curEndFile">
        </el-input>
        <el-input size="small" placeholder="新任务名" v-model="new_taskname" class="input-with-select">
        </el-input>
        <el-button size="small" @click="save_data">保存图</el-button>
    </div>
</template>

<script>
import {getFileInfos} from '@/api/file'

export default {

    data() {
        return {
            name: 'toolbar',
            lineType: 'line',
            taskname: '',
            curBeginFile: '',
            curEndFile: '',
            new_taskname:'',
            files: [],
        };
    },

    mounted() {
        this.taskname = this.$store.getters.taskname;
        this.fetchFiles();
        this.curBeginFile = this.$store.getters.beginFile;
        this.curEndFile = this.$store.getters.endFile;

    },

    watch: {
        lineType(value) {
            this.$emit('change-eage', value);
        }
    },

    methods: {
        fetchFiles() {
            const params = {
                username: this.$store.getters.name,
                type: "data"
            }
            getFileInfos(params).then(response =>{
                this.files = response.data
                console.log(this.files)
            })
        },

        save_data() {
            console.log(this.new_taskname.length)
            if(this.taskname.length==0 && this.new_taskname.lengt== 0){
                    this.$message.info("任务名不能为空");
            }else{
                if(this.new_taskname.length == 0){
                    this.$message.info("保存至任务"+this.taskname);
                    this.$emit('save',this.taskname, this.curBeginFile, this.curEndFile);
                }else{
                    this.$message.info("保存至新任务"+this.new_taskname);
                    this.$emit('save',this.new_taskname, this.curBeginFile, this.curEndFile);
                }
                this.$confirm('回到任务管理界面?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                                }).then(() => {
                                    this.$message("跳转成功");
                                    this.$router.push({path:'/taskManage/index'});
                                }).catch(() => {
                                this.$message({
                                    type: 'info',
                                    message: '继续编辑'
                                });          
                    });                 
            }
        }


    }

    
};
</script>
<style lang="scss">
#toolbar{
  padding: 8px 0px;
  width: 100%;
  border: 1px solid #E9E9E9;
  height: 42px;
  z-index: 3;
  box-shadow: 0px 8px 12px 0px rgba(0, 52, 107, 0.04);
  position: absolute;
  text-align: left;
}
#toolbar *::before{
  font-size: 16px !important;
}
#toolbar .disable{
  color: rgba(0,0,0,0.25);
}
#toolbar .icon-select.disable{
  background: #EEEEEE;
}
#toolbar .separator{
  margin: 4px;
  border-left: 1px solid #E9E9E9;
}
#toolbar .command{
  width: 27px;
  height: 27px;
  margin: 0px 6px;
  border-radius: 2px;
  padding-left: 4px;
  display: inline-block;
  border: 1px solid rgba(2,2,2,0);
}


#toolbar .command:nth-of-type(1){
  margin-left: 24px;
}
#toolbar .command:hover{
  cursor: pointer;
  border: 1px solid #E9E9E9;
}
#toolbar .disable:hover{
  cursor: default;
  border: 1px solid rgba(2,2,2,0);
}
#toolbar {
    .el-radio-group{
        font-size: 0 !important;
    }
}

#toolbar {
    .el-button{
        text-align: center;
    }
}

#toolbar .el-select{
    width: 100px;
}

#toolbar .el-input{
    width: 100px;

}


</style>
